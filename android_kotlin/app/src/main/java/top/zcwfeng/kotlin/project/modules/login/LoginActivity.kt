package top.zcwfeng.kotlin.project.modules.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import top.zcwfeng.CoroutinStudyActivity
import top.zcwfeng.kotlin.MainActivity
import top.zcwfeng.kotlin.R
import top.zcwfeng.kotlin.databinding.ActivityLoginBinding
import top.zcwfeng.kotlin.project.basse.BaseActivity
import top.zcwfeng.kotlin.project.config.Flag
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.login.inter.LoginPresenter
import top.zcwfeng.kotlin.project.modules.login.inter.LoginView
import top.zcwfeng.kotlin.project.modules.register.RegisterActivity

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {
    lateinit var dataBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        dataBinding.userLoginBt.setOnClickListener(onClickListener)
        dataBinding.coroutinDemo.setOnClickListener(onClickListener)
        dataBinding.userRegisterTv.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.user_login_bt -> {
                val username = dataBinding.userPasswordEt.text.toString()
                val password = dataBinding.userPasswordEt.text.toString()
                Log.d(Flag.TAG, ":username--->${username}--password-->${password}")
                presenter.login(this@LoginActivity, username, password)
            }
            R.id.coroutin_demo -> {
                val intent = Intent(this,CoroutinStudyActivity::class.java);
                startActivity(intent)
            }
        }
    }

    override fun loginSuccess(data: LoginRegisterResponse?) {
        Toast.makeText(this@LoginActivity, "登录成功😀", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun failure(errorMsg: String?) {
        Toast.makeText(this@LoginActivity, "登录失败 ~ 呜呜呜", Toast.LENGTH_SHORT).show()

    }

    override fun recycle() {
        presenter.unAttachView()
    }

    override fun createP(): LoginPresenter = LoginPresenterImpl(this@LoginActivity)

}