package top.zcwfeng.koinsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.inject
import top.zcwfeng.koinsample.data.User
import top.zcwfeng.koinsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val user: User by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.userTextView.text = "${user.name}使用 Koin 的心情是：${user.mood}"
        user.mood = "心情复杂"

    }
}
