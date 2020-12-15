package top.zcwfeng.news.homefragment.headlinenews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import top.zcwfeng.news.homefragment.api.NewsChannelsBean;
import top.zcwfeng.news.homefragment.newslist.NewsListFragment;

public class HeadlineNewsFragmentAdapter2 extends FragmentStateAdapter {
    private List<NewsChannelsBean.ChannelList> mChannels;
    private int itemCount = 0;

    public HeadlineNewsFragmentAdapter2(@NonNull FragmentManager fragmentManager,
                                        @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    public void setChannels(List<NewsChannelsBean.ChannelList> channels) {
        this.mChannels = channels;
        itemCount = channels.size();
        notifyDataSetChanged();
    }

    public String getChannelName(int position) {
        if (position >= mChannels.size()) {
            return "";
        }
        return mChannels.get(position).name;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return NewsListFragment.newInstance(mChannels.get(position).channelId, mChannels.get(position).name);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}