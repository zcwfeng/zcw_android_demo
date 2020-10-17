package com.xiangxue.news.homefragment.newslist;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;

import top.zcwfeng.base.customview.customview.BaseCustomViewModel;
import top.zcwfeng.base.customview.mvvm.viewmodel.BaseMvvmViewModel;

import static com.xiangxue.news.homefragment.newslist.NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_ID;
import static com.xiangxue.news.homefragment.newslist.NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_NAME;

public class NewsListViewModel extends BaseMvvmViewModel<NewsListModel, BaseCustomViewModel> {


    SavedStateHandle mSavedStateHandle;

    @ViewModelInject
    public NewsListViewModel(@Assisted SavedStateHandle savedStateHandle) {
        mSavedStateHandle = savedStateHandle;
    }

    @Override
    public NewsListModel createModel() {
        return new NewsListModel(mSavedStateHandle.get(BUNDLE_KEY_PARAM_CHANNEL_ID),
                mSavedStateHandle.get(BUNDLE_KEY_PARAM_CHANNEL_NAME));
    }

    // TODO: 2020/10/17 非常重要的工厂类实现
//    public static class NewsListViewModelFactory implements ViewModelProvider.Factory {
//        private String mChannelId;
//        private String mChannelName;
//
//        public NewsListViewModelFactory(String channelId, String channelName) {
//            mChannelId = channelId;
//            mChannelName = channelName;
//        }
//
//        @NonNull
//        @Override
//        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            return (T) new NewsListViewModel(mChannelId, mChannelName);
//        }
//    }
}
