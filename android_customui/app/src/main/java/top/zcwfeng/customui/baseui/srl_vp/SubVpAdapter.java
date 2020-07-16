package top.zcwfeng.customui.baseui.srl_vp;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SubVpAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public SubVpAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "左边" : "右边";
    }
}
