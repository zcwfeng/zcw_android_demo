package top.zcwfeng.hiltsample.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import top.zcwfeng.hiltsample.data.HttpObject
import javax.inject.Singleton

//@InstallIn(ApplicationComponent::class)
@InstallIn(ActivityComponent::class)
@Module
object HttpModule {
//    @Singleton
    @ActivityScoped
    @Provides
    fun providerHttpObject(): HttpObject {
        return HttpObject()
    }
}