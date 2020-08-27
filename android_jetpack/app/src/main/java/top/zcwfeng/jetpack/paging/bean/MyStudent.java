package top.zcwfeng.jetpack.paging.bean;

import java.util.Objects;

public class MyStudent {
    private String id;
    private String sex;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyStudent)) return false;
        MyStudent myStudent = (MyStudent) o;
        return id.equals(myStudent.id) &&
                sex.equals(myStudent.sex) &&
                name.equals(myStudent.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sex, name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
