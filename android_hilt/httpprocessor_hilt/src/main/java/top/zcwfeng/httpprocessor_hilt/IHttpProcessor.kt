package top.zcwfeng.httpprocessor_hilt

interface IHttpProcessor {
    fun post(url:String,params:Map<String,Any>,callback:ICallback)
}