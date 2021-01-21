package top.zcwfeng.httpprocessor_hilt

interface ICallback {
    fun onSuccess(result: String)
    fun onFailure(e: String)
}