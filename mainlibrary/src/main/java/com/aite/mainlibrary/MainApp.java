package com.aite.mainlibrary;

import android.content.Context;

import com.lzy.basemodule.base.BaseApp;

public class MainApp {
    private static Context mContext;

    /**
     * 子模块和主模块需要共享全局上下文，故需要在app module初始化时传入
     */
    public static void init(Context appContext) {
        if (mContext == null) {
            mContext = appContext.getApplicationContext();

        }
    }



    public static Context getAppContext() {
        return mContext;
    }
}
