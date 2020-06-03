package top.zcwfeng.customui.asmtest.visitortest;

import java.util.Random;

class Manager extends Staff {
    public Manager(String name) {
        super(name);
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getProducts(){
        return new Random().nextInt(10);
    }
}
