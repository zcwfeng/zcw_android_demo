package top.zcwfeng.base.autoservice;

import java.util.ServiceLoader;

public final  class CommonServiceLoader {
    private CommonServiceLoader() {
    }

    public static<T> T load(Class<T> service){
        return ServiceLoader.load(service).iterator().next();
    }
}
