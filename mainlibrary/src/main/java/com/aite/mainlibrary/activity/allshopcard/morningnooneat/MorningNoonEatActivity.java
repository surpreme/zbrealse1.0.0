package com.aite.mainlibrary.activity.allshopcard.morningnooneat;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.MorningNoonEatBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.remembershopbook.RememberShopBookActivity;
import com.aite.mainlibrary.adapter.MorningNoonEatsRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MorningNoonEatActivity extends BaseActivity<MorningNoonEatContract.View, MorningNoonEatPresenter> implements MorningNoonEatContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.smartlayout)
    SmartRefreshLayout smartlayout;
    private MorningNoonEatsRecyAdapter morningNoonEatRecyAdapter;
    private List<MorningNoonEatBean.GoodsListBean> goodsListBeanList = new ArrayList<>();
    private String moniningnoontyye;

    @Override
    protected int getLayoutResId() {
        return R.layout.toolbar_recy;
    }

    @Override
    protected void initView() {
        moniningnoontyye = getIntent().getStringExtra("type");
        assert moniningnoontyye != null;
        initToolbar(moniningnoontyye.equals("morning") ? "早餐" : "午餐");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        morningNoonEatRecyAdapter = new MorningNoonEatsRecyAdapter(context, goodsListBeanList);
        recyclerView.setAdapter(morningNoonEatRecyAdapter);
        smartlayout.setEnableAutoLoadMore(false);
        morningNoonEatRecyAdapter.setLstenerInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                startActivity(RememberShopBookActivity.class,"goods_id",goodsListBeanList.get(postion).getGoods_id());
            }
        });

    }

    @Override
    protected void initDatas() {
        mPresenter.getEatDataList(initParams(), moniningnoontyye.equals("morning") ? "1" : "2");

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        params.put("key", AppConstant.KEY);
        if (moniningnoontyye != null)
            params.put("page_type", moniningnoontyye.equals("morning") ? 1 : 2);
        params.put("curpage", 1);
        return params;
    }

    @Override
    public void getDataListSuccess(Object msg) {
        if (((MorningNoonEatBean) msg).getGoods_list() != null) {
            goodsListBeanList.addAll(((MorningNoonEatBean) msg).getGoods_list());
            morningNoonEatRecyAdapter.notifyDataSetChanged();
        }


    }
}
