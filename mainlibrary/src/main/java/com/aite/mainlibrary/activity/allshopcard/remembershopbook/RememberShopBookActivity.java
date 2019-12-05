package com.aite.mainlibrary.activity.allshopcard.remembershopbook;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.RememberFoodInformationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.sureshopbook.SureShopBookActivity;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.TimeUtils;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RememberShopBookActivity extends BaseActivity<RememberShopBookContract.View, RememberShopBookPresenter> implements RememberShopBookContract.View, OnBannerListener {

    @BindView(R2.id.bottom_btn)
    Button bottomBtn;
    @BindView(R2.id.banner)
    Banner banner;
    @BindView(R2.id.eat_away_checkbox)
    CheckBox eatAwayCheckbox;
    @BindView(R2.id.getshop_tv)
    TextView getshopTv;
    @BindView(R2.id.send_user_tv)
    TextView sendUserTv;
    @BindView(R2.id.choice_time_ll)
    LinearLayout choiceTimeLl;
    @BindView(R2.id.time_tv)
    TextView timeTv;
    @BindView(R2.id.oclock_tv)
    TextView oclockTv;
    @BindView(R2.id.choice_oclock_ll)
    LinearLayout choiceOclockLl;
    @BindView(R2.id.phone_edit)
    TextInputEditText phoneEdit;
    private String EATAWAYTYPE = "1";
    private String mDate = "";
    private boolean isYearCheck = false;
    private boolean isOclockCheck = false;

    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.remembershop;
    }

    @Override
    protected void initView() {
        initToolbar("预约订餐");
        //初始化banner
        initBanner(banner);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);
        eatAwayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendUserTv.setBackground(
                        getResources().getDrawable(isChecked ? R.drawable.round_background_yellow : R.drawable.round_background_white));
                getshopTv.setBackground(
                        getResources().getDrawable(isChecked ? R.drawable.round_background_white : R.drawable.round_background_yellow));

                getshopTv.setTextColor(getResources().getColor(isChecked ? R.color.black : R.color.white));
                sendUserTv.setTextColor(getResources().getColor(isChecked ? R.color.white : R.color.black));
                EATAWAYTYPE = isChecked ? "2" : "1";

            }
        });


    }

    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        params.put("key", AppConstant.KEY);
        params.put("goods_id", getIntent().getStringExtra("goods_id"));
        return params;
    }

    private HttpParams initAllInformationParams() {
        HttpParams params = new HttpParams();
        params.put("key", AppConstant.KEY);
        params.put("goods_id", getIntent().getStringExtra("goods_id"));
        params.put("buyer_phone", getEditString(phoneEdit));
        params.put("type", EATAWAYTYPE);
        params.put("meal_time", isStringEmpty(mDate) ? "" : mDate);
        params.put("meal_address", "南山科技园福安大厦");

        return params;
    }

    @Override
    protected void initDatas() {
        mPresenter.getFoodInformation(initParams());

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @OnClick({R2.id.bottom_btn, R2.id.choice_time_ll, R2.id.choice_oclock_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottom_btn) {
            if (!isOclockCheck && !isYearCheck) {
                showToast("请检查用餐日期");
                return;
            }
            // 产生订单
            mPresenter.postAllInformation(initAllInformationParams());
        } else if (v.getId() == R.id.choice_time_ll) {
            initChoiceTimer(new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    long time = date.getTime();
                    String tim = TimeUtils.stampToDate(time);
                    timeTv.setText(tim);
                    LogUtils.e(String.valueOf(TimeUtils.getTime(date.getTime())) + "--" + tim);
                    mDate = TimeUtils.stampToDate(time);
                    isYearCheck = true;
                }
            }, "选择用餐日期", false);
            pvTime.show();
        } else if (v.getId() == R.id.choice_oclock_ll) {
            initChoiceHMTimer(new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    long time = date.getTime();
                    String tim = TimeUtils.stampToDate(time);
                    oclockTv.setText(String.format("%s:%s", TimeUtils.timestampToDateStrHH(time), TimeUtils.timestampToDateStrMM(time)));
                    LogUtils.e(String.valueOf(TimeUtils.getTime(date.getTime())) + "--" + tim);
                    mDate = mDate + String.format(" %s:%s:%s", TimeUtils.timestampToDateStrHH(time), TimeUtils.timestampToDateStrMM(time), TimeUtils.timestampToDateStrSS(time));
                    isOclockCheck = true;
                }
            }, "选择用餐时间", true);
            pvTime.show();
        }
    }

    @Override
    public void onGetFoodInformationSuccess(Object msg) {
        if (((RememberFoodInformationBean) msg).getAdv_list() != null || ((RememberFoodInformationBean) msg).getAdv_list().size() > 0) {
            for (RememberFoodInformationBean.AdvListBean advListBean : ((RememberFoodInformationBean) msg).getAdv_list()) {
                list_img.add(advListBean.getAdv_content().getAdv_pic());
                list_title.add(advListBean.getAdv_title());
                LogUtils.d(advListBean.getAdv_title());

            }
            startBanner(banner, list_img, list_title);
        }

    }

    @Override
    public void postAllInformationSuccess(Object msg) {
        if ((String) msg != null || !isStringEmpty((String) msg)) {
            LogUtils.d(msg.toString());
            showToast("订单生成成功");
            //订单id
            startActivity(SureShopBookActivity.class, "order_id", msg.toString());

        }


    }


    @Override
    public void OnBannerClick(int position) {

    }

}
