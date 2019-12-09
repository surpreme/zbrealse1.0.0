package com.aite.a;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.aite.a.activity.LoginActivity;
import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.community.utils.MediaLoader;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.basemodule.base.BaseApp;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import chat.model.IMUserSigInfo;
import chat.utils.TXImUtils;

/**
 * 主Application
 */
public class APPSingleton extends BaseApp implements Mark {
    private OnLocationListener onLocationListener;
    public LocationClient mLocationClient;// 定位SDK的核心类
    public MyLocationListener mMyLocationListener;// 定义监听类
    public BDLocation mlocation;
    public Context appContext;
    private NetRun netRun;
    public static String province, city, district;//定位地址
    public static IMUserSigInfo iMUserSigInfo;//IM签名实体类
    public static NotificationManager manager;//通知
    /**
     * 全局的上下文.
     */
    private static Context mContext;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 80001:
                    if (State.UserKey != null) {
                        netRun.LoginValidation();
                    }
                    handler.sendEmptyMessageDelayed(80001, 10000);
                    break;
                case login_validation_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            State.UserKey = null;
                            Toast.makeText(appContext, getString(R.string.near_reminder24),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent3 = new Intent(appContext,
                                    LoginActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent3);
                        }
                    }
                    break;
                case login_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            netRun.getIMUserSig(State.UserId);
                            handler.sendEmptyMessageDelayed(80001, 10000);
                        }
                    }
                    break;
                case get_imusersig_id://获取会员签名
                    if (msg.obj != null) {
                        iMUserSigInfo = (IMUserSigInfo) msg.obj;
                        TXImUtils.TXimLogin(iMUserSigInfo.datas.identifier, iMUserSigInfo.datas.userSig,
                                Integer.parseInt(iMUserSigInfo.datas.sdkAppID));
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        closeAndroidPDialog();
        super.onCreate();
        mContext = getApplicationContext();
        SDKInitializer.initialize(this);
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        try {
            Positioning();
            getLocation();
            startLocation();
//            getAPPSecretString(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        netRun = new NetRun(this, handler);
        initHX();
        //讯飞
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=57510e86");
    }

    //解决  Detected problems with API compatibility(visit g.co/dev/appcompat for more info)
    // 即 AndroidP(API28)对非SDK接口调用的提示
    //原文 : https://www.jianshu.com/p/f87fe39caf1d
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Context.
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /* */

    /********* 定位 ************/
    private void Positioning() {
        LocationClient locationClient = new LocationClient(
                getApplicationContext());

        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 是否打开GPS
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        // 注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                province = location.getProvince();
                city = location.getCity();
                district = location.getDistrict();
//              Log.i("------------------","定位  省="+province+"  市="+city+"  区="+district);
            }
        });
        locationClient.start();
    }

    public void getLocation() {// 获取Location通过LocationManger获取！
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setAddrType("all");
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置高精度定位定位模式
        option.setCoorType("bd09ll");// 设置百度经纬度坐标系格式
        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);// 反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
    }

    public void startLocation() {
        mLocationClient.start();
    }

    public void stopLocation() {
        mLocationClient.stop();
    }

    public class MyLocationListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
            if (onLocationListener != null)
                onLocationListener.getLocation(location);
            if (location == null)
                return;
            mlocation = location;
            stopLocation();

        }
    }

    /**
     * 定位监听接口
     *
     * @author CAOYOU
     */
    public interface OnLocationListener {
        public void getLocation(BDLocation location);
    }

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }


    /**↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓环信↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
    /**
     * 初始化环信
     */
    public void initHX() {
        appContext = this;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader()).build());
        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        TXImUtils.manager = manager;
        TXImUtils.initTxim(this);//初始化腾讯IM
        // 登录
        netRun = new NetRun(mContext, handler);
        SharedPreferences spf = getSharedPreferences("LoginActivity",
                MODE_PRIVATE);
        String username = spf.getString("username", "");
        String password = spf.getString("password", "");
        netRun.login(username, password, "android");

    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 程序终止的时候 移除消息监听

    }

}
