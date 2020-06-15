package top.zcwfeng.customui.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class JsonHttpListener<T> implements IHttpListener {
    // TODO: 2020/6/5  用户用什么样的javabean 来接收结果
    // 字节码泛型
    // 返回对象
    private Class<T> response;
    private IDataListener iDataListener;

    // TODO: 2020/6/5 线程切换
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<T> response, IDataListener iDataListener) {
        this.response = response;
        this.iDataListener = iDataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //inputstream 有数据
        //inputStream 转String
        // TODO: 2020/6/5
        String content = getContent(inputStream);
        final T responseObject = new Gson().fromJson(content, response);
//        String responseObject = response.toString();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                iDataListener.onSuccess(responseObject);
            }
        });
    }

    // TODO: 2020/6/5 动态转
    private String getContent(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    @Override
    public void onFailure() {


    }
}
