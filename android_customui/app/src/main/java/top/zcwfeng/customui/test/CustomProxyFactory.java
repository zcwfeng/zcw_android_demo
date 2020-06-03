package top.zcwfeng.customui.test;

import java.lang.reflect.Proxy;

class CustomProxyFactory {

    public static Object getProxy(Object target) {
        AOPPersonHandler handler = new AOPPersonHandler();
        handler.setTarget(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }
}
