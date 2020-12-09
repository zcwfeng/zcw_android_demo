package com.enjoy.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

class MethodAdapterVisitor extends AdviceAdapter {

    private String className;
    private String methodName;
    private boolean inject;
    private int index;
    private int start, end;

    protected MethodAdapterVisitor(MethodVisitor mv, int access, String name, String desc,
                                   String className) {
        super(Opcodes.ASM5, mv, access, name, desc);
        methodName = name;
        this.className = className;
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        //记录方法是不是被 注解
        if ("Lcom/enjoy/asminject/InjectTime;".equals(desc)) {
            inject = true;
        }
        return super.visitAnnotation(desc, visible);
    }


    @Override
    protected void onMethodEnter() {
        if (inject) {
//                0: invokestatic #2 // Method java/lang/System.currentTimeMillis:()J
//                3: lstore_1
            //储备本地变量备用
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            index = newLocal(Type.LONG_TYPE);
            start = index;
            mv.visitVarInsn(LSTORE, start);
        }

    }


    @Override
    protected void onMethodExit(int opcode) {
        if (inject) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            index = newLocal(Type.LONG_TYPE);
            end = index;
            mv.visitVarInsn(LSTORE, end);

            // getstatic #3 // Field java/lang/System.out:Ljava/io/PrintStream;
            //获得静态成员 out
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

            // new #4 // class java/lang/StringBuilder
            // 引入类型 分配内存 并dup压入栈顶让下面的INVOKESPECIAL 知道执行谁的构造方法
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);

            //invokevirtual #7   // Method java/lang/StringBuilder.append:
            // (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 执行构造方法
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>",
                    "()V", false);

            // ldc #6 // String execute:
            // 把常量压入栈顶 后面使用
            mv.visitLdcInsn("==========>" + className + " execute " + methodName + ": ");

            //invokevirtual #7 // Method java/lang/StringBuilder.append: (Ljava/lang/String;)
            // Ljava/lang/StringBuilder;
            // 执行append方法，使用栈顶的值作为参数
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                    "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

            // lload_3 获得存储的本地变量
            // lload_1
            // lsub   减法指令
            mv.visitVarInsn(LLOAD, end);
            mv.visitVarInsn(LLOAD, start);
            mv.visitInsn(LSUB);

            // invokevirtual #8 // Method java/lang/StringBuilder.append:(J)
            // Ljava/lang/StringBuilder;
            // 把减法结果append
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                    "(J)Ljava/lang/StringBuilder;", false);

            //append "ms."
            mv.visitLdcInsn("ms.");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                    "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

            //tostring
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString",
                    "()Ljava/lang/String;", false);

            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                    "(Ljava/lang/String;)V", false);
        }
    }
}