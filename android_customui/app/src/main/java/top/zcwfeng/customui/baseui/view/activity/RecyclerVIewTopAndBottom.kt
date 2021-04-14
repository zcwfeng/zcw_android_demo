package top.zcwfeng.customui.baseui.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EdgeEffect
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_v_iew_top_and_bottom.*
import top.zcwfeng.customui.R
import top.zcwfeng.customui.baseui.view.recyclerview.CustomAdapter

/**
 * 测试问题用的RecyclerView
 */
class RecyclerVIewTopAndBottom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_v_iew_top_and_bottom)

        val mLayoutManager = LinearLayoutManager(this);

        rv_list.layoutManager = mLayoutManager

//        rv.setItemViewCacheSize(10)

        rv_list.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        var result: List<String> = (1..20).map { it.toString() }

        rv_list.adapter = CustomAdapter(this, result as MutableList<String>)

        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.e("zcw::","onScrollStateChanged-RecyclerVIewTopAndBottom")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    var firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                    if (firstCompletelyVisibleItemPosition == 0) {
                        //滑动到顶部
                        rv_list.overScrollMode = View.OVER_SCROLL_NEVER

                    }
                    var lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (lastCompletelyVisibleItemPosition == layoutManager.getItemCount() - 1) {
                        //滑动到底部
                        rv_list.overScrollMode = View.OVER_SCROLL_ALWAYS

                    }
                }


            }
        })
    }
}