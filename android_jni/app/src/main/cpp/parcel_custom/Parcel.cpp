//
// Created by 张传伟 on 2021/4/22.
//

#include "Parcel.h"

Parcel::Parcel() {
    this->mData = static_cast<char *>(malloc(1024));
}

Parcel::~Parcel() {
    if(this->mData){
        free(this->mData);
    }

    if(this->mDataPos){
        this->mDataPos = NULL;
    }
}

void Parcel::changePos(int pos) {
    this->mDataPos += pos;

}

void Parcel::writeInt(int val) {
//    *(this->mData + this->mDataPos) = val;
    *reinterpret_cast<int *>(this->mData + this->mDataPos) = val;
    changePos(sizeof(int));
}

int Parcel::readInt() {
    jint ret = *reinterpret_cast<int *>(this->mData+ this->mDataPos);
    changePos(sizeof(int));
    return ret;
}

void Parcel::setDataPosition(int pos) {
    this->mDataPos = pos;
}
