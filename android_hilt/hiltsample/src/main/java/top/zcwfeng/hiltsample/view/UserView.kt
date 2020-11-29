package top.zcwfeng.hiltsample.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import dagger.hilt.android.AndroidEntryPoint
import top.zcwfeng.hiltsample.MainActivity
import top.zcwfeng.hiltsample.data.User
import javax.inject.Inject

@AndroidEntryPoint
class UserView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    @Inject lateinit var user: User;

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        text = "${user.name} 现在的心情变成 ${user.mood}"

//        val user = (context as MainActivity).user
//        text = "${user.name} 现在的心情变成 ${user.mood}"

    }

}