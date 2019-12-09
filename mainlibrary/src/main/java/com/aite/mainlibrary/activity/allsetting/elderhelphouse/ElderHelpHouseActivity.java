package com.aite.mainlibrary.activity.allsetting.elderhelphouse;


import android.os.Bundle;
import android.view.Gravity;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.HelpElderHouseListBean;
import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.HelpElderHouseRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.view.SearchEditText;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ElderHelpHouseActivity extends BaseActivity<ElderHelpHouseContract.View, ElderHelpHousePresenter> implements ElderHelpHouseContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    //    @BindView(R2.id.search_view)
//    SearchEditText searchView;
    private HelpElderHouseRecyAdapter helpElderHouseRecyAdapter;
    private List<HelpElderHouseListBean.ListBean> helpelderlistbean = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.recy_toolbar_seacher;
    }

    @Override
    protected void initView() {
        initToolbar("关联养老院");
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(helpElderHouseRecyAdapter = new HelpElderHouseRecyAdapter(context, helpelderlistbean));
        helpElderHouseRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                mPresenter.changeBinderHelpEdlerHouse(
                        initBindingParams(helpelderlistbean.get(postion).getStore_id()),
                        !helpelderlistbean.get(postion).getIs_binding().equals("1") ?
                                AppConstant.BINDHELPELDERHOUSEURL : AppConstant.UNBINDHELPELDERHOUSEURL);

            }
        });

    }

    @Override
    protected void initDatas() {
        mPresenter.getHelpEdlerHouseInformation(initParams());

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    private HttpParams initBindingParams(String STORE_ID) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("store_id", STORE_ID);
        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetHelpEdlerHouseInformationSuccess(Object mag) {
        helpelderlistbean.addAll(((HelpElderHouseListBean) mag).getList());
        helpElderHouseRecyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChangeBinderHelpEdlerHouseSuccess(Object mag) {
        if (((TwoSuccessCodeBean) mag).getMsg().equals("解绑成功") || ((TwoSuccessCodeBean) mag).getMsg().equals("绑定成功")) {
            showToast(((TwoSuccessCodeBean) mag).getMsg(), Gravity.TOP);
            onBackPressed();
        } else {
            showToast("请稍后再试", Gravity.TOP);
            onBackPressed();
        }


    }
}
