package top.zcwfeng.common.views.titleview;

import android.content.Context;
import android.view.View;

import com.xiangxue.webview.WebviewActivity;

import top.zcwfeng.base.customview.customview.BaseCustomView;
import top.zcwfeng.base.customview.customview.IBaseCustomView;
import top.zcwfeng.common.R;
import top.zcwfeng.common.databinding.TitleViewBinding;

public
class TitleView extends BaseCustomView<TitleViewBinding,TitleViewModel> implements IBaseCustomView<TitleViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    @Override
    public void setDataToView(TitleViewModel titleViewModel) {
        mBinding.setViewmodel(titleViewModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void onRootClicked(View view) {
        WebviewActivity.startCommonWeb(getContext(), "News", mViewModel.jumpUrl);
    }


}
