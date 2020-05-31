package top.zcwfeng.kotlin.bean

import kotlinx.android.synthetic.main.activity_main2.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import top.zcwfeng.kotlin.study.Repository
import top.zcwfeng.kotlin.study.User


fun main() {


    val ps = listOf(11, 22, 33, 33)
    val max = ps.maxBy { it.inc() }
    println("max;$max")

    val user1 = User("jane","doe")
    val user2 = User("jane","doe")

    val structurallyEqual = user1 == user2
    val referentiallyEqual = user1 === user2
    print(structurallyEqual)
    print(referentiallyEqual)

    // usage
    val jane = User ("Jane") // same as User("Jane", null)
    val joe = User ("John", "Doe")

    val john = User (firstName = "John", lastName = "Doe")


    // usage
    val a = User (lastName = "Doe") // same as User(null, "Doe")
    val b = User ("John", "Doe")

    val name = if(jane.firstName != null) {
        a.lastName
    }else{
        "unknow"
    }

    print("\n$name is ....")

    var count = if(Repository.instance?.getUsers() != null) {
        Repository.instance?.getUsers()?.size
    }else {
        0
    }

    print("\n$count")

    var temp = runBlocking {

        launch { doword() }
        println("Hello")

        repeat(100_000) { // 启动大量的协程
            launch {
                delay(1000L)
                print(".")
            }
        }
    }

    print(temp)

}

suspend fun doword(){
    delay(1000L)
    println("World")
}

fun customFun(count: Int): String = if (count == 42) {
    "I have a answer."
} else {
    "The answer eludes me."
}

