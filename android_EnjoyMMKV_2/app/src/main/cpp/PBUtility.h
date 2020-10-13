//
// Created by Administrator on 2019/11/7.
//

#ifndef ENJOYMMKV_PBUTILITY_H
#define ENJOYMMKV_PBUTILITY_H

#include <stdint.h>

size_t computeInt32Size(int32_t value) {
    if (value < 0){
        return 10;
    }
    //0xffffffff 表示 32位能表示的 最大值
    //<< 7 则低7位变成0 与上value
    //如果value只要7位就够了则=0,编码只需要一个字节，否则进入其他判断
    if ((value & (0xffffffff << 7)) == 0) {
        return 1;
    } else if ((value & (0xffffffff << 14)) == 0) {
        return 2;
    } else if ((value & (0xffffffff << 21)) == 0) {
        return 3;
    } else if ((value & (0xffffffff << 28)) == 0) {
        return 4;
    }
    return 5;
}


size_t computeInt64Size(int64_t value) {
    if ((value & (0xffffffffffffffffL << 7)) == 0) {
        return 1;
    } else if ((value & (0xffffffffffffffffL << 14)) == 0) {
        return 2;
    } else if ((value & (0xffffffffffffffffL << 21)) == 0) {
        return 3;
    } else if ((value & (0xffffffffffffffffL << 28)) == 0) {
        return 4;
    } else if ((value & (0xffffffffffffffffL << 35)) == 0) {
        return 5;
    } else if ((value & (0xffffffffffffffffL << 42)) == 0) {
        return 6;
    } else if ((value & (0xffffffffffffffffL << 49)) == 0) {
        return 7;
    } else if ((value & (0xffffffffffffffffL << 56)) == 0) {
        return 8;
    } else if ((value & (0xffffffffffffffffL << 63)) == 0) {
        return 9;
    }
    return 10;
}

size_t computeItemSize(std::string key, InputBuffer *value) {
    int32_t keyLength = key.length();
    // 保存key的长度与key数据需要的字节
    int32_t size = keyLength + computeInt32Size(keyLength);
    // 加上保存value的长度与value数据需要的字节
    size += value->length() + computeInt32Size(value->length());
    return size;
}

size_t computeMapSize(std::unordered_map<std::string, InputBuffer *> map) {
    auto iter = map.begin();
    int32_t size = 0;
    for (; iter != map.end(); iter++) {
        auto key = iter->first;
        auto value = iter->second;
        size += computeItemSize(key, value);
    }
    return size;
}
#endif //ENJOYMMKV_PBUTILITY_H
