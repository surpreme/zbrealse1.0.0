package com.aite.mainlibrary.activity.allsetting.minepostbookchriend;


import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Constant.MainUIConstant;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.IconHormationRecyAdapter;
import com.aite.mainlibrary.adapter.PostServiceBookRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MinePostBookChriendActivity extends BaseActivity<MinePostBookChriendContract.View, MinePostBookChriendPresenter> implements MinePostBookChriendContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    private PostServiceBookRecyAdapter postServiceBookRecyAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.minepostbook_activity;
    }

    @Override
    protected void initView() {
        initToolbar("时间银行");
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(postServiceBookRecyAdapter = new PostServiceBookRecyAdapter(context, null));
        postServiceBookRecyAdapter.setClickInterface(new OnClickLstenerInterface.OnRecyClickInterface() {
            @Override
            public void getPostion(int postion) {
//                startActivity(MinePostBookChriendActivity.class);

            }
        });

    }

    @Override
    protected void initDatas() {
        mPresenter.getListInformation(initParams());

    }

    //get
    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
//        //页面类型 1日托 2培训 3就业 4助残活动 5其他服务  必须
//        httpParams.put("page_type", getIntent().getStringExtra("type"));
        //当前页码
        httpParams.put("curpage", 1);
        return httpParams;
    }


    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetListInformationSuccess(Object msg) {

    }
}
