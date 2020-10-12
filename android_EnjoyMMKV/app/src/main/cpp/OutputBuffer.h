//
// Created by Administrator on 2019/11/7.
//

#ifndef ENJOYMMKV_OUTPUTBUFFER_H
#define ENJOYMMKV_OUTPUTBUFFER_H

#include <string>
#include "InputBuffer.h"

class OutputBuffer {
    int8_t *m_buf;
    size_t m_size;
    size_t m_position; //当前写出位置
    bool m_isCopy;
public:

    OutputBuffer(int8_t *buf, size_t size);

    ~OutputBuffer();

    void writeByte(int8_t value);

    void writeInt32(int32_t value);

    void writeInt64(int64_t value);

    void writeString(std::string value);

    void writeData(InputBuffer *value);

    void writeFloat(float value);

    size_t spaceLeft() { return m_size - m_position; }


};


#endif //ENJOYMMKV_OUTPUTBUFFER_H
