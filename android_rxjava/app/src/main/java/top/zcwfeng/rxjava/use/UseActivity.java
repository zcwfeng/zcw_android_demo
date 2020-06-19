package top.zcwfeng.rxjava.use;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import top.zcwfeng.rxjava.R;
import top.zcwfeng.rxjava.retrofit.WanAndroidApi;
import top.zcwfeng.rxjava.retrofit.bean.Bean;
import top.zcwfeng.rxjava.retrofit.bean.ProjectBean;
import top.zcwfeng.rxjava.util.HttpUtil;

public class UseActivity extends AppCompatActivity {
    private WanAndroidApi api;
    private String TAG = UseActivity.class.getName();
    Button button1;
    Button button2;
    Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use);
        button1 = findViewById(R.id.use_rx_retrofit);
        button2 = findViewById(R.id.avoid_shake);
        button3 = findViewById(R.id.flat_map);

        Retrofit retrofit = HttpUtil.getOnLindeCookieRetrofit();
        api = retrofit.create(WanAndroidApi.class);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProjectData();
            }
        });

        antiShakeAction();


        antiShakeActionUpdate();


    }


    /**
     * 请求主数据
     */
    private void getProjectData() {
        api.getProject().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ProjectBean>() {
            @Override
            public void accept(ProjectBean projectBean) throws Exception {
                if (projectBean != null) {
                    Log.d(TAG, "accept: " + projectBean.getData());
                }
            }
        });
    }


    /**
     * 功能防抖+网络嵌套
     */
    private void antiShakeAction() {

        RxView.clicks(button2)
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        api.getProject().compose(upstreamData())
                                .subscribe(new Consumer<ProjectBean>() {
                                    @Override
                                    public void accept(ProjectBean projectBean) throws Exception {
                                        for (ProjectBean.DataBean dataBean : projectBean.getData()) {
                                            api.getProjectItem(1, dataBean.getId())
                                                    .compose(upstreamData())
                                                    .subscribe(new Consumer<Bean>() {
                                                        @Override
                                                        public void accept(Bean bean) throws Exception {
                                                            Log.d(TAG, "accept: antiShakeAction:" + bean);
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                });


    }

    /**
     * 功能防抖+网络嵌套（解决网络嵌套）-> flatMap
     */
    private void antiShakeActionUpdate() {
        RxView.clicks(button3)
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .flatMap(new Function<Object, ObservableSource<ProjectBean>>() {

                    @Override
                    public ObservableSource<ProjectBean> apply(Object o) throws Exception {
                        return api.getProject();
                    }
                })
                .flatMap(new Function<ProjectBean, ObservableSource<ProjectBean.DataBean>>() {
                    @Override
                    public ObservableSource<ProjectBean.DataBean> apply(ProjectBean projectBean) throws Exception {
                        // 我自己搞一个发射器
                        return Observable.fromIterable(projectBean.getData());
                    }
                })
                .flatMap(new Function<ProjectBean.DataBean, ObservableSource<Bean>>() {
                    @Override
                    public ObservableSource<Bean> apply(ProjectBean.DataBean dataBean) throws Exception {
                        return api.getProjectItem(1, dataBean.getId());
                    }
                })
                // 上面是io，这里需要切回主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bean>() {
                    @Override
                    public void accept(Bean bean) throws Exception {
                        // 更新UI
                        Log.d(TAG, "accept:flatmap----> " + bean);
                    }
                });

    }


    public <UD> ObservableTransformer<UD, UD> upstreamData() {
        return new ObservableTransformer<UD, UD>() {
            @Override
            public ObservableSource<UD> apply(Observable<UD> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).map(new Function<UD, UD>() {
                            @Override
                            public UD apply(UD ud) throws Exception {
                                Log.d(UseActivity.this.TAG, "apply: 监听切换线程。。。");
                                return ud;
                            }
                        });
            }
        };
    }
}