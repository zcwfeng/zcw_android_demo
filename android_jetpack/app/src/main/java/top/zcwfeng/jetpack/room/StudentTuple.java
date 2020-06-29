package top.zcwfeng.jetpack.room;

import androidx.room.ColumnInfo;

public class StudentTuple {
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "pwd")
    String password;

    @Override
    public String toString() {
        return "StudentTuple{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public StudentTuple(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
