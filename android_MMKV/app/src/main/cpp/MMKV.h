/**
 * @author Lance
 * @date 2019/1/23
 */

#ifndef MMKV_MMKV_H
#define MMKV_MMKV_H

#include "ProtoBuf.h"
#include "ThreadLock.h"
#include "InterProcessLock.h"
#include "MmapedFile.h"
#include "MMKVMetaInfo.hpp"

enum MMKVMode : uint32_t {
    MMKV_SINGLE_PROCESS = 0x1,
    MMKV_MULTI_PROCESS = 0x2,
};


class MMKV {

public:
    MMKV(const std::string &mmapID, MMKVMode mode);

    ~MMKV();

    static void initializeMMKV(const char *path);

    static MMKV *defaultMMKV(MMKVMode mode);

    static MMKV *mmkvWithID(const std::string &mmapID, MMKVMode mode);

    void close();

public:

    void putInt(const char *key, int32_t value);

    int32_t getInt(const char *key, int32_t defaultValue);

    void putFloat(const char *key, float value);

    float getFloat(const char *key, float defaultValue);

    void putLong(const char *key, int64_t value);

    int64_t getLong(const char *key, int64_t defaultValue);

private:
    void loadFromFile();

    void writeAcutalSize(size_t actualSize);

    bool checkFileCRCValid();

    void recaculateCRCDigest();

    void updateCRCDigest(const uint8_t *ptr, size_t length, bool increaseSequence);

    void setDataForKey(const char *key, ProtoBuf *value);

    void checkLoadData();

    void clearMemoryState();

    void partialLoadFromFile();

    void appendDataWithKey(std::string key, ProtoBuf *value);


private:

    // mmkv的id
    std::string m_mmapID;
    //文件路径
    std::string m_path;
    //crc文件路径
    std::string m_crcPath;

    //用于crc校验的文件（记录了真实数据文件的变更等信息）
    MmapedFile m_metaFile;
    //读取m_metaFile
    MMKVMetaInfo m_metaInfo;
    uint32_t m_crcDigest;

    //锁
    ThreadLock m_lock; //线程互斥锁
    FileLock m_fileLock; //crc文件锁
    InterProcessLock m_sharedProcessLock; //读锁
    InterProcessLock m_exclusiveProcessLock;//写锁
    const bool m_isInterProcess; //是否多进程



    //文件句柄
    int m_fd;
    //文件可写长度
    int32_t m_size;
    //文件内容数据
    int8_t *m_ptr;
    //已经使用的长度
    int32_t m_actualSize;
    //记录的key-value
    std::unordered_map<std::string, ProtoBuf *> m_dic;
    //写出管理类
    ProtoBuf *m_output;


};


#endif //MMKV_MMKV_H
