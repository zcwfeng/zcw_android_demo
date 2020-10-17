//
// Created by 张传伟 on 2020/10/14.
//

#include "GifFrame.h"
#include <android/bitmap.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include "log.h"

#define ARGB_TO_COLOR8888(a, r, g, b) \
((a) << 24 | (b) << 16 | (g) << 8 | (r))

/*

         a         r         g         b
 0000 0000 0000 0000 0000 0000 0000 0000
        24        16        8          0
 */

static int readFunc(GifFileType *fileType, GifByteType *out, int size) {
    JavaInputStream *stream = (JavaInputStream *) fileType->UserData;
    return (int) stream->read(out, size);
}

static int readFunc1(GifFileType *fileType, GifByteType *out, int size) {
    AAsset *asset = (AAsset *) fileType->UserData;
    return AAsset_read(asset, out, (size_t) size);
}

static long getDelayMs(GraphicsControlBlock &block) {
    return block.DelayTime * 10;
}

GifFrame::GifFrame(JavaInputStream *stream) {
//    int Error;
    mGif = DGifOpen(stream, readFunc, NULL);
    //初始化结构图
    DGifSlurp(mGif);
    //1. 一帧一帧的数据
    //2. 扩展信息，控制信息
    GraphicsControlBlock gcb;
    long durationMs = 0;
    for (int i = 0; i < mGif->ImageCount; i++) {
        DGifSavedExtensionToGCB(mGif, i, &gcb);
        durationMs += getDelayMs(gcb);
    }

}


GifFrame::GifFrame(JNIEnv *env, jobject assetManager, char *filename) {

    LOGI("assetManager: %p", assetManager);
    LOGI("filename: %s", filename);
    if (assetManager) {
        AAssetManager *mgr = AAssetManager_fromJava(env, assetManager);
        AAsset *asset = AAssetManager_open(mgr, filename, AASSET_MODE_STREAMING);
        mGif = DGifOpen(asset, readFunc1, NULL);
    } else {//读取外部文件
        mGif = DGifOpenFileName(filename, NULL);
    }
    LOGI("mGif: %p", mGif);
    if (mGif) {
        //初始化化结构图
        DGifSlurp(mGif);
        //1. 一帧一帧的数据
        //2. 扩展信息，控制信息
        GraphicsControlBlock gcb;
        long durationMs = 0;
        for (int i = 0; i < mGif->ImageCount; i++) {
            //获取显示时间
            DGifSavedExtensionToGCB(mGif, i, &gcb);
            durationMs += getDelayMs(gcb);
        }
    }


}

/**
 * libjnigraphics 库会公开允许访问 Java Bitmap 对象的像素缓冲区的 API。工作流如下：

    1调用 AndroidBitmap_getInfo() 以检索信息，例如指定位图句柄的宽度和高度。

    2调用 AndroidBitmap_lockPixels() 以锁定像素缓冲区并检索指向它的指针。

    这样做可确保像素在应用调用 AndroidBitmap_unlockPixels() 之前不会移动。

    3对像素缓冲区进行相应修改，以使其符合相应像素格式、宽度和其他特性。

    4调用 AndroidBitmap_unlockPixels() 以解锁缓冲区。
 * @param env
 * @param bitmap
 * @param frameIndex
 * @return
 */
long GifFrame::loadFrame(JNIEnv *env, jobject bitmap, int frameIndex) {
    //把屏幕(gif的逻辑上的屏幕)上的内容 填充到bitmap
    // 1. 如何获取屏幕上的内容 giflib API
    // 2.  如何填充到bitmap  NDK API  jnigraphics
    AndroidBitmapInfo info;
    //缓冲区像素
    uint32_t *pixels;
    //使用 AndroidBitmap_getInfo() 从 JNI 检索信息，例如指定位图句柄的宽度和高度
    AndroidBitmap_getInfo(env, bitmap, &info);
    //用 AndroidBitmap_lockPixels() 锁定像素缓冲区并检索指向它的指针。
    // 这样做可确保像素在应用调用 AndroidBitmap_unlockPixels() 之前不会移动
    AndroidBitmap_lockPixels(env, bitmap, reinterpret_cast<void **> (&pixels));// bitmap在堆，栈指向堆指针的指针

    //在原生代码中，对像素缓冲区进行相应的修改，以使其符合相应像素格式、宽度和其他特性
    SavedImage savedImage = mGif->SavedImages[frameIndex];
    GifImageDesc imageDesc = savedImage.ImageDesc;
    ColorMapObject *colorMapObject = mGif->SColorMap ?
                                     mGif->SColorMap : imageDesc.ColorMap;
    //colorMapObject -> pixels

    for (int i = 0; i < imageDesc.Height; ++i) {
        for (int j = 0; j < imageDesc.Width; ++j) {
            //把每个像素点的颜色取出来，存到pixels
            int index = i * imageDesc.Width + j;//获取每个像素点的坐标
            GifByteType gifByteType = savedImage.RasterBits[index];//像素值 是压缩的
            //解压
            GifColorType gifColorType = colorMapObject->Colors[gifByteType];//gif的每个像素点上的颜色值
            uint32_t color = ARGB_TO_COLOR8888(0xff, gifColorType.Red, gifColorType.Green,
                                               gifColorType.Blue);
            pixels[index] = color;
        }
    }
    //调用 AndroidBitmap_unlockPixels() 来解锁缓冲区
    AndroidBitmap_unlockPixels(env, bitmap);
    GraphicsControlBlock gcb;//获取控制信息
    DGifSavedExtensionToGCB(mGif, frameIndex, &gcb);
    return getDelayMs(gcb);
}

int GifFrame::getWidth() {
    return mGif ? mGif->SWidth : 0;
}

int GifFrame::getHeight() {
    return mGif ? mGif->SHeight : 0;
}

int GifFrame::getFrameCount() {
    return mGif ? mGif->ImageCount : 0;
}


