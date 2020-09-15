package top.zcwfeng.mvp_observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import top.zcwfeng.mvp_observer.model.MainActivityModel;
import top.zcwfeng.mvp_observer.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity {
    private EditText mUserNameEdit;
    private EditText mUserPswdEdit;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;

    private MainActivityPresenter mainActivityPresenter;
    private MainActivityModel mainActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMVP();
        findView();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用presenter处理业务逻辑
                mainActivityPresenter.doLogin(mUserNameEdit.getEditableText().toString(), mUserPswdEdit.getEditableText().toString());
            }
        });

    }

    private void findView() {
        mUserNameEdit = findViewById(R.id.username_edit);
        mUserPswdEdit = findViewById(R.id.userpswd_edit);
        mLoginBtn = findViewById(R.id.login_btn);
        mProgressBar = findViewById(R.id.progress_bar);

    }

    private void initMVP() {
        mainActivityPresenter = new MainActivityPresenter();
        mainActivityModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory())
                .get(MainActivityModel.class);
        mainActivityPresenter.registerViewModel(mainActivityModel);
        registerObservers();
    }

    private void registerObservers() {
        mainActivityModel.loginState.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case MainActivityModel.LOGIN_START: {
                        //开始登录的时候我们显示一个进度条
                        mProgressBar.setVisibility(View.VISIBLE);
                        break;
                    }
                    case MainActivityModel.LOGIN_SUCCESS: {
                        //登录成功我们隐藏进度条，并且提示用户登录成功
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "login success",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MainActivityModel.LOGIN_FAIL: {
                        //登录成功我们隐藏进度条，并且提示用户登录失败
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "login fail",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default: {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Unknown login state",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


}
