package top.zcwfeng.customwebview;

import android.os.Bundle;

import top.zcwfeng.customwebview.basefragment.BaseWebviewFragment;
import top.zcwfeng.customwebview.utils.WebConstants;


public class CommonWebFragment extends BaseWebviewFragment {

    public String url;

    public static CommonWebFragment newInstance(String url) {
        CommonWebFragment fragment = new CommonWebFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_common_webview;
    }

    @Override
    public int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }
}
