package top.zcwfeng.hiltsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import top.zcwfeng.hiltsample.data.User
import top.zcwfeng.hiltsample.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var user: User
    @Inject lateinit var any:Any
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

    }
}
