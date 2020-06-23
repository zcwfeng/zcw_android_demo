package top.zcwfeng.kotlin.project.basse

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity<P> : AppCompatActivity() where P:IBasePresenter {
    lateinit var presenter:P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createP()

        // 自己去扩展了，可以增加很多的功能
        // 省略 ....
        /*setContentView(getLayoutID())
        initView()
        initData()*/
    }

    abstract fun createP(): P
    abstract fun recycle()

    override fun onDestroy() {
        super.onDestroy()
        recycle()
    }

    fun hideActionBar(){
        var actionBar:ActionBar? = supportActionBar
    }

    fun showActionBar(){
        supportActionBar?.show()
    }

    // TODO 同学们自己去扩展了，可以增加很多的功能
    //  .....

}