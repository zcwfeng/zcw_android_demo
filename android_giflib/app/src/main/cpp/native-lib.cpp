#include <jni.h>
#include <string>
#include "GifFrame.h"
#include <gif_lib.h>

extern "C"
JNIEXPORT jlong JNICALL
Java_top_zcwfeng_giflib_gif_GifFrame_nativeGetFrame(JNIEnv *env, jobject thiz, jlong native_handle,
                                                    jobject bitmap, jint frame_index) {
    GifFrame *gifFrame = reinterpret_cast<GifFrame *>(native_handle);
    jlong delayMs = gifFrame->loadFrame(env, bitmap, frame_index);
    return delayMs;
}


extern "C"
JNIEXPORT jobject JNICALL
Java_top_zcwfeng_giflib_gif_GifFrame_nativeDecodeStream(JNIEnv *env, jclass clazz,
        jobject stream,jbyteArray buffer) {
    // TODO: implement nativeDecodeStream()  创建一个java 的 GifFrame 返回给 Java
    //  先创建C++ 的GifFrame
    //  调用GifFrame 的构造函数
    jclass inputStreamClazz = env->FindClass("java/io/InputStream");
    JavaInputStream::readMethodId = env->GetMethodID(inputStreamClazz, "read", "([BII)I");
    JavaInputStream inputStream(env, stream, buffer);
    GifFrame* gifFrame = new GifFrame(&inputStream);// 返回在堆上
    // 1.GifFrame jclass
    jclass gifFrameClazz = env->FindClass("top/zcwfeng/giflib/gif/GifFrame");
    // 2.Java GifFrame  构造方法，jMethod
    jmethodID gifFrameInit = env->GetMethodID(gifFrameClazz, "<init>", "(JIII)V");
    return env->NewObject(gifFrameClazz,gifFrameInit,
                          reinterpret_cast<jlong>(gifFrame),
                          gifFrame->getWidth(),
                          gifFrame->getHeight(),
                          gifFrame->getFrameCount());
}


extern "C"
JNIEXPORT jobject JNICALL
decode_jni(JNIEnv *env, jclass clazz, jobject assetManager, jstring gifPath) {


    const char* filename = env->GetStringUTFChars(gifPath,0);
    GifFrame * gifFrame = new GifFrame(env,assetManager,filename);
    env->ReleaseStringUTFChars(gifPath,filename);

    // 1.GifFrame jclass
    jclass gifFrameClazz = env->FindClass("top/zcwfeng/giflib/gif/GifFrame");
    // 2.Java GifFrame  构造方法，jMethod
    jmethodID gifFrameInit = env->GetMethodID(gifFrameClazz, "<init>", "(JIII)V");

    return env->NewObject(gifFrameClazz,
                          gifFrameInit,
                          reinterpret_cast<jlong>(gifFrame),
                          gifFrame->getWidth(),
                          gifFrame->getHeight(),
                          gifFrame->getFrameCount()

    );
};

//动态注册
JNINativeMethod method[] = {
        {"nativeDecodeStreamJNI",
                "(Landroid/content/res/AssetManager;Ljava/lang/String;)Ltop/zcwfeng/giflib/gif/GifFrame;",
                (void *) decode_jni},
};

jint registNativeMethod(JNIEnv *env) {
    jclass cl = env->FindClass("top/zcwfeng/giflib/gif/GifFrame");
    if (env->RegisterNatives(cl, method, sizeof(method) / sizeof(method[0])) < 0) {
        return -1;
    }
    return 0;

};

jint unRegistNativeMethod(JNIEnv *env) {
    jclass cl = env->FindClass("top/zcwfeng/giflib/gif/GifFrame");
    if (env->UnregisterNatives(cl)) {
        return -1;
    }

    return 0;
};

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *aram) {
    JNIEnv * env;
    if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_6) == JNI_OK){
        registNativeMethod(env);
        return JNI_VERSION_1_6;
    } else if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_4) == JNI_OK){
        registNativeMethod(env);
        return JNI_VERSION_1_4;
    }
    return JNI_ERR;
};

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *aram) {
    JNIEnv * env;
    if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_6) == JNI_OK){
        unRegistNativeMethod(env);
    } else if(vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_4) == JNI_OK){
        unRegistNativeMethod(env);
    }
};;