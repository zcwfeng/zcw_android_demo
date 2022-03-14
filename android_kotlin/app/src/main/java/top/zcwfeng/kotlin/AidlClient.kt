package top.zcwfeng.kotlin

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import top.zcwfeng.aidl.IZcwAidl
import top.zcwfeng.aidl.Person

class AidlClient : AppCompatActivity() {
    private var aidl: IZcwAidl? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl_client)
        bindService()
    }

    fun clickAidlClient(view: View) {
        try {
            aidl?.addPerson(Person("zcw", 2))
            val persons = aidl?.personList
            Log.e("zcw:::Client","persons->${persons}")
        } catch (e: Exception) {
            Log.e("zcw:::Client", e.message ?: "message error")
        }
    }

    private fun bindService(){
        val intent = Intent()
        intent.component = ComponentName("top.zcwfeng.aidl","top.zcwfeng.aidl.ZcwService")
        bindService(intent,conn, Context.BIND_AUTO_CREATE)
    }

    private val conn: ServiceConnection = object:ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("zcw:::Client","onServiceConnected success")
            aidl = IZcwAidl.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("zcw:::Client","onServiceDisconnected success")
            aidl = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
    }

}