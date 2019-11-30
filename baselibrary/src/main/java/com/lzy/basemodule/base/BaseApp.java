package com.lzy.basemodule.base;

import android.app.Application;
import android.content.Context;

import com.lzy.basemodule.crash.CrashHandler;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


public class BaseApp extends Application {
    protected static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        context = this;
        CrashHandler.init(new CrashHandler(getApplicationContext()));
        BGASwipeBackHelper.init(this, null);


    }

    public static Context getContext() {
        return context;
    }

}
