package com.aite.mainlibrary.activity.allsetting.sosuser;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.BinderSosUserListBean;
import com.aite.mainlibrary.Mainbean.BinderUserListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.addsosuser.AddSosUserActivity;
import com.aite.mainlibrary.adapter.BinSosderUserRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SosUserActivity extends BaseActivity<SosUserContract.View, SosUserPresenter> implements SosUserContract.View {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    private List<BinderSosUserListBean.ListBean> binderSosUserListBeans = new ArrayList<>();
    private BinSosderUserRecyAdapter binSosderUserRecyAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.sos_user;
    }

    @Override
    protected void initView() {
        initToolbar("紧急联系人");
        initBottomBtn("新增紧急联系人", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddSosUserActivity.class);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        binSosderUserRecyAdapter = new BinSosderUserRecyAdapter(context, binderSosUserListBeans);
        recyclerView.setAdapter(binSosderUserRecyAdapter);
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    protected void initDatas() {
        mPresenter.getUserSoslistInformation(initParams());

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetSoslistUserInformation(Object msg) {
        binderSosUserListBeans.addAll(((BinderSosUserListBean) msg).getList());
        binSosderUserRecyAdapter.notifyDataSetChanged();


    }
}
