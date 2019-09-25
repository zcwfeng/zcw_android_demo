package top.zcwfeng.usedatas;

public class UserType {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    private String name;

    private int age;

    private boolean sex;

    public UserType(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    public UserType() {
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