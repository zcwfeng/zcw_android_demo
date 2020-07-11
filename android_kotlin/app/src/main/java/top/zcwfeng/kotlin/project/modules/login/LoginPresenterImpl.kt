package top.zcwfeng.kotlin.project.modules.login

import android.content.Context
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.login.inter.LoginModel
import top.zcwfeng.kotlin.project.modules.login.inter.LoginPresenter
import top.zcwfeng.kotlin.project.modules.login.inter.LoginView

class LoginPresenterImpl(var loginView: LoginView?) : LoginPresenter,
    LoginPresenter.OnLoginListener {

    private val loginModel: LoginModel = LoginModelImpl()

    override fun login(context: Context, username: String, password: String) {
        loginModel?.login(context,username,password,this);
    }

    override fun unAttachView() {
        loginView = null
        loginModel?.cancelRequest()
    }

    override fun loginSuccess(data: LoginRegisterResponse?) {
        loginView?.loginSuccess(data)
    }

    override fun failure(errorMsg: String?) {
        loginView?.failure(errorMsg)
    }


}