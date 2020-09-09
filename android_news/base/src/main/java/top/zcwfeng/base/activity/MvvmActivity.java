package top.zcwfeng.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import top.zcwfeng.base.loadsir.EmptyCallback;
import top.zcwfeng.base.loadsir.ErrorCallback;
import top.zcwfeng.base.loadsir.LoadingCallback;
import top.zcwfeng.base.viewmodel.MvvmBaseViewModel;

public abstract class MvvmActivity <V extends ViewDataBinding,VM extends MvvmBaseViewModel>
        extends AppCompatActivity implements IBaseView {
    protected VM viewModel;
    protected V viewDataBinding;
    private LoadService mLoadService;
    protected abstract void onRetryBtnClick();

    @LayoutRes
    public abstract int getLayoutId();

    public abstract VM getViewModel();

    public abstract int getBindingVariable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        if(viewModel == null) {
            viewModel = getViewModel();
        }
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(),viewModel);
        }
        viewDataBinding.executePendingBindings();
    }

    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if(mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }
}
