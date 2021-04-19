//
// Created by 张传伟 on 2021/4/19.
// Native Register Dynamic and Static Use
//
#include <pthread.h>
#include "include/common_head.h"

//JNIEnv *env, jobject thiz,  默认这两个参数是可以省略，如果不用的话
//void dynamicM01(JNIEnv *env, jobject thiz)
void dynamicM01() {
    LOGD("我是动态注册的函数 dynamicM01...");
}

int dynamicM02(JNIEnv *env, jobject thiz, jstring value) {
    const char *text = env->GetStringUTFChars(value, nullptr);
    LOGD("我是动态注册的函数 dynamicM02...%s", text);
    env->ReleaseStringUTFChars(value, text);
    return 200;
}

JavaVM *javaVm = nullptr;
const char *class_name = "top/zcwfeng/jni/JavaJNIActivity";
//name,signature,*
static const JNINativeMethod methods[] = {
        {"dynamicJavaM01", "()V",                   (void *) (dynamicM01)},
        {"dynamicJavaM02", "(Ljava/lang/String;)I", (int *) (dynamicM02)},
};

jint JNI_OnLoad(JavaVM *vm, void *unused) {
    ::javaVm = vm;
    JNIEnv *jniEnv = nullptr;
    int result = javaVm->GetEnv(reinterpret_cast<void **>(&jniEnv), JNI_VERSION_1_6);
    //result 等与0 成功，默认不成文规则，封装库都是成功就是0【如ffmpeg库等】
    if (result != JNI_OK) {
        return -1;
    }
    LOGE("System.loadLibrary --->JNI Load init success");

    jclass clazz = jniEnv->FindClass(class_name);

    //RegisterNatives(jclass clazz, const JNINativeMethod* methods,jint nMethods)
    jniEnv->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(JNINativeMethod));

    LOGE("动态 注册 dynamic success");
    return JNI_VERSION_1_6;// AS的JDK在JNI默认最高1.6  Java的JDKJNI 1.8
}
struct MyContext{
    JNIEnv *jniEnv = nullptr;
    jobject instance = nullptr;
};

//TODO:JNI 线程
void * thread_task_action(void * pVoid){
    // 有类似场景:如，下载任务，下载完成，下载失败，等等，需要更新UI，要告知Android UI线程情况
    MyContext * context = static_cast<MyContext *>(pVoid);
    // 需要用到JNIEnv *env,jobject thiz，跨函数，跨线程有问题
    /*
    jclass call_class = context->jniEnv->FindClass(class_name);
    context->jniEnv->GetObjectClass(context->instance);*/
    //TODO 验证（Android 进程绑定只有一个JavaVM，是全局的，可以跨越线程的）
    JNIEnv *env = nullptr;
    jint attachResult = ::javaVm->AttachCurrentThread(&env,nullptr);
    if(attachResult != JNI_OK){
        return 0;// attach current thread failed
    }
    // 1 拿到jclass
    jclass call_class = env->GetObjectClass(context->instance);
    // 2 拿到方法
    jmethodID jmethodId = env->GetMethodID(call_class,"updateActivityUI","()V");
    // 3
    env->CallVoidMethod(context->instance,jmethodId);
    ::javaVm->DetachCurrentThread();
    LOGE("C++ 异步线程调用 Java-> updateActivityUI Success! ");
    return nullptr;
}


extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_naitveThread(JNIEnv *env, jobject thiz) {
    // 创建线程
    //    pthread_t pid;
    //    pthread_create(&pid,nullptr,thread_task_action,nullptr);

    MyContext * context = new MyContext;
    context->jniEnv = env;
    //① 报错设计
    //context->instance = thiz;
    //② 修正错误,提升全局引用
    context->instance = env->NewGlobalRef(thiz);

    pthread_t pid;
    pthread_create(&pid,nullptr,thread_task_action,context);
    pthread_join(pid,nullptr);
}

void * run(void * pVoid){
    JNIEnv * newEnv = nullptr;
    ::javaVm->AttachCurrentThread(&newEnv,nullptr);
    ::javaVm->DetachCurrentThread();
    LOGE("native Jni 子线程 env地址 run-->%p jvm地址：%p ~~~~~~~",newEnv,::javaVm);
    return nullptr;
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_nativeFun1(JNIEnv *env, jobject thiz) {
    JavaVM * _vm = nullptr;
    env->GetJavaVM(&_vm);
    // 打印：当前函数env地址， 当前函数jvm地址， 当前函数job地址,  JNI_OnLoad的jvm地址
    LOGE("当前函数env地址 nativeFun1-->%p,  jvm地址:%p, jobject 地址:%p, JNI_OnLoad的jvm地址:%p\n", env, _vm, thiz, ::javaVm);
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_nativeFun2(JNIEnv *env, jobject thiz) {
    JavaVM * _vm = nullptr;
    env->GetJavaVM(&_vm);
    // 打印：当前函数env地址， 当前函数jvm地址， 当前函数job地址,  JNI_OnLoad的jvm地址
    LOGE("当前函数env地址 nativeFun2-->%p,  jvm地址:%p, jobject 地址:%p, JNI_OnLoad的jvm地址:%p\n", env, _vm, thiz, ::javaVm);
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_staticFun3(JNIEnv *env, jclass clazz) {
    JavaVM * _vm = nullptr;
    env->GetJavaVM(&_vm);
    // 打印：当前函数env地址， 当前函数jvm地址， 当前函数job地址,  JNI_OnLoad的jvm地址
    LOGE("当前函数env地址 nativeFun3-->%p,  jvm地址:%p, jclass 地址:%p, JNI_OnLoad的jvm地址:%p\n", env, _vm, clazz , ::javaVm);

    pthread_t pid;
    pthread_create(&pid,0,run,nullptr);

}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_staticFun4(JNIEnv *env, jclass clazz) {
    JavaVM * _vm = nullptr;
    env->GetJavaVM(&_vm);
    // 打印：当前函数env地址， 当前函数jvm地址， 当前函数job地址,  JNI_OnLoad的jvm地址
    LOGE("当前函数env地址 nativeFun4 Java 子线程-->%p,  jvm地址:%p, jclass 地址:%p, JNI_OnLoad的jvm地址:%p\n", env, _vm, clazz, ::javaVm);
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JNIActivityThreadTest_nativeFun5(JNIEnv *env, jobject thiz) {
    JavaVM * _vm = nullptr;
    env->GetJavaVM(&_vm);
    // 打印：当前函数env地址， 当前函数jvm地址， 当前函数job地址,  JNI_OnLoad的jvm地址
    LOGE("当前函数env地址 nativeFun5 跨Activity -->%p,  jvm地址:%p, jobject 地址:%p, JNI_OnLoad的jvm地址:%p\n", env, _vm, thiz, ::javaVm);
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_closeThread(JNIEnv *env, jobject thiz) {
    // TODO: implement closeThread() 释放工作
}