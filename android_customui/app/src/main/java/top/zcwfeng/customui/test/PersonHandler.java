package top.zcwfeng.customui.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class PersonHandler implements InvocationHandler {
    private Object mTarget;
    public void setTarget(Object target){
        mTarget = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(mTarget,args);

        return proxy;
    }
}
