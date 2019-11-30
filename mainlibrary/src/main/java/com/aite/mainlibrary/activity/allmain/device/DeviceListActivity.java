package com.aite.mainlibrary.activity.allmain.device;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.DeviceInformationActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceListActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.device_information_ll)
    LinearLayout deviceInformationLl;
    @BindView(R2.id.search_img)
    ImageView searchImg;

    @Override
    protected int getLayoutResId() {
        return R.layout.device_list;
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(this);
        deviceInformationLl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.device_information_ll) startActivity(DeviceInformationActivity.class);
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
