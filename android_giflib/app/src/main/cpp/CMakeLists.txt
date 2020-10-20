cmake_minimum_required(VERSION 3.10.2)
#project("giflib")

file(GLOB libgif_src ${CMAKE_SOURCE_DIR}/giflib/*.c)

include_directories(${CMAKE_SOURCE_DIR}/giflib)

add_library(
        native-lib
        SHARED
        ${libgif_src}
        JavaInputStream.cpp
        GifFrame.cpp
        native-lib.cpp)


find_library(
        log-lib
        log)

find_library(
        android-lib
        android
)
find_library(
        jnigraphics-lib
        jnigraphics
)

target_link_libraries(
        native-lib
        ${android-lib}
        ${jnigraphics-lib}
        ${log-lib})