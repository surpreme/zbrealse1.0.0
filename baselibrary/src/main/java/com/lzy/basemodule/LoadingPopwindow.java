package com.lzy.basemodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingPopwindow {
    private static LoadingPopwindow mInstance;
    private PopupWindow popupWindow;
    private static final int loaddinglayoutid = R.layout.lodding_layout;

    public static LoadingPopwindow getmInstance() {
        if (mInstance == null) {
            synchronized (LoadingPopwindow.class) {
                if (mInstance == null) {
                    mInstance = new LoadingPopwindow();
                }
            }
        }
        return mInstance;
    }

    public void showloaddingPopupWindow(final Context context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(loaddinglayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(view, 700, 500, false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);

            }
        });

    }

    public void dismissPopWindow() {
        if (popupWindow != null) popupWindow.dismiss();
    }

    void setBackGroundAlpha(float alpha, Context context) {

        WindowManager.LayoutParams layoutParams = ((AppCompatActivity) context).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((AppCompatActivity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((AppCompatActivity) context).getWindow().setAttributes(layoutParams);
    }
}
