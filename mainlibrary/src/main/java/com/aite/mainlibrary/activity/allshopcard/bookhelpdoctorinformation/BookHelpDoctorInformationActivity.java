package com.aite.mainlibrary.activity.allshopcard.bookhelpdoctorinformation;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.HelpDoctorInformationBean;
import com.aite.mainlibrary.Mainbean.StateCodeBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.hekpstart.HekpStartActivity;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.TimeUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BookHelpDoctorInformationActivity extends BaseActivity<BookHelpDoctorInformationContract.View, BookHelpDoctorInformationPresenter> implements BookHelpDoctorInformationContract.View {

    @BindView(R2.id.top_iv)
    ImageView topIv;
    @BindView(R2.id.information_tv)
    TextView informationTv;
    @BindView(R2.id.service_time_tv)
    TextView serviceTimeTv;
    @BindView(R2.id.address_tv)
    TextView addressTv;
    @BindView(R2.id.bottom_btn)
    Button bottomBtn;
    @BindView(R2.id.type_tv)
    TextView typeTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.bookhelpdoctor_infomation;
    }

    @Override
    protected void initView() {
        initToolbar("服务详情");
        initBottomBtn("开始服务", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.StartService(initParams());

            }
        });

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("id", getIntent().getStringExtra("TYPEID"));
        return httpParams;
    }

    @Override
    protected void initDatas() {
        mPresenter.getInformation(initParams());


    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onGetInformationSuccess(Object msg) {
        HelpDoctorInformationBean.InfoBean infoBean = ((HelpDoctorInformationBean) msg).getInfo();
        addressTv.setText(String.format("地点：%s", infoBean.getAddress()));
        serviceTimeTv.setText(String.format("服务时间：%s", TimeUtils.stampToDatemm2(Long.valueOf(infoBean.getAddtime()))));
        Glide.with(context).load(((HelpDoctorInformationBean) msg).getAdvs().getAdv_img()).into(topIv);
        informationTv.setText(String.format("详情%s", infoBean.getRemarks()));
        typeTv.setText(String.format("服务类型：%s", infoBean.getClass_name()));
//        是否可以接单 1是 0否
        LogUtils.d(infoBean.getIs_order());
        bottomBtn.setEnabled(infoBean.getIs_order() == 1);
        if (infoBean.getIs_order() == 0) {
            bottomBtn.setText("已被接单");
            bottomBtn.setAlpha(0.5f);
        }


    }

    @Override
    public void onStartServiceSuccess(Object msg) {
        if (((StateCodeBean) msg).getResult().equals("1")) {
            startActivity(HekpStartActivity.class, "HELPID", getIntent().getStringExtra("TYPEID"));
            showToast(((StateCodeBean) msg).getMsg(), Gravity.TOP);
//            onBackPressed();
        }

    }

}
