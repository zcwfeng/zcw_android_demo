package top.zcwfeng.kotlin.project.views

import android.app.Dialog
import android.content.Context
import top.zcwfeng.kotlin.R

object LoadingDialog {

    private var dialog: Dialog? = null



    @JvmStatic
    fun show2(){

    }
    fun show1(){

    }


    fun show(context: Context){
        dialog = Dialog(context)
        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        // .....
        dialog?.show()
    }

    fun cancel(){
        dialog?.cancel()
    }
}