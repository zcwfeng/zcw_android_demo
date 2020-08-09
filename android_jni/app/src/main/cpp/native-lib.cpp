#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_top_zcwfeng_jni_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_top_zcwfeng_jni_MainActivity_testFromJNI(JNIEnv *env, jobject thiz) {
    std::string zcw = "Test";
    return env->NewStringUTF(zcw.c_str());
}