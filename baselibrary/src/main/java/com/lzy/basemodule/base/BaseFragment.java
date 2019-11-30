package com.lzy.basemodule.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.BasePresenterImpl;
import com.lzy.basemodule.mvp.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends mBaseFragment<V, T> implements View.OnClickListener, BaseView {

    protected abstract void initModel();

    protected abstract void initViews();

    protected abstract int getLayoutResId();

    protected float screenwidth = 0;

    private Unbinder unbinder;

    public View getMview() {
        return mview;
    }

    private View mview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mview = view;
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mPresenter = getInstance(this, 1);
            mPresenter.attachView((V) this);
        } catch (Exception e) {
            LogUtils.e("mvp错误" + e);
        }
        if (screenwidth == 0) {
            screenwidth = context.getResources().getDisplayMetrics().widthPixels;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initModel();

    }

    public float getScreenwidth() {
        return screenwidth;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void showLoading() {
        PopwindowUtils.getmInstance().showloaddingPopupWindow(getActivity());
    }

    @Override
    public void dimissLoading() {
        PopwindowUtils.getmInstance().dismissPopWindow();


    }

    @Override
    public void showError(String msg) {

    }
}
