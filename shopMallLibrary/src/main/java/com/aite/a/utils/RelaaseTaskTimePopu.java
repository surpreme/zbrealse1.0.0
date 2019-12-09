package com.aite.a.utils;

import com.aiteshangcheng.a.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.PopupWindow;


/**
 * 服务时间选择
 * 
 * @author Administrator
 *
 */
public class RelaaseTaskTimePopu extends PopupWindow implements OnClickListener {
	private Activity mActivity = null;
	private CalendarView calendarView1;

	public RelaaseTaskTimePopu(Activity activity) {
		mActivity = activity;
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高LayoutParams.WRAP_CONTENT
		setHeight(LayoutParams.WRAP_CONTENT);
		WindowManager wm = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		View view = View.inflate(mActivity, R.layout.time_choose, null);
		calendarView1 = (CalendarView) view.findViewById(R.id.calendarView1);
		// 监听时间
		calendarView1.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				gettime.onItemClick(year + "-" + (month + 1) + "-" + dayOfMonth);
				dismiss();
			}
		});
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
		// 外部可被操作
		setOutsideTouchable(false);
		setAnimationStyle(R.style.AnimBottomPopupp2);
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
			case 1:
				dismiss();
				break;
			}
		};
	};

	private void showEvent() {
		h.sendEmptyMessageDelayed(0, 500);
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

	}

	// 分类回调
	public getTime gettime = null;

	public void setgetTime(getTime time) {
		gettime = time;
	}

	public interface getTime {
		void onItemClick(String timee);
	}

	private void touming(ListView view, int x) {
		Animation animation = AnimationUtils.loadAnimation(mActivity, x);
		view.startAnimation(animation);
	}

}
