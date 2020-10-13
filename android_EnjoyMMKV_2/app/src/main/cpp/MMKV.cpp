//
// Created by Administrator on 2019/11/6.
//


#include "MMKV.h"
#include "MMKVLog.h"
#include "PBUtility.h"
#include <string>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include <zlib.h>
#include <errno.h>


static unordered_map<string, MMKV *> *g_instanceDic = 0;
static string g_rootDir;
//多线程下创建mmkv的锁
static ThreadLock g_instanceLock;

//默认的mmkv文件
#define DEFAULT_MMAP_ID "mmkv.default"


const int32_t Fixed32Size = 4;

void MMKV::initializeMMKV(const char *path) {
    g_instanceDic = new unordered_map<string, MMKV *>;
    g_instanceLock = ThreadLock();
    g_rootDir = path;
    //创建目录
    mkdir(g_rootDir.c_str(), 0777);
}

MMKV *MMKV::defaultMMKV(MMKVMode mode) {
    return mmkvWithID(DEFAULT_MMAP_ID, mode);
}

MMKV *MMKV::mmkvWithID(const string &mmapID, MMKVMode mode) {
    SCOPEDLOCK(g_instanceLock); //__scopedLock0

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

MMKV::MMKV(const string &mmapID, MMKVMode mode) :
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

//todo 不再使用，请调用close方法(自己编写jni方法，通过jni调用)
void MMKV::close() {
    SCOPEDLOCK(g_instanceLock);
    auto itr = g_instanceDic->find(m_mmapID);
    if (itr != g_instanceDic->end()) {
        g_instanceDic->erase(itr);
    }
    delete this;
}

void MMKV::loadFromFile() {
    if (m_metaFile.isFileValid()) {
        m_metaInfo.read(m_metaFile.getMemory());
    }

    /*----------- PART 1 : 打开文件 -----------*/
    m_fd = open(m_path.c_str(), O_RDWR | O_CREAT, S_IRWXU);

    if (m_fd < 0) {
        //打開失敗
        LOGI("打开文件:%s 失败！", m_path.c_str());
    }
    //读取文件大小
    struct stat st = {0};
    if (fstat(m_fd, &st) != -1) {
        m_size = st.st_size;
    }
    LOGI("打开文件:%s [%d]", m_path.c_str(), m_size);

    /**
     * 健壮性。 文件是否已存在，容量是否满足页大小倍数
     */
    /*----------- PART 2 : 读取有效数据长度 -----------*/
    m_ptr = static_cast<int8_t *>(mmap(0, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd, 0));

    //文件头4个字节写了数据有效区长度
    memcpy(&m_actualSize, m_ptr, Fixed32Size);

    bool loadFromFile = false;
    //有数据
    if (m_actualSize > 0) {
        //数据长度有效：不能比文件还大
        if (m_actualSize + Fixed32Size <= m_size) {
            loadFromFile = true;
        }
        //其他情况，MMKV是交给用户选择
        // 1、OnErrorDiscard 忽略错误，MMKV会忽略文件中原来的内容
        // 2、OnErrorRecover 还原，MMKV尝试按照自己的方式解析文件，并修正长度
    }

    /**
     * 解析 mmkv 文件中的数据 保存到 map集合中
     */

    /*----------- PART 3 : 解析K-V -----------*/

    if (loadFromFile) {
        // 封装的protobuf解析器
        InputBuffer inputBuffer(m_ptr + Fixed32Size, m_actualSize);
        while (!inputBuffer.isAtEnd()) {
            //
            string key = inputBuffer.readString();
            if (key.length() > 0) {
                //读取value（包含value长度+value数据）
                InputBuffer *value = inputBuffer.readData();
//                unordered_map<string,InputBuffer*>::iterator iter;
                auto iter = m_dic.find(key);
                // 集合中找到了老数据
                if (iter != m_dic.end()) {
                    //清理老数据
                    delete iter->second;
                    // java-> map.remove
                    m_dic.erase(key);
                }

                //本次数据有效，加入集合
                if (value && value->length() > 0) {
                    // java-> map.insert
                    m_dic.emplace(key, value);
                }
            }
        }
        //创建输出
        m_output = new OutputBuffer(m_ptr + Fixed32Size + m_actualSize,
                                    m_size - Fixed32Size - m_actualSize);
    } else {
        //todo 文件有问题，忽略文件已存在的数据
        //crc文件写锁
        SCOPEDLOCK(m_exclusiveProcessLock);
        if (m_actualSize > 0) {
            writeAcutalSize(0);
        }

        //创建输出,忽略原数据
        m_output = new OutputBuffer(m_ptr + Fixed32Size,
                                    m_size - Fixed32Size);
        //重新弄 crc文件
        recaculateCRCDigest();
    }
}

int32_t MMKV::getInt(const char *key, int defaultValue) {
    SCOPEDLOCK(m_lock);
    checkLoadData();
    auto itr = m_dic.find(key);
    // 找到了
    if (itr != m_dic.end()) {
        // 获得value-> 解码器
        InputBuffer *buf = itr->second;
        int32_t returnValue = buf->readInt32();
        //多次读取，将position还原为0
        buf->restore();
        return returnValue;
    }
    return defaultValue;
}

void MMKV::putInt(const char *key, int value) {
    size_t size = computeInt32Size(value);
    //编码value
    auto *buffer = new InputBuffer(size);
    // 编码！
    OutputBuffer buf(buffer->data(), buffer->length());
    buf.writeInt32(value);

    //线程锁与进程写锁
    SCOPEDLOCK(m_lock);
    //这里加写锁的目的是为了防止其他进程还在写入数据，阻塞到其他进程数据写完，再检查数据。
    SCOPEDLOCK(m_exclusiveProcessLock);
    checkLoadData();

    //记录到内存
    auto itr = m_dic.find(key);
    if (itr != m_dic.end()) {
        delete itr->second;
    }
    m_dic[key] = buffer;
    //同步到映射区(文件)
    appendDataWithKey(key, buffer);
}

void MMKV::appendDataWithKey(string key, InputBuffer *value) {
    //计算保存这个key-value需要多少字节
    size_t itemSize = computeItemSize(key, value);
    //新加入数据，无论是去重、扩容还是增量，都会修改crc文件，加上写锁
    // 这里加不加写锁都无所谓，反正只在putInt中调用这个方法，putInt中加了写锁了的。
    SCOPEDLOCK(m_exclusiveProcessLock);
    // 空闲空间不够了
    if (itemSize > m_output->spaceLeft()) {
        // 计算去重key后的数据 所需的存储空间
        size_t needSize = computeMapSize(m_dic);
        needSize += Fixed32Size; //总长度 4字节
        //小于文件大小
        if (needSize >= m_size) {
            int32_t oldSize = m_size;
            do {
                //扩充一倍  为什么？？？ mmap规则限制：整数倍
                m_size *= 2;
            } while (needSize >= m_size);
            //重新设定文件大小
            ftruncate(m_fd, m_size);
            //解除映射
            munmap(m_ptr, oldSize);
            //重新映射
            m_ptr = (int8_t *) mmap(m_ptr, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd, 0);
        }
        //全量更新
        writeAcutalSize(needSize - Fixed32Size);
        delete m_output;
        //创建输出
        m_output = new OutputBuffer(m_ptr + Fixed32Size,
                                    m_size - Fixed32Size);
        //把map写入文件
        auto iter = m_dic.begin();
        for (; iter != m_dic.end(); iter++) {
            auto k = iter->first;
            auto v = iter->second;
            m_output->writeString(k);
            m_output->writeData(v);
        }
        recaculateCRCDigest();
    } else {
        //增量更新
        writeAcutalSize(m_actualSize + itemSize);
        //写入key
        m_output->writeString(key);
        //写入value
        m_output->writeData(value);

        auto ptr = (uint8_t *) m_ptr + Fixed32Size + m_actualSize - itemSize;
        updateCRCDigest(ptr, itemSize, 0);
    }
}

void MMKV::writeAcutalSize(size_t size) {
    memcpy(m_ptr, &size, Fixed32Size);
    m_actualSize = size;
}

void MMKV::checkLoadData() {
    if (!m_isInterProcess) {
        return;
    }


    SCOPEDLOCK(m_sharedProcessLock);

    MMKVMetaInfo metaInfo;
    metaInfo.read(m_metaFile.getMemory());
    //本次读取和记录的不同
    if (m_metaInfo.m_sequence != metaInfo.m_sequence) {
        //内存重整，序列号递增
        // 当一个进程发现内存被重整了，就意味着原写指针前面的键值全部失效，那么最简单的做法是全部抛弃掉，从头开始重新加载一遍。
        LOGI("checkData:序列号改变");

        clearMemoryState();
        loadFromFile();
    } else if (m_metaInfo.m_crcDigest != metaInfo.m_crcDigest) {
        LOGI("checkData:校验码改变");

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
                InputBuffer inputBuffer(m_ptr + Fixed32Size + oldActualSize, bufferSize);
                // 追加内容后的校验码
                m_crcDigest = (uint32_t) crc32(m_crcDigest,
                                               reinterpret_cast<const Bytef *>(inputBuffer.getBuf()),
                                               bufferSize);

                if (m_crcDigest == m_metaInfo.m_crcDigest) {
                    //将文件内容解析为map
                    while (!inputBuffer.isAtEnd()) {
                        std::string key = inputBuffer.readString(); // 读取key
                        if (key.length() > 0) {
                            InputBuffer *value = inputBuffer.readData(); //读取value
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

