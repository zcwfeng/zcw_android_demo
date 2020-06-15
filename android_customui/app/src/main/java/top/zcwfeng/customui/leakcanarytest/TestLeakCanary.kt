package top.zcwfeng.customui.leakcanarytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import top.zcwfeng.customui.databinding.ActivityTestLeakCanaryBinding

class TestLeakCanary : AppCompatActivity() {

    private val binding by lazy {
        ActivityTestLeakCanaryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        TestDataModel.getInstance().mRetainedTextView = binding.textview
        TestDataModel.getInstance().activities.add(this)

        catTest()
    }




}