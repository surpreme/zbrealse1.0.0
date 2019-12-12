package com.aite.mainlibrary.activity.allshopcard.numbershop;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.aite.mainlibrary.Mainbean.TimeShoplistBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.adapter.TimeShopRecyAdapter;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NumberShopActivity extends BaseActivity<NumberShopContract.View, NumberShopPresenter> implements NumberShopContract.View {
    private TimeShopRecyAdapter timeShopRecyAdapter;

    private List<TimeShoplistBean.ListBean> timeShoplistbean = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.recy_toolbar;
    }

    @Override
    protected void initView() {
        initToolbar("积分商城");
        timeShopRecyAdapter = new TimeShopRecyAdapter(context, timeShoplistbean);
        initRecy();
        mBaserecyclerView.setAdapter(timeShopRecyAdapter);
        mBaserecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        timeShopRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {

            }
        });
    }

    @Override
    protected void initDatas() {
        mPresenter.GetShopList(initKeyParams());

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetShopListSuccess(Object msg) {
        timeShoplistbean.addAll(((TimeShoplistBean) msg).getList());
        timeShopRecyAdapter.notifyDataSetChanged();

    }
}
