package com.lzy.basemodule.base;

import android.app.Application;
import android.content.Context;

import com.lzy.basemodule.exceptiono.CrashHandler;


public class BaseApp extends Application {
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        context = this;
        CrashHandler.init(new CrashHandler(getApplicationContext()));


    }

    public static Context getContext() {
        return context;
    }

}
