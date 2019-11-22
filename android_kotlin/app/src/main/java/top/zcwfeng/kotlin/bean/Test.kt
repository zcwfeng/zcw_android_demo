package top.zcwfeng.kotlin.bean

import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlinx.coroutines.*;

interface Base{
    fun print()
}

class BaseImpl(var x:Int):Base{
    override fun print() {
        print(x)
    }

}

class BaseBImpl(var y:String):Base{
    override fun print() {
        print("BaseBImpl $y")
    }

}


class Derived(b:Base) : Base by b

// 定义包含属性委托的类
class Example {
    var p: String by Delegate()
}

// 委托的类
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, 这里委托了 ${property.name} 属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
    }
}

val lazyValue: String by lazy {
    println("computed!")     // 第一次调用输出，第二次调用不执行
    "Hello"
}

class User {
    var name: String by Delegates.observable("初始值") {
            prop, old, new ->
        println("旧值：$old -> 新值：$new")
    }
}

class Site(val map: Map<String, Any?>) {
    val name: String by map
    val url: String  by map
}

class Site2(val map: MutableMap<String, Any?>) {
    val name: String by map
    val url: String by map
}

class Foo {
    var notNullBar: String by Delegates.notNull<String>()
    fun isValid():Boolean{
        var somecondition:Boolean = false
        //TODO somecondition
        return somecondition
    }

    fun doSomething()
    {

    }
}

//局部委托属性
fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)

    var someCondition:Boolean = false
    //TODO("Someconditon")

    if (someCondition && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}


suspend fun main()  {
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) //
}



// 这是你的第一个挂起函数
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

private fun notnullBy() {
    var foo = Foo()
    foo.notNullBar = "bar"
    println(foo.notNullBar)
}

fun selfBy() {

    // 构造函数接受一个映射参数
    val site = Site(mapOf(
        "name" to "菜鸟教程",
        "url"  to "www.runoob.com"
    ))
    // 读取映射值
    println(site.name)
    println(site.url)


    var map:MutableMap<String, Any?> = mutableMapOf(
        "name" to "菜鸟教程",
        "url" to "www.runoob.com"
    )

    val site2 = Site2(map)

    println(site2.name)
    println(site2.url)

    println("--------------")
    map.put("name", "Google")
    map.put("url", "www.google.com")

    println(site2.name)
    println(site2.url)
}

private fun observableBy() {
    val user = User()
    user.name = "第一次赋值"
    user.name = "第二次赋值"
}

private fun layzyBy() {
    println(lazyValue)
    println(lazyValue)
}

private fun propertyBy() {
    var e = Example()
    println(e.p)     // 访问该属性，调用 getValue() 函数

    e.p = "Runoob"   // 调用 setValue() 函数
    println(e.p)
}

private fun classBy() {
    var a = BaseImpl(10)
    var b = BaseBImpl("String Test")
    Derived(a).print()
    Derived(b).print()
}