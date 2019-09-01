搭建环境：

1. ffmpeg编译，这个参见笔记或者网上搜索，30分钟左右
2. android 环境搭建

    CMakeList.txt
    
```
# documentation: https://d.android.com/studio/projects/add-native-code.html
# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.4.1)

# 需要引入我们头文件,以这个配置的目录为基准
include_directories(${CMAKE_SOURCE_DIR}/src/main/jniLibs/include)

# 添加共享库搜索路径
link_libraries(${CMAKE_SOURCE_DIR}/src/main/jniLibs/armeabi)

link_directories(${CMAKE_SOURCE_DIR}/src/main/jniLibs/armeabi)
# 指定源文件目录
aux_source_directory(${CMAKE_SOURCE_DIR}/src/main/cpp SRC_LIST)

add_library( # Sets the name of the library.
        ffmpegutils-lib
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        src/main/cpp/FFMPEGUtils.cpp
        ${SRC_LIST})

target_link_libraries( # Specifies the target library.
        # 链接额外的 ffmpeg 的编译
        ffmpegutils-lib
        # 编解码(最重要的库)
        avcodec-57
        # 设备信息
        avdevice-57
        # 滤镜特效处理库
        avfilter-6
        # 封装格式处理库
        avformat-57
        # 工具库(大部分库都需要这个库的支持)
        avutil-55
        # 后期处理
        postproc-54
        # 音频采样数据格式转换库
        swresample-2
        # 视频像素数据格式转换
        swscale-4
        # 链接 android ndk 自带的一些库
        android
        # 链接 OpenSLES
        OpenSLES
        # Links the target library to the log library
        # included in the NDK.
        log)

```

    build.gradle，新建C++项目，src 新建jni sourceforder并且配置
    
```
android {
    compileSdkVersion 28
    buildToolsVersion "29.0.1"
    defaultConfig {
        applicationId "top.zcwfeng.zcwffmpeg"
        minSdkVersion 21
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags "-std=c++11"
                version "3.10.2"
            }

            ndk {
                abiFilters 'armeabi'
            }
        }
    }

    sourceSets {
        main {
            jni.srcDirs ('src/main/jniLibs')
        }
    }
    
    externalNativeBuild {
        cmake {
            path "CMakeLists.txt"
            version "3.10.2"
        }
    }
    
    ......省略其他配置

}

```

3. 导入ffmpeg 必要的so库，编写好CMake脚本，clean project/refresh c++/build project
