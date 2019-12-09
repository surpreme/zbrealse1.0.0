package com.aite.a.view;

import com.aiteshangcheng.a.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.PopupWindow;

public class MyDatepopu extends PopupWindow {
	private Activity mActivity = null;
	private CalendarView calendarView1;
	private String time1 = "";

	public MyDatepopu(Activity activity) {
		mActivity = activity;

		WindowManager wm = mActivity.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		setHeight((int) (height * 0.6));
		// setHeight(LayoutParams.WRAP_CONTENT);
		View view = View.inflate(mActivity, R.layout.timechoose_popu, null);
		calendarView1 = (CalendarView) view.findViewById(R.id.calendarView1);
		// Long nowTime = calendarView1.getDate();
		// SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		// String time = f.format(nowTime);
		
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
				WindowManager.LayoutParams lp = mActivity.getWindow()
						.getAttributes();
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
				WindowManager.LayoutParams lp = mActivity.getWindow()
						.getAttributes();
				lp.alpha = 0.8f;
				mActivity.getWindow().setAttributes(lp);
				break;
			}
		};
	};

	private void showEvent() {
		h.sendEmptyMessageDelayed(0, 500);
	}

	private CalendarView.OnDateChangeListener listener = new CalendarView.OnDateChangeListener() {
		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			String monthh = "", dayOfMonthh = "";
			month = month + 1;
			if (month < 10) {
				monthh = "0" + month;
			} else {
				monthh = month + "";
			}
			if (dayOfMonth < 10) {
				dayOfMonthh = "0" + dayOfMonth;
			} else {
				dayOfMonthh = dayOfMonth + "";
			}
			time1 = year + "-" + monthh + "-" + dayOfMonthh;
			dismiss();
			mdate.onItemClick(time1);
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

	private date mdate = null;

	public void setdate(date p) {
		mdate = p;
	}

	public interface date {
		void onItemClick(String year);
	}
}
