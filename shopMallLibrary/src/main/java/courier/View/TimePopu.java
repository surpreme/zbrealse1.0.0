package courier.View;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

import level3.adapter.ArrayWheelAdapter;
import level3.widget.WheelView;


/**
 * 时间段选择
 */
public class TimePopu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity = null;
    private RelativeLayout rl_c;
    private WheelView wv_1,wv_2,wv_3,wv_4;
    private String time1="00",time2="00",time3="00",time4="00";
    public TimePopu(Activity activity) {
        mActivity = activity;
        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.3));
        setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.popupwindow_time, null);
        rl_c = (RelativeLayout) view.findViewById(R.id.rl_c);
        wv_1= (WheelView) view.findViewById(R.id.wv_1);
        wv_2= (WheelView) view.findViewById(R.id.wv_2);
        wv_3= (WheelView) view.findViewById(R.id.wv_3);
        wv_4= (WheelView) view.findViewById(R.id.wv_4);
        rl_c.setOnClickListener(this);
        initTime();
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置点击视图之外的地方是否取消当前的PopupWindow
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup4);
    }

    Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    WindowManager.LayoutParams lp = mActivity.getWindow()
                            .getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }

        ;
    };

    private void showEvent() {
        h.sendEmptyMessage(0);
//        h.sendEmptyMessageDelayed(0, 300);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showEvent();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showEvent();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showEvent();
    }

    @Override
    public void onClick(View v) {
        if (mmenu == null) return;
        switch (v.getId()) {//发起群聊
        }
        dismiss();
    }

    /**
     * 初始化时间
     */
    private void initTime(){
        List<String> hourslist=new ArrayList<>();
        List<String> minuteslist=new ArrayList<>();
        for (int i=0;i<24;i++){
            if (i<10){
                hourslist.add("0"+i);
            }else{
                hourslist.add(i+"");
            }
        }
        for (int i=0;i<60;i++){
            if (i<10){
                minuteslist.add("0"+i);
            }else{
                minuteslist.add(i+"");
            }
        }
        wv_1.setWheelAdapter(new ArrayWheelAdapter<String>(mActivity));
        wv_1.setSkin(WheelView.Skin.Holo);
        wv_1.setWheelData(hourslist);

        wv_2.setWheelAdapter(new ArrayWheelAdapter<String>(mActivity));
        wv_2.setSkin(WheelView.Skin.Holo);
        wv_2.setWheelData(minuteslist);

        wv_3.setWheelAdapter(new ArrayWheelAdapter<String>(mActivity));
        wv_3.setSkin(WheelView.Skin.Holo);
        wv_3.setWheelData(hourslist);

        wv_4.setWheelAdapter(new ArrayWheelAdapter<String>(mActivity));
        wv_4.setSkin(WheelView.Skin.Holo);
        wv_4.setWheelData(minuteslist);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 15;
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textSize = 12;
        wv_1.setStyle(style);
        wv_2.setStyle(style);
        wv_3.setStyle(style);
        wv_4.setStyle(style);
        wv_1.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                if (mmenu != null){
                    time1=s;
                    mmenu.onItemClick(time1+":"+time2,time3+":"+time4);
                }
//                dismiss();
            }
        });
        wv_2.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                if (mmenu != null){
                    time2=s;
                    mmenu.onItemClick(time1+":"+time2,time3+":"+time4);
                }
//                dismiss();
            }
        });
        wv_3.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                if (mmenu != null){
                    time3=s;
                    mmenu.onItemClick(time1+":"+time2,time3+":"+time4);
                }
//                dismiss();
            }
        });
        wv_4.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                if (mmenu != null){
                    time4=s;
                    mmenu.onItemClick(time1+":"+time2,time3+":"+time4);
                }
//                dismiss();
            }
        });
    }


    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(String starttime,String endtime);
    }

}
