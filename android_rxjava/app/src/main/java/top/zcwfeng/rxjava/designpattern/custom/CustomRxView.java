package top.zcwfeng.rxjava.designpattern.custom;

import android.view.View;

import io.reactivex.Observable;

public
class CustomRxView {

    public static Observable<Object> onClickProxy(View view){
        return  new ViewClickProxyObservable(view);
    }
}
