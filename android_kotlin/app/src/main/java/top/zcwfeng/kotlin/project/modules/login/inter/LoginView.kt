package top.zcwfeng.kotlin.project.modules.login.inter

import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse

interface LoginView {
    fun loginSuccess(data: LoginRegisterResponse?)
    fun failure(errorMsg: String?)

}