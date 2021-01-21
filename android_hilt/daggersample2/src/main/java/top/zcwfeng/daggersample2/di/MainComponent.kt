package top.zcwfeng.daggersample2.di

import dagger.Component
import javax.inject.Inject

@Component(modules = [(MainModule::class)])
interface MainComponent {
    fun testSubComponent():TestSubComponent
}

