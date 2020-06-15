package top.zcwfeng.android_apt.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class CustomRetrofit {
    final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();
    public okhttp3.Call.Factory callFactory;
    public HttpUrl baseUrl;


    public <T> T create(final Class<?> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 解析方法上面所有的Annotation
                ServiceMethod serviceMethod = loadServiceMethod(method);
                return serviceMethod.invoke(args);
            }
        });

    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if(result != null) {
            return result;
        }
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if(result == null) {
                result = new ServiceMethod.Builder(this,method).build();
                serviceMethodCache.put(method,result);
            }
            return result;
        }
    }

    public CustomRetrofit(Call.Factory callFactory, HttpUrl baseUrl) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
    }

    public final static class Builder {
        private HttpUrl baseUrl;
        private okhttp3.Call.Factory callFactory;

        public Builder() {

        }


        public Builder callFactory(okhttp3.Call.Factory factory) {
            this.callFactory = factory;
            return this;
        }

        public Builder baseUrl(String httpUrl) {
            this.baseUrl = HttpUrl.get(httpUrl);
            return this;
        }

        public CustomRetrofit build() {

            if (baseUrl == null) {
                throw new IllegalStateException("Base Url  required");
            }

            okhttp3.Call.Factory callFactory = this.callFactory;
            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }

            return new CustomRetrofit(callFactory, baseUrl);

        }
    }
}
