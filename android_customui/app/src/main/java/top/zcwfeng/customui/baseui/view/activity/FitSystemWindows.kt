package top.zcwfeng.customui.baseui.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import top.zcwfeng.customui.R


/**
 * 测试 fitsSystemWindows
 *
 * Theme 设置沉浸式
 *
<style name="AppTheme_light_fullscreen" parent="Theme.AppCompat.DayNight.NoActionBar">
<!-- Customize your theme here. -->
<item name="colorPrimary">@color/colorPrimary</item>
<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
<item name="colorAccent">@color/colorAccent</item>
<item name="android:windowTranslucentNavigation">true</item>
<item name="android:windowNoTitle">true</item>
</style>
 *
 * window.statusBarColor = Color.TRANSPARENT  沉浸式配置透明
 *
 *
 * 1. android:fitsSystemWindows + FragmentLayout
 * 2. android:fitsSystemWindows + CoordinaryLayout
CoordinatorLayout 做了这些事：
setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

对当前布局的insets做一些处理，并且调用了上面一行代码

 * 3. android:fitsSystemWindows + layout + ui
 */
class FitSystemWindows : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fit_system_windows)
        window.statusBarColor = Color.TRANSPARENT


/*// FrameLayout + android:fitsSystemWindows
        val frameLayout = findViewById<FrameLayout>(R.id.root_layout)
        frameLayout.systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//setOnApplyWindowInsetsListener ()函数去监听WindowInsets发生变化
        val button = findViewById<Button>(R.id.button)
        ViewCompat.setOnApplyWindowInsetsListener(button) { view, insets ->
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.topMargin = insets.systemWindowInsetTop
            insets
        }*/
    }
}