package top.zcwfeng.customui.baseui.view.fragment

import android.R
import androidx.fragment.app.ListFragment
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.viewpager2.widget.ViewPager2
import top.zcwfeng.customui.baseui.view.activity.*
import java.net.ResponseCache


/**
 * 带按钮的Fragment 控制跳转
 */
class ControllerFragment : ListFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val array = arrayOf(
                "Simple文字渐变",  //0
                "文字测量演示",  //1
                "ViewPager+文字变色",  //2
                "过渡绘制演示",  //3
                "文字绘制补充说明",
                "画大鱼",
                "绘制练习",
                "RecyclerView 吸顶",
                "RecyclerView 缓存分析",
                "RecyclerView SlideCard",
                "ViewPager&ViewPager2",
                "PhotoView 多点触摸",
                "Example WeakHandler Use",
                "Socket Client")
        var arrayAdapter = activity?.let { ArrayAdapter(it, R.layout.simple_list_item_1, array) }
        listAdapter = arrayAdapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        when (position) {
            0 -> {
                val intent = Intent(activity, SimpleColorChangeActivity::class.java)
                startActivity(intent)
            }

            2 -> {
                val intent = Intent(activity, ViewPagerActivity::class.java)
                startActivity(intent)

            }

            5 -> {
                val intent = Intent(activity, BigFishActivity::class.java)
                startActivity(intent)

            }
            6 -> {
                val intent = Intent(activity, DrawStudyActivity::class.java)
                startActivity(intent)

            }
            7 -> {
                val intent = Intent(activity, RecyclerViewActivity::class.java)
                startActivity(intent)

            }
            8 -> {
                val intent = Intent(activity, RecyclerViewCacheActivity::class.java)
                startActivity(intent)
            }
            9 -> {
                val intent = Intent(activity, SlideCardActivity::class.java)
                startActivity(intent)
            }
            10 -> {
                val intent = Intent(activity, ViewPager2Activity::class.java)
                startActivity(intent)
            }
            11 -> {
                val intent = Intent(activity, PhotoViewActivity::class.java)
                startActivity(intent)
            }
            12 -> {
                val intent = Intent(activity, ExampleWeakHandlerActivity::class.java)
                startActivity(intent)
            }
            13 -> {
                val intent = Intent(activity, SocetClientActiityJava::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ControllerFragment()
    }
}