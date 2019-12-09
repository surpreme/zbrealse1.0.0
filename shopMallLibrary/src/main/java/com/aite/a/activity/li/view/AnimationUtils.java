package com.aite.a.activity.li.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.aite.a.activity.li.util.PopupWindowUtil;

public class AnimationUtils {
    private static AnimationUtils mInstance;
    private RotateAnimation rotateAnimation;

    public static AnimationUtils getmInstance() {
        if (mInstance == null) {
            synchronized (AnimationUtils.class) {
                if (mInstance == null) {
                    mInstance = new AnimationUtils();
                }
            }
        }
        return mInstance;
    }

    public void RotateAnima(View view) {
      RotateAnima(view,null);
    }
    public void RotateAnima(View view,Animation.AnimationListener listener) {
        //中心旋转
        RotateAnimation rotateAnimation = new RotateAnimation(0,180f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        final RotateAnimation rotateAnimation=new RotateAnimation(
//                0,-270f, Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF, 0);
        rotateAnimation.setFillAfter( true );
        //rotateAnimation.setFillBefore( true );
        rotateAnimation.setDuration( 80 );
        rotateAnimation.setAnimationListener(listener);
        view.startAnimation( rotateAnimation );
    }

}
