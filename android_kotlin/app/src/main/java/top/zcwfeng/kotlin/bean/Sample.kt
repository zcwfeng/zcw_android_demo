package top.zcwfeng.kotlin.bean

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

}

fun customFun(count: Int): String = if (count == 42) {
    "I have a answer."
} else {
    "The answer eludes me."
}

