package top.zcwfeng.jni

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
        sample_text.text = testFromJNI()

        sample_text.setOnClickListener {
            var intent = Intent(this, OpenCVIDCard::class.java)
            startActivity(intent)
        }

        mmap_demo.setOnClickListener {
            Log.e("zcw::","mmap----writeTest readTest")
            writeTest()
            readTest()
        }


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun testFromJNI(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun writeTest()

    external fun readTest()
}
