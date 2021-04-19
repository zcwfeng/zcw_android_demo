package top.zcwfeng.jni

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class JNIActivityThreadTest : AppCompatActivity() {
    external fun nativeFun5()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j_n_i_thread_test)
        nativeFun5();
    }
}