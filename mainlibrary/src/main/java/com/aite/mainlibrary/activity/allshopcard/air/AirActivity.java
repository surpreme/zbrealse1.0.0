package com.aite.mainlibrary.activity.allshopcard.air;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aite.mainlibrary.Mainbean.AirMainListBean;
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
    @BindView(R2.id.all_iv)
    ImageView allIv;
    @BindView(R2.id.service_iv)
    ImageView serviceIv;
    @BindView(R2.id.time_iv)
    ImageView timeIv;
    private AirServiceRecyAdapter airServiceRecyAdapter;
    private List<AirMainListBean.ListBean> airMainListBean = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    private TypeAirBean typeAirBean;
    private String CLASS_ID = "0";
    private String AREA_ID = "0";
    private String TIME_ID = "0";

    @Override
    protected int getLayoutResId() {
        return R.layout.help_doctor_servicer_layout;
    }

    @Override
    protected void initView() {
        initToolbar("喘息服务", getResources().getColor(R.color.white));
        initMoreRecy();
        //初始化banner
        initBanner(banner);
        //smartlayout
        initSmartLayout(true);
        //初始化加载
        initLoadingAnima();
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mBaserecyclerView.setLayoutManager(linearLayoutManager);
        airServiceRecyAdapter = new AirServiceRecyAdapter(context, airMainListBean);
        mBaserecyclerView.setAdapter(airServiceRecyAdapter);
        airServiceRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                Intent intent = new Intent(context, BookTimebankInformationActivity.class);
                intent.putExtra("TYPEID", airMainListBean.get(postion).getId());
                intent.putExtra("activity", "AirActivity");
                startActivity(intent);
//                startActivity(BookTimebankInformationActivity.class,"TYPEID",airMainListBean.get(postion).getId());


            }
        });

    }


    @Override
    protected void initDatas() {
        mPresenter.getTypeDataList(initTypeParams());
        mPresenter.getDataList(initParams());
    }

    @Override
    protected void onSmartLoadMore() {
        super.onSmartLoadMore();
        mPresenter.getDataList(initParams());

    }

    @Override
    protected void onSmartRefresh() {
        super.onSmartRefresh();
        if (airMainListBean != null) {
            airMainListBean.clear();
            airServiceRecyAdapter.notifyDataSetChanged();
        }

        mPresenter.getDataList(initParams());

    }

    private HttpParams initTypeParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("curpage", mCurrentPage);
        httpParams.put("class_id", CLASS_ID);
        httpParams.put("area_id", AREA_ID);
        httpParams.put("time_id", TIME_ID);
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
        if (((AirMainListBean) msg).getList().isEmpty()) {
            initNodata();
        } else {
            stopLoadingAnim();
            showMoreRecy();
            stopNoData();
            airMainListBean.addAll(((AirMainListBean) msg).getList());
            airServiceRecyAdapter.notifyDataSetChanged();
            hasMore = ((AirMainListBean) msg).getIs_nextpage() > 0;
        }
//        if (!airMainListBean.isEmpty())
//            airMainListBean.clear();
//        airMainListBean.addAll(((AirMainListBean) msg).getList());
//        airServiceRecyAdapter.notifyDataSetChanged();
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
            serviceIv.setImageDrawable(getResources().getDrawable(R.drawable.top));
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, typeAirBean.getClass_list());
            showChoicePop(radioGroupRecyAdapter, "CLASS_ID");
        }
        if (v.getId() == R.id.all_ll) {
            if (typeAirBean == null) return;
            allIv.setImageDrawable(getResources().getDrawable(R.drawable.top));
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, typeAirBean.getArea_list());
            showChoicePop(radioGroupRecyAdapter, "AREA_ID");
        }
        if (v.getId() == R.id.time_ll) {
            if (typeAirBean == null) return;
            timeIv.setImageDrawable(getResources().getDrawable(R.drawable.top));
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, typeAirBean.getTime_array());
            showChoicePop(radioGroupRecyAdapter, "TIME_ID");

        }
    }

    private void showChoicePop(RadioGroupRecyAdapter radioGroupRecyAdapter, String type) {
        //初始化选择器
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        radioGroupRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                //for循环不能放在这里 会卡顿 放到适配器中
                LogUtils.d(postion);
                switch (type.toString()) {
                    case "CLASS_ID":
                        CLASS_ID = String.valueOf(postion);

                        break;
                    case "AREA_ID":
                        AREA_ID = String.valueOf(postion);

                        break;
                    case "TIME_ID":
                        TIME_ID = String.valueOf(postion);

                        break;
                    default:
                        break;

                }
                airMainListBean.clear();
                radioGroupRecyAdapter.notifyDataSetChanged();
                mPresenter.getDataList(initParams());
                PopwindowUtils.getmInstance().dismissPopWindow();
            }
        });
        PopwindowUtils.getmInstance().showRecyPopupWindow(context, radioGroupRecyAdapter, manager, fatherTabLl, new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                resetChoiceIv();

            }
        });
    }

    private void resetChoiceIv() {
        serviceIv.setImageDrawable(getResources().getDrawable(R.drawable.low));
        allIv.setImageDrawable(getResources().getDrawable(R.drawable.low));
        timeIv.setImageDrawable(getResources().getDrawable(R.drawable.low));

    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
