package com.lzy.basemodule.util;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.lzy.basemodule.base.BaseApp;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created by l on 2018/3/6.
 */

public class PackageUtils {

    /**
     * 高版本：获取顶层的activity的包名
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static String getHigherPackageName(Context context) {
        String topPackageName = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            //time - 1000 * 1000, time 开始时间和结束时间的设置，在这个时间范围内 获取栈顶Activity 有效
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            // Sort the stats by the last time used
            if (stats != null) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName topActivity = activityManager.getRunningTasks(1).get(0).topActivity;
            topPackageName = topActivity.getPackageName();
        }
        return topPackageName;
    }


    /**
     * 低版本：获取栈顶app的包名
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private String getLowerVersionPackageName(Context context) {
        String topPackageName;//低版本  直接获取getRunningTasks
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName topActivity = activityManager.getRunningTasks(1).get(0).topActivity;
        topPackageName = topActivity.getPackageName();
        return topPackageName;
    }

    /**
     * 获取能在桌面上使用得app
     *
     * @return
     */
    public static List<ResolveInfo> getIntentActivity() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);

        PackageManager manager = BaseApp.getContext().getApplicationContext().getPackageManager();
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(intent, 0);
        return resolveInfos;
    }

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static void startAppByPackageName(String packageName) {
        PackageManager packageManager = BaseApp.getContext().getApplicationContext().getPackageManager();
        if (checkPackInfo(packageName)) {
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            BaseApp.getContext().getApplicationContext().startActivity(intent);
        } else {
        }
    }

    public static void startTargetActivity(String packageName, String targetActivity) {
        Intent intent = new Intent();
        //第一种方式
        ComponentName cn = new ComponentName(packageName, targetActivity);
        try {
            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            BaseApp.getContext().getApplicationContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private static boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = BaseApp.getContext().getApplicationContext().getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static String getVerName() {
        String verName = "";
        try {
            verName = BaseApp.getContext().getPackageManager().getPackageInfo(BaseApp.getContext().getApplicationContext().getPackageName(),
                    0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;

    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }


    public static String getVersionName(Context context, String packagename) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(packagename, 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVersionCode(Context context, String packagename) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(packagename, 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }



}
