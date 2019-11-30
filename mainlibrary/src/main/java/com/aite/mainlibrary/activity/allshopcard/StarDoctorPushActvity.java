package com.aite.mainlibrary.activity.allshopcard;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.DoctormainInformationActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarDoctorPushActvity extends BaseActivity {

    @BindView(R2.id.doctor_infortion_ll)
    LinearLayout doctorInfortionLl;

    @Override
    protected int getLayoutResId() {
        return R.layout.star_doctor_push;
    }

    @Override
    protected void initView() {
        initToolbar("名医列表");
        doctorInfortionLl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.doctor_infortion_ll)
            startActivity(DoctormainInformationActivity.class);

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
