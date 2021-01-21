package top.zcwfeng.daggersample2.di

import dagger.Module
import dagger.Provides
import top.zcwfeng.daggersample2.obj.A
import top.zcwfeng.daggersample2.obj.B
import top.zcwfeng.daggersample2.obj.ObjectForMainModule
import top.zcwfeng.daggersample2.obj.User
import javax.inject.Named

@Module
open class MainModule {
    @Provides
    fun provideObjectForMainModule(): ObjectForMainModule {
        return ObjectForMainModule()
    }
    @Provides
    fun provideB(): B {
        return B()
    }
    @Provides
    fun provideA(b: B): A {
        return A(b)
    }

    @Named("key1")
    @Provides
    fun provideUser(): User {
        return User("David","123")
    }

    @Named("key2")
    @Provides
    fun provideUser2(): User {
        return User("David","456")
    }
}