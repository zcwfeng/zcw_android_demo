package top.zcwfeng.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.zcwfeng.kotlin.coroutine.model.Repo
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TestActivity : AppCompatActivity() {
    val jobs = listOf<Any>()

    val scopeme:CoroutineScope = MainScope()//主线程 第一种

    val disposables:CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        val api = retrofit.create(top.zcwfeng.kotlin.coroutine.Api::class.java)
        //kotlin -> Retrofit
//        api.listRepos("rengwuxian")
//            .enqueue(object : Callback<List<Repo>?> {
//                override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
//                }
//
//                override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
//                    test_tv.text = response.body()?.get(0)?.name
//                    /*val inner:String? = response.body()?.get(0)?.name
//                    api.listRepos("google").enqueue(object : Callback<List<Repo>?> {
//                        override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
//
//                        }
//
//                        override fun onResponse(
//                            call: Call<List<Repo>?>,
//                            response: Response<List<Repo>?>
//                        ) {
//                            test_tv.text = inner + "-" + response.body()?.get(0)?.name
//
//                        }
//                    })*/
//                }
//            })


        //Kotlin Coroutine
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val repos: List<Repo> = api.listReposKt("rengwuxian")
//                test_tv.text = repos[0].name
//            } catch (e: Exception) {
//                test_tv.text = e.message
//            }
//        }


//        val job = GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val repos = async { api.listReposKt("rengwuxian") }
//                val repos2 = async { api.listReposKt("google") }
//                test_tv.text = "${repos.await()[0]?.name}-${repos2.await()[0]?.name}"
//            } catch (e: Exception) {
//                test_tv.text = e.message
//            }
//
//        }
//
//        job.cancel()

        scopeme.launch {
            val repos = async { api.listReposKt("rengwuxian") }
            val repos2 = async { api.listReposKt("google") }
            test_tv.text = "${repos.await()[0]?.name}-${repos2.await()[0]?.name}"
        }

        scopeme.cancel()

//        lifecycleScope.launch {  }
//        lifecycleScope.launchWhenCreated {  }
//        lifecycleScope.launchWhenResumed {  }
//        lifecycleScope.launchWhenStarted {  }


        //Kotlin RxJava
//        api.listReposRx("rengwuxian")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//            .subscribe(object : SingleObserver<List<Repo>?> {
//                override fun onSuccess(t: List<Repo>) {
//                    test_tv.text = t[0]?.name
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                }
//
//                override fun onError(e: Throwable) {
//                    test_tv.text = e.message
//                }
//            })

//        val zip = Single.zip<List<Repo>, List<Repo>, String>(
//            api.listReposRx("rengwuxian"),
//            api.listReposRx("google"),
//            BiFunction { repo1, repo2 -> "${repo1[0].name}-${repo2[0].name}" }
//
//        ).observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<String?> {
//                override fun onSuccess(t: String) {
//                    test_tv.text = t
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                }
//
//                override fun onError(e: Throwable) {
//                    test_tv.text = e.message
//
//                }
//            })


//        api.listReposRx("rengwuxian")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//            .subscribe(object : SingleObserver<List<Repo>?> {
//                override fun onSuccess(t: List<Repo>) {
//                    test_tv.text = t[0]?.name
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                }
//
//                override fun onError(e: Throwable) {
//                    test_tv.text = e.message
//                }
//            })



    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}