//
// Created by 张传伟 on 2021/4/16.
//

#include "fmod_demo.h"
#include <unistd.h>
using namespace FMOD;

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_FmodVoiceActivity_voiceChangeNative(JNIEnv *env, jobject thiz, jint mode,
                                                         jstring path) {
    const char * _path = env->GetStringUTFChars(path, NULL);
    LOGD("%s", _path);
    //Fmod 音效引擎
    System * system = 0;
    // fmod 声音
    Sound * sound = 0;
    // 通道 音轨
    Channel * channel = 0;
    // digital signal process 数字信号处理
    DSP * dsp = 0;
    char *_content = "默认：播放结束";
    bool isPlay = true;

    try {
        // ① 创建系统
        System_Create(&system);
        // ② 初始化
        system->init(32, FMOD_INIT_NORMAL, 0);
        // ③ 创建声音
        system->createSound(_path, FMOD_DEFAULT, 0, &sound);
        // ④ 播放声音
        system->playSound(sound, 0, false, &channel);


        LOGE("%s", "System init sound");
        switch (mode) {
            case top_zcwfeng_jni_FmodVoiceActivity_MODE_NORMAL:
                _content = "原生：播放完毕";
                break;
            case top_zcwfeng_jni_FmodVoiceActivity_MODE_LUOLI:
                _content = "萝莉：播放完毕";
                //1.创建DSP类型 类型是 Pitch 音调调节 默认正常：1.0    0.5 ~ 2.0
                system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                // 2.设置Pitch音调调节为：2.0，音调很高就是萝莉了
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 2.0f);
                // 3.添加音效进去 参数一：0 是因为只有一个dsp
                channel->addDSP(0, dsp);
                break;
            case top_zcwfeng_jni_FmodVoiceActivity_MODE_DASHU:
                _content = "大叔：播放完毕";
                // 1.创建DSP类型是Pitch 音调调节 默认正常：1.0  0.5 ~ 2.0
                system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                // 2.设置Pitch音调调节为：2.0，音调很高就是萝莉了
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.5f);
                // 3.添加音效进去 参数一：0 是因为只有一个dsp
                channel->addDSP(0, dsp);
                break;
            case top_zcwfeng_jni_FmodVoiceActivity_MODE_GAOGUAI:
                _content = "搞怪 小黄人：播放完毕";
                // 1.从通道里面拿频率， 原始频率
                float frequency;
                channel->getFrequency(&frequency);
                // 2.在原来的频率上更改
                channel->setFrequency(frequency * 1.3f);
                break;
            case top_zcwfeng_jni_FmodVoiceActivity_MODE_JINGSONG:
                _content = "惊悚音 播放完毕";
                //大叔
                system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.7f);
                channel->addDSP(0, dsp);
                // 回声 搞点回声
                system->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 400); // 延时的回音
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 40); // 默认：50  0完全衰减了
                channel->addDSP(1, dsp);
                // 颤抖 Tremolo
                system->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_TREMOLO_FREQUENCY, 0.8f);
                dsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.8f);
                channel->addDSP(2, dsp);
                break;
            case top_zcwfeng_jni_FmodVoiceActivity_MODE_KONGLING:
                _content = "空灵：播放完毕";
                //回音 ECHO
                system->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                // 延迟声音
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 150);
                // 默认：50  0完全衰减了
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 20);
                channel->addDSP(0, dsp);
                break;
            default:
                break;
        }

    } catch (...) {
        LOGE("%s", "发生异常");
        goto END;
    }
    system->update();
    while (isPlay) {
        LOGD("%s","Kotlin Play Fmod...");
        channel->isPlaying(&isPlay);
        usleep(1000 * 1000);
    }
    goto END;


    END:
    env->ReleaseStringUTFChars(path, _path);
    sound->release();
    system->close();
    system->release();
    // 调用java弹出播放提示 char* ---> jstring  ---> String(Java)
    jclass clazz = env->GetObjectClass(thiz);
    jmethodID jmethodId = env->GetMethodID(clazz, "playEnd", "(Ljava/lang/String;)V");
    jstring str = env->NewStringUTF(_content);
    env->CallVoidMethod(thiz, jmethodId, str);
}
