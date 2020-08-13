package top.zcwfeng.hiltsample.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import top.zcwfeng.hiltsample.data.User

@Module
@InstallIn(ActivityComponent::class)
abstract class HiltModule {
    @Binds
    abstract fun bindAny(user: User):Any
}