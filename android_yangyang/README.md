Doc 说明

1. UI

- SplashActivity 启动页SingleTask 去掉TitleBar等，加快启动速度。后续加上闪屏广告

根据情况选择theme：android:theme="@style/Theme.AppCompat.NoActionBar"

Handler 延迟，用到了弱引用。弱引用放在引用队列

        ReferenceQueue<Apple> appleReferenceQueue = new ReferenceQueue<>();
        WeakReference<Apple> appleWeakReference = new WeakReference<Apple>(new Apple("青苹果"), appleReferenceQueue);
        WeakReference<Apple> appleWeakReference2 = new WeakReference<Apple>(new Apple("毒苹果"), appleReferenceQueue);

    软引用（SoftReference）
    软引用是用来描述一些有用但并不是必需的对象，在Java中用java.lang.ref.SoftReference类来表示。对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象。因此，这一点可以很好地用来解决OOM的问题，并且这个特性很适合用来实现缓存：比如网页缓存、图片缓存等。
    
    注意：只有在内存不足的时候JVM才会回收软引用关联着的对象。
    
    弱引用（WeakReference）
    弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。


- 

2. 框架

- 使用Process 生成的权限框架
