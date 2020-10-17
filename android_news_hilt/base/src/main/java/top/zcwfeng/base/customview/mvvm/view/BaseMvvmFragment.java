package top.zcwfeng.base.customview.mvvm.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.util.List;

import top.zcwfeng.base.customview.loadsir.CustomCallback;
import top.zcwfeng.base.customview.loadsir.EmptyCallback;
import top.zcwfeng.base.customview.loadsir.ErrorCallback;
import top.zcwfeng.base.customview.loadsir.LoadingCallback;
import top.zcwfeng.base.customview.loadsir.TimeoutCallback;
import top.zcwfeng.base.customview.mvvm.viewmodel.BaseMvvmViewModel;
import top.zcwfeng.base.customview.mvvm.viewmodel.ViewStatus;
import top.zcwfeng.base.customview.utils.ToastUtil;

public abstract
class BaseMvvmFragment<VIEW extends ViewDataBinding, VIEWMODEL extends BaseMvvmViewModel, DATA>
        extends Fragment implements Observer {


    @Override
    public void onChanged(Object o) {
        if (o instanceof ViewStatus && mLoadService != null) {
            switch ((ViewStatus) o) {
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case EMPTY:
                    mLoadService.showCallback(EmptyCallback.class);
                    break;
                case SHOW_CONTENT:
                    mLoadService.showSuccess();
                    break;
                case NO_MORE_DATA:
                    // TODO: 2020/10/16 优化
                    ToastUtil.show("没有更多了");
                    break;
                case REFRESH_ERROR:
                    // TODO: 2020/10/17 LoadSir bug

                    if (((List) viewModel.dataList.getValue()).size() == 0) {
                        mLoadService.showCallback(ErrorCallback.class);
                    } else {
                        if (viewModel.errMessage.getValue() != null) {
                            ToastUtil.show(viewModel.errMessage.getValue().toString());
                        }
                    }
                    break;
                case LOAD_MORE_FAILED:
                    ToastUtil.show(viewModel.errMessage.getValue().toString());
                    break;
            }
            onNetworkResponded(null, false);
        } else if (o instanceof List) {
            onNetworkResponded((List<DATA>) o, true);
        }
    }

    public VIEWMODEL viewModel;
    public VIEW viewDataBinding;

    public abstract void onNetworkResponded(List<DATA> dataList, boolean isDataUpdated);


    public abstract @LayoutRes
    int getLayoutId();

    public abstract String getFragmentTag();

    public abstract VIEWMODEL getViewModel();

    public abstract void onViewCreated();

    protected abstract View getLoadSirView();

    private LoadService mLoadService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onCreateView");
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);



        setLoadSir(getLoadSirView());

        return viewDataBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onViewCreated");
        viewModel = getViewModel();
        getLifecycle().addObserver(viewModel);
        viewModel.dataList.observe(this, this);
        viewModel.viewStatusLiveData.observe(this, this);
        onViewCreated();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onDestroyView");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onAttach");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onDetach");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onStop");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onPause");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onResume");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setLoadSir(View view) {
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .build();
        mLoadService = loadSir.register(view, new Callback.OnReloadListener() {

            @Override
            public void onReload(View v) {
                if (viewModel != null) {
                    viewModel.refresh();
                }
            }
        });
    }

    protected void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }
}
