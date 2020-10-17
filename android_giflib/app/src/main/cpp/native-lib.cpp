#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_top_zcwfeng_giflib_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jlong JNICALL
Java_top_zcwfeng_giflib_gif_GifFrame_nativeGetFrame(JNIEnv *env, jobject thiz, jlong native_handle,
                                                    jobject bitmap, jint frame_index) {
    // TODO: implement nativeGetFrame()
}


extern "C"
JNIEXPORT jobject JNICALL
Java_top_zcwfeng_giflib_gif_GifFrame_nativeDecodeStream(JNIEnv *env, jclass clazz, jobject stream,
                                                        jbyteArray buffer) {
    // TODO: implement nativeDecodeStream()
}


extern "C"
JNIEXPORT jobject JNICALL
Java_top_zcwfeng_giflib_gif_GifFrame_nativeDecodeStreamJNI(JNIEnv *env, jclass clazz,
                                                           jobject assert_manager,
                                                           jstring gif_path) {
    // TODO: implement nativeDecodeStreamJNI()
}