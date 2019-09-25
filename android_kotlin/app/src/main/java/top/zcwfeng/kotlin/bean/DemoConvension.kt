package top.zcwfeng.kotlin.bean

import androidx.constraintlayout.solver.widgets.Rectangle

val name:String = SampleJava.test

class FilledRectangle: Rectangle() {
    fun draw() { /* ... */ }
    val borderColor: String get() = "black"

    inner class Filler {
        fun fill() { /* ... */ }
        fun drawAndFill() {
//            super@FilledRectangle.draw() // Calls Rectangle's implementation of draw()
            fill()
//            println("Drawn a filled rectangle with color ${super@FilledRectangle.bodercolor}") // Uses Rectangle's implementation of borderColor's get()
        }
    }
}

fun demo(){
    loop@ for (i in 1..100) {
        // ...
    }
    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (j > 10) break@loop
        }
    }
}

class Person(name:String){}
class Customer public constructor(name: String) { /*...*/ }

class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)

    init {
        println("First initializer block that prints ${name}")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")
    }
}

fun main() {
    InitOrderDemo("hello")
}