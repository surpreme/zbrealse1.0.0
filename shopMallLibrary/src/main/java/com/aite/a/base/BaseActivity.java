package com.aite.a.base;

import java.util.Locale;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.AppManager;
import com.aite.a.NetworkStateService;
import com.aite.a.NetworkStateService.Reboot;
import com.aite.a.view.MyDialog;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;

/**
 * 基类
 *
 * @author xiaoyu
 */
public abstract class BaseActivity extends Activity implements Reboot, Mark {
    /**
     * 签名
     */
    protected String mySign = null;
    protected String baseStr = null;
    protected static final String SIGN_ = "sign";
    /**
     * 进度条对话框
     */
    protected MyDialog mdialog;
    private View inflate;
    protected JSONObject jsonObject;
    public static final String TAG = BaseActivity.class.getSimpleName();
    protected TextView tv_title_name;
    protected ImageView iv_back, iv_right;

    protected Handler mHandler = null;
    protected InputMethodManager imm;
    private TelephonyManager tManager;
    protected Intent intent;
    public ViewUtils viewUtils;
    public BitmapUtils bitmapUtils;
    public APPSingleton appSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        // AppManager.getInstance().killActivity(SplashActivity.class);
        inflate = View.inflate(this, R.layout.dialog_baseprogress, null);
        mdialog = new MyDialog(this, 150, 150, inflate, R.style.loading_dialog);
        mdialog.setCanceledOnTouchOutside(false);
        int inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        getWindow().setSoftInputMode(inputMode);
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        NetworkStateService.setReboot(this);
        appSingleton = (APPSingleton) getApplication();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * 绑定控件id
     */
    protected abstract void findViewById();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 请求数据
     */
    protected abstract void initData();

    /**
     * 返回
     */
    protected void overrPre() {
        overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }

    /**
     * 向前
     */
    protected void overrIn() {
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action跳转界面
     **/
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void DisPlay(String content) {
        Toast.makeText(this, content, 1).show();
    }

    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    protected void hideOrShowSoftInput(boolean isShowSoft, EditText editText) {
        if (isShowSoft) {
            imm.showSoftInput(editText, 0);
        } else {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    // 获得当前程序版本信息
    protected String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
                0);
        return packInfo.versionName;
    }

    // 獲得設備信息
    protected String getDeviceId() throws Exception {
        String deviceId = tManager.getDeviceId();

        return deviceId;

    }

    /**
     * 获取SIM卡序列号
     *
     * @return
     */
    protected String getToken() {
        return tManager.getSimSerialNumber();
    }

    /* 獲得系統版本 */

    protected String getClientOs() {
        return android.os.Build.ID;
    }

    /* 獲得系統版本號 */
    protected String getClientOsVer() {
        return android.os.Build.VERSION.RELEASE;
    }

    // 獲得系統語言包
    protected String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    protected String getCountry() {

        return Locale.getDefault().getCountry();
    }

    protected void initData(Bundle bundle) {
        // TODO Auto-generated method stub

    }

    // 获得国际化语言
    protected String getI18n(int resId) {
        return getString(resId).toString();
    }

}
