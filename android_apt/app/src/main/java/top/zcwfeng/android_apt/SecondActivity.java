package top.zcwfeng.android_apt;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import top.zcwfeng.android_apt.annotation.Autowired;
import top.zcwfeng.android_apt.annotation.IndectUtils;
import top.zcwfeng.android_apt.bean.UserParceable;
import top.zcwfeng.android_apt.bean.UserSerializable;

public class SecondActivity extends AppCompatActivity {
    @Autowired
    String name;
    @Autowired
    public String attr;
    @Autowired
    int[] arry;
    @Autowired
    UserParceable userParceable;
    @Autowired
    UserParceable[] userParceables;
    @Autowired
    List<UserParceable> userParcelableList;
    @Autowired("users")
    UserSerializable[] userSerializables;

    @Override
    public String toString() {
        return "SecondActivity{" +
                "name='" + name + '\'' +
                ", attr='" + attr + '\'' +
                ", arry=" + Arrays.toString(arry) +
                ", userParceable=" + userParceable +
                ", userParceables=" + Arrays.toString(userParceables) +
                ", userParcelableList=" + userParcelableList +
                ", userSerializables=" + Arrays.toString(userSerializables) +
                '}';
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        IndectUtils.injectExtras(this);
        Log.e("zcw::",toString());
    }
}