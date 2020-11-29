package com.xiangxue.news.homefragment.headlinenews;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xiangxue.news.homefragment.api.NewsChannelsBean;
import com.xiangxue.news.homefragment.newslist.NewsListFragment;

import java.util.HashMap;
import java.util.List;

/**
 
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class HeadlineNewsFragmentAdapter extends FragmentPagerAdapter {
    private List<NewsChannelsBean.ChannelList> mChannels;
    private HashMap<String, Fragment> fragmentHashMap = new HashMap<>();

    public HeadlineNewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setChannels(List<NewsChannelsBean.ChannelList> channels) {
        this.mChannels = channels;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int pos) {
        String key = mChannels.get(pos).channelId + ":" + mChannels.get(pos).name;
        if (fragmentHashMap.get(key) != null) {
            return fragmentHashMap.get(key);
        }
        Fragment fragment = NewsListFragment.newInstance(mChannels.get(pos).channelId, mChannels.get(pos).name);
        fragmentHashMap.put(key, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        if (mChannels != null && mChannels.size() > 0) {
            return mChannels.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).name;
    }
    
    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }
}