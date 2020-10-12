//
// Created by Administrator on 2019/11/7.
//

#include "OutputBuffer.h"


OutputBuffer::OutputBuffer(int8_t *buf, size_t size) {
    m_position = 0;
    m_size = size;
    m_buf = buf;

}

OutputBuffer::~OutputBuffer() {
    m_buf = 0;
}

void OutputBuffer::writeByte(int8_t value) {
    if (m_position == m_size) {
        //满啦，出错啦
        return;
    }
    //将byte放入数组
    m_buf[m_position++] = value;
}


void OutputBuffer::writeInt32(int32_t value) {
    if (value < 0) {
        writeInt64(value);
    } else {
        while (true){
            if (value <= 0x7f){
                writeByte(value);
                break;
            } else{
                // 取低7位，再最高位赋1
                writeByte(value & 0x7f | 0x80);
                value >>= 7;
            }
        }
    }

}

void OutputBuffer::writeInt64(int64_t value) {
    uint64_t  i = value;
    while (true){
        if (i & ~0x7f == 0){
            writeByte(i);
            break;
        } else{
            // 取低7位，再最高位赋1
            writeByte(i & 0x7f | 0x80);
            i >>= 7;
        }
    }
}

void OutputBuffer::writeFloat(float value) {
    //float 4字节 转为int32处理
    //共用体或者指针修改
#if 0
    union Converter{
        int32_t first;
        float sencond;
    };
    Converter converter;
    converter.sencond = value;
    int32_t i = converter.first;
#else
    int32_t i = *(int32_t *) &value;
#endif

    //取低8位写入
    writeByte((i) & 0xff);
    writeByte((i >> 8) & 0xff);
    writeByte((i >> 16) & 0xff);
    writeByte((i >> 24) & 0xff);
}

void OutputBuffer::writeString(std::string value) {
    //写入长度
    size_t numberOfBytes = value.size();
    writeInt32(numberOfBytes);
    //写入数据
    memcpy(m_buf + m_position, value.data(), numberOfBytes);
    m_position += numberOfBytes;
}

void OutputBuffer::writeData(InputBuffer *value) {
    //长度
    writeInt32(value->length());
    //数据
    size_t numberOfBytes = value->length();
    memcpy(m_buf + m_position, value->data(), numberOfBytes);
    m_position += numberOfBytes;
}
