package top.zcwfeng.fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import top.zcwfeng.fragment.struct.BaseFragment;
import top.zcwfeng.fragment.struct.FunctionManager;
import top.zcwfeng.fragment.struct.FunctionNoParamsNotResult;
import top.zcwfeng.fragment.struct.FunctionWithParamAndResult;
import top.zcwfeng.fragment.struct.FunctionWithParamOnly;
import top.zcwfeng.fragment.struct.FunctionWithResultOnly;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragmentArrayList;
    private int currentIndex = 0;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragments();




    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initFragments() {
        fragmentArrayList = new ArrayList<Fragment>(3);
        fragmentArrayList.add(new BlankFragment1());
        fragmentArrayList.add(new BlankFragment2());
        fragmentArrayList.add(new BlankFragment3());
        changeTab(0);

    }

    public void setFunctionsForFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        BaseFragment baseFragment = (BaseFragment) fm.findFragmentByTag(tag);
        FunctionManager functionManager =
                FunctionManager.getInstance();
        baseFragment.setFunctionManager(functionManager.addFunction(new FunctionNoParamsNotResult(BlankFragment1.INSTANCE) {
            @Override
            public void function() {
                Toast.makeText(MainActivity.this,"FunctionNoParamsNotResult",Toast.LENGTH_SHORT).show();

            }
        }).addFunction(new FunctionWithResultOnly<String>(BlankFragment2.INSTANCE_RESULT_ONLY) {
            @Override
            public String function() {
                return "INSTANCE_RESULT_ONLY";
            }
        }).addFunction(new FunctionWithParamOnly<String>(BlankFragment3.INSTANCE_PARAM_ONLY) {
            @Override
            public void function(String params) {
                Toast.makeText(MainActivity.this,params,Toast.LENGTH_SHORT).show();

            }
        }).addFunction(new FunctionWithParamAndResult<String,String>(BlankFragment3.INSTANCE_RESULT_AND_PARAM) {
            @Override
            public String function(String objects) {
                return "With Result And Params" + objects;
            }
        }));
    }


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("main");
                    changeTab(0);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("second");
                    changeTab(1);

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("third");
                    changeTab(2);

                    return true;
            }
            return false;
        }


    };
    public void changeTab(int i) {
//        currentIndex = i;


        Fragment fragment= fragmentArrayList.get(i);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // TODO: 2020/9/23 查看测试Fragment androidX 源代码入口
//        ft.add()
//        ft.remove();
//        ft.replace();
//        ft.hide();
//        ft.show();
        // TODO: 2020/9/23 导航相关
//        ft.setPrimaryNavigationFragment();
        // TODO: 2020/9/23 加入回退栈
//        ft.addToBackStack();

//        ft.commit()
//        ft.commitAllowingStateLoss()
//        ft.commitNow();
//        ft.commitNowAllowingStateLoss();
        // TODO: 2020/9/23 END

        if(null != mCurrentFragment) {
            ft.hide(mCurrentFragment);
            if (!fragment.isAdded()){
                ft.add(R.id.fragment_container,fragment,fragmentArrayList.get(i).getClass().getName()).show(fragment).commit();
                Log.e("zcw",fragment.getTag());


            }else{
                ft.show(fragment).commit();
                Log.e("zcw2",fragment.getTag());

            }


        } else {
            ft.add(R.id.fragment_container,fragment,fragmentArrayList.get(i).getClass().getName()).show(fragment).commit();

        }

        mCurrentFragment = fragment;


    }
}

