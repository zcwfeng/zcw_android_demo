package top.zcwfeng.kotlin.project.modules.login

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import top.zcwfeng.kotlin.project.api.WanAndroidApi
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.login.inter.LoginModel
import top.zcwfeng.kotlin.project.modules.login.inter.LoginPresenter
import top.zcwfeng.kotlin.project.net.APIClient
import top.zcwfeng.kotlin.project.net.APIResponse

class LoginModelImpl :LoginModel {
    override fun login(
        context: Context,
        username: String,
        password: String,
        lsn: LoginPresenter.OnLoginListener
    ) {

        APIClient.instance.instanceRetrofit(WanAndroidApi::class.java)
            . loginAction(username,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: APIResponse<LoginRegisterResponse>(context){
                override fun success(data: LoginRegisterResponse?) {
                    // 成功  data UI
//                    Log.e(Flag.TAG, "success: $data")
//                    Toast.makeText(this@LoginActivity, "登录成功😀", Toast.LENGTH_SHORT).show()
                    lsn.loginSuccess(data)
                }

                override fun failure(errorMsg: String?) {
//                    Log.e(Flag.TAG, "failure: $errorMsg" )
//                    Toast.makeText(this@LoginActivity, "登录失败 ~ 呜呜呜", Toast.LENGTH_SHORT).show()
                    lsn.failure(errorMsg)
                }

            });

    }

    override fun cancelRequest() {
    }
}