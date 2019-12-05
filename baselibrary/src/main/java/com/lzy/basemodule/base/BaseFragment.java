package com.lzy.basemodule.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.R;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.BasePresenterImpl;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.basemodule.util.SystemUtil;
import com.lzy.basemodule.util.toast.ToastTopUtils;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends mBaseFragment<V, T> implements View.OnClickListener, BaseView {

    protected abstract void initModel();

    protected abstract void initViews();

    protected abstract int getLayoutResId();

    private SmartRefreshLayout smartRefreshLayout;

    protected float screenwidth = 0;
    protected boolean hasMore = false;


    protected RecyclerView mBaserecyclerView;

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

        LogUtils.d(this.getClass() + "       onViewCreated");
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

    protected void stopNoData() {
        nodata_lottieAnimationView.setVisibility(View.GONE);
    }

    protected void initMoreRecy() {
        mBaserecyclerView = mview.findViewById(R.id.recycler_view);
    }


    protected void showMoreRecy() {
        mBaserecyclerView.setVisibility(View.VISIBLE);
    }

    protected void initNodata() {
        stopLoadingAnim();
        if (nodata_lottieAnimationView != null) {
            nodata_lottieAnimationView.setVisibility(View.VISIBLE);
            nodata_lottieAnimationView.playAnimation();
        }
    }


    //初始化动画
    protected void initLoadingAnima() {
        lottieAnimationView = mview.findViewById(R.id.lottieAnimationView);
        nodata_lottieAnimationView = mview.findViewById(R.id.nodata_lottieAnimationView);
        lottieAnimationView.playAnimation();   //播放
        smartRefreshLayout.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);
        mBaserecyclerView.setVisibility(View.GONE);

        nodata_lottieAnimationView.setVisibility(View.GONE);
        if (!SystemUtil.isNetworkConnected()) {
            stopLoadingAnim();
            initNodata();
            ToastTopUtils toastTopUtils = new ToastTopUtils();
            toastTopUtils.Short(context, "请检查网络").setGravity(Gravity.TOP).show();
        }

    }

    protected void stopLoadingAnim() {
        if (lottieAnimationView != null) {
            lottieAnimationView.cancelAnimation();  //取消
            lottieAnimationView.setVisibility(View.GONE);
        }
        smartRefreshLayout.setVisibility(View.VISIBLE);


    }

    /**
     * 初始化刷新控件
     * 是否可以上拉加载
     *
     * @param isRefresh
     */
    public void initSmartLayout(boolean isRefresh) {
        try {
            smartRefreshLayout = mview.findViewById(R.id.smartlayout);
            smartRefreshLayout.setEnableAutoLoadMore(isRefresh);
            smartRefreshLayout.setRefreshHeader(new WaterDropHeader(context));
            mCurrentPage = 1;
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    mCurrentPage = 1;
                    onSmartRefresh();
                    smartRefreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败

                }
            });
            smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    LogUtils.d("mCurrentPage" + mCurrentPage);
                    if (hasMore) {
                        mCurrentPage++;
                        smartRefreshLayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                        onSmartLoadMore();
                    } else {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();

                    }

                }
            });
        } catch (Exception e) {
            LogUtils.e("initSmartLayout-fail" + e + e.getClass());
        }

    }


    @Override
    protected void onSmartRefresh() {

    }

    @Override
    protected void onSmartLoadMore() {

    }

    @Override
    public void showError(String msg) {

    }
}
