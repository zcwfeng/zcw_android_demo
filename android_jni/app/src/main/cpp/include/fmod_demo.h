//
// Created by 张传伟 on 2021/4/16.
//

#include <fmod/fmod.hpp>
#include <string>
#include "common_head.h"

#ifndef ANDROID_JNI_FMOD_DEMO_H
#define ANDROID_JNI_FMOD_DEMO_H
/* Header for class top_zcwfeng_jni_FmodVoiceActivity */
#ifdef __cplusplus
extern "C" {
#endif
#undef top_zcwfeng_jni_FmodVoiceActivity_MODE_NORMAL
#define top_zcwfeng_jni_FmodVoiceActivity_MODE_NORMAL 0L
#undef top_zcwfeng_jni_FmodVoiceActivity_MODE_LUOLI
#define top_zcwfeng_jni_FmodVoiceActivity_MODE_LUOLI 1L
#undef top_zcwfeng_jni_FmodVoiceActivity_MODE_DASHU
#define top_zcwfeng_jni_FmodVoiceActivity_MODE_DASHU 2L
#undef top_zcwfeng_jni_FmodVoiceActivity_MODE_JINGSONG
#define top_zcwfeng_jni_FmodVoiceActivity_MODE_JINGSONG 3L
#undef top_zcwfeng_jni_FmodVoiceActivity_MODE_GAOGUAI
#define top_zcwfeng_jni_FmodVoiceActivity_MODE_GAOGUAI 4L
#undef top_zcwfeng_jni_FmodVoiceActivity_MODE_KONGLING
#define top_zcwfeng_jni_FmodVoiceActivity_MODE_KONGLING 5L

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_FmodVoiceActivity_voiceChangeNative(JNIEnv *env, jobject thiz, jint mode,
jstring path);

#ifdef __cplusplus
}
#endif
#endif //ANDROID_JNI_FMOD_DEMO_H
