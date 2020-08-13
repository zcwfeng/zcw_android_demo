package top.zcwfeng.koinsample.view
import android.content.Context
import android.util.AttributeSet
import org.koin.core.KoinComponent
import org.koin.core.inject
import top.zcwfeng.koinsample.data.User


class UserView(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(context, attrs),KoinComponent {
    private val user: User by inject()
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        text = "${user.name} 使用koin的心情 ${user.mood}"
    }
}

