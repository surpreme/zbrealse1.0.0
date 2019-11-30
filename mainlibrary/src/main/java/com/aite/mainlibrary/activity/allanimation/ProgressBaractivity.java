package com.aite.mainlibrary.activity.allanimation;

import android.animation.ValueAnimator;
import android.graphics.Color;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;

public class ProgressBaractivity extends BaseActivity {
    @BindView(R2.id.circleProgressBar_id)
    CircleProgressBar circleProgressBar_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.progreebar;
    }

    @Override
    protected void initView() {
        initToolbar("运动", Color.WHITE);
        ValueAnimator animator = ValueAnimator.ofInt(0, 90);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                if (circleProgressBar_id != null)
                    circleProgressBar_id.setProgress(progress);
            }
        });
//        animator.setRepeatCount(ValueAnimator.INFINITE);动画次数
        animator.setRepeatCount(0);
        animator.setDuration(4000);
        animator.start();
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
//        crilebarProgress.setProgressNum(90, 3000);
//        crilebarProgress.setMaxNum(100);
////        crilebarProgress.setTextView();
//        TextView textView = new TextView(context);
//        crilebarProgress.setTextView(textView);