package coroutines

import android.util.Log
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import kotlinx.coroutines.*
import org.junit.Test
import top.zcwfeng.CoroutinStudyActivity
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

val mScope = MainScope()
class Demo {
    @Test
    fun useRunBlockingTest(){
        runBlocking {
            Log.e(CoroutinStudyActivity.TAG, "runBlocking，$coroutineContext,${coroutineContext[CoroutineName]}")
        }
    }

    @Test
    fun useCourtinScopeTest(){
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(
                CoroutinStudyActivity.TAG,
                "CoroutineExceptionHandler: $throwable"
            )
        }) {
            coroutineScope {
                launch {
                    delay(500)
                    Log.e(CoroutinStudyActivity.TAG, "Child 1 ")
                }
                launch {
                    delay(1000)
                    Log.e(CoroutinStudyActivity.TAG, "Child 2 ")
                    throw  RuntimeException("--> RuntimeException <--")
                }
                launch {
                    delay(1500)
                    Log.e(CoroutinStudyActivity.TAG, "Child 3 ")
                }
            }
        }
    }


    @Test
    fun useSupervisorScopeTest(){
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(
                CoroutinStudyActivity.TAG,
                "CoroutineExceptionHandler: $throwable"
            )
        }) {
            supervisorScope {
                launch {
                    delay(500)
                    Log.e(CoroutinStudyActivity.TAG, "Child 1 ")
                }
                launch {
                    delay(1000)
                    Log.e(CoroutinStudyActivity.TAG, "Child 2 ")
                    throw  RuntimeException("--> RuntimeException <--")
                }
                launch {
                    delay(1500)
                    Log.e(CoroutinStudyActivity.TAG, "Child 3 ")
                }
            }
        }
    }

    @Test
    fun useSupervisorCourtinJobTest(){
        mScope.launch(Dispatchers.Default) {
            delay(500)
            Log.e(CoroutinStudyActivity.TAG, "Child 1")
        }
        // 在Child 2的上下文添加了异常处理
        mScope.launch(Dispatchers.Default + CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(CoroutinStudyActivity.TAG, "CoroutineExceptionHandler: $throwable")
        }) {
            delay(1000)
            Log.e(CoroutinStudyActivity.TAG, "Child 2")
            throw RuntimeException("--> RuntimeException <--")
        }





        mScope.launch(Dispatchers.Default) {
            delay(1500)
            Log.e(CoroutinStudyActivity.TAG, "Child 3")
        }
    }


    @Test
    fun jobTest(){
        val coroutineContext = Job() + Dispatchers.Default + CoroutineName("")
        println("$coroutineContext,${coroutineContext[CoroutineName]}")
        val newCoroutineContext = coroutineContext.minusKey(CoroutineName)
        println("$newCoroutineContext")
    }


    @Test
    fun asyncTest(){
        mScope.launch(Dispatchers.IO) {
            Log.d("asyncTest","asyncTest ${Thread.currentThread().name}")
            val deferred = async {
                delay(2000)
                "我是测试async"
            }
            Log.d("asyncTest","asyncTest ${deferred.await()}")
        }
    }

    @Test
    fun asyncTest2(){
        mScope.launch(Dispatchers.IO) {
            Log.d("asyncTest","asyncTest2 ${Thread.currentThread().name}")

            val job1 = async {
                // 请求1
                delay(1000)
                "1"
            }
            val job2 = async {
                // 请求2
                delay(1000)
                "2"
            }
            Log.d("asyncTest","zcwfeng....")
//            Log.e("asyncTest","merge ${job1.await()} ${job2.await()}")
            println("merge ${job1.await()} ${job2.await()}")
        }

    }

    fun testCoroutine1() {
        GlobalScope.launch(Dispatchers.Main) {
            iocode1()
            uicode1()
        }

        classicIoCode1(false) {
            uicode1()
        }

        classicIoCode2(true){
            uicode1()
        }
    }

    fun testCoroutine2(){
        MainScope().launch(Dispatchers.Main) {
            iocode1()
            uicode1()
        }

        classicIoCode1(false) {
            uicode1()
        }

        classicIoCode2(true){
            uicode1()
        }
    }



    private fun uicode1() {
        println("println uicode1 ${Thread.currentThread().name}")
    }

    private suspend fun iocode1() {
        withContext(Dispatchers.IO) {
            println("println iocode1 ${Thread.currentThread().name}")

        }
    }


    private fun classicIoCode1(uiThread:Boolean,block: () -> Unit) {
        thread {
            println("println  classic iocode1 ${Thread.currentThread().name}")
            if(uiThread){
                runOnUiThread {
                    block()
                }
            }  else {
                block()
            }
        }
    }


    private fun classicIoCode2(uiThread:Boolean,block: () -> Unit) {
        executor.execute {
            println("println  classic iocode1 ${Thread.currentThread().name}")
            if(uiThread){
                runOnUiThread {
                    block()
                }
            }  else {
                block()
            }
        }
    }



    private val executor = ThreadPoolExecutor(5, 10, 1,
        TimeUnit.MINUTES, LinkedBlockingDeque())

}


