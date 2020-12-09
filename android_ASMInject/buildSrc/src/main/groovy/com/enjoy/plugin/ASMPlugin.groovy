package com.enjoy.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ASMPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.getByType(AppExtension.class).registerTransform(new ASMTransform())
    }
}