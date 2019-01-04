package com.example.cpy.ffmpegdemo;

import android.app.Application;
import android.content.Context;

public class AppApplication extends Application {

    private static AppApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static AppApplication getApplication() {
        return mApplication;
    }

    public static Context getContext() {
        return mApplication;
    }

}
