package top.zcwfeng.kotlin.project.modules.register.inter

import android.content.Context

interface RegisterModel {

    fun regist(context: Context,
              username:String,
              password:String,
              repasswd:String,
              lsn: RegisterPresenter.OnRegistListener)
    fun cancelRequest()
}