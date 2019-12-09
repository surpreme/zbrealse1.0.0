package com.aite.mainlibrary.activity.allsetting.helpdocotorinformationbook;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.HelpDoctorBookInformationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.hekpstart.HekpStartActivity;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PostInformationBookActivity extends BaseActivity<PostInformationBookContract.View, PostInformationBookPresenter> implements PostInformationBookContract.View {

    @BindView(R2.id.top_iv)
    ImageView topIv;
    @BindView(R2.id.service_title_tv)
    TextView serviceTitleTv;
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
    @BindView(R2.id.information_tv)
    TextView informationTv;
    @BindView(R2.id.bottom_btn)
    Button bottomBtn;
    @BindView(R2.id.over_service_hide_ll)
    LinearLayout overServiceHideLl;
    @BindView(R2.id.mobile_tv)
    TextView mobileTv;
    @BindView(R2.id.start_iv)
    ImageView startIv;
    @BindView(R2.id.end_iv)
    ImageView endIv;
    private Bundle bundle = new Bundle();

    @Override
    protected int getLayoutResId() {
        return R.layout.helpdoctor_infomation;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        initToolbar("订单详情");
        bundle = getIntent().getExtras();
        bottomBtn.setBackground(getDrawable(R.drawable.round_background_yellow));

    }

    @Override
    protected void initDatas() {
        mPresenter.getBookInforMation(bundle.getString("chriendtype"), initParams());

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("id", isStringEmpty(bundle.getString("id")) ? "" : bundle.getString("id"));
        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    /**
     * 返回字段	类型	说明
     * datas->advs[]	数组	广告
     * datas->advs[]->adv_title	字符串	标题
     * datas->advs[]->adv_img	字符串	图片
     * datas->info[]	数组	服务信息
     * datas->info[]->id	整型	ID
     * datas->info[]->title	字符串	标题
     * datas->info[]->realname	字符串	姓名
     * datas->info[]->mobile	字符串	联系电话
     * datas->info[]->duration	字符串	时长
     * datas->info[]->credit	字符串	奖励积分
     * datas->info[]->address	字符串	地址
     * datas->info[]->time_ymd	字符串	发布时间 年月日
     * datas->info[]->time_hi	字符串	发布时间 时分
     * datas->info[]->remarks	字符串	备注
     * datas->info[]->order_status	字符串	接单状态 -1服务已取消 0待接单 1已接单 2已开始 3已结束 4已评价
     * datas->info[]->start_thumb	数组	开始图片
     * datas->info[]->end_thumb	数组	结束图片
     * datas->info[]->start_time	字符串	开始时间
     * datas->info[]->end_time	字符串	结束时间
     * datas->info[]->order_start_time	字符串	实际开始时间 空为未开始
     * datas->info[]->order_end_time	字符串	实际结束时间 空为未结束
     * error	字符串	错误信息 error_code=0 正确 其他编码错误
     *
     * @param msg
     */
    @Override
    public void onGetBookInforMationSuccess(Object msg) {
        HelpDoctorBookInformationBean infoBean = ((HelpDoctorBookInformationBean) msg);
        if (infoBean == null) return;
        if (infoBean.getInfo() == null) return;
        if (infoBean.getAdvs() == null) return;
        if (infoBean.getAdvs() != null) {
            if (infoBean.getAdvs().getAdv_img() != null) {
                Glide.with(context).load(infoBean.getAdvs().getAdv_img()).into(topIv);
            }
        }
        addressTv.setText(String.format("地点：%s", infoBean.getInfo().getAddress() != null ? infoBean.getInfo().getAddress() : ""));
        serviceTimeTv.setText(String.format("服务时间：%s", infoBean.getInfo().getTime_ymd() != null ? infoBean.getInfo().getTime_ymd() : ""));
        informationTv.setText(String.format("详情: %s", infoBean.getInfo().getRemarks() != null ? infoBean.getInfo().getRemarks() : ""));
        typeTv.setText(String.format("服务类型：%s", infoBean.getInfo().getClass_name()));
        LogUtils.d(infoBean.getInfo().getOrder_status());
        Glide.with(context).load(infoBean.getInfo().getStart_thumb()).into(startIv);
        Glide.with(context).load(infoBean.getInfo().getEnd_thumb()).into(endIv);

        bottomBtn.setEnabled(!infoBean.getInfo().getOrder_status().equals("3"));
        startTimeTv.setText(String.format("开始时间：%s", infoBean.getInfo().getStart_time()));
        endTimeTv.setText(String.format("结束时间：%s", infoBean.getInfo().getEnd_time()));
        bottomBtn.setAlpha(infoBean.getInfo().getOrder_status().equals("3") ? 0.5f : 1.0f);
        mobileTv.setText(String.format("手机号：%s", infoBean.getInfo().getMobile()));
        // case "1":
        //                bottomBtn.setText("待接单");
        //                break;
        switch (infoBean.getInfo().getOrder_status()) {
            case "-1":
                bottomBtn.setText("服务已取消");
                bottomBtn.setEnabled(false);
                bottomBtn.setAlpha(0.7f);
                break;
            case "0":
                bottomBtn.setText("待接单");
                bottomBtn.setEnabled(false);
                bottomBtn.setAlpha(0.7f);
                break;
            case "1":
            case "2":
                bottomBtn.setText("开始服务");
                bottomBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(HekpStartActivity.class, "tb_id", bundle.getString("tb_id"));

                    }
                });
                break;
            case "3":
                bottomBtn.setText("结束服务");
                break;
            case "4":
                bottomBtn.setText("已评价");
                break;
        }
        if (!infoBean.getInfo().getOrder_status().equals("3")) {
            overServiceHideLl.setVisibility(View.GONE);
        } else {
            bottomBtn.setText("已结束");
            bottomBtn.setEnabled(false);
            overServiceHideLl.setVisibility(View.VISIBLE);
        }
    }


}
