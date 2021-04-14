package top.zcwfeng.customui.baseui.dispatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wx.wheelview.adapter.ArrayWheelAdapter
import com.wx.wheelview.widget.WheelView
import kotlinx.android.synthetic.main.activity_scroll_view_and_wheel_view.*
import top.zcwfeng.customui.R

class ScrollViewAndWheelView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view_and_wheel_view)



//        wheelview.setWheelAdapter(ArrayWheelAdapter(this)); // 文本数据源
//        wheelview.setSkin(WheelView.Skin.Common); // common皮肤
//        wheelview.setWheelData();  // 数据集合
    }
}

