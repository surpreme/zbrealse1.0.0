package com.lzy.basemodule.util.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.lzy.basemodule.R;

public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (context == null && msg == null) return;
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);

        } else {
            toast.setText(msg);
        }
        toast.show();
    }
    public static void showToast(Context context, String msg ,int gravity) {
        if (context == null && msg == null) return;
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(gravity, 0, 0);
            View view = LayoutInflater.from(context).inflate(R.layout.error_toast, null);
//            TextView textView=view.findViewById(R.id.error_tv);
//            toast.setView(view);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showSnakbar(View view, String msg, View.OnClickListener onClickListener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("确定", onClickListener).show();
    }

}
