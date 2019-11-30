package com.aite.mainlibrary.activity.allsetting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSosUserActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.add_sos_user;
    }

    @Override
    protected void initView() {
        tvTitle.setText("添加紧急联系人");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
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
