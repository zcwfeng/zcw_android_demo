package top.zcwfeng.usedatas;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName(value = "userName",alternate = {"user_name", "Name"})
    private String name;

    private int age;

    private boolean sex;

    public User(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

}