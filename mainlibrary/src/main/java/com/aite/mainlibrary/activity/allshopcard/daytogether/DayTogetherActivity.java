package com.aite.mainlibrary.activity.allshopcard.daytogether;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.LessDayBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.DayInformationActivity;
import com.aite.mainlibrary.adapter.AllLessBodyRecyAdapter;
import com.aite.mainlibrary.adapter.ChoiceEatTypeRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
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


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class DayTogetherActivity extends BaseActivity<DayTogetherContract.View, DayTogetherPresenter> implements DayTogetherContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    private AllLessBodyRecyAdapter allLessBodyRecyAdapter;
    private List<LessDayBean.GoodsListBean> goodsListBeans = new ArrayList<>();

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
        allLessBodyRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                startActivity(DayInformationActivity.class);
            }
        });
        recyclerView.setAdapter(allLessBodyRecyAdapter);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new BaseItemDecoration(SystemUtil.dip2px(context, 0), SystemUtil.dip2px(context, 10)
                    , SystemUtil.dip2px(context, 10), SystemUtil.dip2px(context, 0)
                    , SystemUtil.dip2px(context, 10), SystemUtil.dip2px(context, 0)
                    , ContextCompat.getColor(context, R.color.noglay), context
                    , 2f, "7.1:12"));
        }
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.infortion_item_ll) startActivity(DayInformationActivity.class);
    }

    //get
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        //页面类型 1日托 2培训 3就业 4助残活动 5其他服务  必须
        httpParams.put("page_type", getIntent().getStringExtra("type"));
        LogUtils.e(getIntent().getStringExtra("type"));
//        //商品关键词
//        httpParams.put("keyword", AppConstant.KEY);
//        //分类编号
//        httpParams.put("gc_id", AppConstant.KEY);
//        //排序方式 0:综合排序 1销量 2：人气 3：价格
//        httpParams.put("sort_id", 0);
//        //排序类型 1：升序 2：降序
//        httpParams.put("order", 1);
//        //时长
//        httpParams.put("time_id", AppConstant.KEY);
//        //当前页码
//        httpParams.put("curpage", 1);
        return httpParams;
    }

    @Override
    protected void initDatas() {
        mPresenter.getListMsg(initParams());

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onGetListSuccess(Object msg) {
        goodsListBeans.addAll(((LessDayBean) msg).getGoods_list());
        allLessBodyRecyAdapter.notifyDataSetChanged();

    }

}
