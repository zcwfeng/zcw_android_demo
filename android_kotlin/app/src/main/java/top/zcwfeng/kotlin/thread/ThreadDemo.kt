package top.zcwfeng.kotlin.thread

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun test() = runBlocking{


    launch (Dispatchers.Unconfined){

        launch (Dispatchers.IO){
            println("Dispatchers.IO-----" + Thread.currentThread().name)

        }

        println("Dispatchers.Main-----" + Thread.currentThread().name)

//        val witchCon = withContext(Dispatchers.IO) {
//            println("withContext---Dispatchers.IO-----" + Thread.currentThread().name)
//
//        }
//        println(witchCon)


    }


//    thread {
//        println("thread-----" + Thread.currentThread().name)
//    }
//
//    async {
//        println("async-----" + Thread.currentThread().name)
//
//    }
}

suspend fun test1(){
    withContext(Dispatchers.IO){
        println("suspend test1 withContext IO")
        println("withContext-----" + Thread.currentThread().name)

    }


    withContext (Dispatchers.Unconfined){
        println("withContext (Dispatchers.Unconfined)-----" + Thread.currentThread().name)

    }
}

suspend fun test2(){
    println("aaaa")
    delay(1000L)
    println("bbbb")
}

suspend fun test3(){
    withContext(Dispatchers.Main) {
        println("adfafa")
    }
}

suspend fun main(){
//    runBlocking {
//        test()
//    }
//    test1()
//        test2()
    test3()
}