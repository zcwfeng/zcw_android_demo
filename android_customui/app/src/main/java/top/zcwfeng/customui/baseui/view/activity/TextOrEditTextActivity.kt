package top.zcwfeng.customui.baseui.view.activity

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_text_or_edit_text.*
import top.zcwfeng.customui.R

class TextOrEditTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_or_edit_text)
        onEditorLsn()

        onGloableLayoutLsn()


    }

    private fun onGloableLayoutLsn() {

        val decorView = window.decorView
        decorView.viewTreeObserver.addOnGlobalFocusChangeListener { _, _ ->
            val r: Rect = Rect()
            decorView.getWindowVisibleDisplayFrame(r)
            val height = decorView.context.resources.displayMetrics.heightPixels
            val diff: Int = height - r.bottom
//            if (diff != 0) {
//                if (contentView.paddingBottom !== diff) {
//                    contentView.setPadding(0, 0, 0, diff)
//                }
//            } else {
//                if (contentView.paddingBottom !== 0) {
//                    contentView.setPadding(0, 0, 0, 0)
//                }
//            }
//            showAViewOverKeyBoard(diff)
        }
    }


//    fun showAViewOverKeyBoard(heightDifference:Int) {
//        if (heightDifference > 0) {//显示
//            if (view == null) {//第一次显示的时候创建  只创建一次
//                view = View.inflate(this, R.layout.item, null);
//                val loginlayoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(-1, -2);
//                loginlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                loginlayoutParams.bottomMargin = heightDifference;
//                rl_root.addView(view, loginlayoutParams);
//            }
//            view.setVisibility(View.VISIBLE);
//        } else {//隐藏
//            if (view != null) {
//                view.setVisibility(View.GONE);
//            }
//
//        }

//    fun test(){
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//
//            //请求权限
//
//            ActivityCompat.requestPermissions(this, new String[{ Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO },  MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
//
//                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//    }

    fun onEditorLsn() {
        val TAG: String = "zcw:::EditText:::"
        commontText.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> Log.e(TAG, "ACTION_SEARCH")

                EditorInfo.IME_ACTION_GO -> Log.e(TAG, "ACTION_GO")

                EditorInfo.IME_ACTION_DONE ->

                    Log.e(TAG, "ACTION_DONE")

                EditorInfo.IME_ACTION_SEND ->

                    Log.e(TAG, "ACTION_SEND")

                EditorInfo.IME_ACTION_NEXT ->

                    Log.e(TAG, "ACTION_NEXT")
                EditorInfo.IME_ACTION_NONE ->

                    Log.e(TAG, "ACTION_NONE")

                else ->
                    Log.e(TAG, "Nothing")
            }
            false
        }


    }
}