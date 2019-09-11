package top.zcwfeng.libcompiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import top.zcwfeng.libannotation.PermissionDenied;
import top.zcwfeng.libannotation.PermissionGrant;
import top.zcwfeng.libannotation.PermissionRational;

/**
 * 文件的生成
 */
@AutoService(Process.class)
public class RuntimePermissionAbstractProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Messager messager;
    private HashMap<String, MethodInfo> methodMap;
    private Filer filer;


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        methodMap.clear();
        messager.printMessage(Diagnostic.Kind.NOTE,"process start...");
        if(!handlerAnnotationInfo(roundEnv,PermissionRational.class))
        {
            return false;
        }
        if(!handlerAnnotationInfo(roundEnv,PermissionDenied.class)){
            return false;
        }
        if(!handlerAnnotationInfo(roundEnv,PermissionGrant.class)){
            return false;
        }

        for(String className:methodMap.keySet()) {
            MethodInfo methodInfo = methodMap.get(className);
            try {
                JavaFileObject sourceFile = filer.createSourceFile(methodInfo.packageName + "." + methodInfo.fileName);
                Writer writer = sourceFile.openWriter();
                writer.write(methodInfo.getnerateJavacode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE,"write file failed:"+e.getMessage());
            }

        }


        messager.printMessage(Diagnostic.Kind.NOTE,"process end...");

        return true;
    }

    private boolean handlerAnnotationInfo(RoundEnvironment roundEnv, Class<? extends Annotation> annotation) {

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
        for (Element e : elements) {
            if (!checkmethodValidator(e, annotation)) {
                return false;
            }

            ExecutableElement methodElement = (ExecutableElement) e;
            TypeElement encloseElement = (TypeElement) e.getEnclosingElement();
            String className = encloseElement.getQualifiedName().toString();

            MethodInfo methodInfo = methodMap.get(className);
            if (methodInfo == null) {
                methodInfo = new MethodInfo(elementUtils, encloseElement);
                methodMap.put(className, methodInfo);
            }


            Annotation annotationClazz = methodElement.getAnnotation(annotation);
            String methodName = methodElement.getSimpleName().toString();
            List<? extends VariableElement> paramters = methodElement.getParameters();

            if (paramters == null || paramters.size() < 1) {
                String message = "the method %s marked by annotation %s must have an unique parameter[String[]permission]";
                throw new IllegalArgumentException(String.format(message, methodName, annotationClazz.getClass().getSimpleName()));
            }

            if (annotationClazz instanceof PermissionGrant) {
                int requestCode = ((PermissionGrant) annotationClazz).value();
                methodInfo.grantMethodMap.put(requestCode, methodName);
            } else if (annotationClazz instanceof PermissionDenied) {
                int requestCode = ((PermissionDenied) annotationClazz).value();
                methodInfo.deniedMethodMap.put(requestCode, methodName);
            } else if (annotationClazz instanceof PermissionRational) {
                int requestCode = ((PermissionRational) annotationClazz).value();
                methodInfo.rationalMethodMap.put(requestCode, methodName);
            }
        }

        return true;

    }



    private boolean checkmethodValidator(Element e, Class<? extends Annotation> permissionClass) {
        if(e.getKind()!= ElementKind.METHOD){
            return false;
        }

        if(ClassValidator.isAbstract(e) || ClassValidator.isPrivate(e))
        {
            return false;
        }

        return true;
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        methodMap = new HashMap<>();
        filer = processingEnv.getFiler();// 帮助生成文件的
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportList = new HashSet<>();
        supportList.add(PermissionGrant.class.getCanonicalName());
        supportList.add(PermissionDenied.class.getCanonicalName());
        supportList.add(PermissionRational.class.getCanonicalName());
        return supportList;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}

