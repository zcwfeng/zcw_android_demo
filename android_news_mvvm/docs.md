# 优化代码

CPU Profile

Debug API

```aidl

 public ArchDemoApplication() {
        Debug.startMethodTracing("zcwfeng");
    }

MainActivity

 @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Debug.stopMethodTracing();
    }
```


严格模式，debug  ---- Application 中添加

```aidl
if (BuildConfig.DEBUG) {
            //线程检测策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads() //读、写操作
                    .detectDiskWrites()
                    .detectNetwork() // or .detectAll() for all detectable problems .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()// sqlite 对象泄露

                    .detectLeakedClosableObjects() //未关闭的Closable对象泄露 .penaltyLog() //违规打印日志
                    .penaltyDeath() //违规崩溃
                    .build());
        }
```

adb 启动 时间测试

```aidl
 -> zcw_android_demo git:(master) ✗ adb shell am start -S -W com.xiangxue.arch_demo/.MainActivity
Stopping: com.xiangxue.arch_demo
Starting: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=com.xiangxue.arch_demo/.MainActivity }
Status: ok
Activity: com.xiangxue.arch_demo/.MainActivity
ThisTime: 5701
TotalTime: 5701
WaitTime: 6122
Complete

```

或者搜索启动ActivityManger: display 展示启动时间

添加闪屏代码 失败

去掉两个布局PageTitleView 和 TitleView 减少LinearLayout层次

viewpager加载改成viewpager2 懒加载

速度提升

```aidl
➜  zcw_android_demo git:(master) ✗ adb shell am start -S -W top.zcwfeng.arch_demo/.MainActivity
Stopping: top.zcwfeng.arch_demo
Starting: Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] cmp=top.zcwfeng.arch_demo/.MainActivity }
Status: ok
Activity: top.zcwfeng.arch_demo/.MainActivity
ThisTime: 858
TotalTime: 858
WaitTime: 867
Complete

```

### Choreographer->ChoreographerHelper
检测掉帧

### Looper->loop
printer 检测卡顿

### include merge 优化

### 过度绘制检查，layout background等

# APK 缩小包体积

### 工具 Android Size Analyzer 插件使用

### Lint 工具 unused resource

### 持续优化，pgn-》 svg

### gradle apk split 分包