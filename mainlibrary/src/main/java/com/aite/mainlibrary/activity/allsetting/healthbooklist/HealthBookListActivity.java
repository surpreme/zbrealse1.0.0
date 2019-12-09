package com.aite.mainlibrary.activity.allsetting.healthbooklist;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.HealthListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.addhealthbook.AddHealthBookActivity;
import com.aite.mainlibrary.adapter.HealthBookRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HealthBookListActivity extends BaseActivity<HealthBookListContract.View, HealthBookListPresenter> implements HealthBookListContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_title_right)
    TextView tvTitleRight;
    private HealthBookRecyAdapter healthBookRecyAdapter;
    private List<HealthListBean.DatasBean> datasBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.recy_right_toolbar;
    }

    //信息类型,1为疾病史,2为医疗笔记,3为过敏反应,4为药物使用
    @Override
    protected void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        switch (getIntent().getStringExtra("type")) {
            case "1":
                tvTitle.setText("疾病史");
                break;
            case "2":
                tvTitle.setText("医疗笔记");
                break;
            case "3":
                tvTitle.setText("过敏反应");
                break;
            case "4":
                tvTitle.setText("药物使用");
                break;
            default:
                break;
        }
        tvTitleRight.setText("添加");
        tvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddHealthBookActivity.class, "type", getIntent().getStringExtra("type"));

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(healthBookRecyAdapter = new HealthBookRecyAdapter(context, datasBeanList));
        healthBookRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
                LogUtils.d(postion);
            }
        });
    }

    @Override
    protected void initDatas() {
        mPresenter.GetInformationList(initParams());

    }

    /**
     * 信息类型,1为疾病史,2为医疗笔记,3为过敏反应,4为药物使用
     * curpage    pagesize
     *
     * @return
     */
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("type", getIntent().getStringExtra("type"));
        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetInformationListSuccess(Object msg) {
        if (((HealthListBean) msg).getDatas() != null) {
            datasBeanList.addAll(((HealthListBean) msg).getDatas());
            healthBookRecyAdapter.notifyDataSetChanged();
        }

    }



}
