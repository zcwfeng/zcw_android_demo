android.jar 源码

https://github.com/anggrayudi/android-hidden-api


Android危险权限
    SMS,STORAGE,CONTACTS,PHONE,CALENDAR,CAMERA,
    LOCATION,BODY_SENSORS,MICROPHONE
    
    
android P 之前

adb shell dumpsys activity | grep "mFocusedActivity"

adb shell dumpsys activity top

权限框架搭建

    libannotation，javalib只需要定义出来对外的annotation即可
    libcompiler，javalib，实际反射生成核心，ClassValidator,MethodInfo,AbstractProcessor集成执行AutoService
    libpermissionhelper,androidlib,定义代理和接口对接UI的工具类
    
中途需要的步骤和遇到的问题解决：

    gradle5.0之后，会有一些莫名其妙的问题提，比如debug进不去。meta-inf不会自动生成

需要做这几件事：
在主的app model里面手动加入配置

    - build.gradle
    implementation project(':libannotation')
    implementation project(':libpermissionhelper')
    annotationProcessor project(':libcompiler')
    
    -build.gradle libcompiler
    mplementation project(':libannotation')
    implementation 'com.google.auto.service:auto-service:1.0-rc5'
    
    -build.gradle libpermissionhelper
    不需要
    
    -build.gradle libannotation
    不需要，可能需要改成1.8或者8
    
如果无法生成动态的类general/debug/apt/xxxx.java
    
    需要手动创建在libcompiler；resources/META-INF/services/你的主要集成的AbstractProcessor全路径
    
    参考java的SPI