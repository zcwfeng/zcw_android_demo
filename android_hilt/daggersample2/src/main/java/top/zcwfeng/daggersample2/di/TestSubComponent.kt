package top.zcwfeng.daggersample2.di

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.Subcomponent
import top.zcwfeng.daggersample2.MainActivity

@Subcomponent(modules = [(TestSubModule::class)])
interface TestSubComponent {
    fun injectMainActivity(activity: MainActivity)
}