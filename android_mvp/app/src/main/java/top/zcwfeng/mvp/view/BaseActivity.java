package top.zcwfeng.mvp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.mvp.presenter.BasePresenter;

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    public T girlPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        girlPresenter = createPresenter();
        girlPresenter.onAttach((V) this);
    }

    protected  abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        girlPresenter.onDetach();
    }
}
