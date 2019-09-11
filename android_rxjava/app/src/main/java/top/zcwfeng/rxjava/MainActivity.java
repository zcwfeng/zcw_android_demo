package top.zcwfeng.rxjava;

import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RxAndroidSamples";
    private final CompositeDisposable disposables = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testRxAndroidWorkRule();



    }


    public void button_run_scheduler(View view){
        onRunSchedulerExampleButtonClicked();
    }

    private void onRunSchedulerExampleButtonClicked() {

        disposables.add(sampleObservable().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext(" + s + ")--->" + Thread.currentThread().getName());


            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError()--->" + Thread.currentThread().getName(), e);

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete()--->" + Thread.currentThread().getName());

            }
        }));
    }

    public Observable<String> sampleObservable(){
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                SystemClock.sleep(5000);

                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private void testRxAndroidWorkRule(){
        Looper backgroundLooper = getMainLooper();
        Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.from(backgroundLooper))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("rxjava_zcw","onSubscri:"+d.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("rxjava_zcw","onNext:" + s);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("rxjava_zcw","onError:"+e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.e("rxjava_zcw","onComplete:");

                    }
                });

    }
}
