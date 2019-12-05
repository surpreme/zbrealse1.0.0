package com.aite.mainlibrary.activity.allshopcard.daytogether;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.DayTogetherChoiceBean;
import com.aite.mainlibrary.Mainbean.LessDayBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.dayinformation.DayInformationActivity;
import com.aite.mainlibrary.adapter.AllLessBodyRecyAdapter;
import com.aite.mainlibrary.adapter.RadioGroupRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.adpter.BaseItemDecoration;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.SystemUtil;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class DayTogetherActivity extends BaseActivity<DayTogetherContract.View, DayTogetherPresenter> implements DayTogetherContract.View, OnClickLstenerInterface.OnRecyClickInterface {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.all_tv)
    TextView allTv;
    @BindView(R2.id.all_ll)
    LinearLayout allLl;
    @BindView(R2.id.type_tv)
    TextView typeTv;
    @BindView(R2.id.type_ll)
    LinearLayout typeLl;
    @BindView(R2.id.time_tv)
    TextView timeTv;
    @BindView(R2.id.time_ll)
    LinearLayout timeLl;
    @BindView(R2.id.father_ll)
    LinearLayout fatherLl;
    private AllLessBodyRecyAdapter allLessBodyRecyAdapter;
    private List<LessDayBean.GoodsListBean> goodsListBeans = new ArrayList<>();
    private DayTogetherChoiceBean dayTogetherChoiceBean;
    private String LIST_ORDER = "0";
    private String LIST_CLASS = "0";
    private String LIST_TIME = "0";


    @Override
    protected int getLayoutResId() {
        return R.layout.day_together;
    }

    @Override
    protected void initView() {
        if (!isStringEmpty(getIntent().getStringExtra("type"))) {
            switch (Objects.requireNonNull(getIntent().getStringExtra("type"))) {
                case "2":
                    initToolbar("培训");
                    break;
                case "3":
                    initToolbar("就业");
                    break;
                case "4":
                    initToolbar("助残活动");
                    break;
                case "5":
                    initToolbar("其他服务");
                    break;
                default:
                    initToolbar("日托");
                    break;

            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        allLessBodyRecyAdapter = new AllLessBodyRecyAdapter(context, goodsListBeans);
        allLessBodyRecyAdapter.setClickInterface(this);
        recyclerView.setAdapter(allLessBodyRecyAdapter);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new BaseItemDecoration(SystemUtil.dip2px(context, 0), SystemUtil.dip2px(context, 10)
                    , SystemUtil.dip2px(context, 10), SystemUtil.dip2px(context, 0)
                    , SystemUtil.dip2px(context, 10), SystemUtil.dip2px(context, 0)
                    , ContextCompat.getColor(context, R.color.noglay), context
                    , 2f, "7.1:12"));
        }
    }

    @OnClick({R2.id.all_ll, R2.id.type_ll, R2.id.time_ll})
    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.infortion_item_ll) startActivity(DayInformationActivity.class);
        if (v.getId() == R.id.all_ll) {
            if (dayTogetherChoiceBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, dayTogetherChoiceBean.getList_order());
            showChoicePop(radioGroupRecyAdapter, "LIST_ORDER");
        } else if (v.getId() == R.id.type_ll) {
            if (dayTogetherChoiceBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, dayTogetherChoiceBean.getList_class());
            showChoicePop(radioGroupRecyAdapter, "LIST_CLASS");
        } else if (v.getId() == R.id.time_ll) {
            if (dayTogetherChoiceBean == null) return;
            RadioGroupRecyAdapter radioGroupRecyAdapter = new RadioGroupRecyAdapter(context, dayTogetherChoiceBean.getList_time());
            showChoicePop(radioGroupRecyAdapter, "LIST_TIME");

        }
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
                    case "LIST_ORDER":
                        LIST_ORDER = String.valueOf(postion);
                        break;
                    case "LIST_CLASS":
                        LIST_CLASS = String.valueOf(postion);
                        break;
                    case "LIST_TIME":
                        LIST_TIME = String.valueOf(postion);
                        break;
                    default:
                        break;

                }
                goodsListBeans.clear();
                radioGroupRecyAdapter.notifyDataSetChanged();
                mPresenter.getListMsg(initParams());
                PopwindowUtils.getmInstance().dismissPopWindow();


            }
        });
        PopwindowUtils.getmInstance().showRecyPopupWindow(context, radioGroupRecyAdapter, manager, fatherLl);
    }

    //get
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        //页面类型 1日托 2培训 3就业 4助残活动 5其他服务  必须
        httpParams.put("page_type", getIntent().getStringExtra("type"));
        LogUtils.e(getIntent().getStringExtra("type"));
        //商品关键词
//        httpParams.put("keyword", AppConstant.KEY);
        //分类编号
        httpParams.put("gc_id", LIST_CLASS);
        //排序方式 0:综合排序 1销量 2：人气 3：价格
        httpParams.put("sort_id", LIST_ORDER);
        //排序类型 1：升序 2：降序
        httpParams.put("order", 1);
        //时长
        httpParams.put("time_id", LIST_TIME);
        //当前页码
        httpParams.put("curpage", 1);
        return httpParams;
    }
    private HttpParams initTypeParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        //页面类型 1日托 2培训 3就业 4助残活动 5其他服务  必须
        httpParams.put("page_type", getIntent().getStringExtra("type"));
        return httpParams;
    }

    @Override
    protected void initDatas() {
        mPresenter.getListMsg(initParams());

    }

    @Override
    protected void initResume() {
        mPresenter.getChoiceInformation(initTypeParams());

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onGetListSuccess(Object msg) {
        goodsListBeans.addAll(((LessDayBean) msg).getGoods_list());
        allLessBodyRecyAdapter.notifyDataSetChanged();

    }

    @Override
    public void onGetchoiceInformationSuccess(Object msg) {
//        ((DayTogetherChoiceBean) msg).getList_class().get(1);
        dayTogetherChoiceBean = ((DayTogetherChoiceBean) msg);

        dayTogetherChoiceBean.getList_class().get(0).setChecked(true);
        dayTogetherChoiceBean.getList_order().get(0).setChecked(true);
        dayTogetherChoiceBean.getList_time().get(0).setChecked(true);

    }


    @Override
    public void getPostion(int postion) {
        startActivity(DayInformationActivity.class, "goods_id", goodsListBeans.get(postion).getGoods_id());

    }
}
