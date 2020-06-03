package top.zcwfeng.customui.test;

public class Worker implements IPerson {
    @Override
    public void say(String what) {
        System.out.println(what);
    }

    @Override
    public void walk(String where) {
        System.out.println(where);
    }
}