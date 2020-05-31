package top.zcwfeng.kotlin

import android.app.Application
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.dbflow5.config.FlowManager

/**
 * 作者：zcw on 2020-01-03
 */
class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        FlowManager.init(this)
    }
}