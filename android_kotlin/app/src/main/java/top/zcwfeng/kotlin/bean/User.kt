package top.zcwfeng.kotlin.bean

class User constructor(name: String){
    var name:String= "Kotin"
    val age:Int?=0
    init {
        this.name = name
    }



    constructor(name:MyName):this(MyObjectInstance.name){

    }

}


var mname:String = "kotlin"
    get() = field
    set(value) {
        field = value
    }

val mList: List<Int> = listOf()


