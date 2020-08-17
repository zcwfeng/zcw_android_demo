package top.zcwfeng.base.autoservice;

import java.util.ServiceLoader;

public final  class CommonServiceLoader {
    private CommonServiceLoader() {
    }

    public static<T> T load(Class<T> service){
        try {
            return ServiceLoader.load(service).iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
