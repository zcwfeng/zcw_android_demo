
cmake_minimum_required(VERSION 3.4.1)

aux_source_directory(src/main/cpp SOURCE)

add_library(
             native-lib

             SHARED

            ${SOURCE})


find_library(
              log-lib

              log )





target_link_libraries(
                       native-lib
                       ${log-lib}
            z)