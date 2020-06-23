package top.zcwfeng.kotlin.project.modules.register

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import top.zcwfeng.kotlin.project.api.WanAndroidApi
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterModel
import top.zcwfeng.kotlin.project.modules.register.inter.RegisterPresenter
import top.zcwfeng.kotlin.project.net.APIClient
import top.zcwfeng.kotlin.project.net.APIResponse

class RegisterModelImpl : RegisterModel {

    override fun regist(
        context: Context,
        username: String,
        password: String,
        repasswd: String,
        lsn: RegisterPresenter.OnRegistListener
    ) {
// 网络模型
        APIClient.instance.instanceRetrofit(WanAndroidApi::class.java)
            .registerAction(username, password, repasswd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : APIResponse<LoginRegisterResponse>(context) {
                override fun success(data: LoginRegisterResponse?) {
                    lsn.registSuccess(data)
                }

                override fun failure(errorMsg: String?) {
                    lsn.failure(errorMsg)
                }

            })
    }

    override fun cancelRequest() {

    }
}