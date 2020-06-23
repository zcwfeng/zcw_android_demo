package top.zcwfeng.kotlin.project.api

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponse
import top.zcwfeng.kotlin.project.entity.LoginRegisterResponseWrappter

interface WanAndroidApi {
    @POST("/user/login")
    @FormUrlEncoded
    fun loginAction(
        @Field("username") username: String,
        @Field("password") password: String
    )
            : Observable<LoginRegisterResponseWrappter<LoginRegisterResponse>>

    @POST("/user/register")
    @FormUrlEncoded
    fun registerAction(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword:String)

            : Observable<LoginRegisterResponseWrappter<LoginRegisterResponse>>
}