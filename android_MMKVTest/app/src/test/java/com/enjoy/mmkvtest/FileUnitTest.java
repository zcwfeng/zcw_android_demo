package com.enjoy.mmkvtest;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FileUnitTest {
    @Test
    public void testFileIO() throws IOException {

        FileInputStream fis = new FileInputStream("D:\\enjoy\\open\\MMKV\\wechat.apk");
        FileOutputStream fos = new FileOutputStream("D:\\enjoy\\open\\MMKV\\wechat2.apk");
        int len;
        byte[] buffer = new byte[4096];

        while ((len = fis.read(buffer)) != -1){
            fos.write(buffer,0,len);
        }

        fis.close();
        fos.close();

    }




    @Test
    public void testFileChannel() throws IOException {
        FileChannel inChannel =
                new FileInputStream("D:\\enjoy\\open\\MMKV\\wechat.apk").getChannel();
        FileChannel outChannel =
                new FileOutputStream("D:\\enjoy\\open\\MMKV\\wechat3.apk").getChannel();
        outChannel.transferFrom(inChannel,0,inChannel.size());

        inChannel.close();
        outChannel.close();
    }



}