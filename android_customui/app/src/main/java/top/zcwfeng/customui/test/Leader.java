package top.zcwfeng.customui.test;

public class Leader implements IPerson{
    IPerson iPerson;

    public Leader(IPerson iPerson) {
        this.iPerson = iPerson;
    }

    @Override
    public void say(String what) {
        System.out.println("我领导，我叫我去说");
        iPerson.say(what);
        System.out.println("事情干完了");
    }

    @Override
    public void walk(String where) {
        System.out.println("我领导，我叫我手走过去");
        iPerson.walk(where);
        System.out.println("事情干完了");
    }
}