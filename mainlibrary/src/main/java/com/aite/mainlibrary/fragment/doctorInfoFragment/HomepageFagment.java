package com.aite.mainlibrary.fragment.doctorInfoFragment;

import android.view.View;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.DoctorListAdapter;
import com.aite.mainlibrary.adapter.HospitalListsAdapter;
import com.lzy.basemodule.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 创建时间 2019/12/11 18:54
 * 描述:  主页
 */
public class HomepageFagment extends BaseFragment {


    @BindView(R2.id.tv_site)
    TextView mTvSite;
    @BindView(R2.id.recy_view1)
    RecyclerView mRecyView1;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private HospitalListsAdapter mAdapter;

    @Override
    protected void initModel() {

    }

    @Override
    protected void initViews() {

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            data.add("222");
        }
        mAdapter = new HospitalListsAdapter(context);
        mRecyView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRecyView1.setNestedScrollingEnabled(false);
        mAdapter.appendData(data);
        mRecyView1.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void onClick(View v) {

    }
}