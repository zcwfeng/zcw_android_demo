package top.zcwfeng.customui.http;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class JsonHttpRequest implements IHttpRequest {

    IHttpListener iHttpListener;
    String url;
    byte[] params;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setParams(byte[] params) {
        this.params = params;
    }

    @Override
    public void execute() {
            //只要回来是个流就行 OKHTTP URLConnection 请求
            doGet();
    }

    public void doGet() {
        URL localURL = null;
        URLConnection connection = null;
        HttpURLConnection httpURLConnection = null;
        try {
            localURL = new URL(url);
            connection = localURL.openConnection();
            httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setConnectTimeout(6000);//
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");//POST
//            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");

            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(params);
            bufferedOutputStream.flush();
            outputStream.close();
            bufferedOutputStream.close();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = httpURLConnection.getInputStream();
                iHttpListener.onSuccess(in);
            } else {
                throw new RuntimeException("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");

        } finally {
            httpURLConnection.disconnect();
        }


    }

    @Override
    public void setListener(IHttpListener listener) {
        this.iHttpListener = listener;
    }
}
