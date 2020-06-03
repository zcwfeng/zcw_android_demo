package top.zcwfeng.customui.asmtest.visitortest;

import java.util.Random;

class Engineer extends Staff{

    public Engineer(String name) {
        super(name);
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getCodeLines(){
        return new Random().nextInt(10 * 10000);
    }
}
