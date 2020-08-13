package top.zcwfeng.daggersample.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import top.zcwfeng.daggersample.DaggerApp
import top.zcwfeng.daggersample.data.User
import javax.inject.Inject

class UserView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    @Inject lateinit var user: User;
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (context.applicationContext as DaggerApp).appComponent.inject(this)
        text = "${user.name} 使用 Dagger的心情是 ${user.mood}"
    }
}