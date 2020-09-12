package top.zcwfeng.base.fragment;

import top.zcwfeng.base.activity.IBaseView;

public interface IBasePagingView extends IBaseView {

    void onLoadMoreFailure(String message);

    void onLoadMoreEmpty();
}