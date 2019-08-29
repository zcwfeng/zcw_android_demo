package top.zcwfeng.libcompiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class ClassValidator {

    public static boolean isPrivate(Element element){
        return element.getModifiers().contains(Modifier.PRIVATE);
    }

    public static boolean isAbstract(Element element){
        return element.getModifiers().contains(Modifier.ABSTRACT);
    }

    public static String getClassName(TypeElement typeElement, String packageName) {
        int packageLen = packageName.length();
        String className = typeElement.getQualifiedName().toString().substring(packageLen).replace(".","");
        return className;
    }
}
