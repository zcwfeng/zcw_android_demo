package top.zcwfeng.kotlin.project.modules.register

import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterView

class RegisterViewImpl : RegisterView {
    override fun registSuccess(data: LoginRegisterResponse?) {
        registSuccess(data)

    }

    override fun failure(errorMsg: String?) {
        failure(errorMsg)
    }
}