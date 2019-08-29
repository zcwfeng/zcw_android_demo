package top.zcwfeng.libcompiler;

import java.util.HashMap;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 方法自动生成
 */
public class MethodInfo {

    public final String packageName;
    public final String className;
    public HashMap<Integer, String> grantMethodMap = new HashMap<>();
    public HashMap<Integer, String> deniedMethodMap = new HashMap<>();
    public HashMap<Integer, String> rationalMethodMap = new HashMap<>();

    private static final String PROXY_NAME = "PermissionProxy";
    public String fileName;
    public MethodInfo(Elements elementUtils, TypeElement typeElement){
        PackageElement packageElement = elementUtils.getPackageOf(typeElement);
        packageName = packageElement.getQualifiedName().toString();
        className = ClassValidator.getClassName(typeElement,packageName);
        fileName = className + "$$" + PROXY_NAME;
    }

    public String getnerateJavacode(){
        StringBuilder builder = new StringBuilder();
        builder.append("//do not modify\n");//注释
        builder.append("package ").append(packageName).append(";\n\n");//top.zcwfeng.package;
        builder.append("import top.zcwfeng.libpermissionhelper.*;");
        builder.append("\n");
        builder.append("public class ")
                .append(fileName).append(" implements " + PROXY_NAME + "<"+ className +">");
        builder.append("{\n");

        generateMethod(builder);


        builder.append("\n}");
        return builder.toString();

    }

    private void generateMethod(StringBuilder builder) {
        generateGrantMethod(builder);
        generateDeniedMethod(builder);
        generateRationalMethod(builder);
    }


    private void generateGrantMethod(StringBuilder builder){
        //public void grant(MainActivity source, String[] permissions)
        //switch (requestCode) {
        //            case RESULT_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE: {
        builder.append("@Override\n");
        builder.append("public void grant(int requestCode," + className
                +" source,String[] permissions){\n" );

        builder.append("switch(requestCode){");

        for(int requestCode:grantMethodMap.keySet()){
            builder.append("case " + requestCode + ":");

            builder.append("source." + grantMethodMap.get(requestCode) + "(permissions);");
            builder.append("break;");
        }

        builder.append("}\n}\n");

    }

    private void generateDeniedMethod(StringBuilder builder){
        builder.append("@Override\n");
        builder.append("public void denied(int requestCode," + className + " source,String[] permissions){\n");
        builder.append("switch(requestCode){");
        for(int requestCode:deniedMethodMap.keySet()){
            builder.append("case " + requestCode + ":");
            builder.append("source." + deniedMethodMap.get(requestCode) + "(permissions);\n");

            builder.append("break;\n");

        }

        builder.append("}\n}\n");


    }

    private void generateRationalMethod(StringBuilder builder){
        builder.append("@Override\n");
        builder.append("public boolean rational(int requestCode," + className + " source,String[] permissions,PermissionCallback callback){\n");
        builder.append("switch(requestCode){");
        for(int requestCode:rationalMethodMap.keySet()){
            builder.append("case " + requestCode + ":");
            builder.append("source." + rationalMethodMap.get(requestCode) + "(permissions,callback);\n");
            builder.append("return true;\n");

        }
        builder.append("}\n");
        builder.append("return false;\n}");


    }
}