package top.zcwfeng.webview.webviewprocess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import top.zcwfeng.base.BaseApplication;
import top.zcwfeng.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import top.zcwfeng.webview.IWebviewProcessToMainProcessInterface;
import top.zcwfeng.webview.mainprocess.MainProcessCommandService;

public class WebViewProcessCommandDispatcher implements ServiceConnection {
    private IWebviewProcessToMainProcessInterface iWebviewProcessToMainProcessInterface;
    private static WebViewProcessCommandDispatcher sInstance;

    public static WebViewProcessCommandDispatcher getInstance() {
        if (sInstance == null) {
            synchronized (WebViewProcessCommandDispatcher.class) {
                sInstance = new WebViewProcessCommandDispatcher();
            }
        }
        return sInstance;
    }

    public void initAidlConnect(){
        Intent intent = new Intent(BaseApplication.sApplication, MainProcessCommandService.class);
        BaseApplication.sApplication.bindService(intent,this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d("onServiceConnected","onServiceConnected");
        iWebviewProcessToMainProcessInterface = IWebviewProcessToMainProcessInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        iWebviewProcessToMainProcessInterface = null;
        initAidlConnect();
    }

    @Override
    public void onBindingDied(ComponentName name) {
        iWebviewProcessToMainProcessInterface = null;
        initAidlConnect();
    }

    public void executeCommand(String commandName, String params, final BaseWebView webView){
        if(iWebviewProcessToMainProcessInterface != null) {
            Log.e("zcw:::","iWebviewProcessToMainProcessInterface why?" + iWebviewProcessToMainProcessInterface);
            try {
                iWebviewProcessToMainProcessInterface.handleWebCommand(commandName, params, new ICallbackFromMainprocessToWebViewProcessInterface.Stub() {
                    @Override
                    public void onResult(String callbackname, String response) throws RemoteException {
                        webView.handleCallBack(callbackname,response);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
