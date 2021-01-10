package top.zcwfeng.daggersample

import android.app.Application
import top.zcwfeng.daggersample.dagger.AppComponent
import top.zcwfeng.daggersample.dagger.DaggerAppComponent
import top.zcwfeng.daggersample.dagger.demo2.component.DaggerMyComponent
import top.zcwfeng.daggersample.dagger.demo2.component.MyComponent
import top.zcwfeng.daggersample.dagger.demo2.di.DaggerPresenterComponent
import top.zcwfeng.daggersample.dagger.demo2.di.PresenterComponent
import top.zcwfeng.daggersample.dagger.demo2.di.PresenterModule
import top.zcwfeng.daggersample.dagger.demo2.module.DatabaseModule
import top.zcwfeng.daggersample.dagger.demo2.module.HttpModule


class DaggerApp: Application() {
    lateinit var appComponent:AppComponent
    lateinit var myComponent:MyComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        myComponent = DaggerMyComponent.builder()
            .httpModule(HttpModule())
            .databaseModule(DatabaseModule())
            .presenterComponent(DaggerPresenterComponent.create())
            .build()
    }



}