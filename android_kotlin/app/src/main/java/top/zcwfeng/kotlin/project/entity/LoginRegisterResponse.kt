package top.zcwfeng.kotlin.project.entity

data class LoginRegisterResponse(
    val admin: Boolean,
    val chapterTops: List<*>,
    val collectIds: List<*>,
    val email: String?,
    val icon: String?,
    val id: Int,
    val nickname: String?,
    val password: String?,
    val publicName: String?,
    val token: String?,
    val type: Int,
    val username: String?
)