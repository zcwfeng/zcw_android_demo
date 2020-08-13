package top.zcwfeng.hiltsample.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import top.zcwfeng.hiltsample.data.User

@Module
@InstallIn(ActivityComponent::class)
object HiltModule2 {
    @Provides
    fun providerUser(): User {
        val user = User()
        user.name = "zcw"
        user.mood = "一言难尽"
        return user
    }
}