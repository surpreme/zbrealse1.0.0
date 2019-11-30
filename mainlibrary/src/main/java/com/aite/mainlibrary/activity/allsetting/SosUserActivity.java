package com.aite.mainlibrary.activity.allsetting;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.AddSosUserActivity;
import com.aite.mainlibrary.adapter.SosUserRecyAdapter;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SosUserActivity extends BaseActivity {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private SosUserRecyAdapter sosUserRecyAdapter;

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
        sosUserRecyAdapter = new SosUserRecyAdapter(this, null, null);
        recyclerView.setAdapter(sosUserRecyAdapter);
        sosUserRecyAdapter.notifyDataSetChanged();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
