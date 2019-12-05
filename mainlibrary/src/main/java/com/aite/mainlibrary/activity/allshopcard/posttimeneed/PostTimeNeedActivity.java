package com.aite.mainlibrary.activity.allshopcard.posttimeneed;


import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.aite.mainlibrary.Mainbean.AllAreaBean;
import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
import com.aite.mainlibrary.Mainbean.TypeBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.timebank.TimeBankActivity;
import com.aite.mainlibrary.adapter.PostHelpDoctorTypeRecyAdapter;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.TimeUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PostTimeNeedActivity extends BaseActivity<PostTimeNeedContract.View, PostTimeNeedPresenter> implements PostTimeNeedContract.View {

    @BindView(R2.id.bottom_btn)
    Button bottomBtn;
    @BindView(R2.id.title_edit)
    TextInputEditText titleEdit;
    @BindView(R2.id.alladdress_edit)
    TextInputEditText alladdressEdit;
    @BindView(R2.id.name_edit)
    TextInputEditText nameEdit;
    @BindView(R2.id.women_man_edit)
    TextInputEditText womenManEdit;
    @BindView(R2.id.phone_edit)
    TextInputEditText phoneEdit;
    @BindView(R2.id.else_information_edit)
    TextInputEditText elseInformationEdit;
    @BindView(R2.id.choice_time_start_ll)
    LinearLayout choiceTimeStartLl;
    @BindView(R2.id.choice_time_end_ll)
    LinearLayout choiceTimeEndLl;
    @BindView(R2.id.start_year_tv)
    TextView startYearTv;
    @BindView(R2.id.start_oclock_tv)
    TextView startOclockTv;
    @BindView(R2.id.start_minute_tv)
    TextView startMinuteTv;
    @BindView(R2.id.end_year_tv)
    TextView endYearTv;
    @BindView(R2.id.end_oclock_tv)
    TextView endOclockTv;
    @BindView(R2.id.end_minute_tv)
    TextView endMinuteTv;
    @BindView(R2.id.area_choice_ll)
    LinearLayout areaChoiceLl;
    @BindView(R2.id.area_tv)
    TextView areaTv;
    @BindView(R2.id.choice_type_ll)
    LinearLayout choiceTypeLl;
    @BindView(R2.id.type_tv)
    TextView typeTv;
    private String startDate = "";
    private String endDate = "";
    private String PROVINCE_ID = "";
    private String CITY_ID = "";
    private String AREA_ID = "";
    private String SERVICETYPEID = "";
    /**
     * 上传服务的种类
     */
    private List<TypeBean.ClassBean> postTypeList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.post_time_service_need;
    }

    @Override
    protected void initView() {
        initToolbar("提交申请");
        bottomBtn.setText("提交");
        initChoiceArea(new OnOptionsSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                areaTv.setTextColor(getColor(R.color.black));
                areaTv.setTextSize(16);
                PROVINCE_ID = options1Itemsnumber.get(options1);
                CITY_ID = options2Itemsnumber.get(options1).get(options2);
                AREA_ID = options3Itemsnumber.get(options1).get(options2).get(options3);

                areaTv.setText(String.format("%s省--%s--%s",
                        options1Items.get(options1),
                        options2Items.get(options1).get(options2),
                        options3Items.get(options1).get(options2).get(options3)));
                LogUtils.e(
                        options1Itemsnumber.get(options1) + options1Items.get(options1)
                                + options2Itemsnumber.get(options1).get(options2) + options2Items.get(options1).get(options2)
                                + options3Itemsnumber.get(options1).get(options2).get(options3) + options3Items.get(options1).get(options2).get(options3));
            }
        }, new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {

            }
        });
//        //初始化服务类型选择器
//        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, ((ElseTimeBankListBean) msg).getClass_list());

    }

    @Override
    protected void initDatas() {
        mPresenter.getChoiceServiceType();
        mPresenter.getAreachoice();
        mPresenter.getChoiceServiceType();

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onPostServiceSuccess(Object msg) {
        if (((TwoSuccessCodeBean) msg).getResult().equals("1")) {
            showToast(((TwoSuccessCodeBean) msg).getMsg(), Gravity.TOP);
//            startActivity(TimeBankActivity.class);
            onBackPressed();
        } else {
            showToast("服务器错误");
            LogUtils.e(msg);
        }

    }

    @Override
    public void onGetAreaChoiceSuccess(Object msg) {
        for (int i = 0; i < ((AllAreaBean) msg).getList().size(); i++) {
            options1Items.add(((AllAreaBean) msg).getList().get(i).getArea_name());
            options1Itemsnumber.add(((AllAreaBean) msg).getList().get(i).getArea_id());
            city = new ArrayList<>();
            citynumber = new ArrayList<>();
            area = new ArrayList<>();
            areanumber = new ArrayList<>();
//            LogUtils.d(i + ((AllAreaBean) msg).getList().get(i).getArea_name());
            List<AllAreaBean.ListBean.CitylistBean> citylist = ((AllAreaBean) msg).getList().get(i).getCitylist();
            for (int o = 0; o < ((AllAreaBean) msg).getList().get(i).getCitylist().size(); o++) {
                city.add(citylist.get(o).getArea_name());
                citynumber.add(citylist.get(o).getArea_id());
                chirendenarea = new ArrayList<>();
                chirendenareanumber = new ArrayList<>();
//                LogUtils.d(i + ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArea_name());

                List<AllAreaBean.ListBean.CitylistBean.ArealistBean> arealist = ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArealist();
                for (int u = 0; u < ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArealist().size(); u++) {
                    chirendenarea.add(arealist.get(u).getArea_name());
                    chirendenareanumber.add(arealist.get(u).getArea_id());

//                    LogUtils.d(i + ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArealist().get(u).getArea_name());
                }
                area.add(chirendenarea);
                areanumber.add(chirendenareanumber);

            }

            options2Items.add(city);
            options2Itemsnumber.add(citynumber);

            options3Items.add(area);
            options3Itemsnumber.add(areanumber);

        }
        if (!options1Items.isEmpty() && !options1Items.isEmpty() && !options3Items.isEmpty())
            pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
    }

    @Override
    public void onChoiceServiceTypeSuccess(Object msg) {
        postTypeList = ((TypeBean) msg).getClassX();
    }

    @OnClick({R2.id.bottom_btn, R2.id.choice_time_start_ll, R2.id.choice_time_end_ll, R2.id.area_choice_ll, R2.id.choice_type_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottom_btn) {
            mPresenter.getPostService(initParams());
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//            PopwindowUtils.getmInstance().showRecyPopupWindow(context, radioGroupRecyAdapter, linearLayoutManager, endYearTv);

        }
        if (v.getId() == R.id.choice_time_end_ll) {
            initChoiceTimer(new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    long time = date.getTime();
                    String tim = TimeUtils.stampToDate(time);
                    endMinuteTv.setText(TimeUtils.timestampToDateStrMM(time));
                    endOclockTv.setText(TimeUtils.timestampToDateStrHH(time));
                    endYearTv.setText(tim);
                    LogUtils.e(String.valueOf(TimeUtils.getTime(date.getTime())) + "--" + tim);
                    endDate = TimeUtils.stampToDate2(time);
                }
            }, "选择结束时间", true);
            pvTime.show();
        } else if (v.getId() == R.id.choice_time_start_ll) {
            initChoiceTimer(new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    long time = date.getTime();
                    String tim = TimeUtils.stampToDate(time);
                    startMinuteTv.setText(TimeUtils.timestampToDateStrMM(time));
                    startOclockTv.setText(TimeUtils.timestampToDateStrHH(time));
                    startYearTv.setText(tim);
                    LogUtils.e(String.valueOf(TimeUtils.getTime(date.getTime())) + "--" + tim);
                    startDate = TimeUtils.stampToDate2(time);

                }
            }, "选择开始时间", true);
            pvTime.show();
        } else if (v.getId() == R.id.area_choice_ll) {
            if (!options1Items.isEmpty() || !options2Items.isEmpty() || !options3Items.isEmpty())
                pvOptions.show();
            else showToast("数据错误");
        } else if (v.getId() == R.id.choice_type_ll) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            PostHelpDoctorTypeRecyAdapter postHelpDoctorTypeRecyAdapter = new PostHelpDoctorTypeRecyAdapter(context, postTypeList);
            postHelpDoctorTypeRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
                @Override
                public void getPostion(int postion) {
                    LogUtils.e(postTypeList.get(postion).getClass_name());
                    typeTv.setText(postTypeList.get(postion).getClass_name());
                    SERVICETYPEID = postTypeList.get(postion).getClass_id();
                    PopwindowUtils.getmInstance().dismissPopWindow();
                }
            });
            PopwindowUtils.getmInstance().showRecyPopupWindow(context, postHelpDoctorTypeRecyAdapter, linearLayoutManager, Gravity.BOTTOM, 0.9f);

        }

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("title", getEditString(titleEdit));
        //服务类型id
        httpParams.put("class_id", SERVICETYPEID);
        //姓名
        httpParams.put("realname", getEditString(nameEdit));
        httpParams.put("mobile", getEditString(phoneEdit));
        httpParams.put("address", getEditString(alladdressEdit));
        //注意事项
        httpParams.put("note", getEditString(elseInformationEdit));
        //备注
        httpParams.put("remarks", getEditString(elseInformationEdit));
//        httpParams.put("start_time", TimeUtils.getCurrTime2());

        httpParams.put("start_time", isStringEmpty(startDate) ? "" : startDate);

//        yyyy-MM-dd HH:mm:ss
        httpParams.put("end_time", isStringEmpty(endDate) ? "" : endDate);
        //省 市 区级id
        httpParams.put("province_id", isStringEmpty(PROVINCE_ID) ? "" : PROVINCE_ID);
        httpParams.put("city_id", isStringEmpty(CITY_ID) ? "" : CITY_ID);
        httpParams.put("area_id", isStringEmpty(AREA_ID) ? "" : AREA_ID);
        return httpParams;
    }


}
