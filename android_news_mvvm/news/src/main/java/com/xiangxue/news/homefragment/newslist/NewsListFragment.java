package com.xiangxue.news.homefragment.newslist;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiangxue.news.R;
import com.xiangxue.news.databinding.FragmentNewsBinding;

import java.util.List;

import top.zcwfeng.base.customview.customview.BaseCustomViewModel;
import top.zcwfeng.base.customview.mvvm.view.BaseMvvmFragment;

/**
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NewsListFragment extends BaseMvvmFragment<FragmentNewsBinding, NewsListViewModel, BaseCustomViewModel> {
    private NewsListRecyclerViewAdapter mAdapter;
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";

    public static NewsListFragment newInstance(String channelId, String channelName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onNetworkResponded(List<BaseCustomViewModel> baseCustomViewModels, boolean isDataUpdated) {
        viewDataBinding.refreshLayout.finishRefresh();
        viewDataBinding.refreshLayout.finishLoadMore();
        if(isDataUpdated){
            mAdapter.setData(baseCustomViewModels);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public String getFragmentTag() {
        return getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME);
    }

    @Override
    public NewsListViewModel getViewModel() {
        return new ViewModelProvider(getActivity(),
                new NewsListViewModel.NewsListViewModelFactory(
                        getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID),
                        getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME)))
                .get(getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID), NewsListViewModel.class);
    }

    @Override
    public void onViewCreated() {
        mAdapter = new NewsListRecyclerViewAdapter();
        viewDataBinding.listview.setHasFixedSize(true);
        viewDataBinding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.listview.setAdapter(mAdapter);


        viewDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.refresh();
            }
        });
        viewDataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.loadNextPage();
            }
        });
        showLoading();
    }

    @Override
    protected View getLoadSirView() {
        return viewDataBinding.refreshLayout;
    }
}
