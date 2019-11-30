package com.aite.mainlibrary.activity.allsetting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBindingUserActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.bottom_btn)
    Button bottomBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.add_binding_user;
    }

    @Override
    protected void initView() {
        tvTitle.setText("添加新账号");
        bottomBtn.setText("确认");
        bottomBtn.setOnClickListener(this);
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
