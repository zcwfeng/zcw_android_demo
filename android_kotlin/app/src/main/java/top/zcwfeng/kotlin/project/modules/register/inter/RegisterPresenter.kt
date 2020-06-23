package top.zcwfeng.kotlin.project.modules.register.inter

import android.content.Context
import top.zcwfeng.kotlin.project.basse.IBasePresenter
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse

// view - P - model
interface RegisterPresenter :IBasePresenter{

    fun regist(context: Context,
               username:String,
               password:String,
               repassword:String)

    interface OnRegistListener{
        fun registSuccess(data: LoginRegisterResponse?)
        fun failure(errorMsg: String?)
    }

}