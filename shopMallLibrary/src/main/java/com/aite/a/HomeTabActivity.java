package com.aite.a;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.aite.a.activity.li.activity.ChoiceActivity;
import com.aite.a.activity.CustomHomeActivity;
import com.aite.a.activity.LoginActivity;
import com.aite.a.activity.MeActivity;
import com.aite.a.activity.MoreActivity;
import com.aite.a.activity.NearbyActivity;
import com.aite.a.activity.PersonalMiYaActivity;
import com.aite.a.activity.ShoppingCartActivity;
import com.aite.a.activity.li.activity.MessageActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.EventBean;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BooleanLogin;
import com.aite.a.view.custom.MyRadioButton;
import com.aiteshangcheng.a.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.rxbus.RxBus;
import com.qq.xgdemo.DiagnosisActivity;

import org.greenrobot.eventbus.EventBus;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 主界面
 *
 * @author xiaoyu 准备实现数据缓存处理
 */
@SuppressWarnings("deprecation")
public class HomeTabActivity extends TabActivity implements Mark {
    private RadioGroup mTabButtonGroup;
    private TabHost mTabHost;
    private SharedPreferences sp;
    public static RadioButton categoryBtn, cartBtn, personalBtn,
            messageBrn;
    public static MyRadioButton mainBtn;
    private Intent intent;
    private String _intentTag;
    private APPSingleton application;
    // private RadioButton periphery;
    private NetRun netRun;
//    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        Intent intent = new Intent(this, NetworkStateService.class);
        startService(intent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tab_home_activity);
        // 透明状态栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        registerReceiver();
        findViewById();
        initView();
        int inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        getWindow().setSoftInputMode(inputMode);
    }

    private void findViewById() {
        mTabButtonGroup = findViewById(R.id.home_radio_button_group);
        mainBtn = findViewById(R.id.home_tab_main);
        categoryBtn = findViewById(R.id.home_tab_category);
        cartBtn = findViewById(R.id.home_tab_cart);
        personalBtn = findViewById(R.id.home_tab_personal);
        messageBrn = findViewById(R.id.home_tab_message);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (Mark.State.UserKey == null) {
//            Intent po = new Intent(this, LoginActivity.class);
//            startActivity(po);
//            overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
//        }
        RxBus.getDefault().subscribe(this, "jumpType", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String o) {
                mTabButtonGroup.check(R.id.home_tab_category);
            }
        });
    }

    /**
     * 个人中心风格 0艾特 1密牙
     */
    private int personal_version = 0;

    private void initView() {
        mTabHost = getTabHost();
        application = (APPSingleton) getApplication();
        netRun = new NetRun(this, handler);
//		Intent i_main = new Intent(this, MainActivity.class);
//		Intent i_main = new Intent(this, JDHomePage.class);
        Intent i_main = new Intent(this, CustomHomeActivity.class);
        Intent i_category = new Intent(this, ChoiceActivity.class);
//		Intent i_category = new Intent(this, CategoryActivity.class);
        Intent i_periphery = new Intent(this, NearbyActivity.class);
        Intent i_cart = new Intent(this, ShoppingCartActivity.class);
        Intent i_message = new Intent(this, MessageActivity.class);
//        Intent i_cart = new Intent(this, CartActivity.class);
        Intent i_personal = new Intent(this, MeActivity.class);
//		Intent i_personal = new Intent(this, PersonalActivity.class);
        // 仿密牙会员中心
        Intent i_personalMiYa = new Intent(this, PersonalMiYaActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY).setIndicator(TAB_CATEGORY)
                .setContent(i_category));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MESSAGE)
                .setIndicator(TAB_MESSAGE).setContent(i_message));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_PERIPHERY)
                .setIndicator(TAB_PERIPHERY).setContent(i_periphery));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CART).setIndicator(TAB_CART)
                .setContent(i_cart));
        switch (personal_version) {
            case 0:
                mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL)
                        .setIndicator(TAB_PERSONAL).setContent(i_personal));
                break;
            case 1:
                mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL)
                        .setIndicator(TAB_PERSONAL).setContent(i_personalMiYa));
                break;
        }
        RxBus.getDefault().subscribe(this, "jumpShopCar", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String o) {
                mTabButtonGroup.check(R.id.home_tab_cart);

            }
        });
        RxBus.getDefault().subscribe(this, "jumpMain", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String o) {
                mTabButtonGroup.check(R.id.home_tab_main);
            }
        });

        mTabButtonGroup
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.home_tab_main) {
                            //首页
                            mTabHost.setCurrentTabByTag(TAB_MAIN);
                            handler.sendEmptyMessage(1);
                        } else if (checkedId == R.id.home_tab_category) {
                            // 分类
                            mTabHost.setCurrentTabByTag(TAB_CATEGORY);
                            handler.sendEmptyMessage(2);
                        } else if (checkedId == R.id.home_tab_periphery) {
                            // 周边商家
                            mTabHost.setCurrentTabByTag(TAB_MESSAGE);
                            handler.sendEmptyMessage(3);
                        } else if (checkedId == R.id.home_tab_cart) {
                            // 购物车
                            mTabHost.setCurrentTabByTag(TAB_CART);
                            handler.sendEmptyMessage(4);
                        } else if (checkedId == R.id.home_tab_personal) {
                            handler.sendEmptyMessage(5);
                            if (State.UserKey != null) {
                                mTabHost.setCurrentTabByTag(TAB_PERSONAL);
                            } else {
                                mainBtn.performClick();
                                intent = new Intent(getApplicationContext(),
                                        LoginActivity.class);
                                intent.putExtra("login_tag", "home");
                                startActivity(intent);
                            }
                        } else if (checkedId == R.id.home_tab_message) {
                            mTabHost.setCurrentTabByTag(TAB_MESSAGE);
                            handler.sendEmptyMessage(6);
                        }

//                        switch (checkedId) {
//                            case R.id.home_tab_main:// 首页
//                                mTabHost.setCurrentTabByTag(TAB_MAIN);
//                                handler.sendEmptyMessage(1);
//                                break;
//                            case R.id.home_tab_category:// 分类
//                                mTabHost.setCurrentTabByTag(TAB_CATEGORY);
//                                handler.sendEmptyMessage(2);
//                                break;
//                            case R.id.home_tab_periphery:// 周边商家
//                                mTabHost.setCurrentTabByTag(TAB_MESSAGE);
//                                handler.sendEmptyMessage(3);
//                                break;
//                            case R.id.home_tab_cart:// 购物车
//                                mTabHost.setCurrentTabByTag(TAB_CART);
//                                handler.sendEmptyMessage(4);
//                                break;
//                            case R.id.home_tab_personal:// 个人中心
//                                handler.sendEmptyMessage(5);
//                                if (State.UserKey != null) {
//                                    mTabHost.setCurrentTabByTag(TAB_PERSONAL);
//                                    // intent = new Intent(getApplicationContext(),
//                                    // PersonalActivity.class);
//                                    // intent.putExtra("login_tag1", "zidong");
//                                    // startActivity(intent);
//                                } else {
//                                    // CommonTools.showShortToast(getApplicationContext(),
//                                    // APPSingleton.getContext().getString(R.string.not_login_please_login).toString());
//                                    // SharedPreferences sp =
//                                    // getSharedPreferences("LoginActivity",
//                                    // MODE_PRIVATE);
//                                    // boolean boolean1 =
//                                    // sp.getBoolean("isautomatic", false);
//                                    // String psw = sp.getString("password", "");
//                                    // if (boolean1&&!psw.equals("")) {
//                                    // intent = new Intent(getApplicationContext(),
//                                    // PersonalActivity.class);
//                                    // intent.putExtra("login_tag1", "zidong");
//                                    // startActivity(intent);
//                                    // }else {
//                                    mainBtn.performClick();
//                                    intent = new Intent(getApplicationContext(),
//                                            LoginActivity.class);
//                                    intent.putExtra("login_tag", "home");
//                                    startActivity(intent);
//                                    // }
//                                }
//                                break;
//                            case R.id.home_tab_message:
//                                mTabHost.setCurrentTabByTag(TAB_MESSAGE);
//                                handler.sendEmptyMessage(6);
//                                break;
//                            default:
//                                break;
//                        }
                    }
                });

        netRun.getGPSInfo(application.province, application.city, application.district);
        requestPhotoPermiss();//权限
    }

    /**
     * 动态注册广播
     */
    private void registerReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(MAIN_);
        myIntentFilter.addAction(PERSONAL_);
        myIntentFilter.addAction(PERIPHERY_);
        myIntentFilter.addAction(CART_);
        myIntentFilter.addAction(CATEGORY_);
        registerReceiver(receiver, myIntentFilter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(CATEGORY_)) {
//				mTabHost.setCurrentTabByTag(TAB_CATEGORY);
                handler.sendEmptyMessage(5);
                personalBtn.performClick();
            }
            if (action.equals(PERSONAL_)) {
                handler.sendEmptyMessage(2);
//				mTabHost.setCurrentTabByTag(TAB_PERSONAL);
                categoryBtn.performClick();
            }
            if (action.equals(PERIPHERY_)) {
                handler.sendEmptyMessage(3);
//				mTabHost.setCurrentTabByTag(PERIPHERY_);
                messageBrn.performClick();
            }
            if (action.equals(CART_)) {
//				mTabHost.setCurrentTabByTag(TAB_CART);
                handler.sendEmptyMessage(4);
                cartBtn.performClick();
            }
            if (action.equals(MAIN_)) {
//				mTabHost.setCurrentTabByTag(TAB_MAIN);
//				handler.sendEmptyMessage(1);
//				mainBtn.setChecked(true);
                handler.sendEmptyMessage(1);
                mainBtn.performClick();
            }
        }
    };

    /**
     * 更新底部导航栏按钮背景
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mainBtn.setTextColor(getResources().getColor(R.color.red));
                    categoryBtn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    cartBtn.setTextColor(getResources().getColor(R.color.main_text));
                    personalBtn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    messageBrn.setTextColor(getResources().getColor(R.color.main_text));

                    break;
                case 2:
                    mainBtn.setTextColor(getResources().getColor(R.color.main_text));
                    categoryBtn.setTextColor(getResources().getColor(R.color.red));
                    cartBtn.setTextColor(getResources().getColor(R.color.main_text));
                    personalBtn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    messageBrn.setTextColor(getResources().getColor(R.color.main_text));

                    break;
                case 3:
                    mainBtn.setTextColor(getResources().getColor(R.color.main_text));
                    categoryBtn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    messageBrn.setTextColor(getResources().getColor(R.color.red));
                    cartBtn.setTextColor(getResources().getColor(R.color.main_text));
                    personalBtn.setTextColor(getResources().getColor(
                            R.color.main_text));

                    this.sendEmptyMessageAtTime(999, 2000);
                    break;
                case 4:
                    mainBtn.setTextColor(getResources().getColor(R.color.main_text));
                    categoryBtn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    messageBrn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    cartBtn.setTextColor(getResources().getColor(R.color.red));
                    personalBtn.setTextColor(getResources().getColor(
                            R.color.main_text));

                    break;
                case 5:
                    mainBtn.setTextColor(getResources().getColor(R.color.main_text));
                    categoryBtn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    messageBrn.setTextColor(getResources().getColor(
                            R.color.main_text));
                    cartBtn.setTextColor(getResources().getColor(R.color.main_text));
                    personalBtn.setTextColor(getResources().getColor(R.color.red));
                    break;
                case 999:
                    APPSingleton appSingleton = (APPSingleton) getApplication();
                    appSingleton.getLocation();
                    break;
            }
        }

        ;
    };

    /**
     * 含有标题、内容、两个按钮的对话框
     **/
    protected void showAlertDialog(String title, String message,
                                   String positiveText,
                                   DialogInterface.OnClickListener onPositiveClickListener,
                                   String negativeText,
                                   DialogInterface.OnClickListener onNegativeClickListener) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN
//                && event.getRepeatCount() == 0)) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
////                ActivityManager.getInstance().finishAllActivity();
//                AppManager.getInstance().AppExit(this);
//            }
//            return false;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, APPSingleton.getContext().getString(R.string.more)
                .toString());
        menu.add(0, 1, 0,
                APPSingleton.getContext().getString(R.string.push_diagnosis)
                        .toString());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, MoreActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, DiagnosisActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 权限
     */
    private void requestPhotoPermiss() {
        PermissionGen.with(HomeTabActivity.this).addRequestCode(JURISDICTION).permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_SETTINGS, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE).request();
    }

    @PermissionSuccess(requestCode = JURISDICTION)
    public void requestPhotoSuccess() {
        //成功之后的处理
//        Log.i("----权限获取成功", "requestPhotoSuccess " );
    }

    @PermissionFail(requestCode = JURISDICTION)
    public void requestPhotoFail() {
        //失败之后的处理，跳到设置界面
        goToSetting(HomeTabActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == JURISDICTION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("----PermissionsResult", " ACCESS_COARSE_LOCATION" + grantResults[i]);
                    Positioning();
                }
            }
        }
    }

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
                APPSingleton.province = location.getProvince();
                APPSingleton.city = location.getCity();
                APPSingleton.district = location.getDistrict();
//                Log.i("------------------","定位  省="+province+"  市="+city+"  区="+district);
                EventBus.getDefault().post(new EventBean(location.getCity()));
            }
        });
        locationClient.start();
    }

    /***
     * 去设置界面
     */
    public static void goToSetting(Context context) {
        //go to setting view
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", "com.aiteshangcheng.a", null);
        intent.setData(uri);
        context.startActivity(intent);
    }

}
