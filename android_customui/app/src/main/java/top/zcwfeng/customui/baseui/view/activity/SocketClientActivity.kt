package top.zcwfeng.customui.baseui.view.activity

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import top.zcwfeng.customui.R
import kotlin.text.StringBuilder

class SocketClientActivity : AppCompatActivity() {
    val ip = "127.0.0.1"
    val PORT:Int = 10065
    lateinit var sb:StringBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket_client)
        sb = StringBuilder()

        object:Thread(){
            override fun run() {

            }
        }.start()
    }


}