package top.zcwfeng.dbdemo.db;

public class Student {
    private String name;
    private int age;
    private String grade;
    //Version2添加
    private String sex;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    private String store;

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

   public Student(){}

    public Student(String name, int age, String grade, String sex,String store) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.sex = sex;
        this.store=store;
    }
}