package top.zcwfeng.jetpack;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<String> getPhoneInfo() {
        if (phoneInfo == null) {
            phoneInfo = new MutableLiveData<>();
            phoneInfo.setValue("");
        }
        return phoneInfo;
    }

    private MutableLiveData<String> phoneInfo;
    private Context context;


    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public void callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneInfo.getValue()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void startSecondActivity(){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context,SecondActivity.class);
        context.startActivity(intent);

    }


    /**
     * 输入
     *
     * @param number
     */
    public void appendNumber(String number) {

        phoneInfo.setValue(phoneInfo.getValue()+number); // 弊端 性能 sb去完成

    }

    /**
     * 删除
     */
    public void backspaceNumber() {
        int length = phoneInfo.getValue().length();
        if (length > 0) {
            phoneInfo.setValue(phoneInfo.getValue().substring(0, phoneInfo.getValue().length() - 1));
        }
    }

    /**
     * 清空
     */
    public void clear() {
        phoneInfo.setValue("");
    }



}
