package top.zcwfeng.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.dbflow5.config.FlowManager.context
import top.zcwfeng.kotlin.databinding.ActivityMainBinding
import top.zcwfeng.kotlin.ui.BlankFragment
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var blankFragment: BlankFragment;

    lateinit var databinding: ActivityMainBinding//null 安全检测
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // KTX Fragment
        blankFragment = BlankFragment.newInstance()
        supportFragmentManager.commit(false) {

            addToBackStack("blank")
            setCustomAnimations(
                R.anim.fragment_fade_enter,
                R.anim.fragment_close_exit
            )
            add(R.id.fragment_container, blankFragment, "blankFrg")
            show(blankFragment)
        }


        testSecurity()


    }

    private fun testSecurity() {
        TODO("Not yet implemented")
        readSecurity()
        writeSecurity()
    }

    private fun writeSecurity() {
        TODO("Not yet implemented")

//        // Although you can define your own key generation parameter specification, it's
//        // recommended that you use the value specified here.
//        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
//        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
//
//        // Creates a file with this name, or replaces an existing file
//        // that has the same name. Note that the file name cannot contain
//        // path separators.
//        val fileToWrite = "my_sensitive_data.txt"
//        val encryptedFile = EncryptedFile.Builder(
//            File(directory, fileToWrite),
//            context,
//            masterKeyAlias,
//            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
//        ).build()
//
//        encryptedFile.openFileOutput().bufferedWriter().use {
//            it.write("MY SUPER-SECRET INFORMATION")
//        }

    }

    private fun readSecurity() {
        TODO("Not yet implemented")

//        // Although you can define your own key generation parameter specification, it's
//        // recommended that you use the value specified here.
//        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
//        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
//
//        val fileToRead = "my_sensitive_data.txt"
//        val encryptedFile = EncryptedFile.Builder(
//            File(directory, fileToRead),
//            context,
//            masterKeyAlias,
//            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
//        ).build()
//
//        val contents = encryptedFile.bufferedReader().useLines { lines ->
//            lines.fold("") { working, line ->
//                "$working\n$line"
//            }
//        }
    }


    public fun goConstrantLayout(view: View) {
//        var intent:Intent = Intent(this,Main2Activity().javaClass)
//        startActivity(intent)
        var intent = Intent(this, Main2Activity::class.java)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
