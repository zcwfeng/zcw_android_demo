package top.zcwfeng.jetpack.mvp.presenter;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.ref.WeakReference;

import top.zcwfeng.jetpack.mvp.view.IBaseView;

public class BasePresenter<T extends IBaseView> implements LifecycleObserver {
    public WeakReference<T> iGoodsView;
    /**
     * 绑定view
     */
    public void attachView(T view){
        iGoodsView=new WeakReference<>(view);
    }
    /**
     * 解绑
     */
    public void detachView(){
        if(iGoodsView!=null){
            iGoodsView.clear();
            iGoodsView=null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateX(LifecycleOwner owner) {

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStartX(LifecycleOwner owner) {
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(LifecycleOwner owner) {
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(LifecycleOwner owner) {
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(LifecycleOwner owner) {
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(LifecycleOwner owner) {
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner owner) {
    }
}