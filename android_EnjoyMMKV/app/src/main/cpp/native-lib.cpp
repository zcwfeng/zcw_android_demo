#include <jni.h>
#include <string>
#include "MMKV.h"

extern "C"
JNIEXPORT void JNICALL
Java_com_enjoy_enjoymmkv_MMKV_jniInitialize(JNIEnv *env, jclass thiz, jstring _path) {
    const char *path = env->GetStringUTFChars(_path, 0);

    MMKV::initializeMMKV(path);

    env->ReleaseStringUTFChars(_path, path);
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_enjoy_enjoymmkv_MMKV_getDefaultMMKV(JNIEnv *env, jclass clazz) {
    MMKV *kv = MMKV::defaultMMKV();
    return reinterpret_cast<jlong>(kv);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_enjoy_enjoymmkv_MMKV_getInt(JNIEnv *env, jclass clazz, jlong native_handle, jstring _key,
                                     jint value) {
    const char *key = env->GetStringUTFChars(_key, 0);
    MMKV *kv = reinterpret_cast<MMKV *>(native_handle);
    int32_t  returnValue = kv->getInt(key,value);
    env->ReleaseStringUTFChars(_key, key);
    return returnValue;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_enjoy_enjoymmkv_MMKV_putInt(JNIEnv *env, jclass clazz, jlong native_handle, jstring _key,jint value) {
    const char *key = env->GetStringUTFChars(_key, 0);
    MMKV *kv = reinterpret_cast<MMKV *>(native_handle);
    kv->putInt(key,value);
    env->ReleaseStringUTFChars(_key, key);
}

