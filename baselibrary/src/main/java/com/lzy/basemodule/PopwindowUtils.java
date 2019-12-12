package com.lzy.basemodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.view.PickerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopwindowUtils {
    private static PopwindowUtils mInstance;
    private PopupWindow popupWindow;
    private static final int loaddinglayoutid = R.layout.lodding_anim_layout;
    private static final int loaddingdatalayoutid = R.layout.datalodding_anim_layout;
    private static final int newuertogtherinformaionlayoutid = R.layout.together_infomation_pop;
    private static final int errorlayoutid = R.layout.error_toast;
    private static final int recylayoutid = R.layout.recy_layout;
    private static final int threeRecylayoutid = R.layout.three_choice;
    private static final int chioceGenderlayoutid = R.layout.choice_gender;
    private static final int bootomrecylayoutid = R.layout.choice_bottom;
    private static final int qrcodelayoutid = R.layout.pop_img;
    private static final int payawylayoutid = R.layout.pop_pay_awy;
    private static final int choicethreerecylayoutid = R.layout.choice_three_recy;

    public static PopwindowUtils getmInstance() {
        if (mInstance == null) {
            synchronized (PopwindowUtils.class) {
                if (mInstance == null) {
                    mInstance = new PopwindowUtils();
                }
            }
        }
        return mInstance;
    }


    public void showloaddingPopupWindow(final Activity context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(loaddinglayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(view, 1000, 700, false);
        final LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
//        lottieAnimationView.playAnimation();   //播放

        popupWindow.setOutsideTouchable(false);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                lottieAnimationView.cancelAnimation();  //取消
            }
        });

    }

    public void showImgPopupWindow(final Context context, String url) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(qrcodelayoutid, null);
        setBackGroundAlpha(0.6f, context);
        popupWindow = new PopupWindow(view, 1000, 700, false);
        ImageView qrcode_iv = view.findViewById(R.id.qrcode_iv);
        Glide.with(context).load(url).into(qrcode_iv);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
            }
        });

    }

    class oo {
        int a;
        List<bb> b;

        class bb {
            int c;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showThreeRecyPopupWindow(final Context context, List<String> yearlist, List<String> mondaylist, View ui) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(choicethreerecylayoutid, null);
        setBackGroundAlpha(1.0f, context);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, 330, false);
        PickerView yearPick = view.findViewById(R.id.year_picker);
        PickerView dayPick = view.findViewById(R.id.day_picker);
        yearPick.setDataList(yearlist);
        yearPick.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(View view, String selected) {
                dayPick.setDataList(mondaylist);

            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.showAsDropDown(ui, 0, 0, Gravity.CENTER);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
            }
        });

    }

    public void showChioceGenderPopupWindow(final Context context, int grivaty, float alpah, OnClickLstenerInterface.OnThingClickInterface onThingClickInterface) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(chioceGenderlayoutid, null);
        setBackGroundAlpha(alpah, context);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        TextView superman_tv = view.findViewById(R.id.superman_tv);
        TextView woman_tv = view.findViewById(R.id.woman_tv);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);

        superman_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onThingClickInterface.getString("superman");
                popupWindow.dismiss();

            }
        });
        woman_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onThingClickInterface.getString("woman");
                popupWindow.dismiss();

            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, grivaty, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                dismissPopWindow();
            }
        });

    }

    public void showChioceBottomPopupWindow(final Context context, int grivaty, float alpah, RecyclerView.Adapter adapter) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(bootomrecylayoutid, null);
        setBackGroundAlpha(alpah, context);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        popupWindow.setFocusable(true);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        recyclerView.setLayoutManager(linearLayoutManager);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, grivaty, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                dismissPopWindow();
            }
        });

    }

    public void showRecyPopupWindow(final Context context, RecyclerView.Adapter recyadpater, LinearLayoutManager linearLayoutManager, int grivaty, float alpah) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(recylayoutid, null);
        setBackGroundAlpha(alpah, context);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        popupWindow.setFocusable(true);

        //图片设置透明度
//        recyclerView.setAlpha(0.4f);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyadpater);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
//        popupWindow.showAsDropDown(ui, 0, 0);
        popupWindow.showAtLocation(view, grivaty, 0, 10);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                dismissPopWindow();
            }
        });

    }

    public void showRecyPopupWindow(final Context context, RecyclerView.Adapter recyadpater, RecyclerView.LayoutManager layoutManager, View ui) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(recylayoutid, null);
        setBackGroundAlpha(1.0f, context);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        popupWindow.setFocusable(true);

        //图片设置透明度
//        recyclerView.setAlpha(0.4f);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyadpater);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(ui, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                recyclerView.setAlpha(1.0f);
                dismissPopWindow();
            }
        });

    }

    public void showRecyPopupWindow(final Context context, RecyclerView.Adapter recyadpater, LinearLayoutManager linearLayoutManager, View ui, PopupWindow.OnDismissListener onDismissListeners) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(recylayoutid, null);
        setBackGroundAlpha(1.0f, context);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        popupWindow.setFocusable(true);

        //图片设置透明度
//        recyclerView.setAlpha(0.4f);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyadpater);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(ui, 0, 0);
        popupWindow.setOnDismissListener(onDismissListeners);


    }


    public void showPayRecyPopupWindow(final Context context, int gravity, RecyclerView.Adapter adapter, LinearLayoutManager linearLayoutManager) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(payawylayoutid, null);
//        ButterKnife.bind(this,view);
        setBackGroundAlpha(0.7f, context);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setFocusable(false);
        //设置触摸
        popupWindow.setTouchable(true);
        RecyclerView recycler_view = view.findViewById(R.id.recycler_view);
        ImageView closeiv = view.findViewById(R.id.close_iv);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(adapter);
        closeiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopWindow();
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, gravity, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                dismissPopWindow();
            }
        });

    }

    public void showAsDownPopupWindow(final Activity activity, String errormsg) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(errorlayoutid, null);
        setBackGroundAlpha(0.6f, activity);
        popupWindow = new PopupWindow(view, 1000, 100, false);
        final TextView errorTv = view.findViewById(R.id.error_tv);
        errorTv.setText(errormsg);
        ImageView background = view.findViewById(R.id.notification_background_img);
        //图片设置透明度
        background.setAlpha(0.4f);
//        lottieAnimationView.playAnimation();   //播放
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, activity);
            }
        });

    }

    public void showErrorPopupWindow(final Activity activity, String errormsg) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(errorlayoutid, null);
        setBackGroundAlpha(0.6f, activity);
        popupWindow = new PopupWindow(view, 1000, 100, false);
        final TextView errorTv = view.findViewById(R.id.error_tv);
        errorTv.setText(errormsg);
        ImageView background = view.findViewById(R.id.notification_background_img);
        //图片设置透明度
        background.setAlpha(0.4f);
//        lottieAnimationView.playAnimation();   //播放
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, activity);
            }
        });

    }

    public void showNewUerTogtherInformaionPopupWindow(final Activity context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(newuertogtherinformaionlayoutid, null);
        setBackGroundAlpha(0.5f, context);
        popupWindow = new PopupWindow(view,
                context.getResources().getDisplayMetrics().widthPixels * 3 / 4,
                context.getResources().getDisplayMetrics().heightPixels * 3 / 4,
                true);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_view);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) popupWindow.dismiss();
            }
        });
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

    public void showloaddingDataPopupWindow(final Context context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(loaddingdatalayoutid, null);
        setBackGroundAlpha(0.5f, context);
        popupWindow = new PopupWindow(view, 1000, 700, false);
        final LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        lottieAnimationView.playAnimation();   //播放

        popupWindow.setOutsideTouchable(false);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1.0f, context);
                lottieAnimationView.cancelAnimation();  //取消
            }
        });

    }

    public void dismissPopWindow() {
        popupWindow.dismiss();
    }


    void setBackGroundAlpha(float alpha, Context context) {
        WindowManager.LayoutParams layoutParams = ((AppCompatActivity) context).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((AppCompatActivity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((AppCompatActivity) context).getWindow().setAttributes(layoutParams);
    }
}
// lottieLike.playAnimation();   //播放
//        lottieLike.pauseAnimation();  //暂停
//        lottieLike.cancelAnimation();  //取消
//        lottieLike.getDuration();   //获取动画时长
//        lottieLike.addAnimatorListener(new Animator.AnimatorListener() {  //添加动画监听
//        @Override
//        public void onAnimationStart(Animator animation) {
//
//        }
//
//        @Override
//        public void onAnimationEnd(Animator animation) {
//
//        }
//
//        @Override
//        public void onAnimationCancel(Animator animation) {
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animator animation) {
//
//        }
//    });

/**
 * popwindow需要延时操作 未初始化完成不可以显示（view）
 * //
 */
//    @SuppressLint("HandlerLeak")
//    private Handler popupHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
////                    PopwindowUtils.getmInstance().showloaddingDataPopupWindow(context);
//                    break;
//                case 1:
////                    PopwindowUtils.getmInstance().dismissPopWindow();
//                    break;
//
//            }
//        }
//
//    };
//        popupHandler.sendEmptyMessageDelayed(0, 1000);
//        popupHandler.sendEmptyMessageDelayed(1, 2000);