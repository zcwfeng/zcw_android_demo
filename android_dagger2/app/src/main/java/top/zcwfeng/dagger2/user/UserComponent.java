package top.zcwfeng.dagger2;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules={UserModule.class,HttpModule.class,TestSingletonModule.class})
public interface UserComponent {
    void inject(MainActivity activity);
}
