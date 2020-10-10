package top.zcwfeng.retrofitdemo.api

import androidx.lifecycle.LiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import top.zcwfeng.retrofitdemo.bean.ProjectBean

/**
 * 用法例子
 */
interface WanAndroidApi {

    //TODO 接收一个字符串表示接口 path ，与 baseUrl 组成完整的 Url
    // https://www.wanandroid.com/project/tree/json
    @GET("project/tree/json")
    fun getProject(): Call<ProjectBean>

    @GET("project/tree/json")
    fun getProject1(): LiveData<ProjectBean>

    //TODO  ResponseBody convert (GsonConvertFactory)->ProjectBean
    @HTTP(method = "get", path = "project/tree/json", hasBody = false)
    fun getProject2(): Call<ResponseBody>

    //TODO 表示响应体的数据用流的方式返回，适用于返回的数据比较大，该注解在在下载大文件的特别有用
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>

    //TODO 使用 `@Headers`注解设置固定的请求头，所有请求头不会相互覆盖，即使名字相同。
    @Headers("Cache-Control: max-age=10000")
    @GET("project/xxx")
    fun exampleHeader(): Call<ResponseBody>

    //TODO 使用 `@Header`注解动态更新请求头，匹配的参数必须提供给 @Header ，若参数值为 null ，这个头会被省略，
    // 否则，会使用参数值的 toString 方法的返回值
    @GET("project/xxx")
    fun example(@Header("Authorization") authorization: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("xxx")
    fun example1(
        @Field("name") vararg name: String,
        @Field("array") array: Array<String>,
        @FieldMap map: Map<String, String>
    ): Call<ResponseBody>

    @GET("article/listproject/{id}/json")
    fun example2(@Path("id") id: Int): Call<ResponseBody>

    // TODO such as:http://www.xxx.xxx?name=sss&age=22
    @GET("XXX")
    fun search(@Query("name") name: String, @QueryMap map: Map<String, String>): Call<ResponseBody>

    ///////TODO 上传单张图片//////
    /**
     * Multipart：表示请求实体是一个支持文件上传的Form表单，需要配合使用@Part,适用于 有文件 上传的场景
     * Part:用于表单字段,Part和PartMap与Multipart注解结合使用,适合文件上传的情况
     * PartMap:用于表单字段,默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传
     * Part 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型；
     *
     * @param file 服务器指定的上传图片的key值
     * @return
     */
    @Multipart
    @POST("project/upload")
    fun upload(@Part("file\";filename=\"test.png") file: RequestBody): Call<ResponseBody>

    @Multipart
    @POST("project/upload")
    fun upload2(@Part file: MultipartBody.Part): Call<ResponseBody>

    // TODO 上传多张图片

    @Multipart
    @POST("project/upload")
    fun upload3(@PartMap map:Map<String,RequestBody>):Call<ResponseBody>

    @Multipart
    @POST("project/upload")
    fun upload4(@PartMap map:Map<String,MultipartBody.Part>):Call<ResponseBody>
}