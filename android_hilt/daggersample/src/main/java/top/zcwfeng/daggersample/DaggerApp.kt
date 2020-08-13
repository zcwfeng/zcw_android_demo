package top.zcwfeng.daggersample

import android.app.Application
import top.zcwfeng.daggersample.dagger.AppComponent
import top.zcwfeng.daggersample.dagger.DaggerAppComponent

class DaggerApp: Application() {
    lateinit var appComponent:AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

}