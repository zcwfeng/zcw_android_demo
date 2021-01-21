package top.zcwfeng.httpprocessor_java_hilt;

/**
 * 顶层的回调接口
 * 将来可能是，json，xml，Protobuf
 */
public interface ICallback {
    void onSuccess(String result);
    void onFailure(String e);
}
