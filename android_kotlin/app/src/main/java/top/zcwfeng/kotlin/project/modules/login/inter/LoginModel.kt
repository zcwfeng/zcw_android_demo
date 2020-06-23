package top.zcwfeng.kotlin.project.modules.login.inter

import android.content.Context

interface LoginModel {

    fun login(context: Context, username:String, password:String,lsn:LoginPresenter.OnLoginListener)
    fun cancelRequest()
}