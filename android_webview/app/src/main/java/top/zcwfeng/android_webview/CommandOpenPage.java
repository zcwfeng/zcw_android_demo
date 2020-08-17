package top.zcwfeng.android_webview;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

import com.google.auto.service.AutoService;

import java.util.Map;

import top.zcwfeng.base.BaseApplication;
import top.zcwfeng.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import top.zcwfeng.webview.command.Command;

@AutoService({Command.class})
public class CommandOpenPage implements Command {
    @Override
    public String name() {
        return "openPage";
    }

    @Override
    public void execute(Map params, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        String targetClass = String.valueOf(params.get("target_class"));
        if (!TextUtils.isEmpty(targetClass)) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(BaseApplication.sApplication, targetClass));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.sApplication.startActivity(intent);
        }
    }
}
