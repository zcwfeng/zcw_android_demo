package com.xiangxue.network.observer;

import com.xiangxue.network.errorhandler.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import top.zcwfeng.base.customview.mvvm.model.BaseMvvmModel;
import top.zcwfeng.base.customview.mvvm.model.MvvmDataObserve;

public class BaseObserver<T> implements Observer<T> {
    BaseMvvmModel baseModel;
    MvvmDataObserve<T> mvvmDataObserve;

    public BaseObserver(BaseMvvmModel baseModel, MvvmDataObserve<T> mvvmDataObserve) {
        this.baseModel = baseModel;
        this.mvvmDataObserve = mvvmDataObserve;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (baseModel != null) {
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onNext(T t) {
        mvvmDataObserve.onSuccess(
                t, false);
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ExceptionHandle.ResponeThrowable){
            mvvmDataObserve.onFailure(e);
        } else {
            mvvmDataObserve.onFailure(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }

}
