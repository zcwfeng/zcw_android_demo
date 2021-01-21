package top.zcwfeng.httpprocessor_java_hilt;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import top.zcwfeng.httpprocessor_java_hilt.annotation.BindOkHttp;
import top.zcwfeng.httpprocessor_java_hilt.annotation.BindVolley;
import top.zcwfeng.httpprocessor_java_hilt.annotation.BindXUtils;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class HttpProcessorModule {
    @BindOkHttp
    @Singleton
    @Binds
    abstract IHttpProcessor bindOKHttp(OkHttpProcessor okHttpProcessor);

    @BindVolley
    @Singleton
    @Binds
    abstract IHttpProcessor bindVolley(VolleyProcessor okHttpProcessor);

    @BindXUtils
    @Singleton
    @Binds
    abstract IHttpProcessor bindXUtils(XUtilsProcessor okHttpProcessor);
}
