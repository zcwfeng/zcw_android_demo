package top.zcwfeng.kotlin

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setListeners()
    }

    private fun setListeners() {
        var clickableViews:List<View> = listOf(
            box_one_text,box_two_text,box_three_text,box_four_text,box_five_text
        ,button_black,button_red,button_green
        )

        for(item in clickableViews){
            item.setOnClickListener{makeColors(it)}
        }

    }

    private fun makeColors(view:View) {
        when(view.id){
            R.id.box_one_text->view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text->view.setBackgroundColor(Color.GRAY)
            R.id.box_three_text->view.setBackgroundColor(Color.GREEN)
            R.id.box_four_text->view.setBackgroundColor(Color.YELLOW)
            R.id.box_five_text->view.setBackgroundColor(Color.RED)

            R.id.button_black->box_three_text.setBackgroundColor(Color.BLACK)
            R.id.button_red->box_four_text.setBackgroundColor(Color.RED)
            R.id.button_green->box_five_text.setBackgroundColor(Color.GREEN)
            else
                ->view.setBackgroundColor(Color.GRAY)

        }
    }


}
