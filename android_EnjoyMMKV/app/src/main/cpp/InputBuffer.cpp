//
// Created by Administrator on 2019/11/6.
//

#include "InputBuffer.h"

InputBuffer::InputBuffer(size_t size) {
    m_size = size;
    m_position = 0;
    isCopy = true;
    if (size > 0) {
        m_buf = static_cast<int8_t *>(malloc(size));
    }
}

InputBuffer::InputBuffer(int8_t *buf, size_t size) {
    m_size = size;
    m_position = 0;
    m_buf = buf;
    isCopy = false;
}

InputBuffer::~InputBuffer() {
    if (isCopy && m_buf){
        free(m_buf);
    }
    m_buf = 0;
}

int8_t InputBuffer::readByte() {
    //不能越界
    if (m_position == m_size) {
        return 0;
    }
    return m_buf[m_position++];
}

std::string InputBuffer::readString() {
    //mmkv保存字符串：字符串长度+字符串
    int32_t size = readInt32();
    //剩下的数据有这么多
    if (size <= (m_size - m_position) && size > 0) {
        std::string result((char *) m_buf + m_position, size);
        m_position += size; //读走了 size个了
        return result;
    }
    return "";
}

int32_t InputBuffer::readInt32() {

    return 0;
}

int32_t InputBuffer::readInt64() {
    //最高位为1
    int8_t  i = readByte();
    //i < 0
    // i & 0x80 == 0
    //readByte();
    //readByte();

    // 0
    //取低7位 i& 0x7f
    int tmp = 0;
    // tmp |= newTemp <<7;
    return 0;
}


InputBuffer *InputBuffer::readData() {
    //获得数据长度
    int32_t size = readInt32();
    //有效
    if (size <= m_size - m_position && size > 0) {
        InputBuffer *data = new InputBuffer(m_buf + m_position, size);
        m_position += size;
        return data;
    }
    return 0;
}

void InputBuffer::restore() {
    m_position = 0;
}

float InputBuffer::readFloat() {
    int8_t b1 = readByte();
    int8_t b2 = readByte();
    int8_t b3 = readByte();
    int8_t b4 = readByte();
    int32_t value =
            (((int32_t) b1 & 0xff)) | (((int32_t) b2 & 0xff) << 8) | (((int32_t) b3 & 0xff) << 16) |
            (((int32_t) b4 & 0xff) << 24);

    float returnValue = *(float *) &value;
    return returnValue;
}



