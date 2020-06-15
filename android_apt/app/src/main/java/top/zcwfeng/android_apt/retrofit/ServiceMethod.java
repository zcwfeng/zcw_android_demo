package top.zcwfeng.android_apt.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import top.zcwfeng.android_apt.retrofit.annotation.FIELD;
import top.zcwfeng.android_apt.retrofit.annotation.GET;
import top.zcwfeng.android_apt.retrofit.annotation.POST;
import top.zcwfeng.android_apt.retrofit.annotation.QUERY;

public class ServiceMethod {
    private final boolean hasBody;
    private final String httpMethod;
    private final Call.Factory callFactory;
    private final HttpUrl baseUrl;
    private final String relativeUrl;
    private final ParameterHandler[] parameterHandlers;
    private FormBody.Builder formBuilder;

    private HttpUrl.Builder baseUrlBuilder;

    public ServiceMethod(Builder builder) {
        baseUrl = builder.mCustomRetrofit.baseUrl;
        callFactory = builder.mCustomRetrofit.callFactory;
        httpMethod = builder.httpMethod;
        relativeUrl = builder.relativeUrl;
        hasBody = builder.hasBody;
        parameterHandlers = builder.parameterHandlers;
        if(hasBody) {
            formBuilder = new FormBody.Builder();
        }
    }

    public Object invoke(Object[] args) {
        // 先处理请求地址
        for (int i = 0; i < parameterHandlers.length; i++) {
            // 本身记录了key，现在给key赋值
            ParameterHandler handlers = parameterHandlers[i];
            handlers.apply(this,args[i].toString());
        }

        HttpUrl url;
        if(baseUrlBuilder == null) {
            baseUrlBuilder = baseUrl.newBuilder(relativeUrl);
        }
        url = baseUrlBuilder.build();

        FormBody formBody = null;
        if(formBuilder != null) {
            formBody = formBuilder.build();
        }

        Request request = new Request.Builder().url(url).method(httpMethod,formBody).build();
        return callFactory.newCall(request);
    }

    // kv 放进请求体
    public void addFieldParameter(String key, String value) {
        formBuilder.add(key,value);
    }

    // kv 放进url
    public void addQueryParameter(String key, String value) {
        if(baseUrlBuilder == null){
            baseUrlBuilder = baseUrl.newBuilder();
        }
        baseUrlBuilder.addQueryParameter(key,value);
    }

    public static class Builder {

        private final CustomRetrofit mCustomRetrofit;
        private final Annotation[] methodAnnotations;
        private final Annotation[][] paramAnnotations;
        private String httpMethod;
        private boolean hasBody;
        private String relativeUrl;
        private ParameterHandler[] parameterHandlers;


        public Builder(CustomRetrofit customRetrofit, Method method) {
            mCustomRetrofit = customRetrofit;
            methodAnnotations = method.getAnnotations();
            paramAnnotations = method.getParameterAnnotations();

        }

        public ServiceMethod build() {
            // 解析方法上面的注释
            for (Annotation methodAnnotation : methodAnnotations
            ) {
                if (methodAnnotation instanceof POST) {
                    this.httpMethod = "POST";
                    this.hasBody = true;
                    this.relativeUrl = ((POST) methodAnnotation).value();
                } else if (methodAnnotation instanceof GET) {
                    this.httpMethod = "GET";
                    this.hasBody = false;
                    this.relativeUrl = ((GET) methodAnnotation).value();


                }
            }

            // 解析方法参数
            int len = paramAnnotations.length;
            parameterHandlers = new ParameterHandler[len];
            for (int i = 0; i < len; i++) {
                Annotation[] annotations = paramAnnotations[i];
                for (Annotation an : annotations) {
                    if (an instanceof FIELD) {
                        // TODO: 2020/6/13 判断如果http method是get，现在解析到field提示使用者用Query
                        //得到field注解,key 保存下来
                        String value = ((FIELD) an).value();
                        parameterHandlers[i] = new ParameterHandler.FieldParameterHandler(value);
                    } else if (an instanceof QUERY) {
                        String value = ((QUERY) an).value();
                        parameterHandlers[i] = new ParameterHandler.QueryParameterHandler(value);

                    }
                }
            }

            return new ServiceMethod(this);
        }
    }
}
