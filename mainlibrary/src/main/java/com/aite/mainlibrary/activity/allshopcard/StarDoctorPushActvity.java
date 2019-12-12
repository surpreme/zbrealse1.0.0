package com.aite.mainlibrary.activity.allshopcard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.DoctorListAdapter;
import com.aite.mainlibrary.base.OnClickRecyclerViewListener;
import com.lzy.basemodule.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StarDoctorPushActvity extends BaseActivity {

    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.ll_nationwide)
    LinearLayout mLlNationwide;
    @BindView(R2.id.ll_division)
    LinearLayout mLlDivision;
    @BindView(R2.id.buy_tv)
    TextView mBuyTv;
    @BindView(R2.id.buy_top_img)
    ImageView mBuyTopImg;
    @BindView(R2.id.buy_choice_ll)
    LinearLayout mBuyChoiceLl;
    @BindView(R2.id.ll_sales)
    LinearLayout mLlSales;
    @BindView(R2.id.away_tv)
    TextView mAwayTv;
    @BindView(R2.id.away_top_img)
    ImageView mAwayTopImg;
    @BindView(R2.id.away_choice_ll)
    LinearLayout mAwayChoiceLl;
    @BindView(R2.id.ll_screen)
    LinearLayout mLlScreen;
    @BindView(R2.id.recy_view)
    RecyclerView mRecyView;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    //医生列表
    private DoctorListAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.star_doctor_push;
    }

    @Override
    protected void initView() {
        initToolbar("名医列表");

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("111");
        }
        mAdapter = new DoctorListAdapter(context);
        mRecyView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRecyView.setNestedScrollingEnabled(false);
        mAdapter.appendData(data);
        mRecyView.setAdapter(mAdapter);

        initListener();

    }

    private void initListener() {
        mAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(DoctorInfoActivity.class);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }


    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @OnClick({R2.id.ll_nationwide, R2.id.ll_division, R2.id.ll_sales, R2.id.ll_screen})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.ll_nationwide) {
            //全国
        } else if (id == R.id.ll_division) {
            //科室
        } else if (id == R.id.ll_sales) {
            //销量
        } else if (id == R.id.ll_screen) {
            //筛选
        }
    }
}
