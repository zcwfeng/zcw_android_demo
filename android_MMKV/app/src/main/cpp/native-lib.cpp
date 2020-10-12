#include <jni.h>
#include "MMKV.h"


extern "C"
JNIEXPORT void JNICALL
Java_com_enjoy_mmkv_MMKV_jniInitialize(JNIEnv *env, jclass type, jstring rootDir_) {
    const char *rootDir = env->GetStringUTFChars(rootDir_, 0);

    MMKV::initializeMMKV(rootDir);

    env->ReleaseStringUTFChars(rootDir_, rootDir);
}


extern "C"
JNIEXPORT jlong JNICALL
Java_com_enjoy_mmkv_MMKV_getDefaultMMKV(JNIEnv *env, jclass type, jint mode) {
    MMKV *kv = MMKV::defaultMMKV(static_cast<MMKVMode>(mode));
    return reinterpret_cast<jlong>(kv);
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_enjoy_mmkv_MMKV_getMMKVWithID(JNIEnv *env, jclass type, jstring mmapID_, jint mode) {
    const char *mmapID = env->GetStringUTFChars(mmapID_, 0);

    MMKV *kv = MMKV::mmkvWithID(mmapID, static_cast<MMKVMode>(mode));
    env->ReleaseStringUTFChars(mmapID_, mmapID);

    return reinterpret_cast<jlong>(kv);
}



extern "C"
JNIEXPORT void JNICALL
Java_com_enjoy_mmkv_MMKV_putInt(JNIEnv *env, jobject instance, jlong handle,
                                jstring key_, jint value) {
    const char *key = env->GetStringUTFChars(key_, 0);
    MMKV *kv = reinterpret_cast<MMKV *>(handle);
    kv->putInt(key, value);
    env->ReleaseStringUTFChars(key_, key);
}



extern "C"
JNIEXPORT jint JNICALL
Java_com_enjoy_mmkv_MMKV_getInt(JNIEnv *env, jobject instance, jlong handle,
                                jstring key_, jint defaultValue) {
    const char *key = env->GetStringUTFChars(key_, 0);
    MMKV *kv = reinterpret_cast<MMKV *>(handle);
    int32_t returnValue = kv->getInt(key, defaultValue);

    env->ReleaseStringUTFChars(key_, key);
    return returnValue;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_enjoy_mmkv_MMKV_putLong__JLjava_lang_String_2J(JNIEnv *env, jobject instance,
                                                        jlong handle, jstring key_,
                                                        jlong value) {
    const char *key = env->GetStringUTFChars(key_, 0);

    MMKV *kv = reinterpret_cast<MMKV *>(handle);
    kv->putLong(key, value);
    env->ReleaseStringUTFChars(key_, key);
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_enjoy_mmkv_MMKV_getLong__JLjava_lang_String_2J(JNIEnv *env, jobject instance,
                                                        jlong handle, jstring key_,
                                                        jlong defaultValue) {
    const char *key = env->GetStringUTFChars(key_, 0);
    MMKV *kv = reinterpret_cast<MMKV *>(handle);
    int64_t returnValue = kv->getLong(key, defaultValue);

    env->ReleaseStringUTFChars(key_, key);
    return returnValue;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_enjoy_mmkv_MMKV_putFloat__JLjava_lang_String_2F(JNIEnv *env, jobject instance,
                                                         jlong handle, jstring key_, jfloat value) {
    const char *key = env->GetStringUTFChars(key_, 0);
    MMKV *kv = reinterpret_cast<MMKV *>(handle);
    kv->putFloat(key, value);
    env->ReleaseStringUTFChars(key_, key);
}


extern "C"
JNIEXPORT jfloat JNICALL
Java_com_enjoy_mmkv_MMKV_getFloat__JLjava_lang_String_2F(JNIEnv *env, jobject instance,
                                                         jlong handle, jstring key_,
                                                         jfloat defaultValue) {
    const char *key = env->GetStringUTFChars(key_, 0);
    MMKV *kv = reinterpret_cast<MMKV *>(handle);
    float returnValue = kv->getFloat(key, defaultValue);
    env->ReleaseStringUTFChars(key_, key);
    return returnValue;
}

