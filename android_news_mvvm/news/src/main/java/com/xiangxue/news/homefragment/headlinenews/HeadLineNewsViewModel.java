package com.xiangxue.news.homefragment.headlinenews;

import com.xiangxue.news.homefragment.api.NewsChannelsBean;

import top.zcwfeng.base.customview.mvvm.viewmodel.BaseMvvmViewModel;

public
class HeadLineNewsViewModel extends
        BaseMvvmViewModel<NewsChannelModel,NewsChannelsBean.ChannelList>{

    @Override
    public NewsChannelModel createModel() {
        return new NewsChannelModel();
    }
}
