package top.zcwfeng.customui.baseui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.badoo.mobile.util.WeakHandler
import top.zcwfeng.customui.R
import top.zcwfeng.customui.databinding.ActivityExampleWeakHandlerBinding

class ExampleWeakHandlerActivity : AppCompatActivity() {

    lateinit var binding: ActivityExampleWeakHandlerBinding
    lateinit var weakHandler: WeakHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_example_weak_handler)

        weakHandler = WeakHandler()

        binding.bg.setOnClickListener {
            weakHandler.postDelayed(Runnable {
                binding.bg.visibility = View.INVISIBLE
            },5000)
        }
    }
}