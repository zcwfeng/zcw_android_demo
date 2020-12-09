package com.enjoy.plugin

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.collect.FluentIterable
import org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter;


class ASMTransform extends Transform {
    @Override
    String getName() {
        return "asm";
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        //清理文件
        outputProvider.deleteAll()


        def inputs = transformInvocation.inputs
        inputs.each {
            def directoryInputs = it.directoryInputs
            directoryInputs.each {
                String dirName = it.name
                File src = it.getFile();
                println("目录：" + src)
                String md5Name = DigestUtils.md5Hex(src.absolutePath)
                File dest = outputProvider.getContentLocation(dirName + md5Name,
                        it.contentTypes, it.scopes,
                        Format.DIRECTORY);
                //插桩
                processInject(src, dest);
            }
        }
    }

    void processInject(File src, File dest) {
        String dir = src.absolutePath
        FluentIterable<File> allFiles = FileUtils.getAllFiles(src)
        for (File file : allFiles) {
            FileInputStream fis = new FileInputStream(file)
            //插桩
            ClassReader cr = new ClassReader(fis)
            // 写出器
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
            //分析，处理结果写入cw
            cr.accept(new ClassInjectTimeVisitor(cw, file.name), ClassReader.EXPAND_FRAMES)

            byte[] newClassBytes = cw.toByteArray()
            //class文件绝对地址
            String absolutePath = file.absolutePath
            //class文件绝对地址去掉目录，得到全类名
            String fullClassPath = absolutePath.replace(dir, "")
            File outFile = new File(dest, fullClassPath)
            FileUtils.mkdirs(outFile.parentFile)
            FileOutputStream fos = new FileOutputStream(outFile)
            fos.write(newClassBytes)
            fos.close()
        }

    }
}
