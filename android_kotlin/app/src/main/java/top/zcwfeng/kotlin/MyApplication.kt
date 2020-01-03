package top.zcwfeng.kotlin

import android.app.Application
import com.dbflow5.config.FlowManager

/**
 * 作者：zcw on 2020-01-03
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FlowManager.init(this);
    }
}