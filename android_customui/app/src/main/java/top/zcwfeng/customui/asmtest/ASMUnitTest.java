package top.zcwfeng.customui.asmtest;


import org.junit.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ASMUnitTest {
    @Test
    public void test() {
        try {
            FileInputStream fis = new FileInputStream("/Users/zcw/dev/workspace_android/temp/zcw_android_demo/android_customui/app/src/main/java/top/zcwfeng/customui/asmtest/InjectTest.class");

            // 获取分析器，读取class
            ClassReader reader = new ClassReader(fis);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

            reader.accept(new MyClassVisitor(Opcodes.ASM8, writer), ClassReader.EXPAND_FRAMES);

            byte[] bytes = writer.toByteArray();
            FileOutputStream fout = new FileOutputStream("/Users/zcw/dev/workspace_android/temp/zcw_android_demo/android_customui/app/src/main/java/top/zcwfeng/customui/asmtest/InjectTest2.class");
            fout.write(bytes);
            fout.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor(int api) {
            super(api);
        }

        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new MyMethordVisitor(api, methodVisitor, access, name, descriptor);
        }
    }

    /**
     * 用来访问方法信息
     */
    static class MyMethordVisitor extends AdviceAdapter {


        /**
         * Constructs a new {@link AdviceAdapter}.
         *
         * @param api           the ASM API version implemented by this visitor. Must be one of {@link
         *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
         * @param methodVisitor the method visitor to which this adapter delegates calls.
         * @param access        the method's access flags (see {@link Opcodes}).
         * @param name          the method's name.
         * @param descriptor    the method's descriptor (see {@link Type Type}).
         */
        protected MyMethordVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        // 方法进入时
        int s;//局部变量表的下标

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();

            if(!inject) {
                return;
            }
//            INVOKESTATIC java/lang/System.currentTimeMillis ()J
//            LSTORE 1
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            s = newLocal(Type.LONG_TYPE);
            storeLocal(s);

        }

        // ASM Viewer 插件---》对照ASM Outline 插件拷贝过来修改
        int e;

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            if(!inject) {
                return;
            }
            //            INVOKESTATIC java/lang/System.currentTimeMillis ()J
//            LSTORE 1
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            e = newLocal(Type.LONG_TYPE);
            storeLocal(e);

//            GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
//            NEW java/lang/StringBuilder
//            DUP
            visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            visitTypeInsn(NEW, "java/lang/StringBuilder");
            visitInsn(DUP);
//            INVOKESPECIAL java/lang/StringBuilder.<init> ()V
//            INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//            LDC "execute time="
            visitLdcInsn("execute time=");
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//            visitVarInsn(LLOAD, 3);
//            visitVarInsn(LLOAD, 1);
            loadLocal(e);
            loadLocal(s);
//            visitInsn(LSUB);
            math(SUB, Type.LONG_TYPE);

//            INVOKEVIRTUAL java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
//            INVOKEVIRTUAL java/lang/StringBuilder.toString ()Ljava/lang/String;
//            INVOKEVIRTUAL java/io/ PrintStream.println (Ljava/lang/String;)V
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }

        boolean inject = false;
        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            System.out.println(getName() + "----->" + descriptor);
            if("Ltop/zcwfeng/customui/asmtest/ASMTest;".equals(descriptor)) {
                inject = true;
            }
            return super.visitAnnotation(descriptor, visible);
        }
    }

}
