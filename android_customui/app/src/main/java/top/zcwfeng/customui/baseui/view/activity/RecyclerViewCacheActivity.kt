package top.zcwfeng.customui.baseui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view_cache.*
import top.zcwfeng.customui.R
import top.zcwfeng.customui.baseui.view.recyclerview.CustomAdapter

class RecyclerViewCacheActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_cache)

        val mLayoutManager = GridLayoutManager(this,5)

        rv.layoutManager = mLayoutManager

//        rv.setItemViewCacheSize(10)

        rv.addItemDecoration(DividerItemDecoration(this,LinearLayout.VERTICAL))

        var result: List<String> = (1..1000).map { it.toString() }

        rv.adapter = CustomAdapter(this, result as MutableList<String>)
    }
}