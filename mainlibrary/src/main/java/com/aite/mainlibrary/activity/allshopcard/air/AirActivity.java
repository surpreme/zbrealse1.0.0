package com.aite.mainlibrary.activity.allshopcard.air;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.AirMainListBean;
import com.aite.mainlibrary.Mainbean.HelpEatUIBean;
import com.aite.mainlibrary.Mainbean.TypeAirBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.booktimebankinformation.BookTimebankInformationActivity;
import com.aite.mainlibrary.activity.allshopcard.postairneed.PostAirNeedActivity;
import com.aite.mainlibrary.adapter.AirServiceRecyAdapter;
import com.aite.mainlibrary.adapter.RadioGroupRecyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AirActivity extends BaseActivity<AirContract.View, AirPresenter> implements AirContract.View, OnBannerListener {

    @BindView(R2.id.floatbutton)
    FloatingActionButton floatbutton;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.banner)
    Banner banner;
    @BindView(R2.id.father_tab_ll)
    LinearLayout fatherTabLl;
    @BindView(R2.id.all_ll)
    LinearLayout allLl;
    @BindView(R2.id.service_ll)
    LinearLayout serviceLl;
    @BindView(R2.id.time_ll)
    LinearLayout timeLl;
    private AirServiceRecyAdapter airServiceRecyAdapter;
    private List<AirMainListBean.ListBean> listBean = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    private TypeAirBean typeAirBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.help_doctor_servicer_layout;
    }

    @Override
    protected void initView() {
        initToolbar("喘息服务", getResources().getColor(R.color.white));
        //初始化banner
        initBanner(banner);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        airServiceRecyAdapter = new AirServiceRecyAdapter(context, listBean);
        recyclerView.setAdapter(airServiceRecyAdapter);
        airServiceRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                Intent intent = new Intent(context, BookTimebankInformationActivity.class);
                intent.putExtra("TYPEID", listBean.get(postion).getId());
                intent.putExtra("activity", "AirActivity");
                startActivity(intent);
//                startActivity(BookTimebankInformationActivity.class,"TYPEID",listBean.get(postion).getId());


            }
        });

    }


    @Override
    protected void initDatas() {
        mPresenter.getTypeDataList(initParams());
        mPresenter.getDataList(initParams());
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    @Override
    protected void initResume() {


    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onDataListSuccess(Object msg) {
        if (!listBean.isEmpty())
            listBean.clear();
        listBean.addAll(((AirMainListBean) msg).getList());
        airServiceRecyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTypeDatasucess(Object msg) {
        typeAirBean = ((TypeAirBean) msg);
        if (typeAirBean.getAdv_list() != null || typeAirBean.getAdv_list().size() > 0) {
            for (TypeAirBean.AdvListBean advListBean : typeAirBean.getAdv_list()) {
                list_img.add(advListBean.getAdv_content().getAdv_pic());
                list_title.add(advListBean.getAdv_title());
                LogUtils.d(advListBean.getAdv_title());

            }
            banner.setImages(list_img);
            banner.setBannerTitles(list_title);
            banner.startAutoPlay();
            banner.start();
        }
        typeAirBean.getClass_list().get(0).setChecked(true);
        typeAirBean.getArea_list().get(0).setChecked(true);
        typeAirBean.getTime_array().get(0).setChecked(true);
    }

    @OnClick({R2.id.floatbutton, R2.id.all_ll, R2.id.time_ll, R2.id.service_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatbutton) {
            startActivity(PostAirNeedActivity.class);
        }
        if (v.getId() == R.id.service_ll) {
            if (typeAirBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, typeAirBean.getClass_list());
            showChoicePop(radioGroupRecyAdapter);
        }
        if (v.getId() == R.id.all_ll) {
            if (typeAirBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, typeAirBean.getArea_list());
            showChoicePop(radioGroupRecyAdapter);
        }
        if (v.getId() == R.id.time_ll) {
            if (typeAirBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, typeAirBean.getTime_array());
            showChoicePop(radioGroupRecyAdapter);

        }
    }

    private void showChoicePop(RadioGroupRecyAdapter radioGroupRecyAdapter) {
        //初始化选择器
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        radioGroupRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                //for循环不能放在这里 会卡顿 放到适配器中
                LogUtils.d(postion);
            }
        });
        PopwindowUtils.getmInstance().showRecyPopupWindow(context, radioGroupRecyAdapter, manager, fatherTabLl);
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
