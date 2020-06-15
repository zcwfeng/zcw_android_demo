package top.zcwfeng.customui.leakcanarytest

import android.app.Activity
import android.widget.TextView

class TestDataModel {
    var mRetainedTextView: TextView? = null
    var activities = mutableListOf<Activity>()

    companion object {
        @JvmStatic
        private var sInstance: TestDataModel? = null

        @JvmStatic
        public fun getInstance(): TestDataModel {
            if (sInstance == null) {
                sInstance = TestDataModel()
            }
            return sInstance!!;
        }
    }


}