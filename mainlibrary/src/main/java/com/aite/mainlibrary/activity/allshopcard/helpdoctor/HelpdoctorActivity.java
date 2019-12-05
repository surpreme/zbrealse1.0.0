package com.aite.mainlibrary.activity.allshopcard.helpdoctor;


import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aite.mainlibrary.Mainbean.ElseHelpDoctorBean;
import com.aite.mainlibrary.Mainbean.HelpDoctorListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.bookhelpdoctorinformation.BookHelpDoctorInformationActivity;
import com.aite.mainlibrary.activity.allshopcard.posthelpdoctor.PostHelpDoctorActivity;
import com.aite.mainlibrary.adapter.HelpDoctorRecyAdapter;
import com.aite.mainlibrary.adapter.RadioGroupRecyAdapter;
import com.blankj.rxbus.RxBus;
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
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HelpdoctorActivity extends BaseActivity<HelpdoctorContract.View, HelpdoctorPresenter> implements HelpdoctorContract.View, OnBannerListener {
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
    private List<HelpDoctorListBean.ListBean> helpDoctorlistBean = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    private ElseHelpDoctorBean elseHelpDoctorBean;
    private String CLASS_ID = "0";
    private String AREA_ID = "0";
    private String TIME_ID = "0";

    @Override
    protected int getLayoutResId() {
        return R.layout.help_doctor_servicer_layout;
    }

    @Override
    protected void initView() {
        initToolbar("助医服务", getResources().getColor(R.color.white));
        initMoreRecy();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mBaserecyclerView.setLayoutManager(linearLayoutManager);
        helpDoctorRecyAdapter = new HelpDoctorRecyAdapter(context, helpDoctorlistBean);
        mBaserecyclerView.setAdapter(helpDoctorRecyAdapter);
        //smartlayout
        initSmartLayout(true);
        //初始化加载
        initLoadingAnima();
        //banner
        initBanner(banner);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);
        RxBus.getDefault().subscribe(context, "helpDoctorChange", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String o) {
                if (o.equals("helpDoctorChange")) {
                    onSmartRefresh();
                }

            }
        });
        helpDoctorRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
//                startActivity(HelpDoctorInformationActivity.class, "TYPEID", helpDoctorlistBean.get(postion).getId());
                startActivity(BookHelpDoctorInformationActivity.class, "TYPEID", helpDoctorlistBean.get(postion).getId());

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
            showChoicePop(radioGroupRecyAdapter,"CLASS_ID");
        }
        if (v.getId() == R.id.all_ll) {
            if (elseHelpDoctorBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseHelpDoctorBean.getArea_list());
            showChoicePop(radioGroupRecyAdapter,"AREA_ID");
        }
        if (v.getId() == R.id.time_ll) {
            if (elseHelpDoctorBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, elseHelpDoctorBean.getTime_array());
            showChoicePop(radioGroupRecyAdapter,"TIME_ID");

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
                helpDoctorlistBean.clear();
                radioGroupRecyAdapter.notifyDataSetChanged();
                mPresenter.getList(initParams());
                PopwindowUtils.getmInstance().dismissPopWindow();
            }
        });
        PopwindowUtils.getmInstance().showRecyPopupWindow(context, radioGroupRecyAdapter, manager, fatherTabLl);
    }

    @Override
    protected void onSmartLoadMore() {
        super.onSmartLoadMore();
        mPresenter.getList(initParams());

    }

    @Override
    protected void onSmartRefresh() {
        super.onSmartRefresh();
        if (helpDoctorlistBean != null) {
            helpDoctorlistBean.clear();
            helpDoctorRecyAdapter.notifyDataSetChanged();
        }

        mPresenter.getList(initParams());

    }

    @Override
    public void onGetListSuccess(Object msg) {
        if (((HelpDoctorListBean) msg).getList().isEmpty()) {
            initNodata();
        } else {
            stopLoadingAnim();
            showMoreRecy();
            stopNoData();
            helpDoctorlistBean.addAll(((HelpDoctorListBean) msg).getList());
            helpDoctorRecyAdapter.notifyDataSetChanged();
            hasMore = ((HelpDoctorListBean) msg).getIs_nextpage() > 0;
        }

//        LoadMoreSmartLayout(((HelpDoctorListBean) msg).getIs_nextpage() > 0, new OnSmartLayoutLstenerInterface.OnLoadMoreInterface() {
//            @Override
//            public void getCurrentPage(int postion) {
//
//            }
//        });
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
