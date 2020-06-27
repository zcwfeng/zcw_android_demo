package top.zcwfeng.jetpack.utils;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

public class LiveDataBus {
    //存放订阅者
    private Map<String, MutableLiveData<Object>> bus;
    private static LiveDataBus liveDataBus = new LiveDataBus();

    private LiveDataBus() {
        bus = new HashMap();
    }
    public static LiveDataBus getInstance() {
        return liveDataBus;
    }
    //注册订阅者
    public synchronized <T> MutableLiveData<T> with(String key, Class<T> type) {
        if(!bus.containsKey(key)){
            bus.put(key,new MutableLiveData<Object>());
        }
        return (MutableLiveData<T>)bus.get(key);
    }
}