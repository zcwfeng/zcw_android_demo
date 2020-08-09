package top.zcwfeng.materialdesign.md2.nestedscrollview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.materialdesign.databinding.ActivityNestedScrollviewBinding;
import top.zcwfeng.materialdesign.md2.adapter.MyFragmentAdapter;
import top.zcwfeng.materialdesign.md2.fragment.NestedTestFragment;

public class NestedScrollViewActivity extends AppCompatActivity {

    private ActivityNestedScrollviewBinding binding;

    private List<Fragment> fragmentList;
    private List<String> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNestedScrollviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        fragmentList = new ArrayList<>();
        for(int i = 0 ; i < 4;i++){
            fragmentList.add(NestedTestFragment.newIntance("NestedScrollView"));
        }
        titles = new ArrayList<>();
        titles.add("首页");
        titles.add("商品");
        titles.add("个人");
        titles.add("关于");



        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(this,fragmentList);
        binding.viewPager.setAdapter(myFragmentAdapter);


        new TabLayoutMediator(binding.tab, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        }).attach();




    }



}
