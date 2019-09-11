package top.zcwfeng.kotlin.bean

import java.io.File

class Sample {
//    var view: View? = null
    val size = 18
    var myName:String? = "David"
        get(){
            return field + "nb"
        }
        set(value) {
            field = "Cube" + value
        }
    var myName2:String = "David"

    var srcList = listOf("a","b","c")
    var anys:List<Any> = srcList
    var srcSet = setOf("a","b","c")
    var srcHashSet = hashSetOf("a","b","c")
    val map = mapOf("key1" to 1,"key2" to 2,"key3" to 3)
    var hasMap = hashMapOf("key1" to 1,"key2" to 2,"key3" to 3)
    val value1 = map.get("key1")
    val value2 = map["key2"]
    val srcMultiList = mutableListOf("a","b","c")



// 错误
//    var str: Array<String> = arrayOf("a","b","c")
//    var arrAnys:Array<Any> = str

    fun cook(name:String?){}
    fun cook2(name:String){}

    fun test(){
        cook(myName)
        cook2(myName2)
    }

    fun collection(){
        println(myName)
        srcList.toMutableList()
        anys.toMutableList()
        srcSet.toMutableSet()
        hasMap.toMutableMap()
        map.toMutableMap()
        srcMultiList.toList()
        sequenceOf("a", "b", "c")
        val list = listOf("a", "b", "c")
        list.asSequence()
        val sequence = generateSequence(0) { it + 1 }


    }

    fun testprint(){
        println("hahahaha ${myName2}")
    }

    fun forLoop(){
        for(x in 1..100 step 2) {
            print(x)
        }
        val items = listOf("挣扎","等待","恐惧","积累","人寿住寂寞","破茧而出")
        for(item in items) print(item)
        for(index in items.indices) print(items[index])
        var index =0
        while(index < items.size) {
            print(items[index])
            index++
        }

        for((k,v) in map) {
            print("$k --- $v")
        }


        for (i in 1..100) { println(i) }  // closed range: includes 100
        for (i in 1 until 100) { println(i) } // half-open range: does not include 100
        for (x in 2..10 step 2) { println(x) }
        for (x in 10 downTo 1) { println(x) }
    }

    fun filterDemo(){
        var list = listOf(1,2,3,4,5,6,7)
//        var bigger = list.filter { x->x>3 }
        var bigger = list.filter { it>3 }
        println(bigger)

        if(1 in list) print("ok")


    }

    object instance{
        var name:String = "instance"
    }

    fun shorthand(){
        var files = try{
            File("Test").listFiles()
        }catch (e:NullPointerException){
            print(e.message)
        }
        println(files)

    }

    fun arryOfOnes(size:Int):IntArray{
        return IntArray(size).apply { fill(-1) }
    }


    fun describeVal(any:Any):String=
        when(any){
            1->"One"
            "Hello"->"Two"
            is Long->"Three"
            !is String -> "Four"
            else->"other"
        }


    fun swapValue(){
        var a = 1
        var b = 2
        a = b.also { b = a }

        print("$a----$b")
    }

}

fun main() {

//    println(System.currentTimeMillis())
//    val listimpl = List(100_000){it +1}
//    val arrays:Array<Int> = Array(100_000) {i->(i +1)}
//    val arrays2 = IntArray(100_000){it + 1}
//    println(System.currentTimeMillis())

//    println(listimpl)
//      Sample().testprint()
//    Sample().shorthand()
//    println(Sample().arryOfOnes(10).get(0))
    Sample().swapValue()
}

