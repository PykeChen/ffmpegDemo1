//
// Created by 陈培源 on 2018/9/24.
//
#include <jni.h>
#include <cstring>
#include <malloc.h>

#ifdef __cplusplus
extern "C" {
#endif

static jstring charTojstring(JNIEnv *env, const char *pat) {
    //定义java String类 strClass
    jclass strClass = (env)->FindClass("Ljava/lang/String;");
    //获取String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    //建立byte数组
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    //将char* 转换为byte数组
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    // 设置String, 保存语言类型,用于byte数组转换至String时的参数
    jstring encoding = (env)->NewStringUTF("GB2312");
    //将byte数组转换为java String,并输出
    return (jstring) (env)->NewObject(strClass, ctorID, bytes, encoding);
}

static char *jstringToChar(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("GB2312");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}


static char* stringcat(const char *s1,const char *s2) {
    char *p, *p0;//p用于存储拼接后的字符串,p0备用
    p = new char[strlen(s1) + strlen(s2) + 1];//使用new为p申请恰好的堆空间
    if (p == NULL) {//检测是否申请空间成功
//        cout << "Out of space!" << endl;
    }
    p0 = p;//让P0指向P
    while (*s1) {//s1中的元素不为'\0'时继续遍历
        *p++ = *s1++;//将p的元素用s1元素挨个填充
    }
    while (*s2) {
        *p++ = *s2++;
    }
    *p = '\0';//尾部加上结束符
    return p0;//返回p0

}


#ifdef __cplusplus
}
#endif //FFMPEGDEMO_JNIHELPER_H
