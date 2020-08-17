package top.zcwfeng.webview;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import top.zcwfeng.webview.databinding.ActivityWebviewBinding;
import top.zcwfeng.webview.utils.Constants;

public class WebviewActivity extends AppCompatActivity {
    ActivityWebviewBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        mDataBinding.title.setText(getIntent().getStringExtra(Constants.TITLE));
        mDataBinding.actionBar.setVisibility(getIntent()
                .getBooleanExtra(Constants.IS_SHOW_ACTION_BAR, true)
                ? View.VISIBLE : View.GONE);

        mDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebviewActivity.this.finish();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = MyWebViewFragment.newInstance(getIntent().getStringExtra(Constants.URL),true);
        fragmentTransaction.replace(R.id.web_view_fragment_container,fragment).commitAllowingStateLoss();
    }

    public void updateTitle(String title){
        mDataBinding.title.setText(title);
    }

}