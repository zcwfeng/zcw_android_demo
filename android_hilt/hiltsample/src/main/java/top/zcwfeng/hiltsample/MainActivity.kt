package top.zcwfeng.hiltsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import top.zcwfeng.hiltsample.data.HttpObject
import top.zcwfeng.hiltsample.data.User
import top.zcwfeng.hiltsample.databinding.ActivityMainBinding
import top.zcwfeng.hiltsample.interfacedi.TestInterface
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var user: User
    @Inject lateinit var any:Any

    @Inject
    lateinit var httpObject:HttpObject
    @Inject
    lateinit var httpObject2:HttpObject
    @Inject
    lateinit var testInterface:TestInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // TextView 1
//        userTextView.text = "${user.name} 现在的心情是 ${user.mood}"


        // UserViw inject 2
//        user.mood = "非常焦虑"
        val anyUser = any as User
        binding.userTextView.text = "${anyUser.name} 现在的心情是 ${anyUser.mood}"

        binding.userTextView.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))

        }

        Log.e("zcwfeng",httpObject.toString() + "------httpObject1");
        Log.e("zcwfeng",httpObject2.toString() + "------httpObject2");
        testInterface.method()

    }
}
