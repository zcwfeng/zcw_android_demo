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
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import top.zcwfeng.news.R;
import top.zcwfeng.news.databinding.FragmentHomeBinding;
import top.zcwfeng.news.homefragment.api.NewsChannelsBean;

public class HeadlineNewsFragment2 extends Fragment {
    public HeadlineNewsFragmentAdapter2 adapter;
    FragmentHomeBinding viewDataBinding;
    HeadLineNewsViewModel headLineNewsViewModel = new HeadLineNewsViewModel();
    private TabLayoutMediator tabLayoutMediator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        // TODO: 5 优化 viewpager -> viewpager2 懒加载
        adapter = new HeadlineNewsFragmentAdapter2(getChildFragmentManager(),getLifecycle());
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewDataBinding.viewpager2.setAdapter(adapter);
//        viewDataBinding.tablayout.setupWithViewPager(viewDataBinding.viewpager2);

        tabLayoutMediator = new TabLayoutMediator(viewDataBinding.tablayout, viewDataBinding.viewpager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(adapter.getChannelName(position));
                    }
                });
        tabLayoutMediator.attach();
        viewDataBinding.viewpager2.setOffscreenPageLimit(1);
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
