package top.zcwfeng.hilt.model

import javax.inject.Inject

data class User(val name:String,val desc:String) {
    @Inject
    constructor():this(name = "David",desc = "一言难尽")

}

