package top.zcwfeng.httpprocessor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import top.zcwfeng.httpprocessor.bean.ResponceData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        Log.e("zcwfeng", "11111111");

        String url="https://v.juhe.cn/historyWeather/citys";
        HashMap<String,Object> params=new HashMap<>();
        //https://v.juhe.cn/historyWeather/citys?&province_id=2&key=bb52107206585ab074f5e59a8c73875b
        params.put("province_id","2");
        params.put("key","bb52107206585ab074f5e59a8c73875b");
        HttpHelper.obtain().post(url, params, new HttpCallback<ResponceData>() {
            @Override
            public void onSuccess(ResponceData objResult) {
                Log.e("zcwfeng", "click(View view) ==== onSuccess");
                Toast.makeText(MainActivity.this, objResult.getResultcode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}