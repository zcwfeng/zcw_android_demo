package com.enjoy.asminject;


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
import java.io.FileOutputStream;
import java.io.IOException;

public class ASMUnitTest {


    @Test
    public void test() throws IOException {
        /**
         * 1、获得待插桩的字节码数据
         */
        FileInputStream fis = new FileInputStream("F:\\Lance\\ASM\\ASMInject\\app\\src\\test\\java\\com\\enjoy\\asminject\\InjectTest.class");

        /**
         * 2、执行插桩
         */
        ClassReader cr = new ClassReader(fis);  // class解析器
        //执行解析
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cr.accept(new MyClassVisitor(Opcodes.ASM7, classWriter), 0);

        /**
         * 3、输出结果
         */
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream fos = new FileOutputStream("F:\\Lance\\ASM\\ASMInject\\app\\src\\test\\java2\\com\\enjoy\\asminject\\InjectTest.class");
        fos.write(bytes);
        fos.close();
    }


    static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new MyMethodVisitor(api, methodVisitor, access, name, descriptor);
        }
    }

    static class MyMethodVisitor extends AdviceAdapter {
        int startTime;
        boolean isInject = false;

        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (descriptor.equals("Lcom/enjoy/asminject/ASMTest;")) {
                isInject = true;
            }
            return super.visitAnnotation(descriptor, visible);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            if (!isInject){
                return;
            }
            // long l = System.currentTimeMillis();
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            startTime = newLocal(Type.LONG_TYPE);
            storeLocal(startTime);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            if (!isInject){
                return;
            }
//        long e = System.currentTimeMillis();
//        System.out.println("execute:"+(e-l)+" ms.");


            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            int endTime = newLocal(Type.LONG_TYPE);
            storeLocal(endTime);

            getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));
            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            dup();

            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));

            visitLdcInsn("execute:");

            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

            loadLocal(endTime);
            loadLocal(startTime);
            math(SUB, Type.LONG_TYPE);

            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("toString", "()Ljava/lang/String;"));
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"), new Method("println", "(Ljava/lang/String;)V"));

        }
    }

}
