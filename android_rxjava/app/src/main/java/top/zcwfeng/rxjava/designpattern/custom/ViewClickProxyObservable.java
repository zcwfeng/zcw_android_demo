package top.zcwfeng.rxjava.designpattern.custom;

import android.os.Looper;
import android.view.View;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public
class ViewClickProxyObservable extends Observable<Object> {

    private View view;
    private static Object EVENT = new Object();

    public ViewClickProxyObservable(View view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        MyListenerObserver myListenerObserver = new MyListenerObserver(view,observer);
        observer.onSubscribe(myListenerObserver);
        this.view.setOnClickListener(myListenerObserver);
    }

    final static class MyListenerObserver implements View.OnClickListener, Disposable{
        private final View view;
        private final Observer<Object> objectObserver;// 存一份下一层
        private final AtomicBoolean isDisposable = new AtomicBoolean();

        public MyListenerObserver(View view, Observer objectObserver) {
            this.view = view;
            this.objectObserver = objectObserver;
        }

        @Override
        public void onClick(View v) {
            if(!isDisposed()){
                objectObserver.onNext(EVENT);
            }
        }

        @Override
        public void dispose() {
            if(isDisposable.compareAndSet(false,true)){
                if(Looper.myLooper() == Looper.getMainLooper()) {
                    view.setOnClickListener(null);
                }else {
                    //童工handler
//                    new Handler(Looper.getMainLooper()){
//                        @Override
//                        public void handleMessage(Message msg) {
//                            super.handleMessage(msg);
//                            view.setOnClickListener(null);
//                        }
//                    };

                    AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                        @Override
                        public void run() {
                            view.setOnClickListener(null);
                        }
                    });
                }
            }
        }

        @Override
        public boolean isDisposed() {

            return isDisposable.get();
        }
    }
}
