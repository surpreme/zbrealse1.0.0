package com.aite.a.view;

import java.text.SimpleDateFormat;

import com.aiteshangcheng.a.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;


public class MyDatepopu2 extends PopupWindow implements OnClickListener {
    private Activity mActivity = null;
    private TextView tv_time, tv_determine, tv_date;
    private RelativeLayout rl_title;
    private TimePicker tp_time;
    private CalendarView calendarView1;
    private String time1 = "", time2 = "";
    private int type;

    public MyDatepopu2(Activity activity, int type) {
        this.type = type;
        mActivity = activity;

        WindowManager wm = mActivity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight((int) (height * 0.6));
//		setHeight(LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mActivity, R.layout.timechoose2_popu, null);
        tp_time = (TimePicker) view.findViewById(R.id.tp_time);
        calendarView1 = (CalendarView) view.findViewById(R.id.calendarView1);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_determine = (TextView) view.findViewById(R.id.tv_determine);
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        tv_determine.setOnClickListener(this);
//		Calendar calendar = Calendar.getInstance(); // 获取当前时间
//		String year = calendar.get(Calendar.YEAR) + "";
//		String month = calendar.get(Calendar.MONTH) + 1 + "";
//		String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        // String hour=calendar.get(Calendar.HOUR_OF_DAY)+"";
        // String minute=calendar.get(Calendar.MINUTE)+"";
        // String second=calendar.get(Calendar.SECOND)+"";
        // String time=year+month+day+hour+minute+second;
//		int y = Integer.parseInt(year);
//		int m = Integer.parseInt(month);
//		int d = Integer.parseInt(day);
        tv_date.setOnClickListener(this);
        tv_time.setOnClickListener(this);


        // 是否使用24小时制
        tp_time.setIs24HourView(true);

        tp_time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time2 = hourOfDay + ":" + minute;
                tv_time.setText(hourOfDay + ":" + minute);
            }
        });
        Long nowTime = calendarView1.getDate();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String time = f.format(nowTime);
        tv_date.setText(time);
        time1 = time;
        calendarView1.setOnDateChangeListener(listener);

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
                WindowManager.LayoutParams lp = mActivity
                        .getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        // 设置PopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.AnimBottomPopup);

    }

    Handler h = new Handler() {
        // 显示玩popup后，改变背景透明度
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    WindowManager.LayoutParams lp = mActivity
                            .getWindow().getAttributes();
                    lp.alpha = 0.8f;
                    mActivity.getWindow().setAttributes(lp);
                    break;
            }
        }

        ;
    };

    private void showEvent() {
        h.sendEmptyMessageDelayed(0, 500);
    }

    private CalendarView.OnDateChangeListener listener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month,
                                        int dayOfMonth) {
            month = month + 1;
            time1 = year + "-" + month + "-" + dayOfMonth + " ";
            tv_date.setText(year + "-" + month + "-" + dayOfMonth + " ");
            if (type == 1) {

            } else if (type == 2) {
//				tv_determine.setBackgroundResource(R.drawable.bc_subscribe2);
//				tv_date.setTextColor(Color.WHITE);
//				tv_time.setTextColor(Color.WHITE);
//				tv_determine.setTextColor(0XFFFF4181);
//				rl_title.setBackgroundColor(0XFFFF4181);
                calendarView1.setVisibility(View.GONE);
                tp_time.setVisibility(View.VISIBLE);
            }
//			mdate.onItemClick(year + "", month + "", dayOfMonth + "");
            // System.out.println("-------------" + year + "-" + month + "-"
            // + dayOfMonth);
        }
    };


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
        if (v.getId() == R.id.tv_determine) {
            dismiss();
            mdate.onItemClick(time1 + time2, "", "");
        } else if (v.getId() == R.id.tv_date) {
            calendarView1.setVisibility(View.VISIBLE);
            tp_time.setVisibility(View.GONE);
        } else if (v.getId() == R.id.tv_time) {
            calendarView1.setVisibility(View.GONE);
            tp_time.setVisibility(View.VISIBLE);
        }

//		switch (v.getId()) {
//		case R.id.tv_determine:
//			dismiss();
//			mdate.onItemClick(time1+time2, "", "");
//			break;
//			case R.id.tv_date:
////				tv_determine.setBackgroundResource(R.drawable.bc_subscribe);
////				tv_date.setTextColor(0xffBABABA);
////				tv_time.setTextColor(0xffBABABA);
////				tv_determine.setTextColor(Color.WHITE);
////				rl_title.setBackgroundColor(Color.WHITE);
//				calendarView1.setVisibility(View.VISIBLE);
//				tp_time.setVisibility(View.GONE);
//				break;
//			case R.id.tv_time:
////				tv_determine.setBackgroundResource(R.drawable.bc_subscribe2);
////				tv_date.setTextColor(Color.WHITE);
////				tv_time.setTextColor(Color.WHITE);
////				tv_determine.setTextColor(0XFFFF4181);
////				rl_title.setBackgroundColor(0XFFFF4181);
//				calendarView1.setVisibility(View.GONE);
//				tp_time.setVisibility(View.VISIBLE);
//				break;
//		}
    }

    private date mdate = null;

    public void setdate(date p) {
        mdate = p;
    }

    public interface date {
        void onItemClick(String year, String month, String dayOfMonth);
    }
}
