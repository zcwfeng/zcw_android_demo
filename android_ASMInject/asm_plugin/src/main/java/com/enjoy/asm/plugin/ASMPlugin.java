package com.enjoy.asm.plugin;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ASMPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        BaseExtension android = project.getExtensions().getByType(BaseExtension.class);

        // android 插件 能够获得所有的class
        // 同时他提供一个接口，能够让我们也获得所有class
        android.registerTransform(new ASMTransform());

    }
}
