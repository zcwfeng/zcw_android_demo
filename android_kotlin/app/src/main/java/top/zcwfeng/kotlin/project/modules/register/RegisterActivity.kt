package top.zcwfeng.kotlin.project.modules.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistry
import androidx.databinding.DataBindingUtil
import top.zcwfeng.kotlin.R
import top.zcwfeng.kotlin.databinding.ActivityRegisterBinding
import top.zcwfeng.kotlin.project.basse.BaseActivity
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterPresenter
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterView

class RegisterActivity : BaseActivity<RegisterPresenter>() ,RegisterView {
    lateinit var databinding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding  = DataBindingUtil.setContentView(this,R.layout.activity_register)
        databinding.userRegisterBt.setOnClickListener {
            //调用注册
            val usernameStr = databinding.userPasswordEt.text.toString()
            val passwordStr = databinding.userPasswordEt.text.toString()
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