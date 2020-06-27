package top.zcwfeng.jetpack.livedata;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import top.zcwfeng.jetpack.R;
import top.zcwfeng.jetpack.utils.LiveDataBus;

public class TestLiveDataBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_live_data_bus);

        //-> 测试 要吧MainActivity LiveDataBus和LiveDataBusX 保持一致
        LiveDataBus.getInstance().with("data",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if(s!=null)
                            Toast.makeText(TestLiveDataBusActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}