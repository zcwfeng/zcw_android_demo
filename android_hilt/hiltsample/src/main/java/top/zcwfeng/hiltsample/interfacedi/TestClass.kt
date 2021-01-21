package top.zcwfeng.hiltsample.interfacedi

import android.util.Log
import javax.inject.Inject

class TestClass:TestInterface {
    @Inject
    constructor()

    override fun method() {
        Log.e("zcwfeng", "method: 构造方法Test注入" )
    }
}