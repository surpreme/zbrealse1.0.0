package com.lzy.basemodule.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.R;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.BasePresenterImpl;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.basemodule.util.SystemUtil;
import com.lzy.basemodule.util.TimeUtils;
import com.lzy.basemodule.util.toast.ToastUtils;
import com.lzy.basemodule.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

public class mBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView, BGASwipeBackHelper.Delegate {
    protected Context context;
    public T mPresenter;
    protected BGASwipeBackHelper mSwipeBackHelper;
    protected TimePickerView pvTime;
    protected OptionsPickerView pvOptions;

    //  省
    protected List<String> options1Items = new ArrayList<>();
    protected List<String> city;
    //  市
    protected List<List<String>> options2Items = new ArrayList<>();
    //  区
    protected List<List<List<String>>> options3Items = new ArrayList<>();
    //  省地理
    protected List<String> options1Itemsnumber = new ArrayList<>();
    protected List<String> citynumber;
    protected List<List<String>> area;
    protected List<List<String>> areanumber;
    protected List<String> chirendenarea;
    protected List<String> chirendenareanumber;
    //  市地理
    protected List<List<String>> options2Itemsnumber = new ArrayList<>();
    //  区地理
    protected List<List<List<String>>> options3Itemsnumber = new ArrayList<>();
    public static final String PERMISSION_RECORD_AUDIO = android.Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = android.Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = android.Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = android.Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO, PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE, PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA, PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_COARSE_LOCATION, PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,};

    /**
     * 6.0权限出错 数组越界
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //已授权
            } else {
                //拒绝授权
            }
        }

    }

    protected OptionsPickerView initChoiceArea(OnOptionsSelectListener onOptionsSelectListener, OnOptionsSelectChangeListener onOptionsSelectChangeListener) {
        pvOptions = new OptionsPickerBuilder(this, onOptionsSelectListener)
                .setOptionsSelectChangeListener(onOptionsSelectChangeListener)
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(0xFFF9731E)//标题文字颜色
                .setSubmitColor(0xFFF9731E)//确定按钮文字颜色
                .setCancelColor(0xFFF9731E)//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
//                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
        return pvOptions;
    }

    protected TimePickerView initNoTitleChoiceTimer(OnTimeSelectListener listener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2019, 10, 21);
        endDate.set(2050, 11, 31);
        pvTime = new TimePickerBuilder(this, listener)
                .setType(new boolean[]{true, true, true, true, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(0xFFF9731E)//标题文字颜色
                .setSubmitColor(0xFFF9731E)//确定按钮文字颜色
                .setCancelColor(0xFFF9731E)//取消按钮文字颜色
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .build();
        return pvTime;

    }

    protected TimePickerView initChoiceTimer(OnTimeSelectListener listener, String title,boolean isHM) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2019, 10, 21);
        endDate.set(2050, 11, 31);
        pvTime = new TimePickerBuilder(this, listener)
                .setType(new boolean[]{true, true, true, isHM, isHM, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(0xFFF9731E)//标题文字颜色
                .setSubmitColor(0xFFF9731E)//确定按钮文字颜色
                .setCancelColor(0xFFF9731E)//取消按钮文字颜色
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTitleText(title)
//                .isDialog(true)//是否显示为对话框样式
                .build();
        return pvTime;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);

    }

    //初始化banner
    protected void initBanner(Banner banner) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);
        banner.isAutoPlay(true);

    }

    //初始化侧滑返回
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    public void initToolbar(String title) {
        try {
            ImageView backImg = this.findViewById(R.id.iv_back);
            backImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            TextView titleTv = this.findViewById(R.id.tv_title);
            titleTv.setText(title);
        } catch (Exception e) {
            LogUtils.e("initToolbar-fail" + e);
        }

    }

    /**
     * @param title
     * @param color Color.WHITE
     */
    public void initToolbar(String title, int color) {
        try {
            ImageView backImg = this.findViewById(R.id.iv_back);
            backImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            TextView titleTv = this.findViewById(R.id.tv_title);
            titleTv.setTextColor(color);
            titleTv.setText(title);
        } catch (Exception e) {
            LogUtils.e("initToolbar-fail" + e);
        }

    }

    protected String getEditString(EditText editText) {
        if (isEditTextEmpty(editText)) {
            ToastUtils.showToast(context, "请检查输入的信息");
            return "";
        }
        try {
            return editText.getText().toString().trim();
        } catch (Exception e) {
            LogUtils.e(e);
            ToastUtils.showToast(context, "请检查输入的信息");
        }
        return null;
    }

    protected boolean isStringEmpty(String s) {
        if (s == null ||
                s.length() == 0 ||
                s.toString().equals("")) {
            return true;
        } else return false;
    }

    protected boolean isEditTextEmpty(EditText editText) {
        if (editText.getText().toString().trim() == null ||
                editText.getText().toString().length() == 0 ||
                editText.getText().toString().trim().equals("")) {
            return true;
        } else return false;
    }

    /**
     * 转换字符为小数后两位
     * 格式化，区小数后两位
     */
    protected String haveTwoDouble(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    protected boolean isEditTextEmpty(TextInputEditText editText) {
        if (editText.getText().toString().trim() == null ||
                editText.getText().toString().length() == 0 ||
                editText.getText().toString().trim().equals("")) {
            return true;
        } else return false;
    }

    protected String getEditString(TextInputEditText editText) {
        if (isEditTextEmpty(editText)) {
            ToastUtils.showToast(context, "请检查输入的信息");
            return "";
        }
        try {
            return editText.getText().toString().trim();
        } catch (Exception e) {
            LogUtils.d(e);
            ToastUtils.showToast(context, "请检查输入的信息");

        }
        return null;
    }

    protected void openImg(Activity activity, int choiceimgNumber, int resultcode) {
        Matisse.from(activity)
                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(choiceimgNumber) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .theme(R.style.Matisse_Dracula)
                .forResult(resultcode); // 设置作为标记的请求码
    }


    public void applypermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Arrays.toString(requestPermissions));
            if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
                ActivityCompat.requestPermissions((Activity) context, requestPermissions, 1);
            }
        }
    }

    /**
     * mvp
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz, String tag, String extra) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(tag, extra);
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dimissLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();

    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }
}
