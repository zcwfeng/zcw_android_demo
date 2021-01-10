package top.zcwfeng.daggersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import top.zcwfeng.daggersample.dagger.demo2.component.DaggerMyComponent
import top.zcwfeng.daggersample.dagger.demo2.module.DatabaseModule
import top.zcwfeng.daggersample.dagger.demo2.module.HttpModule
import top.zcwfeng.daggersample.data.HttpObject
import javax.inject.Inject

class SecondActivity : AppCompatActivity() {
    @Inject
    lateinit var httpObject:HttpObject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // 语法1
//        DaggerMyComponent.create().injectSecondActivity(this)
//        DaggerMyComponent.builder().httpModule(HttpModule())
//            .databaseModule(DatabaseModule())
//            .build()
//            .injectSecondActivity(this)

        (application as DaggerApp).myComponent.injectSecondActivity(this)
        Log.e("httpObject",httpObject.toString() + "--sec")
    }
}