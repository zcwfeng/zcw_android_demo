//
// Created by 张传伟 on 2020/10/14.
//

#ifndef ANDROID_GIFLIB_LOG_H
#define ANDROID_GIFLIB_LOG_H
#include <android/log.h>
#define TAG "zcwfeng"
#define LOG_DEBUG true

#ifdef LOG_DEBUG
#define LOGI(...) \
        __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#else
#define LOGI(...)
#endif
#endif //ANDROID_GIFLIB_LOG_H
