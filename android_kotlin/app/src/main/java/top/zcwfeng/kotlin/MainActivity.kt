package top.zcwfeng.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import top.zcwfeng.kotlin.bean.MyName
import top.zcwfeng.kotlin.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {


    lateinit var databinding:ActivityMainBinding//null 安全检测
    var myName:MyName = MyName("Alice")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        databinding.myName = myName
        databinding.btnKotlin.setOnClickListener {
            rollDice(it)
        }



    }


    suspend fun dostomething(foo:String):String{
        return ""
    }

    public fun goConstrantLayout(view:View){
//        var intent:Intent = Intent(this,Main2Activity().javaClass)
//        startActivity(intent)
        var intent:Intent = Intent(this,Main2Activity::class.java)
        startActivity(intent)
    }

    private fun rollDice(view: View) {
        var RandomInt = Random().nextInt(6) + 1

        var drawableResource = when(RandomInt){
            1-> R.drawable.dice_1
            2-> R.drawable.dice_2
            3-> R.drawable.dice_3
            4-> R.drawable.dice_4
            5-> R.drawable.dice_5
            else -> R.drawable.dice_6

        }

        databinding.apply {

            databinding.textName.setText("Finshy")
            databinding.diceImage.setImageResource(drawableResource)
            invalidateAll()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databinding.btnKotlin.setOnClickListener(null)
    }
}
