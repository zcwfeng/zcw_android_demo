package top.zcwfeng.daggersample2.di

import dagger.Module
import dagger.Provides
import top.zcwfeng.daggersample2.obj.ObjectForTestSubModule

@Module
class TestSubModule {
    @Provides
    fun provideObjectForTestSubModule(): ObjectForTestSubModule {
        return ObjectForTestSubModule()
    }
}