package com.enjoy.asm.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.InputStreamReader;

public class ClassInjectTimeVisitor extends ClassVisitor {

    private String className;

    public ClassInjectTimeVisitor(ClassVisitor cv, String fileName) {
        super(Opcodes.ASM5, cv);
        className = fileName.substring(0,fileName.lastIndexOf("."));
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature,
                exceptions);
        return new MethodAdapterVisitor(mv, access, name, desc,className);
    }

}