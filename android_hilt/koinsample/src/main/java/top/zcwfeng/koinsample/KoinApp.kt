package top.zcwfeng.koinsample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import top.zcwfeng.koinsample.data.User

class KoinApp : Application() {
    val appModule = module {
        single { User() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@KoinApp)
            modules(appModule)
        }
    }
}