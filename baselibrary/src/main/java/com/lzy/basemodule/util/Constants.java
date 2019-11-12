package com.lzy.basemodule.util;

import android.os.Environment;

import com.lzy.basemodule.base.BaseApp;

import java.io.File;

class Constants {
    public static final String PATH_DATA = BaseApp.getContext().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";
    public static final String FILE_PROVIDER_AUTHORITY="com.lzy.super.fileprovider";

}
