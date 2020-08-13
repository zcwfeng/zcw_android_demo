package top.zcwfeng.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import top.zcwfeng.base.loadsir.ErrorCallback;
import top.zcwfeng.base.loadsir.LoadingCallback;
import top.zcwfeng.webview.databinding.FragmentMyWebViewBinding;
import top.zcwfeng.webview.utils.Constants;

public class MyWebViewFragment extends Fragment implements WebViewCallBack, OnRefreshListener {
    private String mUrl;
    private boolean mCanNativeRefresh;
    private boolean mIsError = false;
    private static final String TAG = MyWebViewFragment.class.getName();
    private FragmentMyWebViewBinding mBinding;
    private LoadService mLoadService;


    public MyWebViewFragment() {
    }


    public static MyWebViewFragment newInstance(String url, boolean canNativeRefresh) {
        MyWebViewFragment fragment = new MyWebViewFragment();
        Bundle args = new Bundle();
        args.putString(Constants.URL, url);
        args.putBoolean(Constants.CAN_NATIVE_REFRESH, canNativeRefresh);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(Constants.URL);
            mCanNativeRefresh = getArguments().getBoolean(Constants.CAN_NATIVE_REFRESH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_web_view, container, false);
        mBinding.webview.loadUrl(mUrl);
        mBinding.webview.registerWebViewCallback(this);
        mLoadService = LoadSir.getDefault().register(mBinding.smartrefreshlayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mLoadService.showCallback(LoadingCallback.class);
                mBinding.webview.reload();
            }
        });


        mBinding.smartrefreshlayout.setOnRefreshListener(this);
        mBinding.smartrefreshlayout.setEnableRefresh(true);
        mBinding.smartrefreshlayout.setEnableLoadMore(false);
        return mLoadService.getLoadLayout();

    }

    @Override
    public void pageStarted(String url) {
        if(mLoadService != null){
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void pageFinished(String url) {
        if(mIsError) {
            mBinding.smartrefreshlayout.setEnableRefresh(true);
        } else {
            mBinding.smartrefreshlayout.setEnableRefresh(mCanNativeRefresh);
        }
        Log.d(TAG, "pageFinished");
        mBinding.smartrefreshlayout.finishRefresh();
        if (mLoadService != null) {
            if(mIsError){
                mLoadService.showCallback(ErrorCallback.class);
            }   else {
                mLoadService.showSuccess();
            }
        }
        mIsError = false;
    }

    @Override
    public void onError() {
        Log.e(TAG, "onError");
        mIsError = true;
        mBinding.smartrefreshlayout.finishRefresh();
    }

    @Override
    public void updateTitle(String title) {
        // TODO: 2020/8/12 有没有更好的更新方案
        if (getActivity() instanceof WebviewActivity) {
            ((WebviewActivity)getActivity()).updateTitle(title);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mBinding.webview.reload();
    }
    
    
}