//
// Created by 张传伟 on 2020/10/14.
//

#include "JavaInputStream.h"

#define min(a, b) a<b ? a:b
jmethodID JavaInputStream::readMethodId = 0;

JavaInputStream::JavaInputStream(JNIEnv *env, jobject inputStream, jbyteArray byteArray) :
        mEnv(env),
        mInputStream(inputStream),
        mByteArray(mByteArray),
        mByteArrayLength(mEnv->GetArrayLength(byteArray)) {

}


size_t JavaInputStream::read(void *buffer, size_t size) {
    //读取的 总数据的大小
    size_t totalBytesRead = 0;
    do {
        //获取size的最小值
        size_t minSize = min(size, mByteArrayLength);
        jint byteRead = mEnv->CallByteMethod(mInputStream, readMethodId, mByteArray, 0, minSize);
        //捕获异常
        if(mEnv->ExceptionCheck()|| byteRead < 0) {
            mEnv->ExceptionClear();
            return 0;
        }
        //将读取的数据放到buffer
        mEnv->GetByteArrayRegion(mByteArray, 0, byteRead, static_cast<jbyte *>(buffer));
        //下次读取 从buffer的bytesRead出开始保存
        buffer = (char *)buffer + byteRead;
        size -= byteRead;
        totalBytesRead += byteRead;

    } while (size > 0);
    return totalBytesRead;
}
