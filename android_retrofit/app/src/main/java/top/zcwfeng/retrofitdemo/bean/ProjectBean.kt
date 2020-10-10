package top.zcwfeng.retrofitdemo.bean

data class ProjectBean(
    val `data`: List<Data>,
    val errorCode: Int,
    val errorMsg: String
)