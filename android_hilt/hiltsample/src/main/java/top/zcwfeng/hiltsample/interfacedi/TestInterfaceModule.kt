package top.zcwfeng.hiltsample.interfacedi

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class TestInterfaceModule {
    @Binds
    abstract fun abc(testClass:TestClass):TestInterface
}