package top.zcwfeng.jetpack.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 解除 LiveDataBus 粘性问题，某些场景需要
 */
public class LiveDataBusX {
    //存放订阅者
    private Map<String, BusMutableLiveData<Object>> bus;

    private static LiveDataBusX liveDataBus = new LiveDataBusX();

    private LiveDataBusX() {
        bus = new HashMap<>();
    }

    public static LiveDataBusX getInstance() {
        return liveDataBus;
    }

    //注册订阅者，（存入map） Hook前用MutableLiveData
    public synchronized <T> BusMutableLiveData<T> with(String key, Class<T> type){
        if(!bus.containsKey(key)){
            bus.put(key,new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) bus.get(key);
    }

    public static class BusMutableLiveData<T> extends MutableLiveData<T> {
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            hook(observer);
        }
        //在这里去改变onChange的流程
        private void hook(Observer<? super T> observer) {
            try {
                //1.得到mLastVersion
                //获取到LiveData的类中的mObservers对象
                Class<LiveData> liveDataClass = LiveData.class;
                Field mObserversField = liveDataClass.getDeclaredField("mObservers");
                mObserversField.setAccessible(true);
                //获取到这个成员变量的对象
                Object mObserversObject = mObserversField.get(this);
                //得到map对应的class对象
                Class<?> mObserversClass = mObserversObject.getClass();
                //获取到mObservers对象的get方法
                Method get = mObserversClass.getDeclaredMethod("get", Object.class);
                get.setAccessible(true);
                //执行get方法
                Object invokeEntry=get.invoke(mObserversObject,observer);
                //定义一个空的对象
                Object observerWraper=null;
                if(invokeEntry!=null && invokeEntry instanceof Map.Entry){
                    observerWraper=((Map.Entry)invokeEntry).getValue();
                }
                if(observerWraper==null){
                    throw new NullPointerException("observerWraper is null");
                }
                //得到ObserverWrapper的类对象  编译擦除问题会引起多态冲突所以用getSuperclass
                Class<?> superclass = observerWraper.getClass().getSuperclass();
                Field mLastVersion = superclass.getDeclaredField("mLastVersion");
                mLastVersion.setAccessible(true);
                //2.得到mVersion
                Field mVersion = liveDataClass.getDeclaredField("mVersion");
                mVersion.setAccessible(true);
                //3.把mVersion的数据填入到mLastVersion中
                Object mVersionValue=mVersion.get(this);
                mLastVersion.set(observerWraper,mVersionValue);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}