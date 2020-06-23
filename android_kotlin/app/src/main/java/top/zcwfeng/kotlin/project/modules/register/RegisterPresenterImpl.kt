package top.zcwfeng.kotlin.project.modules.register

import android.content.Context
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterModel
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterPresenter
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterView

class RegisterPresenterImpl constructor(var view: RegisterView?) : RegisterPresenter,
    RegisterPresenter.OnRegistListener {
    private val registerModel: RegisterModel = RegisterModelImpl()


    override fun regist(context: Context, username: String, password: String, repassword: String) {

        registerModel.regist(context, username, password, repassword, this)

    }

    override fun unAttachView() {
    }

    override fun registSuccess(data: LoginRegisterResponse?) {
        view?.registSuccess(data)
    }

    override fun failure(errorMsg: String?) {
        view?.failure(errorMsg)
    }
}