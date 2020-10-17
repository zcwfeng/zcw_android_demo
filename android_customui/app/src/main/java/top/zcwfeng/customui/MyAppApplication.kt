package top.zcwfeng.customui

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.tencent.mmkv.MMKV
import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.LeakCanary
import leakcanary.OnHeapAnalyzedListener
import shark.HeapAnalysis

class MyAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val rootDir = MMKV.initialize(this);

        Log.e("MMKV","init root $rootDir")

//        LeakCanary.config = LeakCanary.config.copy(
//                onHeapAnalyzedListener = LeakUpLoader()
//        )

//        registerActivityLifecycleCallbacks(MyActivityLifecycleCallback())
    }

    class LeakUpLoader : OnHeapAnalyzedListener {
        private val defaultListener = DefaultOnHeapAnalyzedListener.create()
        override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {
            Log.i("zcw", "${heapAnalysis.javaClass.simpleName} onHeapAnalyzed()")
            defaultListener.onHeapAnalyzed(heapAnalysis)
        }
    }

    class MyActivityLifecycleCallback : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        }

        override fun onActivityResumed(activity: Activity) {
        }
    }


}