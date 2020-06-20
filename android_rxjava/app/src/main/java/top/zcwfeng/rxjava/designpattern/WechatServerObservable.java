package top.zcwfeng.rxjava.designpattern;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.rxjava.Flag;

// 微信公众号
public
class WechatServerObservable implements Observable{
    String message;
    List<Observer> mlist = new ArrayList<>();

    @Override
    public void addOberver(Observer observer) {
        mlist.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        mlist.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer:mlist){
            observer.update(message);
        }
    }

    @Override
    public void pushMessages(String message) {
        Log.d(Flag.TAG, "pushMessages: 服务端push message");
        this.message = message;
        notifyObservers();
    }
}
