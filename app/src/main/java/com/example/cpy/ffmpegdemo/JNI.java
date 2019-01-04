package com.example.cpy.ffmpegdemo;

public class JNI {

    static {
        System.loadLibrary("MyFFmpeg");
    }

    public static native void splitPCMFile(String filePath, String dstDir);

}
