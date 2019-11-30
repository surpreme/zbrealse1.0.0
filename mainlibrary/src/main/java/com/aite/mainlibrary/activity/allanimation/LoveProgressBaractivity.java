package com.aite.mainlibrary.activity.allanimation;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.view.CircleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoveProgressBaractivity extends BaseActivity {
    //    @BindView(R2.id.crilebar_progress)
//    CircleBarView crilebarProgress;
    @BindView(R2.id.circleProgressBar_id)
    CircleProgressBar circleProgressBarId;

    @Override
    protected int getLayoutResId() {
        return R.layout.loveprogreebar;
    }

    /**
     * 动画出错 空指针
     */
    @Override
    protected void initView() {
        initToolbar("心率", Color.WHITE);
        ValueAnimator animator = ValueAnimator.ofInt(0, 75);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                if (circleProgressBarId != null)
                    circleProgressBarId.setProgress(progress);
            }
        });
//        animator.setRepeatCount(ValueAnimator.INFINITE);动画次数
        animator.setRepeatCount(0);
        animator.setDuration(4000);
        animator.start();
//        crilebarProgress.setProgressNum(90, 3000);
//        crilebarProgress.setMaxNum(100);
//        crilebarProgress.setTextView();
//        TextView textView = new TextView(context);
//        crilebarProgress.setTextView(textView);
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
    public void showError(String msg) {

    }
}
//        crilebarProgress.setProgressNum(90, 3000);
//        crilebarProgress.setMaxNum(100);
////        crilebarProgress.setTextView();
//        TextView textView = new TextView(context);
//        crilebarProgress.setTextView(textView);