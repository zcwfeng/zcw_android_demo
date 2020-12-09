cmake_minimum_required(VERSION 3.10.2)

add_library(
        FaceTracker
        SHARED
        native-lib.cpp
        FaceTrack.cpp
)


# 设置变量
#-L：引入库查找路径
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}")

# 引入头文件
include_directories(include)

target_link_libraries(
        FaceTracker
        opencv_java4
        SeetaFaceLandmarker
        log
)
