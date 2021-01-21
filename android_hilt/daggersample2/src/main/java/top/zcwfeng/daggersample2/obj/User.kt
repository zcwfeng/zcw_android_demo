package top.zcwfeng.daggersample2.obj

import javax.inject.Inject

data class User @Inject constructor(var name: String, var pwd: String)
