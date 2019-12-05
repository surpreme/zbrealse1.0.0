package com.aite.mainlibrary.activity.allsetting.adressfix;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.addadrress.AddAdrressActivity;
import com.aite.mainlibrary.adapter.AdrressFixRecyAdapter;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AdressFixActivity extends BaseActivity<AdressFixContract.View, AdressFixPresenter> implements AdressFixContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private AdrressFixRecyAdapter adrressFixRecyAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.sos_user;
    }

    @Override
    protected void initView() {
        initToolbar("我的地址");
        initBottomBtn("添加新地址", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddAdrressActivity.class);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adrressFixRecyAdapter = new AdrressFixRecyAdapter(this, null, null);
        recyclerView.setAdapter(adrressFixRecyAdapter);
        adrressFixRecyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {


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


}