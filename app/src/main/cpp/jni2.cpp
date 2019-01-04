//
// Created by 陈培源 on 2018/9/24.
//

#include "jni2.h"
#include <android/log.h>
#include <stdio.h>
#include <string.h>
#include "JniHelper.cpp"

#define TAG "cpy"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

const char *DIR = "/Users/cpy/StudioProjects/FFmpegDemo/";

JNIEXPORT void JNICALL Java_com_example_cpy_ffmpegdemo_JNI_splitPCMFile
        (JNIEnv *env, jobject, jstring filePath, jstring outDir) {
    char *path = jstringToChar(env, filePath);

    char *dir = jstringToChar(env, outDir);

    char *dirFile1 = stringcat(dir, "/output_l.pcm");
    char *dirFile2 = stringcat(dir, "/output_r.pcm");

    FILE *fp = fopen(path, "rb+");
    FILE *fp1 = fopen(dirFile1, "wb+");
    FILE *fp2 = fopen(dirFile2, "wb+");


    LOGE("char length  %lu, %d", sizeof(char), sizeof(short));

    if (!fp) {
        LOGE("file not exist %s", path);

    } else {

        LOGE("file exist %s, *d", path);

        unsigned char *sample = static_cast<unsigned char *>(malloc(4));

        while (!feof(fp)) {
            fread(sample, 1, 4, fp);

            short *sampleNum = NULL;
            sampleNum = reinterpret_cast<short *>(sample);
            *sampleNum = *sampleNum / 2;

            fwrite(sample, 1, 2, fp1);
            fwrite(sample + 2, 1, 2, fp2);
        }

        free(sample);
        fclose(fp);
        fclose(fp1);
        fclose(fp2);

    }

    LOGE("spltPCMFile");

}
