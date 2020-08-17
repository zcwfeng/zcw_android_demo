package top.zcwfeng.webview.command;

import java.util.Map;

import top.zcwfeng.webview.ICallbackFromMainprocessToWebViewProcessInterface;

public interface Command {
    String name();
    void execute(Map params,ICallbackFromMainprocessToWebViewProcessInterface callback);
}
