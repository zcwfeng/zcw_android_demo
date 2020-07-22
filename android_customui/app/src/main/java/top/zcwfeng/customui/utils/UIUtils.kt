package top.zcwfeng.customui.utils

import android.content.Context

fun dp2px(context: Context, dpValue: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dpValue * scale * 0.5f).toInt()
}