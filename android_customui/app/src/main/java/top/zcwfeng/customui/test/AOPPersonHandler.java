package top.zcwfeng.customui.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class AOPPersonHandler implements InvocationHandler {
    private Object mTarget;

    public void setTarget(Object target) {
        mTarget = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        AOPUtil aop = new AOPUtil();
        aop.method1();
        Object object = method.invoke(mTarget, args);
        aop.method2();
        return object;
    }
}
