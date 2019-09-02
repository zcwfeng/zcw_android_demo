//#include "FFMPEGUtils.h"
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <android/log.h>
#include "FFMpegUtils.h"
#include <string.h>
#define LOGI(format, ...) __android_log_print(ANDROID_LOG_INFO,"top_zcwfeng",format,__VA_ARGS__)

JNIEXPORT void JNICALL Java_top_zcwfeng_zcwffmpeg_FFMpegUtils_logMetada
        (JNIEnv *env, jclass, jstring videopath) {
    //string -> char *
    char* filepath=(char* )env->GetStringUTFChars(videopath,JNI_FALSE);
    LOGI("视频路径%s", filepath);
//    1.
    av_register_all();
//    2.解封装F
    AVFormatContext *p_format_ctx = avformat_alloc_context();
//    3.打开
    if(avformat_open_input(&p_format_ctx,filepath,NULL,NULL) != 0){
        LOGI("%s", "无法打开视频");
        return;

    }
//    4. 获取视频信息
    if(avformat_find_stream_info(p_format_ctx,NULL)<0){
        LOGI("%s", "无法打读取视频信息");
        return;

    }
//    5. 获取视频流
    int i = 0,v_stream_idx=-1;
    for(i=0;i<p_format_ctx ->nb_streams;i++){
        if(p_format_ctx->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO){
            v_stream_idx = i;
            break;
        }
    }

    if(v_stream_idx == -1) {
        LOGI("%s", "无法打读取视频流");
        return;

    }
// 6. 解码器
//    AVCodecParameters *pCodecCtx = p_format_ctx->streams[v_stream_idx]->codecpar;
//  7.获取视频数据
    AVDictionaryEntry *tag = NULL;
//    tag = av_dict_get(p_format_ctx->metadata,"album",tag,AV_DICT_IGNORE_SUFFIX);

//    int angle = -1;
//    if(tag){
//        angle = atoi(tag->value);
//        LOGI("angle:%s",angle);
//    }

//      char *album = NULL;
//      if(tag) {
//          album = tag->value;
//          LOGI("album:%s",album);
//      }


    while ((tag = av_dict_get(p_format_ctx->metadata, "", tag, AV_DICT_IGNORE_SUFFIX)))
    {
        LOGI("%s=%s\n", tag->key, tag->value);
    }


    //分辨率
    LOGI("%d", p_format_ctx->streams[v_stream_idx]->codecpar->width);
    LOGI("%d", p_format_ctx->streams[v_stream_idx]->codecpar->height);


//    8. 释放内存
//    avformat_free_context(p_format_ctx);


    avformat_close_input(&p_format_ctx);
    env->ReleaseStringUTFChars(videopath, filepath);
}


