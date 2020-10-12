/**
 * @author Lance
 * @date 2019/1/25
 */

#include "ProtoBuf.h"


ProtoBuf::ProtoBuf(int8_t *buf, int32_t size, bool isCopy) {
    m_size = size;
    m_position = 0;
    m_buf = buf;
    m_isCopy = isCopy;
    if (isCopy) {
        m_buf = static_cast<int8_t *>(malloc(size));
        memcpy(m_buf, buf, size);
    }
}

ProtoBuf::ProtoBuf(int32_t size) {
    m_position = 0;
    m_size = size;
    m_isCopy = true;
    if (size > 0) {
        m_buf = static_cast<int8_t *>(malloc(size));
    }
}


ProtoBuf::~ProtoBuf() {
    if (m_isCopy && m_buf) {
        free(m_buf);
    }
    m_buf = 0;
}

int32_t ProtoBuf::computeInt32Size(int32_t value) {

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

int32_t ProtoBuf::computeInt64Size(int64_t value) {
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

int32_t ProtoBuf::computeItemSize(std::string key, ProtoBuf *value) {
    int32_t keyLength = key.length();
    // 保存key的长度与key数据需要的字节
    int32_t size = keyLength + ProtoBuf::computeInt32Size(keyLength);
    // 加上保存value的长度与value数据需要的字节
    size += value->length() + ProtoBuf::computeInt32Size(value->length());
    return size;
}


int32_t ProtoBuf::computeMapSize(std::unordered_map<std::string, ProtoBuf *> map) {
    auto iter = map.begin();
    int32_t size = 0;
    for (; iter != map.end(); iter++) {
        auto key = iter->first;
        auto value = iter->second;
        size += computeItemSize(key, value);
    }
    return size;
}

std::string ProtoBuf::readString() {
    //获得字符串长度
    int32_t size = readInt32();
    //剩下的数据有这么多
    if (size <= (m_size - m_position) && size > 0) {
        std::string result((char *) m_buf + m_position, size);
        m_position += size;
        return result;
    }
    return "";
}

int32_t ProtoBuf::readInt32() {
    //int8_t -128 ~ 127
    int8_t tmp = readByte();
    // 读出来是负数 则最高位必为1
    // 而且本身protobuf编码最高位1也是 需要下一个字节，否则当前一个字节搞定
    if (tmp >= 0) {
        return tmp;
    }
    //获得低7位数据
    int32_t result = tmp & 0x7f;

#if 1
    int32_t i = 1;
    while (true) {
        tmp = readByte();
        if (tmp >= 0) {
            //最高位就是0 不用 &7f 了
            result |= tmp << (7 * i);
            break;
        }
        // 当前字节放到前面 与 result 合并
        //最高位为1,只拿低7位
        result |= (tmp & 0x7f) << (7 * i);
        i++;
    }
#else

    //第二个字节结束(最高位是0)
    if ((tmp = this->readByte()) >= 0) {
        //将上个字节低7位与当前字节拼起来
        result |= tmp << 7;
    } else {
        //第二个字节和第一个拼起来
        result |= (tmp & 0x7f) << 7;
        //读取第三个字节
        if ((tmp = this->readByte()) >= 0) {
            result |= tmp << 14;
        } else {
            result |= (tmp & 0x7f) << 14;
            if ((tmp = this->readByte()) >= 0) {
                result |= tmp << 21;
            } else {
                result |= (tmp & 0x7f) << 21;
                result |= (tmp = this->readByte()) << 28;
                //读完5个字节之后 还有数据 肯定就是负数了
                if (tmp < 0) {
                    // 比如 -1 ： int32 用 uint64 来编码 会变成 对 0xffffffffffffffff 编码
                    // 解析这个数据时，
                    for (int i = 0; i < 5; i++) {
                        if (this->readByte() >= 0) {
                            return result;
                        }
                    }
                }
            }
        }
    }
#endif
    return result;
}

int64_t ProtoBuf::readInt64() {
    //int8_t -128 ~ 127
    int8_t tmp = readByte();
    // 读出来是负数 则最高位必为1需要下一个字节，否则当前一个字节搞定
    if (tmp >= 0) {
        return tmp;
    }
    //获得低7位数据
    int64_t result = tmp & 0x7f;

    int32_t i = 1;
    while (true) {
        tmp = readByte();
        if (tmp >= 0) {
            //需要强转下
            result |= (int64_t) tmp << (7 * i);

            break;
        }
        result |= (int64_t) (tmp & 0x7f) << (7 * i);
        i++;
    }
    return result;
}

float ProtoBuf::readFloat() {
    int8_t b1 = readByte();
    int8_t b2 = readByte();
    int8_t b3 = readByte();
    int8_t b4 = readByte();
    int32_t value =
            (((int32_t) b1 & 0xff)) | (((int32_t) b2 & 0xff) << 8) | (((int32_t) b3 & 0xff) << 16) |
            (((int32_t) b4 & 0xff) << 24);
#if 0
    union Converter{
        int32_t first;
        float sencond;
    };
    Converter converter;
    converter.first = value;
    float returnValue = converter.sencond;
#else
    float returnValue = *(float *) &value;
#endif

    return returnValue;
}


int8_t ProtoBuf::readByte() {
    if (m_position == m_size) {
        return 0;
    }
    return m_buf[m_position++];
}


ProtoBuf *ProtoBuf::readData() {
    //获得数据长度
    int32_t size = readInt32();
    //有效
    if (size <= m_size - m_position && size > 0) {
        ProtoBuf *data = new ProtoBuf(m_buf + m_position, size, true);
        m_position += size;
        return data;
    }
    return 0;
}

void ProtoBuf::writeByte(int8_t value) {
    if (m_position == m_size) {
        //满啦，出错啦
        return;
    }
    //将byte放入数组
    m_buf[m_position++] = value;
}


void ProtoBuf::writeInt32(int32_t value) {
#if 0
    while (true) {
        // 0x7f  = 00000000 00000000 00000000 01111111 = 0x0000007f  因为与int32t与计算0x7f前补0
        // ~0x7f = 11111111 11111111 11111111 10000000 = 0xFFFFFF80
        // value & ~0x7f == 0 表示小于 10000000 则为true，即判断是不是只要7位就可以表示
        if ((value & ~0x7f) == 0) {
            writeByte(value);
            return;
        } else {
            //大于7位，则先记录低7位，并且将最高位置为1
            //1、& 0x7F 获得低7位数据
            //2、| 0x80 让最高位变成1，表示超过1个字节记录整个数据
            writeByte((value & 0x7F) | 0x80);
            //7位已经写完了，处理更高位的数据
            value >>= 7;
        }
    }
#else
    uint32_t i = value;
    while (true) {
        // 0x7f  = 00000000 00000000 00000000 01111111 = 0x0000007f  因为与int32t与计算0x7f前补0
        // ~0x7f = 11111111 11111111 11111111 10000000 = 0xFFFFFF80
        // i & ~0x7f == 0 表示小于 10000000 则为true，即判断是不是只要7位就可以表示
        if (i <= 0x7f) {
            writeByte(i);
            return;
        } else {
            //大于7位，则先记录低7位，并且将最高位置为1
            //1、& 0x7F 获得低7位数据
            //2、| 0x80 让最高位变成1，表示超过1个字节记录整个数据
            writeByte((i & 0x7F) | 0x80);
            i >>= 7;
        }
    }
#endif
}

void ProtoBuf::writeInt64(int64_t value) {
    uint64_t i = value;
    while (true) {
        if ((i & ~0x7f) == 0) {
            writeByte(i);
            return;
        } else {
            writeByte((i & 0x7F) | 0x80);
            i >>= 7;
        }
    }
}

void ProtoBuf::writeFloat(float value) {
    //float 4字节 转为int32处理
    //float直接转int会出现精度丢失，可以用共用体或者指针修改
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

void ProtoBuf::writeString(std::string value) {
    size_t numberOfBytes = value.size();
    writeInt32(numberOfBytes);
    memcpy(m_buf + m_position, value.data(), numberOfBytes);
    m_position += numberOfBytes;
}

void ProtoBuf::writeData(ProtoBuf *data) {
    writeInt32(data->length());

    size_t numberOfBytes = data->length();
    memcpy(m_buf + m_position, data->getBuf(), numberOfBytes);
    m_position += numberOfBytes;
}

void ProtoBuf::restore() {
    m_position = 0;
}











