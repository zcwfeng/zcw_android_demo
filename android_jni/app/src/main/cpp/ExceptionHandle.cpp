//
// Created by 张传伟 on 2021/4/22.
//
#include "common_head.h"

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_exception(JNIEnv *env, jclass clazz) {
    jfieldID f_id = env->GetStaticFieldID(clazz, "name999", "Ljava/lang/String;");

    jthrowable thr = env->ExceptionOccurred();
    if(thr){
        LOGE("exception() C++层有异常 监测到了");
        env->ExceptionClear();
        jfieldID f_id = env->GetFieldID(clazz, "name", "Ljava/lang/String;");
        LOGI("拿到 jfieldID f_id name");

    }
}

/**
 * native层主动抛出的异常
 */
extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_exception2(JNIEnv *env, jclass clazz) {

    jfieldID f_id = env->GetStaticFieldID(clazz, "name111", "Ljava/lang/String;");

    jthrowable thr = env->ExceptionOccurred();
    if(thr){
        LOGE("exception2() C++层有异常 监测到了");
        env->ExceptionClear();
        // Throw抛一个 Java的对象     java/lang/String    java/xxxxx/xxx/NullExxx
        jclass clazz = env->FindClass("java/lang/NoSuchFieldException");
        env->ThrowNew(clazz,"NoSuchFieldException,找不到name111 字段，发生错误");
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_exception3(JNIEnv *env, jclass clazz) {
    jmethodID m_id = env->GetStaticMethodID(clazz,"show","()V");
    env->CallStaticVoidMethod(clazz,m_id);

    if(env->ExceptionCheck()){
        env->ExceptionDescribe();
        env->ExceptionClear();
    }

    // ① 奔溃后，下面的语句，照样打印
    LOGI("C++层>>>>>>>>>>>>>>>>>>>>>>>>>>>>1");
    LOGI("C++层>>>>>>>>>>>>>>>>>>>>>>>>>>>>2");
    LOGI("C++层>>>>>>>>>>>>>>>>>>>>>>>>>>>>3");
    LOGI("C++层>>>>>>>>>>>>>>>>>>>>>>>>>>>>4");
    LOGI("C++层>>>>>>>>>>>>>>>>>>>>>>>>>>>>5");

    // ② 局部引用崩溃会抹掉
//    env->NewStringUTF("AAAA");

}