package top.zcwfeng.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import top.zcwfeng.hilt.databinding.ActivityMainBinding
import top.zcwfeng.hilt.model.User
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        databinding.content.text = "${user.name}现在的心情是${user.desc}"

    }
}