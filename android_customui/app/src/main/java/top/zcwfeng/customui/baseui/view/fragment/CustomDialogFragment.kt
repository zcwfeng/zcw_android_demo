package top.zcwfeng.customui.baseui.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import top.zcwfeng.customui.R


class CustomDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,R.style.DialogFullScreen); //dialog全屏

    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        //去掉dialog的标题，需要在setContentView()之前
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = this.dialog!!.window
        //去掉dialog默认的padding
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes
        lp?.width = WindowManager.LayoutParams.MATCH_PARENT
        lp?.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = lp
        val view = inflater.inflate(R.layout.fragment_custom_dialog, container, false)

        //确定Dialog布局
        return view
    }

    override fun onResume() {
        super.onResume()
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }
}