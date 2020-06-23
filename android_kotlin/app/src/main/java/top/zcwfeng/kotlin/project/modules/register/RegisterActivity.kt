package top.zcwfeng.kotlin.project.modules.register

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import top.zcwfeng.kotlin.R
import top.zcwfeng.kotlin.project.basse.BaseActivity
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterPresenter
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterView

class RegisterActivity : BaseActivity<RegisterPresenter>() ,RegisterView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        user_register_bt.setOnClickListener {
            //调用注册
            val usernameStr = user_phone_et.text.toString()
            val passwordStr = user_password_et.text.toString()
            presenter.regist(this@RegisterActivity, usernameStr, passwordStr, passwordStr)
        }
    }

    override fun createP(): RegisterPresenter = RegisterPresenterImpl(this@RegisterActivity)

    override fun recycle() {
        presenter.unAttachView()
    }

    override fun registSuccess(data: LoginRegisterResponse?) {
        Toast.makeText(this, "注册成功😀", Toast.LENGTH_SHORT).show()
    }

    override fun failure(errorMsg: String?) {
        Toast.makeText(this, "注册失败 ~ 呜呜呜", Toast.LENGTH_SHORT).show()
    }
}