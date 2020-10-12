# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html


cmake_minimum_required(VERSION 3.4.1)

aux_source_directory(. SOURCE)

add_library(
             mmkv

             SHARED

             ${SOURCE} )

target_link_libraries(
        mmkv
                    log )