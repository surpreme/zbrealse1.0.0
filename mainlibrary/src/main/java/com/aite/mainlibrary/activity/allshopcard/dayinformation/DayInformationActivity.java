package com.aite.mainlibrary.activity.allshopcard.dayinformation;


import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.aite.mainlibrary.Mainbean.LessBodyInformationBean;
import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allmain.buydaytogether.BuyDayTogetherActivity;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class DayInformationActivity extends BaseActivity<DayInformationContract.View, DayInformationPresenter> implements DayInformationContract.View {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.goods_iv)
    ImageView goodsIv;
    @BindView(R2.id.goods_price_tv)
    TextView goodsPriceTv;
    @BindView(R2.id.goods_information_tv)
    TextView goodsInformationTv;
    @BindView(R2.id.collect_iv)
    ImageView collectIv;
    @BindView(R2.id.buy_service_tv)
    TextView buy_service_tv;
    @BindView(R2.id.talk_all_tv)
    TextView talkAllTv;
    @BindView(R2.id.talk_good_tv)
    TextView talkGoodTv;
    @BindView(R2.id.talk_norm_tv)
    TextView talkNormTv;
    @BindView(R2.id.talk_bad_tv)
    TextView talkBadTv;
    @BindView(R2.id.talk_good_number_tv)
    TextView talkGoodNumberTv;
    @BindView(R2.id.talk_norm_number_tv)
    TextView talkNormNumberTv;
    @BindView(R2.id.talk_bad_number_tv)
    TextView talkBadNumberTv;
    @BindView(R2.id.talk_all_number_tv)
    TextView talkAllNumberTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.day_togther_information;
    }

    @Override
    protected void initView() {
//        buy_tv.setOnClickListener(this);
//        collectIv.setOnClickListener(this);

    }

    @OnClick({R2.id.buy_service_tv, R2.id.iv_back, R2.id.collect_iv})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        } else if (v.getId() == R.id.buy_service_tv) {
            LogUtils.d("goods_id", !isStringEmpty(getIntent().getStringExtra("goods_id")) ? getIntent().getStringExtra("goods_id") : "");
            startActivity(BuyDayTogetherActivity.class, "goods_id",
                    !isStringEmpty(getIntent().getStringExtra("goods_id")) ? getIntent().getStringExtra("goods_id") : "");
        } else if (v.getId() == R.id.collect_iv) {
            mPresenter.onCollect(initParams());
        }
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("goods_id",
                !isStringEmpty(getIntent().getStringExtra("goods_id")) ? getIntent().getStringExtra("goods_id") : "");
        return httpParams;
    }

    @Override
    protected void initDatas() {
        mPresenter.onGetInformation(initParams());

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @SuppressLint({"NewApi", "DefaultLocale"})
    @Override
    public void onGetInformationSuccess(Object msg) {
        LessBodyInformationBean lessBodyInformationBean = ((LessBodyInformationBean) msg);
        collectIv.setImageDrawable(getDrawable((lessBodyInformationBean.getIsFavorites() == 1 ? R.drawable.ic_collect_full : R.drawable.collect_red)));
        Glide.with(context).load(lessBodyInformationBean.getGoods_info().getGoods_image_url()).into(goodsIv);
        goodsInformationTv.setText(lessBodyInformationBean.getGoods_info().getGoods_name());
        goodsPriceTv.setText(String.format("￥ %s", lessBodyInformationBean.getGoods_info().getGoods_price()));
        talkAllTv.setText(String.format("全部评价(%d)", lessBodyInformationBean.getEvaluate_info().getAll()));
        talkGoodTv.setText(String.format("好评(%d)", lessBodyInformationBean.getEvaluate_info().getAll()));
        talkNormTv.setText(String.format("中评(%d)", lessBodyInformationBean.getEvaluate_info().getAll()));
        talkBadTv.setText(String.format("差评(%d)", lessBodyInformationBean.getEvaluate_info().getAll()));

        talkAllNumberTv.setText(String.format("%s%%", String.format("共有(%d)", lessBodyInformationBean.getEvaluate_info().getGood_star())));
        talkBadNumberTv.setText(String.format("%s%%", String.format("差评(%d)", lessBodyInformationBean.getEvaluate_info().getBad_percent())));
        talkNormNumberTv.setText(String.format("%s%%", String.format("中评(%d)", lessBodyInformationBean.getEvaluate_info().getNormal_percent())));
        talkGoodNumberTv.setText(String.format("%s%%", String.format("好评(%d)", lessBodyInformationBean.getEvaluate_info().getGood_percent())));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCollectSuccess(Object msg) {
        if (((TwoSuccessCodeBean) msg).getResult().equals("1") && ((TwoSuccessCodeBean) msg).getMsg().equals("收藏成功")) {
            showToast(((TwoSuccessCodeBean) msg).getMsg());
            collectIv.setImageDrawable(getDrawable(R.drawable.ic_collect_full));
        }
        if (((TwoSuccessCodeBean) msg).getResult().equals("1") && ((TwoSuccessCodeBean) msg).getMsg().equals("取消收藏成功")) {
            showToast(((TwoSuccessCodeBean) msg).getMsg());
            collectIv.setImageDrawable(getDrawable(R.drawable.collect_red));
        }

    }

}
