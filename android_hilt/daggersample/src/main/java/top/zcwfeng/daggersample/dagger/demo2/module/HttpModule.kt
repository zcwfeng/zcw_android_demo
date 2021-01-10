package top.zcwfeng.daggersample.dagger.demo2.module

import dagger.Module
import dagger.Provides
import top.zcwfeng.daggersample.dagger.scope.UserScope
import top.zcwfeng.daggersample.data.HttpObject
import javax.inject.Singleton

/**
 * 用来提供对象
 */
//component<--->Module 只要有一个Component加了@singleton 那么 对应的Module 必须也加上
//@Singleton
@UserScope
@Module
class HttpModule {
//    @Singleton
    @UserScope
    @Provides
    fun providerHttpObject():HttpObject{
        return HttpObject()
    }
}