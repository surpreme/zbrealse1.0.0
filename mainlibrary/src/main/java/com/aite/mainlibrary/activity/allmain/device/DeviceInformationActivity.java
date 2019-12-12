package com.aite.mainlibrary.activity.allmain.device;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allanimation.LoveProgressBaractivity;
import com.aite.mainlibrary.activity.allanimation.ProgressBaractivity;
import com.aite.mainlibrary.activity.allanimation.SleepCircleBarStepViewActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceInformationActivity extends BaseActivity {
    @BindView(R2.id.love_ll)
    LinearLayout love_ll;
    @BindView(R2.id.foot_ll)
    LinearLayout footLl;
    @BindView(R2.id.sleep_ll)
    LinearLayout sleepLl;


    @Override
    protected int getLayoutResId() {
        return R.layout.device_information;
    }

    @Override
    protected void initView() {
        initToolbar("小米手环");
        love_ll.setOnClickListener(this);
        footLl.setOnClickListener(this);
        sleepLl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.love_ll) startActivity(LoveProgressBaractivity.class);
        if (v.getId() == R.id.foot_ll) startActivity(ProgressBaractivity.class);
        if (v.getId() == R.id.sleep_ll) startActivity(SleepCircleBarStepViewActivity.class);
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
