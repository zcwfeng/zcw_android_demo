package com.xiangxue.news.homefragment.newslist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import top.zcwfeng.base.customview.customview.BaseCustomViewModel;
import top.zcwfeng.base.customview.mvvm.viewmodel.BaseMvvmViewModel;

public class NewsListViewModel extends BaseMvvmViewModel<NewsListModel, BaseCustomViewModel> {

    private String mChannelId;
    private String mChannelName;

    public NewsListViewModel(String channelId, String channelName) {
        mChannelId = channelId;
        mChannelName = channelName;
    }

    @Override
    public NewsListModel createModel() {
        return new NewsListModel(mChannelId, mChannelName);
    }

    // TODO: 2020/10/17 非常重要的工厂类实现
    public static class NewsListViewModelFactory implements ViewModelProvider.Factory {
        private String mChannelId;
        private String mChannelName;

        public NewsListViewModelFactory(String channelId, String channelName) {
            mChannelId = channelId;
            mChannelName = channelName;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new NewsListViewModel(mChannelId, mChannelName);
        }
    }
}
