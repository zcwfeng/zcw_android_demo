package com.enjoy.mmkvtest;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void int_convert_float(){
        float i = 1.1f;
        int j = Float.floatToIntBits(i);
        float k = Float.intBitsToFloat(j);
        System.out.println(k);
    }


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        try {
            FileInputStream fis =
                    new FileInputStream(new File("C:\\Users\\86139\\Desktop\\mmkv.default"));

            //读4个字节  ： 总长度
            byte[] sizeBytes = new byte[4];
            fis.read(sizeBytes);

            //10
            int keylength = fis.read();
            //读出key
            byte[] b = new byte[keylength];
            fis.read(b);
            System.out.println(new String(b)); //key

            int valuelength = fis.read();
            b = new byte[valuelength];
            fis.read(b);
            System.out.println(Arrays.toString(b));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void testEncode() {
        int value = 0x80;
        while (true) {
            // (value & ~0x7f) == 0
            if (value <= 0x7f) {
                byte i = (byte) value;
                System.out.println("写入:" + i);
                return;
            } else {
                // 获得低7位数据
                int i = value & 0x7f;
                i = i | 0x80;
                System.out.println("写入:" + i);
                value >>= 7;
            }
        }

    }


    @Test
    public void testDecode() {
        byte[] value = {(byte) 255, (byte) 255,(byte) 255,(byte) 255,7};
        int index = 0;
        /**
         *  java byte 取值为 -128->127
         *  如果是128，则 java byte值被转换为 -128，129则为-127
         *  127为 0x7f，所以如果java byte的值小于0，则表示大于0x7f，需要下一个字节
         */
        byte tmp = value[index++];
        if (tmp >= 0) {
            System.out.println(tmp);
            return;
        }
        // 需要下一个字节
        // 最高位是标记位，所以获得低7位
        int result = tmp & 0x7f;
        tmp = value[index++];
        if (tmp >= 0) {
            //在第二个字节后面补7个0
            int i = tmp << 7;
            //第一个字节 + 第二个字节
            result = result | i;
            System.out.println(result);
            return;
        }


        //更多字节......

        //第二个字节拼到第一个字节前面
        result |= (tmp & 0x7f) << 7;
        //读取第三个字节
        tmp = value[index++];
        if (tmp >= 0) {
            result |= tmp << 14;
            System.out.println(result);
            return;
        }

        //第三个字节拼到前面
        result |= (tmp & 0x7f) << 14;
        tmp = value[index++];
        if (tmp >= 0) {
            result |= tmp << 21;
            System.out.println(result);
            return;
        }

        //第四个字节拼到前面
        result |= (tmp & 0x7f) << 21;
        tmp = value[index++];
        result |= tmp << 28;
        System.out.println(result);
    }
}