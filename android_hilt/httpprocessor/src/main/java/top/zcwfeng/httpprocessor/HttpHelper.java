package top.zcwfeng.httpprocessor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 业务员功能
 */
public class HttpHelper implements IHttpProcessor{
    //单例
    private static HttpHelper instance;
    public static HttpHelper obtain(){
        synchronized (HttpHelper.class){
            if(instance==null){
                instance=new HttpHelper();
            }
        }
        return instance;
    }
    private HttpHelper(){}

    //定义一个业主，卖房的人
    private static IHttpProcessor mIHttpProcessor=null;

    //通过一个API来设置哪一个业主卖出自己的房子，（谁来完成网络访问）
    public static void init(IHttpProcessor httpProcessor){
        mIHttpProcessor=httpProcessor;
    }


    @Override
    public void post(String url, Map<String, Object> params, ICallback callback) {
        //http://www.aaa.bbb/index?&user=jett&pwd=123
        //http://www.aaa.bbb/index
        //&user=jett&pwd=123
        String finalUrl=appendParams(url,params);//get 转换成post
        mIHttpProcessor.post(finalUrl,params,callback);
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback callback) {

    }


    public static String appendParams(String url, Map<String,Object> params) {
        if(params==null || params.isEmpty()){
            return url;
        }
        StringBuilder urlBuilder=new StringBuilder(url);
        if(urlBuilder.indexOf("?")<=0){
            urlBuilder.append("?");
        }else{
            if(!urlBuilder.toString().endsWith("?")){
                urlBuilder.append("&");
            }
        }
        for(Map.Entry<String,Object> entry:params.entrySet()){
            urlBuilder.append("&"+entry.getKey())
                    .append("=")
                    .append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();
    }
    private static String encode(String str){
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
