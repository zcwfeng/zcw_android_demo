package com.enjoy.asminject.example;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class InjectTimeUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {

        /**
         * 1、准备待分析的class
         */
        FileInputStream fis = new FileInputStream
                ("src/test/java/com/enjoy/asminject/InjectTest.class");

        /**
         * 2、执行分析与插桩
         */
        //class阅读器
        ClassReader cr = new ClassReader(fis);
        // 写出器 COMPUTE_FRAMES 计算所有的内容，后续操作更简单
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        //分析，处理结果写入cw EXPAND_FRAMES 以扩展栈帧解析(android中会加入一些扩展数据所以必须使用此参数)
        cr.accept(new ClassAdapterVisitor(cw), ClassReader.EXPAND_FRAMES);


        /**
         * 3、输出
         */
        byte[] newClassBytes = cw.toByteArray();
        File file = new File("D:/enjoy/open/ASM/ASMInject/app/src/test/java2/com/enjoy/asminject/");
        file.mkdirs();

        FileOutputStream fos = new FileOutputStream
                ("D:/enjoy/open/ASM/ASMInject/app/src/test/java2/com/enjoy/asminject/InjectTest" +
                        ".class");
        fos.write(newClassBytes);

        fos.close();
    }


    /**
     * 获取  ClassReader 分析结果的类
     */
    static class ClassAdapterVisitor extends ClassVisitor {

        public ClassAdapterVisitor(ClassVisitor cv) {
            super(Opcodes.ASM7, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                         String[] exceptions) {
            System.out.println("方法:" + name + " 签名:" + desc);

            MethodVisitor mv = super.visitMethod(access, name, desc, signature,
                    exceptions);
            return new MethodAdapterVisitor(api,mv, access, name, desc);
        }
    }

}