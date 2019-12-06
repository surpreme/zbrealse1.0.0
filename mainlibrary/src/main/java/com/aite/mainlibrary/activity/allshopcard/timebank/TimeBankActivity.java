package com.aite.mainlibrary.activity.allshopcard.timebank;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aite.mainlibrary.Mainbean.ElseTimeBankListBean;
import com.aite.mainlibrary.Mainbean.TimeBankListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.NumberBankActivity;
import com.aite.mainlibrary.activity.allshopcard.booktimebankinformation.BookTimebankInformationActivity;
import com.aite.mainlibrary.activity.allshopcard.posttimeneed.PostTimeNeedActivity;
import com.aite.mainlibrary.adapter.RadioGroupRecyAdapter;
import com.aite.mainlibrary.adapter.TimeBankRecyAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class TimeBankActivity extends BaseActivity<TimeBankContract.View, TimeBankPresenter> implements TimeBankContract.View, OnBannerListener {

    @BindView(R2.id.number_bank_tv)
    TextView numberBankTv;
    @BindView(R2.id.post_service_need_img)
    ImageView postServiceNeedImg;
    @BindView(R2.id.user_name_tv)
    TextView user_name_tv;
    @BindView(R2.id.member_levelname_tv)
    TextView memberLevelnameTv;
    @BindView(R2.id.banner)
    Banner banner;
    @BindView(R2.id.user_icon)
    ImageView userIcon;
    @BindView(R2.id.father_tab_ll)
    LinearLayout fatherTabLl;
    @BindView(R2.id.all_ll)
    LinearLayout allLl;
    @BindView(R2.id.service_ll)
    LinearLayout serviceLl;
    @BindView(R2.id.time_ll)
    LinearLayout timeLl;
    @BindView(R2.id.number_bank_ll)
    LinearLayout numberBankLl;
    @BindView(R2.id.all_iv)
    ImageView allIv;
    @BindView(R2.id.service_iv)
    ImageView serviceIv;
    @BindView(R2.id.time_iv)
    ImageView timeIv;
    private TimeBankRecyAdapter timeBankRecyAdapter;
    private List<TimeBankListBean.ListBean> timeBankListBean = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    private ElseTimeBankListBean elseTimeBankListBean;
    private String CLASS_ID = "0";
    private String AREA_ID = "0";
    private String TIME_ID = "0";

    @Override
    protected int getLayoutResId() {
        return R.layout.time_bank;
    }

    @Override
    protected void initView() {
        initToolbar("时间银行");
        initMoreRecy();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mBaserecyclerView.setLayoutManager(linearLayoutManager);
        timeBankRecyAdapter = new TimeBankRecyAdapter(context, timeBankListBean);
        mBaserecyclerView.setAdapter(timeBankRecyAdapter);
        //smartlayout
        initSmartLayout(true);
        //初始化加载
        initLoadingAnima();
//        fatherTabLl.setOnClickListener(this);
        timeBankRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                Intent intent = new Intent(context, BookTimebankInformationActivity.class);
                intent.putExtra("TYPEID", timeBankListBean.get(postion).getId());
                intent.putExtra("activity", "TimeBankActivity");
                startActivity(intent);
//                startActivity(BookTimebankInformationActivity.class, "TYPEID", String.valueOf(timeBankListBean.get(postion).getId()));


            }
        });
        //banner
        initBanner(banner);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);
    }

    @OnClick({R2.id.post_service_need_img, R2.id.all_ll, R2.id.time_ll, R2.id.service_ll, R2.id.number_bank_ll})
    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.number_bank_tv) {
//        }
        if (v.getId() == R.id.post_service_need_img) {
            startActivity(PostTimeNeedActivity.class);
        }
        if (v.getId() == R.id.service_ll) {
            if (elseTimeBankListBean == null) return;
            serviceIv.setImageDrawable(getResources().getDrawable(R.drawable.top));
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseTimeBankListBean.getClass_list());
            showChoicePop(radioGroupRecyAdapter, "CLASS_ID");
        }
        if (v.getId() == R.id.all_ll) {
            if (elseTimeBankListBean == null) return;
            allIv.setImageDrawable(getResources().getDrawable(R.drawable.top));
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseTimeBankListBean.getArea_list());
            showChoicePop(radioGroupRecyAdapter, "AREA_ID");
        }
        if (v.getId() == R.id.time_ll) {
            if (elseTimeBankListBean == null) return;
            timeIv.setImageDrawable(getResources().getDrawable(R.drawable.top));
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseTimeBankListBean.getTime_array());
            showChoicePop(radioGroupRecyAdapter, "TIME_ID");

        }
        if (v.getId() == R.id.number_bank_ll) {
            startActivity(NumberBankActivity.class);


        }

    }

    @Override
    protected void onSmartLoadMore() {
        super.onSmartLoadMore();
        mPresenter.showUiListData(initParams());

    }

    private void resetChoiceIv() {
        serviceIv.setImageDrawable(getResources().getDrawable(R.drawable.low));
        allIv.setImageDrawable(getResources().getDrawable(R.drawable.low));
        timeIv.setImageDrawable(getResources().getDrawable(R.drawable.low));

    }

    @Override
    protected void onSmartRefresh() {
        super.onSmartRefresh();
        if (timeBankListBean != null) {
            timeBankListBean.clear();
            timeBankRecyAdapter.notifyDataSetChanged();
        }

        mPresenter.showUiListData(initParams());

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
                timeBankListBean.clear();
                radioGroupRecyAdapter.notifyDataSetChanged();
                mPresenter.showUiListData(initParams());
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

    @Override
    protected void initDatas() {
        mPresenter.showElseUiListData(initElseParams());
        mPresenter.showUiListData(initParams());

    }


    private HttpParams initElseParams() {
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
    public void onMainUiListDataSuccess(Object msg) {
        if (((TimeBankListBean) msg).getList() ==
                null || ((TimeBankListBean) msg).getList().isEmpty()) {
            initNodata();
        } else {
            stopLoadingAnim();
            showMoreRecy();
            stopNoData();
            timeBankListBean.addAll(((TimeBankListBean) msg).getList());
            timeBankRecyAdapter.notifyDataSetChanged();
            hasMore = ((TimeBankListBean) msg).getIs_nextpage() > 0;
        }


    }

    /**
     * MemberInfoBean
     * member_name : 18614079738
     * member_avatar : http://zhongbyi.aitecc.com/data/upload/shop/common/default_user_portrait.gif
     * member_levelname : 默认等级
     */
    @Override
    public void onElseMainUiListDataSuccess(Object msg) {
        elseTimeBankListBean = ((ElseTimeBankListBean) msg);
        //name
        user_name_tv.setText(elseTimeBankListBean.getMember_info().getMember_name());
        //level
        memberLevelnameTv.setText(elseTimeBankListBean.getMember_info().getMember_levelname());
        if (elseTimeBankListBean.getMember_info().getMember_avatar() != null)
            Glide.with(context)
                    .load(elseTimeBankListBean.getMember_info().getMember_avatar())
                    .apply(RequestOptions.circleCropTransform())
                    .into(userIcon);
        //banner
        if (elseTimeBankListBean.getAdv_list() != null || elseTimeBankListBean.getAdv_list().size() > 0) {
            for (ElseTimeBankListBean.AdvListBean advListBean : elseTimeBankListBean.getAdv_list()) {
                list_img.add(advListBean.getAdv_content().getAdv_pic());
                list_title.add(advListBean.getAdv_title());
                LogUtils.d(advListBean.getAdv_title());

            }
            banner.setImages(list_img);
            banner.setBannerTitles(list_title);
            banner.startAutoPlay();
            banner.start();
        }

        elseTimeBankListBean.getClass_list().get(0).setChecked(true);
        elseTimeBankListBean.getArea_list().get(0).setChecked(true);
        elseTimeBankListBean.getTime_array().get(0).setChecked(true);


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
