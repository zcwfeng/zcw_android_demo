//
// Created by 张传伟 on 2021/4/22.
//
#include "common_head.h"
#include "malloc.h"

#ifndef ANDROID_JNI_PARCEL_H
#define ANDROID_JNI_PARCEL_H

class Parcel {
public:
    virtual ~Parcel();

    Parcel();

    jint readInt();

    void writeInt(int val);

    void setDataPosition(int pos);

private:
    void changePos(int pos);//用于改变旧地址位置

    char *mData = 0;
    int mDataPos = 0;//对共享内存地址位置偏移
    jint len = 0;
};


#endif //ANDROID_JNI_PARCEL_H
