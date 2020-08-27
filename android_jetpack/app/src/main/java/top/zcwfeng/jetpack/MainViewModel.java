package top.zcwfeng.jetpack;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import top.zcwfeng.jetpack.databinding.NameActivity;
import top.zcwfeng.jetpack.livedata.TestLiveDataBusActivity;
import top.zcwfeng.jetpack.mvp.MvpActivity;
import top.zcwfeng.jetpack.navigation.NavigationActivity;
import top.zcwfeng.jetpack.paging.PagingActivity;
import top.zcwfeng.jetpack.room.RoomActivity;
import top.zcwfeng.jetpack.utils.LiveDataBus;

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

    public void startDatabindingDemoActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, MyDatabindDemoActivity.class);
        context.startActivity(intent);
    }
    public void startPagingActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, PagingActivity.class);
        context.startActivity(intent);

    }

    public void startNameViewModelActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, NameActivity.class);
        context.startActivity(intent);

    }

    public void startMvpActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, MvpActivity.class);
        context.startActivity(intent);

    }

    public void startRoomActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, RoomActivity.class);
        context.startActivity(intent);

    }

    public void startNavigationActivity(){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, NavigationActivity.class);
        context.startActivity(intent);
    }

    public void startLiveDataBusActivity() {
        //-> LiveDataBus 换成 LiveDataBusX 解决粘性
        //-> 粘性数据执行
        //-> new LiveData() ——> 绑定Observer —>  setValue(onChanged)    正常LiveDataBusX
        //-> new LiveData() ——> setValue(onChanged) —> 绑定Observer     LiveDataBus
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, TestLiveDataBusActivity.class);
        context.startActivity(intent);
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    //发送消息
                    LiveDataBus.getInstance().with("data", String.class).postValue("David-LiveDataBus");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }


    /**
     * 输入
     *
     * @param number
     */
    public void appendNumber(String number) {

        phoneInfo.setValue(phoneInfo.getValue() + number); // 弊端 性能 sb去完成

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
