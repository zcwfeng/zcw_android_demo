//
// Created by 张传伟 on 2021/4/19.
//
#include <android/log.h>
#include <jni.h>
#ifndef ANDROID_JNI_COMMON_HEAD_H
#define ANDROID_JNI_COMMON_HEAD_H
#define LOG_TAG "native_zcw"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#endif //ANDROID_JNI_COMMON_HEAD_H
