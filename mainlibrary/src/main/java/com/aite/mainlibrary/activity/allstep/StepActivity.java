package com.aite.mainlibrary.activity.allstep;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.view.StatusBarUtils;
import com.lzy.basemodule.view.StepsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.stepview)
    StepsView stepview;
    private int mprogress = 5;


    @Override
    protected int getLayoutResId() {
        return R.layout.sign_in_day_layout;
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(this);
        stepview.setOnClickListener(this);
        initToolbar("签到");
        StatusBarUtils.setColor(context, getResources().getColor(R.color.blue_day_login));
        setInitStepview(24, mprogress);

    }

    private void setInitStepview(int month, int progress) {
        stepview.setMonthDays(month);
        stepview.setProgress(progress);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.stepview) {
            mprogress++;
            setInitStepview(31, mprogress);
        }
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
