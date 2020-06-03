package top.zcwfeng.customui.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class ClassLoaderTest {
    public ClassLoaderTest() {
    }

    class Inner{

    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(ClassLoaderTest.class.getName());
        Constructor[] ctors = clazz.getDeclaredConstructors();
        Constructor[] publicctors = clazz.getConstructors();
        Method[] methods = clazz.getMethods();
        Annotation[] annotations = clazz.getAnnotations();
        Class[] clazzInners = clazz.getDeclaredClasses();
        Class<?> innerOuter = Class.forName("top.zcwfeng.customui.test.ClassLoaderTest$Inner");
        for (Constructor c : ctors) {
            System.out.println("class::ctors::" + c.getName());
        }
        for (Constructor c : publicctors) {
            System.out.println("class::publictors::"+ c.getName());
        }
        for (Method c : methods) {
            System.out.println("class::methods::" + c.getName());
        }
        for (Annotation c : annotations) {
            System.out.println("class::annotations::" + c.toString());
        }
        for (Class c:clazzInners){
            System.out.println("class::annotations::" + c.getDeclaringClass());
        }
        System.out.println("class::innerOuter::"+innerOuter.getPackage().getName());
        System.out.println("class::innerOuter::"+innerOuter.getSuperclass().getName());

    }
}
