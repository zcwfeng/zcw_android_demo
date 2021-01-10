package top.zcwfeng.daggersample.dagger.demo2.di

import dagger.Module
import dagger.Provides
import top.zcwfeng.daggersample.dagger.scope.AppScope

@AppScope
@Module
class PresenterModule {
    @AppScope
    @Provides
    fun providePresenter():Presenter{
        return Presenter()
    }
}