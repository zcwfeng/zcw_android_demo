package top.zcwfeng.rxjava.hook;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zcwfeng.rxjava.Flag;
import top.zcwfeng.rxjava.R;


/**
 * 1. Observer 源码
 * 2. Observable 创建过程
 * new ObservableCreate<T>(source)------自定义Observable source
 * 3. subscribe 订阅流程
 * source
 * ----ObservableCreate
 *      ---new CreateEmitter(observer)
 *          onSubscribe
 *
 *
 */
public class RxSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_design);
        hook();
    }

    private void hook() {
        //hook
//        RxJavaPlugins.setOnObservableAssembly(new Function<Observable, Observable>() {
//            @Override
//            public io.reactivex.Observable apply(Observable maybe) throws Exception {
//                Log.d(Flag.TAG, "apply: 全局项目监听" + maybe);
//                return maybe;
//            }
//        });
//
//        // hook
//        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
//            @Override
//            public Scheduler apply(Scheduler scheduler) throws Exception {
//                Log.d(Flag.TAG, "apply: hook setIoSchedulerHandler");
//                return scheduler;
//            }
//        });

        testHook();
        test2();
    }

    public void testHook() {

        //2.创建过程
        Observable.create(
                // 自定义source
                new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                Log.d(Flag.TAG, "ObservableEmitter: " + Thread.currentThread().getName());

                emitter.onNext("来Hook");
                emitter.onComplete();
            }
        })
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object s) throws Exception {
                        Log.d(Flag.TAG, "apply:----- map");
                        return "Hook Map";
                    }
                })
                .subscribeOn(Schedulers.io())// 线程池
                .observeOn(AndroidSchedulers.mainThread())// Looper.mainLooper切换
                // 3.订阅过程-----ObservableCreate.subscrib
                .subscribe(
                        //自定义观察者
                        new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(Flag.TAG, "onSubscribe: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(Flag.TAG, "onNext: ---" + s + " Thread---" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(Flag.TAG, "apply:----- onComplete");

                        finish();
                    }
                });


    }



    private void test(){
        Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {

            }
        }).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) throws Exception {
                return null;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });



        // 背压
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {

            }
        },BackpressureStrategy.BUFFER)
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Exception {
                        return "";
                    }
                }).subscribe(new Subscriber<Object>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //接触变黄警告 Disposable d =
    private void test2(){
        Disposable d = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }

        })
                //ObservableSubscribeOn.subscrbe.
                .subscribeOn(

                        Schedulers.io()) // (new IOScheduler------> 线程池)
//                .subscribeOn(Schedulers.newThread())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    private void test3(){
        Disposable d = Observable.just("").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

}