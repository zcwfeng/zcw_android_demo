//
// Created by 张传伟 on 2020/10/14.
//

#ifndef ANDROID_GIFLIB_JAVAINPUTSTREAM_H
#define ANDROID_GIFLIB_JAVAINPUTSTREAM_H
#include <jni.h>

class JavaInputStream {
public:
    JavaInputStream(JNIEnv* env,jobject inputStream,jbyteArray byteArray);

    JavaInputStream(JNIEnv *mEnv, const _jobject *mInputStream, const _jbyteArray *mByteArray,
                    const size_t mByteArrayLength);

    size_t read(void* buffer,size_t size);

private:
    JNIEnv *mEnv;
    const jobject mInputStream;
    const jbyteArray mByteArray;
    const size_t mByteArrayLength;
public:
    static jmethodID readMethodId;
};


#endif //ANDROID_GIFLIB_JAVAINPUTSTREAM_H
