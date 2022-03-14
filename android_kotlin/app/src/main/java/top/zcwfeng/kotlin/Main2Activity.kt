package top.zcwfeng.kotlin

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import top.zcwfeng.kotlin.databinding.ActivityMain2Binding
import top.zcwfeng.kotlin.databinding.ActivityMainBinding


class Main2Activity : AppCompatActivity() {
    lateinit var databinding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        setListeners()

    }


    private fun setListeners() {
        var clickableViews: List<View> = listOf(
            databinding.boxOneText,
            databinding.boxTwoText,
            databinding.boxThreeText,
            databinding.boxFourText,
            databinding.boxFiveText,
            databinding.buttonBlack,
            databinding.buttonRed,
            databinding.buttonGreen
        )


        for (item in clickableViews) {
            item.setOnClickListener { makeColors(it) }
        }

    }

    private fun makeColors(view: View) {
        when (view.id) {
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)
            R.id.box_three_text -> view.setBackgroundColor(Color.GREEN)
            R.id.box_four_text -> view.setBackgroundColor(Color.YELLOW)
            R.id.box_five_text -> view.setBackgroundColor(Color.RED)

            R.id.button_black -> databinding.boxThreeText.setBackgroundColor(Color.BLACK)
            R.id.button_red -> databinding.boxFourText.setBackgroundColor(Color.RED)
            R.id.button_green -> databinding.boxFiveText.setBackgroundColor(Color.GREEN)
            else
            -> view.setBackgroundColor(Color.GRAY)

        }
    }


}
