package top.zcwfeng.customwebview.command.webviewprocess;

import android.content.Context;
import android.util.Log;

import java.util.Map;

import top.zcwfeng.customwebview.command.base.Command;
import top.zcwfeng.customwebview.command.base.ResultBack;
import top.zcwfeng.customwebview.utils.WebConstants;

public class WebviewProcessCommandsManager {
    private static WebviewProcessCommandsManager instance;
    private LocalCommands localCommands;

    private WebviewProcessCommandsManager() {
        localCommands = new LocalCommands();
    }

    public static WebviewProcessCommandsManager getInstance() {
        if (instance == null) {
            synchronized (WebviewProcessCommandsManager.class) {
                instance = new WebviewProcessCommandsManager();
            }
        }
        Log.d("WebviewProcessCommandsManager:", instance + "");
        return instance;
    }

    /**
     * 动态注册command
     * 应用场景：其他模块在使用WebView的时候，需要增加特定的command，动态加进来
     */
    public void registerCommand(int commandLevel, Command command) {
        switch (commandLevel) {
            case WebConstants.LEVEL_LOCAL:
                localCommands.registerCommand(command);
                break;
        }
    }

    /**
     * Commands handled by Webview itself, these command does not require aidl.
     */
    public void findAndExecLocalCommnad(Context context, int level, String action, Map params, ResultBack resultBack) {
        if (localCommands.getCommands().get(action) != null) {
            localCommands.getCommands().get(action).exec(context, params, resultBack);
        }
    }

    public boolean checkHitLocalCommand(int level, String action) {
        return localCommands.getCommands().get(action) != null;
    }

}

