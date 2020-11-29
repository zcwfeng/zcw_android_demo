package top.zcwfeng.common.views.pictureview;

import android.content.Context;
import android.view.View;

import com.xiangxue.webview.WebviewActivity;

import top.zcwfeng.base.customview.customview.BaseCustomView;
import top.zcwfeng.common.R;
import top.zcwfeng.common.databinding.PictureTitleViewBinding;

public class PictureTitleView extends BaseCustomView<PictureTitleViewBinding,PictureTitleViewModel> {
    public PictureTitleView(Context context) {
        super(context);
    }




    @Override
    public void setDataToView(PictureTitleViewModel pictureTitleViewModel) {
        mBinding.setViewmodel(pictureTitleViewModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.picture_title_view;
    }

    @Override
    public void onRootClicked(View view) {
        WebviewActivity.startCommonWeb(getContext(), "News", mViewModel.jumpUrl);

    }



}
