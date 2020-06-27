package top.zcwfeng.jetpack.mvp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.jetpack.mvp.presenter.BasePresenter;

public abstract class BaseActivity<T extends BasePresenter,V extends IBaseView>  extends AppCompatActivity {
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //由activity选择一个表示层
        presenter=createPresenter();
        presenter.attachView((V)this);

        registerSDK();
        init();
    }
    protected abstract T createPresenter();

    protected void init(){}
    protected void registerSDK(){}
    protected void unRegisterSDK(){}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unRegisterSDK();
    }
}