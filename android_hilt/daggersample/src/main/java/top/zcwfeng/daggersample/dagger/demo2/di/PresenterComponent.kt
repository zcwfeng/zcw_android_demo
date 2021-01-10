package top.zcwfeng.daggersample.dagger.demo2.di

import dagger.Component
import top.zcwfeng.daggersample.MainActivity2
import top.zcwfeng.daggersample.dagger.scope.AppScope
import javax.inject.Singleton
@AppScope
@Component(modules = [PresenterModule::class])
open interface PresenterComponent {
    // 之前如果有注入过Component进入MainActivity2 同时多个会报错
    // 需要更改写法
//    fun inject(mainActivity: MainActivity2)
    fun providerPresenter():Presenter;
}