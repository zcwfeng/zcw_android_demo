package top.zcwfeng.news.homefragment.headlinenews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import top.zcwfeng.news.R;
import top.zcwfeng.news.databinding.FragmentHomeBinding;
import top.zcwfeng.news.homefragment.api.NewsChannelsBean;

public class HeadlineNewsFragment extends Fragment {
    public HeadlineNewsFragmentAdapter adapter;
    FragmentHomeBinding viewDataBinding;
    HeadLineNewsViewModel headLineNewsViewModel = new HeadLineNewsViewModel();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        adapter = new HeadlineNewsFragmentAdapter(getChildFragmentManager());
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        viewDataBinding.viewpager.setAdapter(adapter);
//        viewDataBinding.tablayout.setupWithViewPager(viewDataBinding.viewpager);
//        viewDataBinding.viewpager.setOffscreenPageLimit(1);
        headLineNewsViewModel.dataList.observe(this, new Observer<List<NewsChannelsBean.ChannelList>>() {
            @Override
            public void onChanged(List<NewsChannelsBean.ChannelList> channelLists) {
                adapter.setChannels(channelLists);

            }
        });
        getLifecycle().addObserver(headLineNewsViewModel);
        // TODO: 2 优化  idleHandler 延迟加载
        headLineNewsViewModel.getCachedDataAndload();
//        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                headLineNewsViewModel.getCachedDataAndload();
//                return false;
//            }
//        });
        return viewDataBinding.getRoot();
    }


}
