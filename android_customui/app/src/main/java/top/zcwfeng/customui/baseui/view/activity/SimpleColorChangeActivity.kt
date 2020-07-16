package top.zcwfeng.customui.baseui.view.activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.fragment_custom_text_view.*
import top.zcwfeng.customui.R


class SimpleColorChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_color_change)

        Handler().postDelayed(Runnable {
            ObjectAnimator.ofFloat(custom_tv,"percent",0f,1f).setDuration(5000).start()
        },2000)
    }
}