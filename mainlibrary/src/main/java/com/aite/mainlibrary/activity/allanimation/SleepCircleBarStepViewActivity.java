package com.aite.mainlibrary.activity.allanimation;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.view.CircleBarStepView;
import com.lzy.basemodule.view.CircleBarStepView2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 不初始化会出错 环形进度条
 */
public class SleepCircleBarStepViewActivity extends BaseActivity {
    @BindView(R2.id.circle)
    CircleProgressBar circle;
    @BindView(R2.id.circle_progress)
    CircleBarStepView2 mCircleProgress;

    @Override
    protected int getLayoutResId() {
        return R.layout.sleepprogreebar;
    }

    @Override
    protected void initView() {
        initToolbar("睡眠", Color.WHITE);
//        sleepCircleBarStepView.setMyFootNum(4500);
        mCircleProgress.setMax(100);
        mCircleProgress.setProgressWithAnimation(50);
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
