package top.zcwfeng.kotlin.project.modules.login.inter

import android.content.Context
import top.zcwfeng.kotlin.project.basse.IBasePresenter
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse

// view - P - model
interface LoginPresenter :IBasePresenter{

    fun login(context: Context, username:String, password:String)

    interface OnLoginListener{
        fun loginSuccess(data: LoginRegisterResponse?)
        fun failure(errorMsg: String?)
    }

}