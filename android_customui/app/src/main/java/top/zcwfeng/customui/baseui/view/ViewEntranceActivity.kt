package top.zcwfeng.customui.baseui.view

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.zcwfeng.customui.R
import top.zcwfeng.customui.baseui.view.fragment.ControllerFragment

class ViewEntranceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entrance)

        val fragment = ControllerFragment.newInstance()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment,fragment.tag)
        transaction.commitAllowingStateLoss()


    }







}