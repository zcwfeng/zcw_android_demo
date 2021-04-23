//
// Created by 张传伟 on 2021/4/22.
//
#include "common_head.h"

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_localCache(JNIEnv *env, jclass clazz, jstring name) {
    // OpenCV 或者 WebRTC，大量使用静态缓存

    // 非静态缓存

    jfieldID f_id = nullptr;
    if (f_id == nullptr) {
        f_id = env->GetStaticFieldID(clazz, "name1", "Ljava/lang/String;");
    } else {
        LOGE("%s","jfieldID f_id--- 空的");
    }
    // 修改
    env->SetStaticObjectField(clazz, f_id, name);
    f_id = nullptr;
}

static jfieldID f_name1_id = nullptr;
static jfieldID f_name2_id = nullptr;
static jfieldID f_name3_id = nullptr;
static jfieldID f_name4_id = nullptr;
static jfieldID f_name5_id = nullptr;

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_initStaticCache(JNIEnv *env, jclass clazz) {
    f_name1_id = env->GetStaticFieldID(clazz,"name1", "Ljava/lang/String;");
    f_name2_id = env->GetStaticFieldID(clazz,"name2", "Ljava/lang/String;");
    f_name3_id = env->GetStaticFieldID(clazz,"name3", "Ljava/lang/String;");
    f_name4_id = env->GetStaticFieldID(clazz,"name4", "Ljava/lang/String;");
    f_name5_id = env->GetStaticFieldID(clazz,"name5", "Ljava/lang/String;");
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_staticCache(JNIEnv *env, jclass clazz, jstring name) {
    // 不会反复执行GetStaticFieldID
    env->SetStaticObjectField(clazz, f_name1_id,name);
    env->SetStaticObjectField(clazz, f_name2_id,name);
    env->SetStaticObjectField(clazz, f_name3_id,name);
    env->SetStaticObjectField(clazz, f_name4_id,name);
    env->SetStaticObjectField(clazz, f_name5_id,name);

}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_clearStaticCache(JNIEnv *env, jclass clazz) {
    f_name1_id = nullptr;
    f_name2_id = nullptr;
    f_name3_id = nullptr;
    f_name4_id = nullptr;
    f_name5_id = nullptr;
}