//
// Created by Administrator on 2019/11/6.
//

#ifndef ENJOYMMKV_MMKV_H
#define ENJOYMMKV_MMKV_H

#include <string>
#include <unordered_map>
#include "InputBuffer.h"
#include "OutputBuffer.h"
#include "ThreadLock.h"
#include "InterProcessLock.h"
#include "ScopedLock.h"
#include "MmapedFile.h"
#include "MMKVMetaInfo.hpp"


using namespace std;

enum MMKVMode : uint32_t {
    MMKV_SINGLE_PROCESS = 0x1,
    MMKV_MULTI_PROCESS = 0x2,
};

class MMKV {
public:
    MMKV(const string &mmapID, MMKVMode mode);
    ~MMKV();
    void close();
public:
    static void initializeMMKV(const char *path);

    static MMKV *defaultMMKV(MMKVMode mode);

    static MMKV *mmkvWithID(const string &mmapID,MMKVMode mode);

    int32_t getInt(const char *key, int defaultValue);

    void putInt(const char *key, int value);


    void appendDataWithKey(string key, InputBuffer *value);



private:
    void loadFromFile();

private:
    string m_mmapID;
    string m_path;// mmkv持久化文件地址
    string m_crcPath; //crc文件地址
    MmapedFile m_metaFile;
    FileLock m_fileLock; //文件锁
    ThreadLock m_lock; //线程互斥锁
    InterProcessLock m_sharedProcessLock;// 读锁
    InterProcessLock m_exclusiveProcessLock;//写锁
    bool m_isInterProcess;//是否多进程模式
    uint32_t m_crcDigest; //crc数据
    int m_fd; //打开的文件句柄
    size_t m_size; //文件大小
    int8_t *m_ptr; //映射的数据区
    size_t m_actualSize; //有效数据长度
    MMKVMetaInfo m_metaInfo;
    //记录的key-value
    std::unordered_map<std::string, InputBuffer *> m_dic;

    //操作映射区（文件） 的类
    OutputBuffer *m_output;

    void partialLoadFromFile();
    void clearMemoryState();
    void checkLoadData();
    void writeAcutalSize(size_t size);
    void recaculateCRCDigest();
    void updateCRCDigest(const uint8_t *ptr, size_t length, bool increaseSequence);
};


#endif //ENJOYMMKV_MMKV_H
