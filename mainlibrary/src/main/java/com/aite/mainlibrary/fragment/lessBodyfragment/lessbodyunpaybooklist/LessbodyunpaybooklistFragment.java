package com.aite.mainlibrary.fragment.lessBodyfragment.lessbodyunpaybooklist;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aite.mainlibrary.Mainbean.BookLessBodyFamilyBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.adapter.MineLessBodybookRecyAdapter;
import com.blankj.rxbus.RxBus;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseFragment;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.MVPBaseFragment;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LessbodyunpaybooklistFragment extends BaseFragment<LessbodyunpaybooklistContract.View, LessbodyunpaybooklistPresenter> implements LessbodyunpaybooklistContract.View {
    private MineLessBodybookRecyAdapter mineLessBodybookRecyAdapter;
    private List<BookLessBodyFamilyBean.OrderListBean> orderListBeans = new ArrayList<>();
    private String PAGE_TYPE = "1";


    @Override
    protected void initModel() {
        mPresenter.getinformation(initParams());


    }

    @Override
    protected void initViews() {
        PAGE_TYPE = getArguments().getString("page_type");
        initMoreRecy();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mBaserecyclerView.setLayoutManager(linearLayoutManager);
        mineLessBodybookRecyAdapter = new MineLessBodybookRecyAdapter(context, orderListBeans);
        mBaserecyclerView.setAdapter(mineLessBodybookRecyAdapter);
        //smartlayout
        initSmartLayout(true);
        //初始化加载
        initLoadingAnima();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.smartlayout_recy_layout;
    }

    @Override
    protected void onSmartLoadMore() {
        super.onSmartLoadMore();
        mPresenter.getinformation(initParams());

    }

    @Override
    protected void onSmartRefresh() {
        super.onSmartRefresh();
        if (orderListBeans != null) {
            orderListBeans.clear();
            mineLessBodybookRecyAdapter.notifyDataSetChanged();
        }

        mPresenter.getinformation(initParams());

    }

    /**
     * key	get	字符串	必须			会员登录key
     * curpage	get	字符串	必须	1		当前页码
     * state	get	整型	必须	0		状态 0全部 1待付款 2已付款 3已完成 4评价 5已取消
     * page_type	get	整型	必须	1		页面类型 1日托 2培训 3就业 4助残活动 5其他服务
     *
     * @return
     */
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("curpage", mCurrentPage);
        httpParams.put("state", 1);
        httpParams.put("page_type", PAGE_TYPE);
        return httpParams;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetinformationSuccess(Object msg) {
        if (((BookLessBodyFamilyBean) msg).getOrder_list().isEmpty()) {
            initNodata();
        } else {
            stopLoadingAnim();
            showMoreRecy();
            stopNoData();
            orderListBeans.addAll(((BookLessBodyFamilyBean) msg).getOrder_list());
            mineLessBodybookRecyAdapter.notifyDataSetChanged();
            hasMore = ((BookLessBodyFamilyBean) msg).getIs_nextpage() > 0;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
