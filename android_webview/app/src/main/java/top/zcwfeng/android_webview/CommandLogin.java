package top.zcwfeng.android_webview;

import android.os.RemoteException;
import android.util.Log;

import com.google.auto.service.AutoService;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import top.zcwfeng.base.autoservice.CommonServiceLoader;
import top.zcwfeng.common.autoservice.IUserCenterService;
import top.zcwfeng.common.eventbus.LoginEvent;
import top.zcwfeng.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import top.zcwfeng.webview.command.Command;

@AutoService({Command.class})
public class CommandLogin implements Command {
    private static final String TAG = CommandLogin.class.getName();
    IUserCenterService iUserCenterService = CommonServiceLoader.load(IUserCenterService.class);
    ICallbackFromMainprocessToWebViewProcessInterface callback;
    String callbacknameFromNativeJS;
    @Override
    public String name() {
        return "login";
    }

    public CommandLogin() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void execute(Map params, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        Log.d(TAG,"login->" + params);
        if(iUserCenterService != null && !iUserCenterService.isLogined()){
            iUserCenterService.login();
            this.callback = callback;
            this.callbacknameFromNativeJS = params.get("callbackname").toString();
        }
    }


    @Subscribe
    public void onMessageEvent(LoginEvent event){
        Log.d(TAG,"login->" + event.userName);
        HashMap map = new HashMap();
        map.put("accountName",event.userName);
        if(this.callback != null){
            try {
                callback.onResult(callbacknameFromNativeJS,new Gson().toJson(map));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

}
