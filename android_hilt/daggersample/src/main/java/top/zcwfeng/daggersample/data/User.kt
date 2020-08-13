package top.zcwfeng.daggersample.data

import top.zcwfeng.daggersample.dagger.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
data class User constructor(var id:Int,var name:String,var mood:String){
    @Inject
    constructor():this(1,"David","Dagger 毫无波澜")
}