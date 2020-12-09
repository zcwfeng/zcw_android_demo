package com.enjoy.asm.plugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;
import com.google.common.collect.FluentIterable;

import org.apache.commons.codec.digest.DigestUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class ASMTransform extends Transform {
    @Override
    public String getName() {
        return "asm";
    }

    /**
     * 处理所有class
     *
     * @return
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * 范围是整个项目所有的类，包含依赖的库
     *
     * @return
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    //不使用增量
    @Override
    public boolean isIncremental() {
        return false;
    }

    /**
     * android插件将所有的class通过这个方法告诉给我们
     *
     * @param transformInvocation
     * @throws TransformException
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);

        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        //清理文件
        outputProvider.deleteAll();


        //得到所有的输入
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        for (TransformInput input : inputs) {
            // 处理class目录
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                // 直接复制输出到对应的目录
                String dirName = directoryInput.getName();
                File src = directoryInput.getFile();
                System.out.println("目录：" + src);
                String md5Name = DigestUtils.md5Hex(src.getAbsolutePath());
                File dest = outputProvider.getContentLocation(dirName + md5Name,
                        directoryInput.getContentTypes(), directoryInput.getScopes(),
                        Format.DIRECTORY);
                //todo 插桩
                processInject(src, dest);
            }
            // 处理jar（依赖）的class todo 先不处理了
            for (JarInput jarInput : input.getJarInputs()) {
                String jarName = jarInput.getName();
                File src = jarInput.getFile();
                System.out.println("jar包：" + src);
                String md5Name = DigestUtils.md5Hex(src.getAbsolutePath());
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4);
                }
                File dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
                FileUtils.copyFile(src, dest);
            }
        }
    }

    private void processInject(File src, File dest) throws IOException {
        String dir = src.getAbsolutePath();
        FluentIterable<File> allFiles = FileUtils.getAllFiles(src);
        for (File file : allFiles) {
            FileInputStream fis = new FileInputStream(file);
            //插桩
            ClassReader cr = new ClassReader(fis);
            // 写出器
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            //分析，处理结果写入cw
            cr.accept(new ClassInjectTimeVisitor(cw,file.getName()), ClassReader.EXPAND_FRAMES);

            byte[] newClassBytes = cw.toByteArray();
            String absolutePath = file.getAbsolutePath();
            String fullClassPath = absolutePath.replace(dir, "");
            File outFile = new File(dest, fullClassPath);
            FileUtils.mkdirs(outFile.getParentFile());
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(newClassBytes);
            fos.close();
        }

    }
}
