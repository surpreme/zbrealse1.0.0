package com.aite.a.activity.li.util;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.activity.li.adapter.PopAdapter;
import com.aiteshangcheng.a.R;

import org.w3c.dom.Text;

import java.util.List;

public class PopupWindowUtil {
    private static PopupWindowUtil mInstance;
    private static int nearbottomlayoutid = R.layout.near_pop_layout;
    private static int protectedlayoutid = R.layout.protected_pop;

    private PopupWindow popupWindow;

    public static PopupWindowUtil getmInstance() {
        if (mInstance == null) {
            synchronized (PopupWindowUtil.class) {
                if (mInstance == null) {
                    mInstance = new PopupWindowUtil();
                }
            }
        }
        return mInstance;
    }

    public void setProtectedPop(final Context context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(protectedlayoutid, null);
        popupWindow = new PopupWindow(view, 800, LinearLayout.LayoutParams.WRAP_CONTENT, false);
//        lottieAnimationView.playAnimation();   //播放
        popupWindow.setOutsideTouchable(false);
        popupWindow.setContentView(view);
        TextView textView = view.findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    public void setNearbottomPop(final Context context, View view, final List<String> list, PopAdapter.OnclickInterface onclickInterface) {
        @SuppressLint("InflateParams") View more_view = LayoutInflater.from(context).inflate(nearbottomlayoutid, null);
        setBackGroundAlpha(1.0f, context);
        popupWindow = new PopupWindow(more_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(more_view);
        popupWindow.showAsDropDown(view);
        RecyclerView recyclerView = more_view.findViewById(R.id.pop_recy);

//        final List<String> list = new ArrayList<>();
//        list.add("附近");
//        list.add("500m以内");
//        list.add("1km-2km");
//        list.add("2km-5km");
//        list.add("5km以上");

        PopAdapter popAdapter = new PopAdapter(context, list);
        recyclerView.setAdapter(popAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        popAdapter.setOnclickInterface(onclickInterface);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                dismissPopWindow();

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
