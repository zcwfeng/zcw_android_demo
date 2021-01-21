package top.zcwfeng.httpprocessor_java_hilt;

import java.util.Map;

/**
 * 类似房产公司，房子访问能力
 */
public interface IHttpProcessor {
    // 网络访问能力
    void post(String url, Map<String,Object> params,ICallback callback);
    void get(String url, Map<String,Object> params,ICallback callback);
}
