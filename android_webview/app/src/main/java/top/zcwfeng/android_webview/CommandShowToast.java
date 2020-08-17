package top.zcwfeng.android_webview;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.auto.service.AutoService;

import java.util.Map;

import top.zcwfeng.base.BaseApplication;
import top.zcwfeng.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import top.zcwfeng.webview.command.Command;

@AutoService({Command.class})
public class CommandShowToast implements Command {
    @Override
    public String name() {
        return "showToast";
    }

    @Override
    public void execute(final Map params, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseApplication.sApplication, String.valueOf(
                        params.get("message")
                ), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
