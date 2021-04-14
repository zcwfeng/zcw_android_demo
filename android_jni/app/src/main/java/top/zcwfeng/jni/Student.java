package top.zcwfeng.jni;

import android.util.Log;

public class Student {

    private final static String TAG = Student.class.getSimpleName();

    public String name;
    public int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Log.d(TAG, "Java setName name:" + name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        Log.d(TAG, "Java setAge age:" + age);
        this.age = age;
    }

    public static void showInfo(String info) {
        Log.d(TAG, "showInfo info:" + info);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}