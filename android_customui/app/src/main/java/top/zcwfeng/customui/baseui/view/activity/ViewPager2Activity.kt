package top.zcwfeng.customui.baseui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import top.zcwfeng.customui.R

class ViewPager2Activity : AppCompatActivity() {
    lateinit var viewpager:ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)
    }
}