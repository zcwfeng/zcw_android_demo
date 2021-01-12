package top.zcwfeng.httpprocessor;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 回调接口的一种实现 new HttpCallback<JavaBean>
 */
public abstract class HttpCallback<Result> implements ICallback {

    @Override
    public void onSuccess(String result) {
        //result 就是网络访问第三方框架返回的字符串
//        1. 得到调用者用什么样的JavaBean接受数据
        Class<?> clz = analysisClassInfo(this);
//        2. 把result转换成对应的javabean
        Gson gson = new Gson();
        Result objResult = (Result) gson.fromJson(result, clz);
//        3.objResult 交给程序员使用
        onSuccess(objResult);
    }

    public abstract void onSuccess(Result objResult);

    private Class<?> analysisClassInfo(Object object){
        // getGenericSuperclass
        // 得到包含原始类型，参数化类型，数组，类型变量，进本数据类型
        Type type = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    @Override
    public void onFailure(String e) {

    }
}
