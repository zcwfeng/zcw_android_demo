package top.zcwfeng.mvp.presenter;

import java.lang.ref.WeakReference;

public class BasePresenter<T> {
    protected WeakReference<T> mViewRef;

    public void onAttach(T view){
        this.mViewRef = new WeakReference<>(view);
    }

    public void onDetach(){
        mViewRef.clear();
    }
}
