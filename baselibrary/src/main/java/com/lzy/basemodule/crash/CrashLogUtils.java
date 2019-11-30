package com.lzy.basemodule.crash;

import android.os.Environment;
import android.text.TextUtils;
import com.lzy.basemodule.util.PackageUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by l on 2017/10/9.
 */

public class CrashLogUtils {
    private static CrashLogUtils mInstance;

    private CrashLogUtils() {
    }

    public static CrashLogUtils getInstance() {
        if (mInstance == null) {
            synchronized (CrashLogUtils.class) {
                if (mInstance == null) {
                    mInstance = new CrashLogUtils();
                }
            }
        }
        return mInstance;
    }

    public void writeCrashLog(String throwableMsg, String filePath) throws IOException {
        File file;
        if (TextUtils.isEmpty(filePath)) {
            file = new File(Environment.getExternalStorageDirectory() + "/LockCrash.txt");
        } else {
            file = new File(filePath);
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        long currenttime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currenttime));
        //time
        pw.println(time);
        //version
        pw.println(PackageUtils.getVerName());
        //stack
        pw.println(throwableMsg);
        pw.close();
    }

    public void writeCrashLog(String throwableMsg) throws IOException {
        writeCrashLog(throwableMsg, Environment.getExternalStorageDirectory() + "/LzyCrash.txt");
    }

    public String getThrowableMsg(Throwable throwable) {
        String stackOverFlow = throwable.toString();
        stackOverFlow = new Date().toString() + " " + stackOverFlow;
        StackTraceElement[] traces = throwable.getStackTrace();
        for (StackTraceElement trace : traces) {
            stackOverFlow += "\n\t" + trace;
        }
        Throwable cause = throwable.getCause();
        if (cause != null) {
            stackOverFlow += "\nCaused by: " + cause.toString();
            traces = cause.getStackTrace();
            for (StackTraceElement trace : traces) {
                stackOverFlow += "\n\t" + trace;
            }
        }
        return stackOverFlow;
    }
}
