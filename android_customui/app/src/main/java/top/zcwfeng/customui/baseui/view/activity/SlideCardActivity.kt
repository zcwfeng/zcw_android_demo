package top.zcwfeng.customui.baseui.view.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_slide_card.*
import top.zcwfeng.customui.R
import top.zcwfeng.customui.R.layout.item_swipe_card
import top.zcwfeng.customui.baseui.view.recyclerview.adapter.UniversalAdapter
import top.zcwfeng.customui.baseui.view.recyclerview.adapter.ViewHolder
import top.zcwfeng.customui.baseui.view.slidecard.CardConfig
import top.zcwfeng.customui.baseui.view.slidecard.SlideCallback
import top.zcwfeng.customui.baseui.view.slidecard.SlideCardBean
import top.zcwfeng.customui.baseui.view.slidecard.SlideCardLayoutManager


class SlideCardActivity : AppCompatActivity() {
    private lateinit var mAdapter: UniversalAdapter<SlideCardBean>
    private val mDatas: List<SlideCardBean> = SlideCardBean.initDatas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_card)
        rv_slide.layoutManager = SlideCardLayoutManager()
        mAdapter = object : UniversalAdapter<SlideCardBean>(this, mDatas, item_swipe_card) {
            override fun convert(viewHolder: ViewHolder, slideCardBean: SlideCardBean) {
                viewHolder.setText(R.id.tvName, slideCardBean.name)
                viewHolder.setText(R.id.tvPrecent, slideCardBean.postition.toString() + "/" + mDatas.size)
                Glide.with(this@SlideCardActivity)
                        .load(slideCardBean.url)
                        .into(viewHolder.getView(R.id.iv) as ImageView)
            }
        }
        rv_slide.adapter = mAdapter
        CardConfig.initConfig(this)
        val slideCallback = SlideCallback(rv_slide,mAdapter,mDatas)
        val itemTouchHelper = ItemTouchHelper(slideCallback)
        itemTouchHelper.attachToRecyclerView(rv_slide)
    }
}