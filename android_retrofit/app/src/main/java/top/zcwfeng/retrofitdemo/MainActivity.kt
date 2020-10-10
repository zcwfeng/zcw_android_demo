package top.zcwfeng.retrofitdemo

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.zcwfeng.retrofitdemo.api.WanAndroidApi
import top.zcwfeng.retrofitdemo.bean.ProjectBean
import top.zcwfeng.retrofitdemo.databinding.ActivityMainBinding
import top.zcwfeng.retrofitdemo.retrofit.RetrofitClient
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var viewDataBinding: ActivityMainBinding

    private val wanAndroidApi = RetrofitClient.instance.getService(WanAndroidApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(viewDataBinding.toolbar)

        viewDataBinding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        testRetrofit()


    }

    companion object {
        const val TAG = "zcwfeng"
    }

    private fun testupload4() {
        val files = listOf<File>()
        val map = mutableMapOf<String, MultipartBody.Part>()
        files.forEachIndexed { index, file ->
            val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
            val part = MultipartBody.Part.createFormData("上传的key${index}", file.name, requestBody)
            map["上传的key${index}"] = part
        }
        wanAndroidApi.upload4(map)
    }

    private fun testUpload3() {

        val files = listOf<File>()
        val map = mutableMapOf<String, RequestBody>()
        files.forEach() { file ->
            val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
            map["file\";filename=\"test.png"] = requestBody
        }

        wanAndroidApi.upload3(map)
    }

    private fun testuload2() {
        val file = File("")
        val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
        val filePart = MultipartBody.Part.createFormData("upload key", file.name, requestBody)
        val call = wanAndroidApi.upload2(filePart)
        call.execute()
    }

    private fun testupload() {
        val file = File("")
        val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
        val call = wanAndroidApi.upload(requestBody)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                Log.i(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }

    // TODO: Retrofit 使用步骤
    private fun testRetrofit() {
        //TODO 1. 构建一个retrofit对象
        val retrofit = Retrofit.Builder()
            //Retrofit2的baseUrl 必须以 /(斜杆)结束，抛出一个IllegalArgumentException
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //TODO 2. 获取WanAndroidApi接口的代理对象
        val wanAndroidApi = retrofit.create(WanAndroidApi::class.java)

        //TODO 3. 获取具体的请求业务方法
        val projectCall = wanAndroidApi.getProject()

        //TODO 发起请求
        // 同步 val projectBean  = projectCall.execute()
        // 异步
        projectCall.enqueue(object : Callback<ProjectBean> {
            override fun onFailure(call: Call<ProjectBean>, t: Throwable) {
                Log.i(TAG, "错误：${t.message}")
            }

            override fun onResponse(call: Call<ProjectBean>, response: Response<ProjectBean>) {
                Log.i(TAG, "成功： ${response.body()}")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}