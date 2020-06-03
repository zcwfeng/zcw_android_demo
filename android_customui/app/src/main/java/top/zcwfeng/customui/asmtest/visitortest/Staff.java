package top.zcwfeng.customui.asmtest.visitortest;

import java.util.Random;

public abstract class Staff {
    public String name;
    public int kpi;

    public Staff(String name) {
        this.name = name;
        this.kpi = new Random().nextInt(10);
    }

    abstract void accept(Visitor visitor);
}
