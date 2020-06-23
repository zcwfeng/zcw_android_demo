package top.zcwfeng.kotlin.project.entity

data class LoginRegisterResponseWrappter<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)

