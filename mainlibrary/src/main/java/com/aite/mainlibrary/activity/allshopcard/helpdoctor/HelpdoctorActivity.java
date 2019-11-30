package com.aite.mainlibrary.activity.allshopcard.helpdoctor;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.ElseHelpDoctorBean;
import com.aite.mainlibrary.Mainbean.HelpDoctorListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.bookhelpdoctorinformation.BookHelpDoctorInformationActivity;
import com.aite.mainlibrary.activity.allshopcard.helpdoctorinformation.HelpDoctorInformationActivity;
import com.aite.mainlibrary.activity.allshopcard.posthelpdoctor.PostHelpDoctorActivity;
import com.aite.mainlibrary.adapter.HelpDoctorRecyAdapter;
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

public class HelpdoctorActivity extends BaseActivity<HelpdoctorContract.View, HelpdoctorPresenter> implements HelpdoctorContract.View, OnBannerListener {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
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
    private HelpDoctorRecyAdapter helpDoctorRecyAdapter;
    private List<HelpDoctorListBean.ListBean> listBean = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    private ElseHelpDoctorBean elseHelpDoctorBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.help_doctor_servicer_layout;
    }

    @Override
    protected void initView() {
        initToolbar("助医服务", getResources().getColor(R.color.white));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        helpDoctorRecyAdapter = new HelpDoctorRecyAdapter(context, listBean);
        recyclerView.setAdapter(helpDoctorRecyAdapter);
        //banner
        initBanner(banner);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);
        helpDoctorRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
//                startActivity(HelpDoctorInformationActivity.class, "TYPEID", listBean.get(postion).getId());
                startActivity(BookHelpDoctorInformationActivity.class, "TYPEID", listBean.get(postion).getId());

            }
        });
    }

    @OnClick({R2.id.floatbutton, R2.id.all_ll, R2.id.time_ll, R2.id.service_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatbutton) {
            startActivity(PostHelpDoctorActivity.class);
        }
        if (v.getId() == R.id.service_ll) {
            if (elseHelpDoctorBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseHelpDoctorBean.getClass_list());
            showChoicePop(radioGroupRecyAdapter);
        }
        if (v.getId() == R.id.all_ll) {
            if (elseHelpDoctorBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseHelpDoctorBean.getArea_list());
            showChoicePop(radioGroupRecyAdapter);
        }
        if (v.getId() == R.id.time_ll) {
            if (elseHelpDoctorBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseHelpDoctorBean.getTime_array());
            showChoicePop(radioGroupRecyAdapter);

        }
//        if (v.getId() == R.id.father_tab_ll) {
//            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseHelpDoctorBean.getTime_array());
//            showChoicePop(radioGroupRecyAdapter);
//        }
    }

    @Override
    protected void initDatas() {
        mPresenter.getList(initParams());
        mPresenter.getTypeData(initParams());


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
    public void onGetListSuccess(Object msg) {
        listBean.addAll(((HelpDoctorListBean) msg).getList());
        helpDoctorRecyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetTypeDataSuccess(Object msg) {
//        ((ElseHelpDoctorBean) msg).getAdv_list();

        elseHelpDoctorBean = ((ElseHelpDoctorBean) msg);
        //banner
        if (elseHelpDoctorBean.getAdv_list() != null || elseHelpDoctorBean.getAdv_list().size() > 0) {
            for (ElseHelpDoctorBean.AdvListBean advListBean : elseHelpDoctorBean.getAdv_list()) {
                list_img.add(advListBean.getAdv_content().getAdv_pic());
                list_title.add(advListBean.getAdv_title());
                LogUtils.d(advListBean.getAdv_title());

            }
            banner.setImages(list_img);
            banner.setBannerTitles(list_title);
            banner.startAutoPlay();
            banner.start();
        }
        elseHelpDoctorBean.getClass_list().get(0).setChecked(true);
        elseHelpDoctorBean.getArea_list().get(0).setChecked(true);
        elseHelpDoctorBean.getTime_array().get(0).setChecked(true);
    }


    @Override
    public void OnBannerClick(int position) {

    }
}
