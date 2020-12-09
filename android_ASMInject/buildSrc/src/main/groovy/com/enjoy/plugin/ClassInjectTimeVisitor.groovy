package com.enjoy.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class ClassInjectTimeVisitor extends ClassVisitor {

    String className

    ClassInjectTimeVisitor(ClassVisitor cv, String fileName) {
        super(Opcodes.ASM5, cv);
        className = fileName.substring(0, fileName.lastIndexOf("."))
    }


    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature,
                              String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature,
                exceptions)
        return new MethodAdapterVisitor(mv, access, name, desc, className)
    }

}