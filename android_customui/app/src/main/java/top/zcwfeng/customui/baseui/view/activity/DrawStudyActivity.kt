package top.zcwfeng.customui.baseui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_draw_study.*
import top.zcwfeng.customui.R
import top.zcwfeng.customui.baseui.view.finsh.FishDrawable2
import top.zcwfeng.customui.baseui.view.finsh.PeopleDrawable

class DrawStudyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_study)
//        draw_container.setImageDrawable(FishDrawable2())
//        draw_container.setImageDrawable(PeopleDrawable())
    }
}