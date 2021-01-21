package top.zcwfeng.daggersample2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import top.zcwfeng.daggersample2.di.DaggerMainComponent
import top.zcwfeng.daggersample2.obj.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import dagger.Lazy

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var objectForMainModule: ObjectForMainModule

    @Inject
    lateinit var objectForTestSubModule: ObjectForTestSubModule

    @field:[Named("key1")]
    @Inject
    lateinit var user1: User

    @field:[Named("key2")]
    @Inject
    lateinit var user2: User

    @Inject
    lateinit var a: A

    @Inject
    lateinit var b: B

    @Inject
    lateinit var lazy: Lazy<A>

    @Inject
    lateinit var provider: Provider<A>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainComponent.create().testSubComponent().injectMainActivity(this)

        Log.e("zcwfeng", "${objectForMainModule.hashCode()}---->objectForMainModule.hashCode")
        Log.e("zcwfeng", "${objectForTestSubModule.hashCode()}---->objectForTestSubModule.hashCode")
        Log.e("zcwfeng", "${user1.hashCode()}---->user1.hashCode")
        Log.e("zcwfeng", "${user2.hashCode()}---->user2.hashCode")
        Log.e("zcwfeng", "${a.hashCode()}---->a.hashCode")
        Log.e("zcwfeng", "${b.hashCode()}---->b.hashCode")
        Log.e("zcwfeng", "${lazy.get().hashCode()}---->lazy.value.hashCode")
        Log.e("zcwfeng", "${provider.get().hashCode()}---->provider.get().hashCode")
    }
}