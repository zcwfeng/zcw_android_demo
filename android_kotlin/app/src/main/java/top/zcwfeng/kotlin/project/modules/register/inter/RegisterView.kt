package top.zcwfeng.kotlin.project.modules.register.inter

import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse

interface RegisterView {
    fun registSuccess(data: LoginRegisterResponse?)
    fun failure(errorMsg: String?)

}