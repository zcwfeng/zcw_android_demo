package top.zcwfeng.rxjava.designpattern;

import android.util.Log;

import top.zcwfeng.rxjava.Flag;

// 观察者实现-----微信公众好关注个人
public class UserPerson implements Observer {
    String name;
    String message;// 为了了解原理，可以用泛型

    public UserPerson(String name) {
        this.name = name;
    }

    @Override
    public void update(Object value) {
        message = (String) value;
        Log.d(Flag.TAG, "update: " + value);
    }
}
