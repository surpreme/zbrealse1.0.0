package com.aite.mainlibrary.activity.allshopcard.helpdoctorinformation;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.HelpDoctorInformationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.TimeUtils;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HelpDoctorInformationActivity extends BaseActivity<HelpDoctorInformationContract.View, HelpDoctorInformationPresenter> implements HelpDoctorInformationContract.View {
    @BindView(R2.id.service_title_tv)
    TextView service_title_tv;
    @BindView(R2.id.type_tv)
    TextView typeTv;
    @BindView(R2.id.service_time_tv)
    TextView serviceTimeTv;
    @BindView(R2.id.address_tv)
    TextView addressTv;
    @BindView(R2.id.service_back_recy)
    RecyclerView serviceBackRecy;
    @BindView(R2.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R2.id.end_time_tv)
    TextView endTimeTv;
    @BindView(R2.id.banner)
    Banner banner;
    @BindView(R2.id.top_iv)
    ImageView topIv;

    /**
     * id : 16
     * member_id : 7
     * title : 借记卡
     * doctor_name : yfry
     * department : yfry
     * class_id : 1
     * realname :
     * mobile : 18566775552
     * province_id : 1
     * city_id : 2
     * area_id : 3
     * address : 北京天津河北KKK路他
     * start_time : 1575000000
     * end_time : 1577851200
     * duration : 792.0
     * remarks : thighs
     * credit : 0.00
     * addtime : 1574859287
     * status : 1
     * order_status : 0
     * class_name : 陪伴
     * time_ymd : 2019-11-29
     * is_order : 1
     */
    @Override
    public void onGetInformationSuccess(Object msg) {
        HelpDoctorInformationBean.InfoBean infoBean = ((HelpDoctorInformationBean) msg).getInfo();
        addressTv.setText(String.format("地点：%s", infoBean.getAddress()));
        service_title_tv.setText(String.format("服务项目：%s", infoBean.getTitle()));
        startTimeTv.setText(String.format("开始时间%s", TimeUtils.stampToDatemm2(Long.valueOf(infoBean.getStart_time()))));
        endTimeTv.setText(String.format("结束时间%s", TimeUtils.stampToDatemm2(Long.valueOf(infoBean.getEnd_time()))));
        serviceTimeTv.setText(String.format("服务时间：%s", TimeUtils.stampToDatemm2(Long.valueOf(infoBean.getAddtime()))));
        typeTv.setText(String.format("服务类型：%s", infoBean.getClass_name()));
        Glide.with(context).load(((HelpDoctorInformationBean) msg).getAdvs().getAdv_img()).into(topIv);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.helpdoctor_infomation;
    }

    @Override
    protected void initView() {
        initToolbar("助医详情");
        LogUtils.e(getIntent().getStringExtra("TYPEID"));

    }

    @Override
    protected void initDatas() {
        mPresenter.getInformation(initParams());

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("id", getIntent().getStringExtra("TYPEID"));
        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }



}
