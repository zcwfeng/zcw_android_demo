package top.zcwfeng.daggersample.dagger.demo2.module

import dagger.Module
import dagger.Provides
import top.zcwfeng.daggersample.data.DatabaseObject
import top.zcwfeng.daggersample.data.HttpObject
import java.lang.reflect.Constructor
import javax.inject.Singleton
//component<--->Module 只要有一个Component加了@singleton 那么 对应的Module 必须也加上
//@Singleton
@Module
class DatabaseModule() {
    @Provides
    fun provideDatabaseObject():DatabaseObject{
        return DatabaseObject()
    }
}