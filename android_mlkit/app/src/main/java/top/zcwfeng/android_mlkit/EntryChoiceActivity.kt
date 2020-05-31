package top.zcwfeng.android_mlkit

import android.content.Intent
import com.firebase.example.internal.BaseEntryChoiceActivity
import com.firebase.example.internal.Choice
import top.zcwfeng.android_mlkit.java.ChooserActivity

class EntryChoiceActivity : BaseEntryChoiceActivity() {

    override fun getChoices(): List<Choice> {
        return listOf(
                Choice(
                        "Java",
                        "Run the Firebase ML Kit quickstart written in Java.",
                        Intent(this,
                                ChooserActivity::class.java)),
                Choice(
                        "Kotlin",
                        "Run the Firebase ML Kit quickstart written in Kotlin.",
                        Intent(
                                this,
                                top.zcwfeng.android_mlkit.kotlin.ChooserActivity::class.java))
        )
    }
}
