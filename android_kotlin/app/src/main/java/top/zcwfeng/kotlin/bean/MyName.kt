package top.zcwfeng.kotlin.bean

import androidx.databinding.ObservableField

data class MyName (

    var name: ObservableField<String> = ObservableField<String>(),
    var nickName:String=""
)


