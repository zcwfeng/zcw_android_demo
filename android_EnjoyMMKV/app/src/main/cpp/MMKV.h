//
// Created by Administrator on 2019/11/6.
//

#ifndef ENJOYMMKV_MMKV_H
#define ENJOYMMKV_MMKV_H

#include <string>
#include <unordered_map>
#include "InputBuffer.h"
#include "OutputBuffer.h"

using namespace std;

class MMKV {
public:
    MMKV(const string &mmapID);
    ~MMKV();
    void close();
public:
    static void initializeMMKV(const char *path);

    static MMKV *defaultMMKV();

    static MMKV *mmkvWithID(const string &mmapID);

    int32_t getInt(const char *key, int defaultValue);

    void putInt(const char *key, int value);


    void appendDataWithKey(string key, InputBuffer *value);



private:
    void loadFromFile();

private:
    string m_mmapID;
    string m_path;// mmkv持久化文件地址
    int m_fd; //打开的文件句柄
    size_t m_size; //文件大小
    int8_t *m_ptr; //映射的数据区
    size_t m_actualSize; //有效数据长度

    //记录的key-value
    std::unordered_map<std::string, InputBuffer *> m_dic;

    //操作映射区（文件） 的类
    OutputBuffer *m_output;

    void writeAcutalSize(size_t size);
};


#endif //ENJOYMMKV_MMKV_H
