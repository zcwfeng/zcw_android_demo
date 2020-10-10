package coroutines

import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import kotlinx.coroutines.*
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class Demo {
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


