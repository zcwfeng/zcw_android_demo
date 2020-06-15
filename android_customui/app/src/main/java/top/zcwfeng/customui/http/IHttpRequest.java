package top.zcwfeng.customui.http;

/**
 * 顶层相应接口(用来扩展拼装HttpTask)
 */
public interface IHttpRequest {
    void setUrl(String url);
    void setParams(byte[] params);
    void execute();
    // 两个接口链接在一起
    void setListener(IHttpListener listener);
}
