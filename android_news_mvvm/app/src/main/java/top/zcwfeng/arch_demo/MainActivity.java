package top.zcwfeng.arch_demo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.lang.reflect.Field;

import q.rorbin.badgeview.QBadgeView;
import top.zcwfeng.arch_demo.databinding.ActivityMainBinding;
import top.zcwfeng.arch_demo.fragments.AccountFragment;
import top.zcwfeng.arch_demo.fragments.CategoryFragment;
import top.zcwfeng.arch_demo.fragments.ServiceFragment;
import top.zcwfeng.news.homefragment.headlinenews.HeadlineNewsFragment2;

public class MainActivity extends AppCompatActivity {
    private final Fragment mHomeFragment = new HeadlineNewsFragment2();
    private final CategoryFragment mCategoryFragment = new CategoryFragment();
    private final ServiceFragment mServiceFragment = new ServiceFragment();
    private final AccountFragment mAccountFragment = new AccountFragment();
    private ActivityMainBinding viewDataBinding;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
//        Debug.stopMethodTracing();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 1 启动优化 优化启动白屏
        super.onCreate(savedInstanceState);

        // TODO Test BlockCanery 卡顿分析测试
//        try {
//            Thread.sleep(3_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // TODO: Test Choreographer 掉帧检测猜测，适合线上统计数据
//        ChoreographerHelper.start();

        viewDataBinding = DataBindingUtil.setContentView(this,  R.layout.activity_main);

        fromFragment = mCategoryFragment;
        //Set Toolbar
        setSupportActionBar(viewDataBinding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_home));

        /**
         * Disable shift method require for to prevent shifting icon.
         * When you select any icon then remain all icon shift
         * @param view
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(viewDataBinding.bottomView);
        }

        viewDataBinding.bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragCategory = null;
                // init corresponding fragment
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragCategory = mHomeFragment;
                        break;
                    case R.id.menu_categories:
                        fragCategory = mCategoryFragment;
                        break;
                    case R.id.menu_services:
                        fragCategory = mServiceFragment;
                        break;
                    case R.id.menu_account:
                        fragCategory = mAccountFragment;
                        break;
                }
                //Set bottom menu selected item text in toolbar
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(item.getTitle());
                }
                switchFragment(fromFragment, fragCategory);
                fromFragment = fragCategory;
                return true;
            }
        });
        viewDataBinding.bottomView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mHomeFragment, mHomeFragment.getClass().getSimpleName());
        transaction.commit();
        showBadgeView(3, 5);
    }

    Fragment fromFragment;

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.add(R.id.container, to, to.getClass().getName()).commit();
                }

            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.show(to).commit();
                }

            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                // item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) viewDataBinding.bottomView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;

            // 显示badegeview
            new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth + 50, 13, false).setBadgeNumber(showNumber);
        }
    }
}
