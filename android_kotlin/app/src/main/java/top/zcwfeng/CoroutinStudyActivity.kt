package top.zcwfeng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import top.zcwfeng.kotlin.R

class CoroutinStudyActivity : AppCompatActivity() {
    companion object {
        const val TAG = "zcwfeng"

    }

    private val mScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutin_study)

        baseCoutinTest()
        baseAsyncTest()
        baseJobTest()
        // 用作开发中的测试代码，是阻塞代码
        baseRunBlocking()

        baseCancelCoroutin()



    }

    private fun baseCancelCoroutin() {
        useSupervisorJob()


        useSupervisorScope()
    }

    private fun useSupervisorScope() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(
                TAG,
                "CoroutineExceptionHandler: $throwable"
            )
        }) {
            supervisorScope {
                launch {
                    delay(500)
                    Log.e(TAG, "Child 1 ")
                }
                launch {
                    delay(1000)
                    Log.e(TAG, "Child 2 ")
                    throw  RuntimeException("--> RuntimeException <--")
                }
                launch {
                    delay(1500)
                    Log.e(TAG, "Child 3 ")
                }
            }
        }
    }

    private fun useSupervisorJob() {
        //        使用SupervisorJob*
        mScope.launch(Dispatchers.Default) {
            delay(500)
            Log.e(TAG, "Child 1")
        }

        mScope.launch(Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            Log.e(CoroutinStudyActivity.TAG, "CoroutineExceptionHandler: $throwable")
        }) {
            delay(1000)
            Log.e(CoroutinStudyActivity.TAG, "Child 2")
            throw RuntimeException("--> RuntimeException <--")
        }

        mScope.launch(Dispatchers.Default) {
            delay(1500)
            Log.e(TAG, "Child 3")
        }

    }

    private fun baseRunBlocking() {

        runBlocking {
            Log.e(TAG, "runBlocking，$coroutineContext,${coroutineContext[CoroutineName]}")
        }
    }

    // 3
    private fun baseJobTest() {
        val coroutineContext = Job() + Dispatchers.Default + CoroutineName("myContext")
        Log.e(TAG, "$coroutineContext,${coroutineContext[CoroutineName]}")
        val newCoroutineContext = coroutineContext.minusKey(CoroutineName)
        Log.e(TAG, "$newCoroutineContext")
    }

    // 2
    private fun baseAsyncTest() {
        asyncTest()
        asyncTest2()
    }

    // 1
    private fun baseCoutinTest() {
        val job1 = mScope.launch {
            delay(1000)
        }
        val job2 = mScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                Log.e("zcwfeng", "update UI")
            }
        }
        mScope.launch(Dispatchers.IO) {
            val userInfo = getUserInfo()
            Log.e("zcwfeng", "userInfo $userInfo")
        }
    }

    private suspend fun getUserInfo() =
        withContext(Dispatchers.IO) {
            delay(2000)
            "Kotlin"
        }


    private fun asyncTest() {
        mScope.launch {
            val deferred = async(Dispatchers.IO) {
                delay(2000)
                "asyncTest"
            }
            Log.e("zcwfeng", deferred.await())
        }
    }

    private fun asyncTest2() {
        mScope.launch {
            val job1 = async {
                // 请求1
                delay(5000)
                "1"
            }
            val job2 = async {
                // 请求2
                delay(5000)
                "2"
            }
            val job3 = async {
                // 请求3
                delay(5000)
                "3"
            }
            val job4 = async {
                // 请求4
                delay(5000)
                "4"
            }
            val job5 = async {
                // 请求5
                delay(5000)
                "5"
            }

            // 代码执行到此处时  5个请求已经同时在执行了
            // 等待各job执行完 将结果合并
            Log.e(
                "zcwfeng",
                "asyncTest2: ${job1.await()} ${job2.await()} ${job3.await()} ${job4.await()} ${job5.await()}"
            )

            // 因为我们设置的模拟时间都是5000毫秒  所以当job1执行完时  其他job也均执行完成
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mScope.cancel()
    }
}