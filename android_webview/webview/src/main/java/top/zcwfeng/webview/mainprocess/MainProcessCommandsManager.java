package top.zcwfeng.webview.mainprocess;

import android.os.RemoteException;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import top.zcwfeng.webview.ICallbackFromMainprocessToWebViewProcessInterface;
import top.zcwfeng.webview.IWebviewProcessToMainProcessInterface;
import top.zcwfeng.webview.command.Command;

public class MainProcessCommandsManager extends IWebviewProcessToMainProcessInterface.Stub {

    private static MainProcessCommandsManager sInstance;
    private static HashMap<String, Command> mCommands = new HashMap<>();

    public static MainProcessCommandsManager getInstance() {
        if (sInstance == null) {
            synchronized (MainProcessCommandsManager.class) {
                sInstance = new MainProcessCommandsManager();
            }
        }
        return sInstance;
    }

    private MainProcessCommandsManager() {
        ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class);
        for (Command command : serviceLoader) {
            if (!mCommands.containsKey(command.name())) {
                mCommands.put(command.name(), command);
            }

        }

    }

    public void executeCommand(String commandName, Map params, ICallbackFromMainprocessToWebViewProcessInterface callback) {
        mCommands.get(commandName).execute(params,callback);
    }

    @Override
    public void handleWebCommand(String commandName, String jsonParam,ICallbackFromMainprocessToWebViewProcessInterface callback) throws RemoteException {
        MainProcessCommandsManager.getInstance().executeCommand(commandName,
                new Gson().fromJson(jsonParam, Map.class),callback);
    }
}
