package top.zcwfeng.customui.http;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * 合并两个接口
 */
public class HttpTask<T> implements Runnable{
    private IHttpRequest request;
    private IHttpListener listener;

    public HttpTask(String url,T requestData,IHttpRequest request, IHttpListener listener) {
        this.request = request;
        this.listener = listener;
        this.request.setListener(listener);
        this.request.setUrl(url);
        // TODO: 2020/6/5 写一个模板犯法给子类用处理不用的HttpTask（json,xml,protobuf）
        if(requestData != null) {
            String  dataStr = new Gson().toJson(requestData);
            try {
                this.request.setParams(dataStr.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void run() {
        request.execute();
    }
}
