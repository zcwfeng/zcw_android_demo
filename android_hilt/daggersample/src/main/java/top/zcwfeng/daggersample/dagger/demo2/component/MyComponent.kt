package top.zcwfeng.daggersample.dagger.demo2.component

import dagger.Component
import top.zcwfeng.daggersample.MainActivity2
import top.zcwfeng.daggersample.SecondActivity
import top.zcwfeng.daggersample.dagger.demo2.di.PresenterComponent
import top.zcwfeng.daggersample.dagger.demo2.module.DatabaseModule
import top.zcwfeng.daggersample.dagger.demo2.module.HttpModule
import top.zcwfeng.daggersample.dagger.scope.UserScope
import javax.inject.Singleton

//@Singleton
@UserScope
@Component(modules = [HttpModule::class,DatabaseModule::class]
,dependencies = [PresenterComponent::class])
interface MyComponent {
    // 这里不能用多态，父类不行
    fun injectMainActivity(mainActivity: MainActivity2)
    fun injectSecondActivity(secondActivity:SecondActivity)
}