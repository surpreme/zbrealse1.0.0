package com.lzy.basemodule.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lzy.basemodule.R;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BasePresenterImpl;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.basemodule.view.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V extends BaseView,T extends BasePresenterImpl<V>>  extends mBaseActivity<V, T> implements View.OnClickListener {
    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initDatas();

    protected abstract void initResume();

    protected abstract void initReStart();


    private Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        try{
            //mvp
            mPresenter= getInstance(this,1);
            mPresenter.attachView((V) this);
        }catch (Exception e){
            LogUtils.e(e);
        }
        context = this;
        unbinder = ButterKnife.bind((Activity) context);
        AppManager.getInstance().addActivity((Activity) context);
        StatusBarUtils.setColor(context, getResources().getColor(R.color.white));
        initView();
        initDatas();

    }

    protected void killThisActvity() {
        AppManager.getInstance().killActivity((Activity) context);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter!=null)
            mPresenter.detachView();
    }

    @Override
    public void onClick(View v) {

    }

}
