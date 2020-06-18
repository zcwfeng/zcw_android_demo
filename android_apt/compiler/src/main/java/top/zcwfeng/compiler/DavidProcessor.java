package top.zcwfeng.compiler;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
@SupportedAnnotationTypes("top.zcwfeng.android_apt.annotation.DavidAnnotation")
public class DavidProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager message = processingEnv.getMessager();
        message.printMessage(Diagnostic.Kind.NOTE,"=============");
        System.out.println("------------------test--------------");
        return false;
    }


}