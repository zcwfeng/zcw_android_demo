package top.zcwfeng.customui.http;

import java.io.InputStream;

/**
 * 顶层相应接口
 */
public interface IHttpListener {
    void onSuccess(InputStream inputStream);
    void onFailure();
}
