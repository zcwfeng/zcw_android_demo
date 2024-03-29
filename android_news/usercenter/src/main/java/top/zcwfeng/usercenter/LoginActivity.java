package top.zcwfeng.usercenter;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import top.zcwfeng.base.preference.PreferencesUtil;
import top.zcwfeng.usercenter.databinding.ActivityLoginBinding;


/**f
 * Created by Akshay Raj on 6/16/2016.
 * akshay@snowcorp.org
 * fileupload.snowcorp.org
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // Hide Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
    }

    private void init() {
        // Login button Click Event
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Hide Keyboard
                PreferencesUtil.getInstance().setString("UserName", mBinding.lTextEmail.getEditText().getText().toString());
                finish();
            }
        });
    }
}
