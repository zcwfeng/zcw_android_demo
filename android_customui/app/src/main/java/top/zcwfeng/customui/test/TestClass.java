package top.zcwfeng.customui.test;

import java.lang.reflect.Proxy;

class TestClass {
//    IPerson iPerson;
//
//    public void HookUser() {
//        iPerson = new Worker();
//    }
//
//    public void doWhat() {
//        iPerson.say("我是工人");
//
//    }

    public static void main(String[] args) {
        final Leader leader = new Leader(new Worker());
        PersonHandler handler = new PersonHandler();
        handler.setTarget(leader);
        IPerson personImpl = (IPerson) Proxy.newProxyInstance(Leader.class.getClassLoader(),
                new Class[]{IPerson.class},
                handler);

        personImpl.say("我是工人");
        personImpl.walk("foot");

        IPerson target = new Worker();
        IPerson person = (IPerson) CustomProxyFactory.getProxy(target);
        person.say("worker");
        person.walk("walk");
    }
}