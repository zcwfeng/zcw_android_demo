# documentation: https://d.android.com/studio/projects/add-native-code.html
cmake_minimum_required(VERSION 3.4.1)

include_directories("include")
# 编译源文件
#file(GLOB my_source_path src/main/cpp/*.cpp src/main/cpp/*.c)

# 方法1
aux_source_directory(${CMAKE_SOURCE_DIR} my_source_path)
aux_source_directory("${CMAKE_SOURCE_DIR}/bzip2" SOURCES)
aux_source_directory("${CMAKE_SOURCE_DIR}/opencv2" SOURCES2)
# 方法2
#file(GLOB ${my_source_path} *.h *.cpp *.c)

add_library(native-lib SHARED ${my_source_path} ${SOURCES} ${SOURCES2})

#add_library(native-lib SHARED ${allCpp} )


#add_library(opencv_java4 SHARED IMPORTED)
#add_library(fmod SHARED IMPORTED)
#add_library(fmodL SHARED IMPORTED)

#set_target_properties(lib_opencv
#        PROPERTIES IMPORTED_LOCATION
#        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${CMAKE_ANDROID_ARCH_ABI}/libopencv_java4.so)



# 类似
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/../jniLibs/${CMAKE_ANDROID_ARCH_ABI}")




target_link_libraries( # Specifies the target library.
        native-lib
        log #${log-lib}
        jnigraphics
        opencv_java3
        fmod
        fmodL
        )

# 调试用 set 函数 可以调优设置
# 开启输出详细的编译和链接信息
set(CMAKE_VERBOSE_MAKEFILE on)
message("zcw before")

message(STATUS ${SOURCES})

message(STATUS "zcw after")