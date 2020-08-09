package top.zcwfeng.materialdesign.viewpager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import top.zcwfeng.materialdesign.R;
import top.zcwfeng.materialdesign.databinding.ActivityViewPager2Binding;
import top.zcwfeng.materialdesign.viewpager2.adapter.MyFragmentAdapter;
import top.zcwfeng.materialdesign.viewpager2.fragment.FourFragment;
import top.zcwfeng.materialdesign.viewpager2.fragment.OneFragment;
import top.zcwfeng.materialdesign.viewpager2.fragment.ThreeFragment;
import top.zcwfeng.materialdesign.viewpager2.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {

    private ActivityViewPager2Binding binding;

    private List<Fragment> fragmentList;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityViewPager2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.collapsingToolbarLayout.setTitle("ViewPager2");
        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    binding.collapsingToolbarLayout.setTitle("ViewPager2");
                }else{
                    binding.collapsingToolbarLayout.setTitle("");
                }
            }
        });

        binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        fragmentList = new ArrayList<>();
        fragmentList.add(OneFragment.newIntance());
        fragmentList.add(TwoFragment.newIntance());
        fragmentList.add(ThreeFragment.newIntance());
        fragmentList.add(FourFragment.newIntance());
        titles = new ArrayList<>();
        titles.add("OneFragment");
        titles.add("TwoFragment");
        titles.add("ThreeFragment");
        titles.add("FourFragment");



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