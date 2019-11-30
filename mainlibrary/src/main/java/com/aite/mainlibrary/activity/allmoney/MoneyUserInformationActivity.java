package com.aite.mainlibrary.activity.allmoney;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyUserInformationActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.recy_toolbar;
    }

    @Override
    protected void initView() {

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
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
    }

}
