//
// Created by 张传伟 on 2020/12/8.
//

#include <jni.h>
#include <android/log.h>
#include "FaceTrack.h"

extern "C"
JNIEXPORT jlong JNICALL
Java_top_zcwfeng_opengl_face_FaceTracker_nativeCreateObject(JNIEnv *env, jclass clazz,
                                                            jstring face_model,
                                                            jstring landmarker_model) {
    const char *facemodel = env->GetStringUTFChars(face_model, 0);
    const char *landmarkermodel = env->GetStringUTFChars(landmarker_model, 0);

    FaceTrack *faceTrack = new FaceTrack(facemodel, landmarkermodel);

    env->ReleaseStringUTFChars(face_model, facemodel);
    env->ReleaseStringUTFChars(landmarker_model, landmarkermodel);
    return (jlong) faceTrack;
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_opengl_face_FaceTracker_nativeDestroyObject(JNIEnv *env, jclass clazz,
                                                             jlong thiz) {
    if (thiz != 0) {
        FaceTrack *tracker = reinterpret_cast<FaceTrack *>(thiz);
        tracker->stop();
        delete tracker;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_opengl_face_FaceTracker_nativeStart(JNIEnv *env, jclass clazz, jlong thiz) {
    if (thiz != 0) {
        FaceTrack *tracker = reinterpret_cast<FaceTrack *>(thiz);
        tracker->run();
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_top_zcwfeng_opengl_face_FaceTracker_nativeStop(JNIEnv *env, jclass clazz, jlong thiz) {
    if (thiz != 0) {
        FaceTrack *tracker = reinterpret_cast<FaceTrack *>(thiz);
        tracker->stop();
    }
}

extern "C"
JNIEXPORT jobject JNICALL
Java_top_zcwfeng_opengl_face_FaceTracker_nativeDetect(JNIEnv *env, jclass clazz, jlong thiz,
                                                      jbyteArray input_image, jint width,
                                                      jint height, jint rotation_degrees) {
    if (thiz == 0) {
        return 0;
    }
    FaceTrack *tracker = reinterpret_cast<FaceTrack *>(thiz);
    jbyte *inputImage = env->GetByteArrayElements(input_image, 0);

    //I420
    Mat src(height * 3 / 2, width, CV_8UC1, inputImage);

    // 转为RGBA
    cvtColor(src, src, CV_YUV2RGBA_I420);
    //旋转
    if (rotation_degrees == 90) {
        rotate(src, src, ROTATE_90_CLOCKWISE);
    } else if (rotation_degrees == 270) {
        rotate(src, src, ROTATE_90_COUNTERCLOCKWISE);
    }
    //镜像问题，可以使用此方法进行垂直翻转
//    flip(src,src,1);
    Mat gray;
    cvtColor(src, gray, CV_RGBA2GRAY);
    equalizeHist(gray, gray);


    cv::Rect face;
    std::vector<SeetaPointF> points;
    tracker->process(gray, face, points);


    int w = src.cols;
    int h = src.rows;
    gray.release();
    src.release();
    env->ReleaseByteArrayElements(input_image, inputImage, 0);

    if (!face.empty() && !points.empty()) {
        jclass cls = env->FindClass("top/zcwfeng/opengl/face/Face");
        jmethodID construct = env->GetMethodID(cls, "<init>", "(IIIIIIFFFFFFFFFF)V");
        SeetaPointF left = points[0];
        SeetaPointF right = points[1];
        SeetaPointF nose = points[2];
        SeetaPointF mouse_left = points[3];
        SeetaPointF mouse_right = points[4];
        __android_log_print(ANDROID_LOG_ERROR,"zcw_opengl","%s","nativeDetect before");
        __android_log_print(ANDROID_LOG_ERROR,"zcw_opengl","%d,%d,%d,%d,%d,%d,%d,%d,%d,%d"
                ,left.x, left.y, right.x, right.y, nose.x, nose.y, mouse_left.x,
                            mouse_left.y, mouse_right.x, mouse_right.y);

        jobject obj = env->NewObject(cls, construct, face.width, face.height, w, h, face.x, face.y,
                                     left.x, left.y, right.x, right.y, nose.x, nose.y, mouse_left.x,
                                     mouse_left.y, mouse_right.x, mouse_right.y);
        __android_log_print(ANDROID_LOG_ERROR,"zcw_opengl","%s","nativeDetect after");

        return obj;
    }

    return 0;
}