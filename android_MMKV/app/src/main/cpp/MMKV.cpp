/**
 * @author Lance
 * @date 2019/1/23
 */

#include <sys/stat.h>
#include <sys/mman.h>
#include <unistd.h>
#include <fcntl.h>
#include <zlib.h>
#include <errno.h>
#include "MMKV.h"
#include "ProtoBuf.h"
#include "MMKVLog.h"
#include "ScopedLock.h"



//默认的mmkv文件
#define DEFAULT_MMAP_ID "mmkv.default"

using namespace std;

//无序容器，查找速度快。存储所有创建的mmkv实例
static unordered_map<std::string, MMKV *> *g_instanceDic;
//多线程下创建mmkv的锁
static ThreadLock g_instanceLock;

const int32_t Fixed32Size = 4;


static string g_rootDir;


/**
 * 创建目录
 * @param path
 */
void MMKV::initializeMMKV(const char *path) {
    g_instanceDic = new unordered_map<std::string, MMKV *>;
    g_instanceLock = ThreadLock();

    g_rootDir = path;
    //创建目录 文件权限为可读可写
    mkdir(g_rootDir.c_str(), 0777);
}


MMKV *MMKV::defaultMMKV(MMKVMode mode) {
    return mmkvWithID(DEFAULT_MMAP_ID, mode);
}

MMKV *MMKV::mmkvWithID(const std::string &mmapID, MMKVMode mode) {
    SCOPEDLOCK(g_instanceLock);
    // unordered_map<string, MMKV *>::iterator
    auto itr = g_instanceDic->find(mmapID);
    if (itr != g_instanceDic->end()) {
        MMKV *kv = itr->second;
        return kv;
    }
    //创建并放入集合
    auto kv = new MMKV(mmapID, mode);
    (*g_instanceDic)[mmapID] = kv;
    return kv;
}


//注意参数定义的顺序
MMKV::MMKV(const std::string &mmapID, MMKVMode mode) :
        m_mmapID(mmapID),
        m_path(g_rootDir + "/" + mmapID),
        m_crcPath(m_path + ".crc"),
        m_metaFile(m_crcPath),
        m_fileLock(m_metaFile.getFd()),
        m_sharedProcessLock(&m_fileLock, SharedLockType),
        m_exclusiveProcessLock(&m_fileLock, ExclusiveLockType),
        m_isInterProcess((mode & MMKV_MULTI_PROCESS) != 0) {

    m_crcDigest = 0;
    m_sharedProcessLock.m_enable = m_isInterProcess;
    m_exclusiveProcessLock.m_enable = m_isInterProcess;
    //crc文件读锁
    SCOPEDLOCK(m_sharedProcessLock);
    loadFromFile();
}

MMKV::~MMKV() {
    delete m_output;
    m_output = 0;
    munmap(m_ptr, m_size);
    m_ptr = 0;
    ::close(m_fd);
    auto iter = m_dic.begin();
    while (iter != m_dic.end()) {
        delete iter->second;
        iter = m_dic.erase(iter);
    }
}

void MMKV::loadFromFile() {
    if (m_metaFile.isFileValid()) {
        m_metaInfo.read(m_metaFile.getMemory());
    }

    m_fd = open(m_path.c_str(), O_RDWR | O_CREAT, S_IRWXU);
    //    //读取文件大小页面
    struct stat st = {0};
    if (fstat(m_fd, &st) != -1) {
        m_size = st.st_size;
    }
    //不足1页 或者 不是刚好x页 补足整数页
    if (m_size < DEFAULT_MMAP_SIZE || (m_size % DEFAULT_MMAP_SIZE != 0)) {
        int32_t oldSize = m_size;
        m_size = ((m_size / DEFAULT_MMAP_SIZE) + 1) * DEFAULT_MMAP_SIZE;
        //修改文件大小
        if (ftruncate(m_fd, m_size) != 0) {
            m_size = st.st_size;
        }
        //如果文件大小被增加了， 让增加这些大小的内容变成空
        zeroFillFile(m_fd, oldSize, m_size - oldSize);
    }
    //将文件映射进内存
    //1、映射区的开始地址，设置为0时表示由系统决定映射区的起始地址
    //2、映射区的长度。长度单位是 以字节为单位，不足一内存页按一内存页处理
    //3、PROT_READ：内容可以被读取 + PROT_WRITE：可以写入
    //4、MAP_SHARED：共享映射空间。对共享区的写入，相当于输出到文件。
    //5、映射的文件
    //6、被映射对象内容的起点
    m_ptr = static_cast<int8_t *>(mmap(0, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd, 0));
    //mmap 成功,就可以关闭文件 不影响创建的映射区
    //close(m_fd); 但是后面需要操作文件


    //文件头4个字节写了数据有效区长度
    memcpy(&m_actualSize, m_ptr, Fixed32Size);
    bool loadFromFile = false;

    if (m_actualSize > 0) {
        //数据有效
        if (m_actualSize < m_size && m_actualSize + Fixed32Size <= m_size) {
            if (checkFileCRCValid()) {
                loadFromFile = true;
            } else {
                //MMKV默认选择丢弃错误。
            }
        } else {
            //MMKV默认也是选择丢弃错误。
        }
    }

    if (loadFromFile) {
        LOGI("loading [%s] with crc %u sequence %u", m_mmapID.c_str(),
             m_metaInfo.m_crcDigest, m_metaInfo.m_sequence);
        ProtoBuf inputBuffer(m_ptr + Fixed32Size, m_actualSize);
        m_dic.clear();
        //将文件内容解析为map
        while (!inputBuffer.isAtEnd()) {
            std::string key = inputBuffer.readString(); // 读取key
            if (key.length() > 0) {
                ProtoBuf *value = inputBuffer.readData(); //读取value
                //存在老数据,则先回收老数据
                auto oldBuf = m_dic.find(key);
                if (oldBuf != m_dic.end()) {
                    //清理内存
                    delete oldBuf->second;
                    m_dic.erase(key);
                }
                //数据有效
                if (value && value->length() > 0) {
                    m_dic.emplace(key, value);
                }
            }
        }
        //创建输出
        m_output = new ProtoBuf(m_ptr + Fixed32Size + m_actualSize,
                                m_size - Fixed32Size - m_actualSize);
    } else {
        //crc文件写锁
        SCOPEDLOCK(m_exclusiveProcessLock);
        if (m_actualSize > 0) {
            writeAcutalSize(0);
        }

        //创建输出,忽略原数据
        m_output = new ProtoBuf(m_ptr + Fixed32Size,
                                m_size - Fixed32Size);
        //重新弄 crc文件
        recaculateCRCDigest();
    }
}

void MMKV::writeAcutalSize(size_t actualSize) {
    assert(m_ptr != 0);
    assert(m_ptr != MAP_FAILED);

    memcpy(m_ptr, &actualSize, Fixed32Size);
    m_actualSize = actualSize;
}


void MMKV::clearMemoryState() {
    SCOPEDLOCK(m_lock);


    m_dic.clear();

    if (m_output) {
        delete m_output;
    }
    m_output = 0;

    if (m_ptr && m_ptr != MAP_FAILED) {
        if (munmap(m_ptr, m_size) != 0) {
            LOGE("fail to munmap [%s], %s", m_mmapID.c_str(), strerror(errno));
        }
    }
    m_ptr = 0;
    if (m_fd >= 0) {
        if (::close(m_fd) != 0) { //关闭文件
            LOGE("fail to close [%s], %s", m_mmapID.c_str(), strerror(errno));
        }
    }
    m_fd = -1;
    m_size = 0;
    m_actualSize = 0;
}


void MMKV::partialLoadFromFile() {
    m_metaInfo.read(m_metaFile.getMemory());
    size_t oldActualSize = m_actualSize;
    memcpy(&m_actualSize, m_ptr, Fixed32Size);

    if (m_actualSize > 0) {
        if (m_actualSize < m_size && m_actualSize + Fixed32Size <= m_size) {
            //增长（写了更多数据）
            if (m_actualSize > oldActualSize) {
                size_t bufferSize = m_actualSize - oldActualSize;
                //把新数据读出来
                ProtoBuf inputBuffer(m_ptr + Fixed32Size + oldActualSize, bufferSize);
                // 追加内容后的校验码
                m_crcDigest = (uint32_t) crc32(m_crcDigest,
                                               reinterpret_cast<const Bytef *>(inputBuffer.getBuf()),
                                               bufferSize);

                if (m_crcDigest == m_metaInfo.m_crcDigest) {
                    //将文件内容解析为map
                    while (!inputBuffer.isAtEnd()) {
                        std::string key = inputBuffer.readString(); // 读取key
                        if (key.length() > 0) {
                            ProtoBuf *value = inputBuffer.readData(); //读取value
                            //存在老数据,则先回收老数据
                            auto oldBuf = m_dic.find(key);
                            if (oldBuf != m_dic.end()) {
                                //清理内存
                                delete oldBuf->second;
                                m_dic.erase(key);
                            }
                            //数据有效
                            if (value && value->length() > 0) {
                                m_dic.emplace(key, value);
                            }
                        }
                    }
                    m_output->seek(bufferSize);
                    return;
                } else {
                    //wrong
                }
            }
        }
    }

    clearMemoryState();
    loadFromFile();
}


void MMKV::checkLoadData() {
    if (!m_isInterProcess) {
        return;
    }
    MMKVMetaInfo metaInfo;
    metaInfo.read(m_metaFile.getMemory());
    //本次读取和记录的不同
    if (m_metaInfo.m_sequence != metaInfo.m_sequence) {
        //内存重整，序列号递增
        // 当一个进程发现内存被重整了，就意味着原写指针前面的键值全部失效，那么最简单的做法是全部抛弃掉，从头开始重新加载一遍。
        LOGI("checkData:序列号改变");
        SCOPEDLOCK(m_sharedProcessLock);
        clearMemoryState();
        loadFromFile();
    } else if (m_metaInfo.m_crcDigest != metaInfo.m_crcDigest) {
        LOGI("checkData:校验码改变");
        SCOPEDLOCK(m_sharedProcessLock);
        size_t fileSize = 0;
        struct stat st = {0};
        if (fstat(m_fd, &st) != -1) {
            fileSize = (size_t) st.st_size;
        }
        if (m_size != fileSize) {
            // 发生文件增长，必然已经先发生了内存重整，与内存重整一样的处理
            LOGI("checkData:文件大小改变");
            clearMemoryState();
            loadFromFile();
        } else {
            LOGI("checkData:写指针增长");
            // 文件大小不变，可能写指针增长
            partialLoadFromFile();
        }
    }
}

void MMKV::setDataForKey(const char *key, ProtoBuf *value) {
    //线程锁与进程写锁
    SCOPEDLOCK(m_lock);
    SCOPEDLOCK(m_exclusiveProcessLock);  //这里加写锁的目的是为了防止其他进程还在写入数据，阻塞。
    checkLoadData();

    auto itr = m_dic.find(key);
    if (itr != m_dic.end()) {
        delete itr->second;
    }
    m_dic[key] = value;

    appendDataWithKey(key, value);
}


void MMKV::appendDataWithKey(std::string key, ProtoBuf *value) {
    //计算保存这个key-value需要多少字节
    int32_t itemSize = ProtoBuf::computeItemSize(key, value);

    SCOPEDLOCK(m_exclusiveProcessLock);//写锁

    // 空闲内存不够了
    if (itemSize > m_output->spaceLeft()) {

        // 计算总大小
        int32_t needSize = ProtoBuf::computeMapSize(m_dic);
        needSize += Fixed32Size;

        //计算每个item的平均长度
        int32_t avgItemSize = needSize / std::max<int32_t>(1, m_dic.size());
        //计算将来可能增加的用量 （最少增加8个）
        int32_t futureUsage = avgItemSize * std::max<int32_t>(8, (m_dic.size() + 1) / 2);
        //如果需要的大小比当前大 肯定要扩容，还有个条件就是 需要的大小加上未来可能增加的大小比当前大 也要扩容 不是一拿来就扩容，也是为了文件防止无限增大
        if (needSize >= m_size || (needSize + futureUsage) >= m_size) {
            //为了防止将来使用大小不够导致频繁重写，扩充一倍
            int32_t oldSize = m_size;
            do {
                //扩充一倍
                m_size *= 2;

            } while (needSize + futureUsage >= m_size); //如果在需要的与将来可能增加的加起来比扩容后还要大，继续扩容

            //重新设定文件大小
            ftruncate(m_fd, m_size);
            //清空文件
            //zeroFillFile(m_fd, oldSize, m_size-oldSize);
            //解除映射
            munmap(m_ptr, oldSize);
            //重新映射
            m_ptr = (int8_t *) mmap(m_ptr, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd, 0);
        }


        // 写入数据大小
        writeAcutalSize(needSize - Fixed32Size);
        delete m_output;
        //创建输出
        m_output = new ProtoBuf(m_ptr + Fixed32Size,
                                m_size - Fixed32Size);
        //写入数据
        auto iter = m_dic.begin();
        for (; iter != m_dic.end(); iter++) {
            auto k = iter->first;
            auto v = iter->second;
            m_output->writeString(k);
            m_output->writeData(v);
        }
        recaculateCRCDigest();
    } else {
        /**
         *  足够，直接append加入
         */
        //写入4个字节总长度
        writeAcutalSize(m_actualSize + itemSize);

        //写入key
        m_output->writeString(key);
        //写入value
        m_output->writeData(value);

        auto ptr = (uint8_t *) m_ptr + Fixed32Size + m_actualSize - itemSize;
        updateCRCDigest(ptr, itemSize, 0);
    }
}

void MMKV::putInt(const char *key, int32_t value) {
    int32_t size = ProtoBuf::computeInt32Size(value);
    ProtoBuf *buf = new ProtoBuf(size);
    buf->writeInt32(value);
    buf->restore();
    setDataForKey(key, buf);
}

int32_t MMKV::getInt(const char *key, int32_t defaultValue) {
    SCOPEDLOCK(m_lock);
    checkLoadData();
    auto itr = m_dic.find(key);
    if (itr != m_dic.end()) {
        ProtoBuf *buf = itr->second;
        int32_t returnValue = buf->readInt32();
        //多次读取，将position还原为0
        buf->restore();
        return returnValue;
    }
    return defaultValue;
}

void MMKV::putLong(const char *key, int64_t value) {
    int32_t size = ProtoBuf::computeInt64Size(value);
    ProtoBuf *buf = new ProtoBuf(size);
    buf->writeInt64(value);
    buf->restore();
    setDataForKey(key, buf);
}

int64_t MMKV::getLong(const char *key, int64_t defaultValue) {
    SCOPEDLOCK(m_lock);
    checkLoadData();
    auto itr = m_dic.find(key);
    if (itr != m_dic.end()) {
        ProtoBuf *buf = itr->second;
        int64_t returnValue = buf->readInt64();
        //多次读取，将position还原为0
        buf->restore();
        return returnValue;
    }
    return defaultValue;
}

void MMKV::putFloat(const char *key, float value) {

    //float 定长4字节
    ProtoBuf *buf = new ProtoBuf(4);
    buf->writeFloat(value);
    buf->restore();
    setDataForKey(key, buf);
}

float MMKV::getFloat(const char *key, float defaultValue) {
    SCOPEDLOCK(m_lock);
    checkLoadData();
    auto itr = m_dic.find(key);
    if (itr != m_dic.end()) {
        ProtoBuf *buf = itr->second;
        float returnValue = buf->readFloat();
        buf->restore();
        return returnValue;
    }
    return defaultValue;
}

void MMKV::close() {
    SCOPEDLOCK(g_instanceLock);
    SCOPEDLOCK(m_lock);
    auto itr = g_instanceDic->find(m_mmapID);
    if (itr != g_instanceDic->end()) {
        g_instanceDic->erase(itr);
    }
    delete this;
}


bool MMKV::checkFileCRCValid() {
    if (m_ptr && m_ptr != MAP_FAILED) {
        if (!m_metaFile.isFileValid()) {
            LOGE("Meta file not valid %s", m_mmapID.c_str());
            return false;
        }
        // 获得内容的crc校验
        m_crcDigest =
                (uint32_t) crc32(0, (const uint8_t *) m_ptr + Fixed32Size, (uint32_t) m_actualSize);
        // 计算出的内容的校验码与crc文件中记录的校验码匹配，校验成功
        m_metaInfo.read(m_metaFile.getMemory());
        if (m_crcDigest == m_metaInfo.m_crcDigest) {
            return true;
        }
        LOGE("check crc [%s] fail, crc32:%u, m_crcDigest:%u", m_mmapID.c_str(),
             m_metaInfo.m_crcDigest, m_crcDigest);
    }
    return false;
}

void MMKV::recaculateCRCDigest() {
    if (m_ptr && m_ptr != MAP_FAILED) {
        m_crcDigest = 0;
        updateCRCDigest((const uint8_t *) m_ptr + Fixed32Size, m_actualSize, 1);
    }
}

void MMKV::updateCRCDigest(const uint8_t *ptr, size_t length, bool increaseSequence) {
    if (!ptr || !m_metaFile.isFileValid()) {
        return;
    }
    //更新校验码
    m_crcDigest = (uint32_t) crc32(m_crcDigest, ptr, (uint32_t) length);

    m_metaInfo.m_crcDigest = m_crcDigest;
    if (increaseSequence) {
        m_metaInfo.m_sequence++;
    }
    if (m_metaInfo.m_version == 0) {
        m_metaInfo.m_version = 1;
    }
    auto crcPtr = m_metaFile.getMemory();
    m_metaInfo.write(crcPtr);
}





