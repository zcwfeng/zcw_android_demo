package top.zcwfeng.news;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

import q.rorbin.badgeview.QBadgeView;
import top.zcwfeng.news.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding viewDataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //Set Toolbar
        setSupportActionBar(viewDataBinding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_home));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(viewDataBinding.bottomView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;

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