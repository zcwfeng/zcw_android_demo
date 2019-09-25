package top.zcwfeng.dagger2.user;

import javax.inject.Singleton;

import dagger.Component;
import top.zcwfeng.dagger2.HttpModule;
import top.zcwfeng.dagger2.MainActivity;

@Singleton
@Component(modules={UserModule.class, HttpModule.class})
public interface UserComponent {
    void inject(MainActivity activity);
}
