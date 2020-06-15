package top.zcwfeng.android_apt.bean;

import java.io.Serializable;

public class UserSerializable implements Serializable {
    String name;

    public UserSerializable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserSerializable{" +
                "name='" + name + '\'' +
                '}';
    }
}
