package com.enjoy.asminject.example;

import com.enjoy.asminject.ASMTest;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

/**
 * AdviceAdapter: 子类
 * 对methodVisitor进行了扩展， 能让我们更加轻松的进行方法分析
 */
public class MethodAdapterVisitor extends AdviceAdapter {

    private boolean inject;

    protected MethodAdapterVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }


    /**
     * 分析方法上面的注解
     * 在这里干嘛？？？
     * <p>
     * 判断当前这个方法是不是使用了injecttime，如果使用了，我们就需要对这个方法插桩
     * 没使用，就不管了。
     *
     * @param desc
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(ASMTest.class).equals(desc)) {
            System.out.println(desc);
            inject = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    private int start, end;

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        if (inject) {
            //执行完了怎么办？ 记录到本地变量中
            invokeStatic(Type.getType("Ljava/lang/System;"),
                    new Method("currentTimeMillis", "()J"));

            start = newLocal(Type.LONG_TYPE); //创建本地 LONG类型变量
            //记录 方法执行结果给创建的本地变量
            storeLocal(start);
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (inject){
            invokeStatic(Type.getType("Ljava/lang/System;"),
                    new Method("currentTimeMillis", "()J"));
            end = newLocal(Type.LONG_TYPE);
            storeLocal(end);

            getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io" +
                    "/PrintStream;"));

            //分配内存 并dup压入栈顶让下面的INVOKESPECIAL 知道执行谁的构造方法创建StringBuilder
            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            dup();
            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"),new Method("<init>","()V"));


            visitLdcInsn("execute:");
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

            //减法
            loadLocal(end);
            loadLocal(start);
            math(SUB,Type.LONG_TYPE);


            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append","(J)Ljava/lang/StringBuilder;"));
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("toString","()Ljava/lang/String;"));
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new Method("println","(Ljava/lang/String;)V"));

        }
    }
}