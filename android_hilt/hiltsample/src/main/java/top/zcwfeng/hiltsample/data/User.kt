package top.zcwfeng.hiltsample.data

import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
data class User constructor(var id:Int,var name:String,var mood:String){
    @Inject
    constructor():this(1,"David","毫无波澜")
}