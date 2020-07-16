package top.zcwfeng.customui.baseui.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

private const val TITLE = "param1"

class TagFragment : Fragment() {
    private var mTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTitle = it.getString(TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val tv = TextView(activity)
        tv.textSize = 60f
        val r = Random()
        tv.setBackgroundColor(Color.argb(r.nextInt(120), r.nextInt(255),
                r.nextInt(255), r.nextInt(255)))
        tv.text = mTitle
        tv.gravity = Gravity.CENTER
        return tv
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                TagFragment().apply {
                    arguments = Bundle().apply {
                        putString(TITLE, param1)
                    }
                }
    }
}