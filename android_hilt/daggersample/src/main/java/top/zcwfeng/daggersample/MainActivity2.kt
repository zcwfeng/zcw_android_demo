package top.zcwfeng.daggersample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import top.zcwfeng.daggersample.dagger.demo2.di.Presenter
import top.zcwfeng.daggersample.data.HttpObject
import top.zcwfeng.daggersample.databinding.ActivityMain2Binding
import javax.inject.Inject

class MainActivity2 : AppCompatActivity() {

    @Inject
    lateinit var httpObject1: HttpObject

    @Inject
    lateinit var httpObject2: HttpObject

    @Inject
    lateinit var presenter: Presenter
    @Inject
    lateinit var presenter2: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        // 语法1
//        DaggerMyComponent.create().injectMainActivity(this)

//
//        DaggerMyComponent.builder().httpModule(HttpModule())
//            .databaseModule(DatabaseModule())
//            .build()
//            .injectMainActivity(this)

        // 全局singleton
        (application as DaggerApp).myComponent.injectMainActivity(this)
        Log.e("httpObject1", httpObject1.toString())
        Log.e("httpObject2", httpObject2.toString())
        Log.e("presenter", presenter.toString())
        Log.e("presenter", presenter.toString())


        binding.clickSecondActivity.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))

        }

    }
}