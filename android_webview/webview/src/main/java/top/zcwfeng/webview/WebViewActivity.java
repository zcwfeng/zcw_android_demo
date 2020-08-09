package top.zcwfeng.webview;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import top.zcwfeng.webview.databinding.ActivityWebviewBinding;
import top.zcwfeng.webview.utils.Constants;

public class WebviewActivity extends AppCompatActivity {
    ActivityWebviewBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        mDataBinding.webview.getSettings().setJavaScriptEnabled(true);
        mDataBinding.title.setText(getIntent().getStringExtra(Constants.TITLE));
        mDataBinding.actionBar.setVisibility(getIntent()
                .getBooleanExtra(Constants.IS_SHOW_ACTION_BAR, false)
                ? View.VISIBLE : View.GONE);
        mDataBinding.webview.loadUrl(getIntent().getStringExtra(Constants.URL));
        mDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebviewActivity.this.finish();
            }
        });
    }
}