package top.zcwfeng.rxjava;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zcwfeng.rxjava.designpattern.UserPerson;
import top.zcwfeng.rxjava.designpattern.WechatServerObservable;
import top.zcwfeng.rxjava.use.UseActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RxAndroidSamples";
//    final static String PATH = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1794202421,3172844000&fm=26&gp=0.jpg";
    final static String PATH = "https://raw.githubusercontent.com/zcwfeng/zcw_android_demo/master/docs/rx/Rx_mind.png";
    private ImageView image;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.action_image);

        Snackbar.make(getWindow().getDecorView(),"SnackBar Test....", Snackbar.LENGTH_LONG).show();
    }

    public void startUserActivity(View view){
        Intent intent = new Intent(this, UseActivity.class);
        startActivity(intent);
    }

    public void myRxDesignPatternObserve(View view){
        String msg = "重大消息Android程序员必需要跟进学习Kotliin了！！!";
        // 创建公众号服务
        top.zcwfeng.rxjava.designpattern.Observable wechatServerObservable =
                new WechatServerObservable();
        // 创建用户观察者
        UserPerson user = new UserPerson("David");
        UserPerson user2 = new UserPerson("Lucy");
        UserPerson user3 = new UserPerson("Lili");
        UserPerson user4 = new UserPerson("Malong");
        // 订阅 （订阅---还是被观察者，订阅观察者）
        wechatServerObservable.addOberver(user);
        wechatServerObservable.addOberver(user2);
        wechatServerObservable.addOberver(user3);
        wechatServerObservable.addOberver(user4);
        // 发布消息
        wechatServerObservable.pushMessages(msg);
        ////////////
        wechatServerObservable.removeObserver(user4);
        wechatServerObservable.pushMessages(msg);
    }


    public void button_run_scheduler(View view) {
        rxJavaDownloadImageAction(view);
    }

    // 抽取订阅,切换线程 封装上游数据
    //.subscribeOn(Schedulers.io())
    //.observeOn(AndroidSchedulers.mainThread());
    public static final <UD> ObservableTransformer<UD,UD> upstreamData(){
        return new ObservableTransformer<UD, UD>() {
            @Override
            public ObservableSource<UD> apply(Observable<UD> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).map(new Function<UD, UD>() {
                            @Override
                            public UD apply(UD ud) throws Exception {
                                Log.d(TAG, "apply: 监听切换线程。。。");
                                return ud;
                            }
                        });
            }
        };
    }

    private final Bitmap drawWaterMark(Bitmap bitmap,String text,Paint paint,int paddingLeft,int paddingTop) {
        Bitmap.Config bitmapCofing = bitmap.getConfig();
        paint.setDither(true);//获取清晰的采样图片
        paint.setFilterBitmap(true);// 过滤
        if(bitmapCofing == null){
            bitmapCofing = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapCofing,true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text,paddingLeft,paddingTop,paint);
        return bitmap;
    }

    private void rxJavaDownloadImageAction(View view) {

//        请求----->卡片（map）执行请求
//        --------> 终点 更新UI

        // 起点
        // 第二步
        Observable.just(PATH)
                // 根据上层变化变化，卡片map
                // 第三步
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        URL url = new URL(PATH);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.connect();
                        int responseCode = httpURLConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            return bitmap;
                        }
                        return null;
                    }
                })



                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Paint paint = new TextPaint();
                        paint.setTextSize(88);
                        paint.setColor(Color.RED);
                        Bitmap filterBitmap = drawWaterMark(bitmap,"RxJava",paint,88,88);
                        return filterBitmap;
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Log.d(TAG, "apply: 下载图片：" + SystemClock.elapsedRealtimeNanos());
                        return bitmap;
                    }
                })
                .compose(MainActivity.<Bitmap>upstreamData())

                //终点 起点 订阅
                .subscribe(

                        /* 终点 */
                        new Observer<Bitmap>() {
                            /* 订阅开始 */
                            @Override
                            public void onSubscribe(Disposable d) {
                                // 第一步
                                progressDialog = new ProgressDialog(MainActivity.this);
                                progressDialog.setTitle("下载");
                                progressDialog.show();
                            }

                            /* 拿到事件 */
                            // 第四步
                            @Override
                            public void onNext(Bitmap bitmap) {
                                image.setImageBitmap(bitmap);
                            }

                            /* 错误 */
                            @Override
                            public void onError(Throwable e) {

                            }

                            //完成 第五步
                            @Override
                            public void onComplete() {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                            }
                        });


    }


}
