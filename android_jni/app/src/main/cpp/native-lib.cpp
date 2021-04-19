#include <string>
#include <android/bitmap.h>
#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <opencv2/opencv.hpp>
#include "top_zcwfeng_jni_JavaJNIActivity.h"
#include "common_head.h"

#define DEFAULT_CARD_WIDTH 640
#define DEFAULT_CARD_HEIGHT 400
#define  FIX_IDCARD_SIZE Size(DEFAULT_CARD_WIDTH,DEFAULT_CARD_HEIGHT)
#define FIX_TEMPLATE_SIZE  Size(153, 28)

//TODO:END TEST 实现
extern "C"
JNIEXPORT // 标记该方法可以被外部调用
jstring // Java <---> native 转换用的
// Java_包名_类名_方法名  ，注意：我们的包名 _     native _1
// JNIEnv * env  JNI：的桥梁环境    300多个函数，所以的JNI操作，必须靠他
// jobject jobj  谁调用，就是谁的实例  MainActivity this
// jclass clazz 谁调用，就是谁的class MainActivity.class
JNICALL Java_top_zcwfeng_jni_JavaJNIActivity_getStringPwd
        (JNIEnv *env, jobject jobj) {

}

extern "C"
JNIEXPORT jstring JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_getStringPwd2(JNIEnv *env, jclass clazz) {
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_changeName(JNIEnv *env, jobject thiz) {
    // 获取class
    jclass jcs = env->GetObjectClass(thiz);
//    jfieldID GetFieldID(jclass clazz, const char* name 方法名, const char* sig属性签名)
    jfieldID jfid = env->GetFieldID(jcs, "name", "Ljava/lang/String;");
//    jobject GetObjectField(jobject obj, jfieldID fieldID),用到了指针转换
    jstring j_str = static_cast<jstring>(env->GetObjectField(thiz, jfid));
    // 打印字符串
    char *c_str = const_cast<char *>(env->GetStringUTFChars(j_str, NULL));
    LOGD("native: %s", c_str);
    // 修改Tesla,必须写出jstring才能转换
    jstring jName = env->NewStringUTF("Tesla");
    env->SetObjectField(thiz, jfid, jName);

}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_changeAge(JNIEnv *env, jclass clazz) {
    const char *sig = "I";

    jfieldID jfid = env->GetStaticFieldID(clazz, "age", sig);
    jint age = env->GetStaticIntField(clazz, jfid);
    age += 10;
    //jint----int
    LOGI("native: %d", age);
    env->SetStaticIntField(clazz, jfid, age);

}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_callAddMethod(JNIEnv *env, jobject jobj) {
    // 自己得到class
    jclass javaJNIActivityClass = env->GetObjectClass(jobj);

//        jmethodID GetMethodID(jclass clazz, const char* name, const char* sig)
    jmethodID jmid = env->GetMethodID(javaJNIActivityClass, "add", "(II)I");
    // 调用Java 方法
    jint sum = env->CallIntMethod(jobj, jmid, 3, 4);
    LOGE("add sum result ->java:%d", sum);
}


extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_testArrayAction(
        JNIEnv *env,
        jobject thiz,
        jint count,
        jstring text_info,
        jintArray ints,
        jobjectArray strs) {

    // ① 基本数据类型  jint count,jint 就是可以直接用 int， jstring text_info
    int intCount = count;
    LOGI("param1:count->", intCount);

    // const char* GetStringUTFChars(jstring string, jboolean* isCopy)
    const char *textp = env->GetStringUTFChars(text_info, NULL);
    LOGI("param2 textInfo:%s\n", textp);

    // ② 把int[] 转成 int*
    // jint* GetIntArrayElements(jintArray array, jboolean* isCopy)
    int *j_intarrayp = env->GetIntArrayElements(ints, NULL);

    // Java层数组的长度
    // jsize GetArrayLength(jarray array) -- jintArray ints 可以放入到 jarray的参数中去
    jsize size = env->GetArrayLength(ints);
    // 此时C++的修改，影响不了Java层
    for (int i = 0; i < size; ++i) {
        *(j_intarrayp + i) += 100;
        LOGI("param3 int[]:%d\n", *j_intarrayp + i);
    }

    /**
    * 0:           刷新Java数组，并 释放C++层数组
    * JNI_COMMIT:  只提交 只刷新Java数组，不释放C++层数组
    * JNI_ABORT:   只释放C++层数组
    */
    env->ReleaseIntArrayElements(ints, j_intarrayp, NULL);

    // ③：jobjectArray 代表是Java的引用类型数组，不一样
    int strsize = env->GetArrayLength(strs);
    int i;
    for (i = 0; i < strsize; ++i) {
        jstring j_str = static_cast<jstring>(env->GetObjectArrayElement(strs, i));
        // 想要打印必须转换成c的格式
        // const char* GetStringUTFChars(jstring string, jboolean* isCopy)
        const char *charp = env->GetStringUTFChars(j_str, NULL);
        LOGI("param4: 引用类型String 具体的：%s\n", charp);
        // 释放jstring
        env->ReleaseStringUTFChars(j_str, charp);
    }

}
extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_putObject(JNIEnv *env, jobject thiz, jobject student,
                                               jstring str) {
    const char *strChar = env->GetStringUTFChars(str, NULL);
    LOGI("strChar：%s\n", strChar);
    env->ReleaseStringUTFChars(str, strChar);
    // --------------
    // 1.寻找类 Student
//    env->FindClass("top/zcwfeng/jni/Student");
    jclass student_class = env->GetObjectClass(student);
    // 2.Student类里面的函数规则  签名
    jmethodID setName = env->GetMethodID(student_class, "setName", "(Ljava/lang/String;)V");
    jmethodID getName = env->GetMethodID(student_class, "getName", "()Ljava/lang/String;");
    jmethodID showInfo = env->GetStaticMethodID(student_class, "showInfo", "(Ljava/lang/String;)V");
    // 3.调用 setName
    jstring value = env->NewStringUTF("我是Student测试");
    env->CallVoidMethod(student, setName, value);
    // 4.调用 getName
    jstring nameResult = static_cast<jstring>(env->CallObjectMethod(student, getName));
    const char *getNameResult = env->GetStringUTFChars(nameResult, NULL);
    LOGE("调用到getName方法，值是:%s\n", getNameResult);
    // 5.调用静态showInfo
    jstring info = env->NewStringUTF("静态方法Student:我是C++");
    env->CallStaticVoidMethod(student_class, showInfo, info);
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_insertObject(JNIEnv *env, jobject thiz) {
    // 1.通过包名+类名的方式 拿到 Student class  凭空拿class
    jclass student_class = env->FindClass("top/zcwfeng/jni/Student");
    // 2.通过student的class  实例化此Student对象   C++ new Student，
    // AllocObject 只实例化对象，不会调用对象的构造函数
    //    env->NewObject()  实例化对象，会调用对象的构造函数
    jobject student_obj = env->AllocObject(student_class);
    // 3.方法签名的规则
    jmethodID setName = env->GetMethodID(student_class, "setName", "(Ljava/lang/String;)V");
    jmethodID setAge = env->GetMethodID(student_class, "setAge", "(I)V");
    // 调用方法
    jstring strvalue = env->NewStringUTF("David,zcwfeng");
    env->CallVoidMethod(student_obj, setName, strvalue);
    env->CallVoidMethod(student_obj, setAge, 100);

    // ====================  下面是 Person对象  调用person对象的  setStudent 函数等
    // 4.通过包名+类名的方式 拿到 Student class  凭空拿class
    const char *personp = "top/zcwfeng/jni/Person";
    jclass person_class = env->FindClass(personp);
    // AllocObject 只实例化对象，不会调用对象的构造函数
    jobject jobj = env->AllocObject(person_class);
    // setStudent 此函数的 签名 规则
    jmethodID jmethodId = env->GetMethodID(person_class, "setStudent",
                                           "(Ltop/zcwfeng/jni/Student;)V");
    env->CallVoidMethod(jobj, jmethodId, student_obj);
}

// 在java中这是全局引用，在c中仍然是局部引用
jclass dogClass;
extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_testQuote(JNIEnv *env, jobject thiz) {
    if (dogClass == NULL) {
//        dogClass = env->FindClass("top/zcwfeng/jni/Dog");

        //!!!!!!
        // 升级全局引用： JNI函数结束也不释放，必须手动释放
        // 相当于： C++ 对象 new、手动delete
        jclass temp = env->FindClass("top/zcwfeng/jni/Dog");
        dogClass = static_cast<jclass>(env->NewGlobalRef(temp));
        // 注意！！！：用完了，如果不用了，马上释放
        env->DeleteLocalRef(temp);
    }

    jmethodID init = env->GetMethodID(dogClass, "<init>", "()V");
    jobject dog = env->NewObject(dogClass,init);

    init = env->GetMethodID(dogClass, "<init>", "(I)V");
    dog = env->NewObject(dogClass,init,101);

    init = env->GetMethodID(dogClass, "<init>", "(II)V");
    dog = env->NewObject(dogClass,init,100,200);

    init = env->GetMethodID(dogClass, "<init>", "(III)V");
    dog = env->NewObject(dogClass,init,301,401,501);

    env->DeleteLocalRef(dog);
}

// 非常方便，可以使用了
extern int age; // 声明age
extern void show(); // 声明show函数

// 手动释放全局引用
extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_jni_JavaJNIActivity_delQuote(JNIEnv *env, jobject thiz) {
    if (dogClass != NULL) {
        LOGE("全局引用释放完毕，上面的按钮已经失去全局引用，再次点击会报错");
        env->DeleteGlobalRef(dogClass);
        // 最好给一个NULL，指向NULL的地址,避免悬空或者野指针
        dogClass = NULL;
    }
    //TODO: 测试 external
    show();
}

// TODO:END TEST



extern "C" JNIEXPORT jstring JNICALL
Java_top_zcwfeng_jni_MainActivity_stringFromJNI(
        JNIEnv *env,
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

extern "C" {

using namespace cv;
using namespace std;

extern JNIEXPORT void JNICALL Java_org_opencv_android_Utils_nBitmapToMat2
        (JNIEnv *env, jclass, jobject bitmap, jlong m_addr, jboolean needUnPremultiplyAlpha);
extern JNIEXPORT void JNICALL Java_org_opencv_android_Utils_nMatToBitmap
        (JNIEnv *env, jclass, jlong m_addr, jobject bitmap);

jobject createBitmap(JNIEnv *env, Mat srcData, jobject config) {
    // Image Details
    int imgWidth = srcData.cols;
    int imgHeight = srcData.rows;
    int numPix = imgWidth * imgHeight;
    jclass bmpCls = env->FindClass("android/graphics/Bitmap");
    jmethodID createBitmapMid = env->GetStaticMethodID(bmpCls, "createBitmap",
                                                       "(IILandroid/graphics/Bitmap/Config;)Landroid/graphics/Bitmap;");
    jobject jBmpObj = env->CallStaticObjectMethod(bmpCls, createBitmapMid, imgWidth, imgHeight,
                                                  config);
    Java_org_opencv_android_Utils_nMatToBitmap(env, 0, (jlong) &srcData, jBmpObj);
//    mat2Bitmap(env, srcData, jBmpObj);
    return jBmpObj;
}


JNIEXPORT jobject JNICALL
Java_top_zcwfeng_jni_ImageProcess_getIdNumber(JNIEnv *env, jclass type, jobject src,
                                              jobject config) {
    Mat src_img;
    Mat dst_img;
    //imshow("src_", src_img);
    //讲bitmap转换为Mat型格式数据
    Java_org_opencv_android_Utils_nBitmapToMat2(env, type, src, (jlong) &src_img, 0);

    Mat dst;
    //无损压缩//640*400
    resize(src_img, src_img, FIX_IDCARD_SIZE);
    //imshow("dst", src_img);
    //灰度化
    cvtColor(src_img, dst, COLOR_BGR2GRAY);
    //imshow("gray", dst);

    //二值化
    threshold(dst, dst, 100, 255, THRESH_BINARY);
    //imshow("threshold", dst);

    //加水膨胀，发酵
    Mat erodeElement = getStructuringElement(MORPH_RECT, Size(20, 10));
    erode(dst, dst, erodeElement);
    //imshow("erode", dst);

    ////轮廓检测 // arraylist
    vector<vector<Point> > contours;
    vector<Rect> rects;

    findContours(dst, contours, RETR_TREE, CHAIN_APPROX_SIMPLE, Point(0, 0));

    for (int i = 0; i < contours.size(); i++) {
        Rect rect = boundingRect(contours.at(i));
        //rectangle(dst, rect, Scalar(0, 0, 255));  // 在dst 图片上显示 rect 矩形
        if (rect.width > rect.height * 9) {
            rects.push_back(rect);
            rectangle(dst, rect, Scalar(0, 255, 255));
            dst_img = src_img(rect);
        }

    }
    // imshow("轮廓检测", dst);


    if (rects.size() == 1) {
        Rect rect = rects.at(0);
        dst_img = src_img(rect);
    } else {
        int lowPoint = 0;
        Rect finalRect;
        for (int i = 0; i < rects.size(); i++) {
            Rect rect = rects.at(i);
            Point p = rect.tl();
            if (rect.tl().y > lowPoint) {
                lowPoint = rect.tl().y;
                finalRect = rect;
            }
        }
        rectangle(dst, finalRect, Scalar(255, 255, 0));
        //imshow("contours", dst);
        dst_img = src_img(finalRect);
    }

    jobject bitmap = createBitmap(env, dst_img, config);

    end:
    src_img.release();
    dst_img.release();
    dst.release();

    return bitmap;

//    if (!dst_img.empty()) {
//        imshow("target", dst_img);
//    }

}
}


extern "C" {
extern int m_patch_main(int argc, char *argv[]);
}

extern "C"
JNIEXPORT jint JNICALL
Java_top_zcwfeng_jni_BsPatchUtils_path(JNIEnv *env, jclass _jclass, jstring old_apk,
                                       jstring new_apk,
                                       jstring path) {
    int argc = 4;
    char *argv[argc];
    argv[0] = "bspatch";

    argv[1] = const_cast<char *>(env->GetStringUTFChars(old_apk, 0));
    argv[2] = const_cast<char *>(env->GetStringUTFChars(new_apk, 0));
    argv[3] = const_cast<char *>(env->GetStringUTFChars(path, 0));


    int result = m_patch_main(argc, argv);
    env->ReleaseStringUTFChars(old_apk, argv[1]);
    env->ReleaseStringUTFChars(new_apk, argv[2]);
    env->ReleaseStringUTFChars(path, argv[3]);
    return result;
}

