package top.zcwfeng.customui.baseui.view

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

const val TAG = "zcw:::"



inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}


inline fun FragmentActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun<T> Fragment.startActivity(activity: FragmentActivity?, clazz:Class<T>){
    val intent = Intent(activity?.baseContext,clazz);
    activity?.startActivity(intent)
}

fun View.dp2px(dp: Float): Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().displayMetrics)
}

fun View.sp2px(sp: Float): Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp, Resources.getSystem().displayMetrics)
}

fun getScreenWidth(context:Context) : Int{
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

fun getScreenHeight(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}