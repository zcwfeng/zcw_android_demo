package top.zcwfeng.jni

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


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
            Log.e("zcw::", "mmap----writeTest readTest")
            writeTest()
            readTest()
        }

        bspatch_demo.setOnClickListener {
            Log.e("zcw::", "差异化更新")
            patch()

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

    fun patch() {
        val newFile = File(getExternalFilesDir("apk"), "app.apk")
        val patchFile = File(getExternalFilesDir("apk"), "patch.apk")
        val result: Int = BsPatchUtils.path(
            applicationInfo.sourceDir, newFile.getAbsolutePath(),
            patchFile.getAbsolutePath()
        )
        if (result == 0) {
            install(newFile)
        }
    }

    private fun install(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            val apkUri: Uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        startActivity(intent)
    }


}
