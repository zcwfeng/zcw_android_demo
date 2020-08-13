package top.zcwfeng.daggersample.dagger

import dagger.Component
import top.zcwfeng.daggersample.MainActivity
import top.zcwfeng.daggersample.dagger.scope.ActivityScope
import top.zcwfeng.daggersample.view.UserView

@ActivityScope
@Component
interface AppComponent {
    fun inject(mainActivity:MainActivity)
    fun inject(userView:UserView)
}