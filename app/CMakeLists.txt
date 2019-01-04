# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             MyFFmpeg

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp
             src/main/cpp/jni2.cpp
             src/main/cpp/JniHelper.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# 添加android库支持 ??
find_library( android-lib
              android )

# 设置ffmpeg库所在路径的目录
set(distribution_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

add_library( avutil-56
             SHARED
             IMPORTED )
set_target_properties( avutil-56
           PROPERTIES IMPORTED_LOCATION
           ${distribution_DIR}/libavutil-56.so)

add_library( swresample-3
        SHARED
        IMPORTED )
set_target_properties( swresample-3
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libswresample-3.so)

add_library( avfilter-7
        SHARED
        IMPORTED )
set_target_properties( avfilter-7
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libavfilter-7.so)

add_library( avformat-58
        SHARED
        IMPORTED )
set_target_properties( avformat-58
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libavformat-58.so)

add_library( swscale-5
        SHARED
        IMPORTED )
set_target_properties( swscale-5
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libswscale-5.so)

add_library( avcodec-58
        SHARED
        IMPORTED )
set_target_properties( avcodec-58
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libavcodec-58.so)

include_directories(src/main/jniLibs/include)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       MyFFmpeg

                       # Links the target library to the log library
                       # included in the NDK.
                       avcodec-58
                       avfilter-7
                       avformat-58
                       avutil-56
                       swresample-3
                       swscale-5
                       ${log-lib}
                       ${android-lib} )