package top.zcwfeng.customui.http;

public
interface IDataListener<T> {
    void onSuccess(T t);
    void onFailed();
}
