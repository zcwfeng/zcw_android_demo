package top.zcwfeng.customui.baseui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import top.zcwfeng.customui.R
import top.zcwfeng.customui.baseui.view.model.Star
import top.zcwfeng.customui.baseui.view.recyclerview.StarAdapter
import top.zcwfeng.customui.baseui.view.recyclerview.StarDecoration


class RecyclerViewActivity : AppCompatActivity() {
    val starList = mutableListOf<Star>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initData()
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.addItemDecoration(StarDecoration(this))
        rv_list.adapter = StarAdapter(starList,this)
    }


    private fun initData() {

        for(i in 0 until 4){
            for(j in 0 until 20){
                if(i % 2 == 0){
                    starList.add(Star("何炅$j","快乐家族$i"))
                }else {
                    starList.add(Star("汪涵$j","天天兄弟$i"))

                }
            }
        }



    }
}