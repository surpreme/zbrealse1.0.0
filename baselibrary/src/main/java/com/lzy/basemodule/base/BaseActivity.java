package com.lzy.basemodule.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.R;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.BasePresenterImpl;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.basemodule.util.SystemUtil;
import com.lzy.basemodule.util.toast.ToastUtils;
import com.lzy.basemodule.util.toast.ToastTopUtils;
import com.lzy.basemodule.view.StatusBarUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-23
 * @desc:
 */

public abstract class BaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends mBaseActivity<V, T> implements View.OnClickListener, BaseView {
    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initDatas();

    protected abstract void initResume();

    protected abstract void initReStart();


    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    private Bundle savedInstanceState;


    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
//        if (isCanSavedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty())
            this.savedInstanceState = savedInstanceState;
        try {
            if (isUseMvp()) {
                //mvp
                mPresenter = getInstance(this, 1);
                mPresenter.attachView((V) this);
            }
        } catch (Exception e) {
            LogUtils.e("mvp（可能未使用mvp格式）" + "/n" + e.getClass() + e);
        }
        context = this;
        unbinder = ButterKnife.bind((Activity) context);
        AppManager.getInstance().addActivity((Activity) context);
//        StatusBarUtils.setTransparent(context);
        StatusBarUtils.setColor(context, getResources().getColor(R.color.white));
        initView();
        initDatas();

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected boolean isUseMvp() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initResume();
        if (!SystemUtil.isNetworkConnected()) {
//            ToastUtils.showToast(context, "请检查网络设置");
            LogUtils.d("当前无网络");
        }


    }

    public void initBottomBtn(String txt, View.OnClickListener listener) {
        try {
            Button bottom_btn = this.findViewById(R.id.bottom_btn);
            bottom_btn.setOnClickListener(this);
            bottom_btn.setText(txt);
            bottom_btn.setOnClickListener(listener);
        } catch (Exception e) {
            LogUtils.e("initBottomBtn-fail" + e);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        applypermission();
    }

    @Override
    protected void onSmartLoadMore() {


    }

    protected boolean isFileHas(File file) {
        return file.exists();
    }

    @Override
    protected void onSmartRefresh() {
        if (!SystemUtil.isNetworkConnected())
            initNodata();

    }

    protected String getUrlKey(String url, String key) {
        return Uri.parse(url).getQueryParameter(key);
    }

    protected int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    protected int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    protected void showTopToasts(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastTopUtils toastUtil = new ToastTopUtils();
                toastUtil.Short(context, msg).setGravity(Gravity.TOP).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();


            }
        });
    }

    protected void showToast(final String msg, final int gravity) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastTopUtils toastUtil = new ToastTopUtils();
                toastUtil.Short(context, msg).setGravity(gravity).show();


            }
        });
    }

    protected void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(context, msg);

            }
        });
    }

    protected String getBaseUrl() {
        return getResources().getString(R.string.baseUrl);
    }

    protected void killThisActvity() {
        AppManager.getInstance().killActivity((Activity) context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {
        PopwindowUtils.getmInstance().showloaddingPopupWindow(this);
    }

    @Override
    public void dimissLoading() {
        PopwindowUtils.getmInstance().dismissPopWindow();

    }

    @Override
    public void showError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(context, msg);
                LogUtils.e("服务器返回错误信息-----------" + msg);
            }
        });

    }
}
