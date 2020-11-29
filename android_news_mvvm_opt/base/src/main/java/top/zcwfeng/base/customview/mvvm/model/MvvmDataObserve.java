package top.zcwfeng.base.customview.mvvm.model;

public
interface MvvmDataObserve<F> {
    void onSuccess(F t,boolean isFromcache);
    void onFailure(Throwable e);
}
