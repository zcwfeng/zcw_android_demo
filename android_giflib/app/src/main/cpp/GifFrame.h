//
// Created by 张传伟 on 2020/10/14.
//

#ifndef ANDROID_GIFLIB_GIFFRAME_H
#define ANDROID_GIFLIB_GIFFRAME_H
#include "JavaInputStream.h"
#include <gif_lib.h>
class GifFrame {
public:
    GifFrame(JavaInputStream* stream);
    GifFrame(JNIEnv* env,jobject assetManager,const char* filename);
    ~GifFrame();

    long loadFrame(JNIEnv* env,jobject bitmap,int frameIndex);
    int getWidth();
    int getHeight();
    int getFrameCount();
private:
    GifFileType * mGif;

};


#endif //ANDROID_GIFLIB_GIFFRAME_H
