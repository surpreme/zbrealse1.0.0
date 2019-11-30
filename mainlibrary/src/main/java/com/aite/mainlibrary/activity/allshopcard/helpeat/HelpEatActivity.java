package com.aite.mainlibrary.activity.allshopcard.helpeat;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.HelpEatUIBean;
import com.aite.mainlibrary.Mainbean.MainUiDataBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.choiceeat.ChoiceEatActivity;
import com.aite.mainlibrary.activity.allshopcard.morningnooneat.MorningNoonEatActivity;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.view.GlideImageLoader;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HelpEatActivity extends BaseActivity<HelpEatContract.View, HelpEatPresenter> implements HelpEatContract.View, OnBannerListener {
    @BindView(R2.id.tv_title_right)
    TextView tvTitleR2ight;
    @BindView(R2.id.morning_ll)
    LinearLayout morningLl;
    @BindView(R2.id.noon_ll)
    LinearLayout noonLl;
    @BindView(R2.id.choice_ll)
    LinearLayout choiceLl;
    @BindView(R2.id.message_tv)
    TextView messageTv;
    @BindView(R2.id.banner)
    Banner banner;
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.help_eat_layout;
    }


    @Override

    protected void initView() {
        initToolbar("助餐");
        morningLl.setOnClickListener(this);
        noonLl.setOnClickListener(this);
        messageTv.setOnClickListener(this);
        choiceLl.setOnClickListener(this);
        //初始化banner
        initBanner(banner);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.morning_ll) {
            startActivity(MorningNoonEatActivity.class, "type", "morning");
        } else if (v.getId() == R.id.noon_ll) {
            startActivity(MorningNoonEatActivity.class, "type", "noon");
        } else if (v.getId() == R.id.choice_ll) {
            startActivity(ChoiceEatActivity.class);
        } else if (v.getId() == R.id.message_tv) {
            PopwindowUtils.getmInstance()
                    .showRecyPopupWindow(context, null, null, messageTv);

        }


    }

    @Override
    protected void initDatas() {
        mPresenter.showUiData(initParams());

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
    public void getUiDataSuccess(Object msg) {
        messageTv.setText(String.format("当前位置：南山区 %s", ((HelpEatUIBean) msg).getNursing_store_name()));
        if (((HelpEatUIBean) msg).getAdv_list() != null || ((HelpEatUIBean) msg).getAdv_list().size() > 0) {
            for (HelpEatUIBean.AdvListBean advListBean : ((HelpEatUIBean) msg).getAdv_list()) {
                list_img.add(advListBean.getAdv_content().getAdv_pic());
                list_title.add(advListBean.getAdv_title());
                LogUtils.d(advListBean.getAdv_title());

            }
            banner.setImages(list_img);
            banner.setBannerTitles(list_title);
            banner.startAutoPlay();
            banner.start();
        }
    }


    @Override
    public void OnBannerClick(int position) {

    }
}
