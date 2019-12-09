package com.aite.a.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;
import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * 就是自定义的Dialog,不可back或点击外部销毁
 * 
 * @author liangshiquan
 * 
 */
public class Dialog {
	public final static int SELECT_DIALOG = 1;
	public final static int RADIO_DIALOG = 2;

	/**
	 * 创建一个内容多选对话框
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param items
	 *            数组
	 * @param dialogItemClickListener
	 *            监听点击的内容结果
	 * @return
	 */
	public static android.app.Dialog showListDialog(Context context, String title, String[] items, final DialogItemClickListener dialogItemClickListener) {
		return ShowDialog(context, title, items, dialogItemClickListener);
	}

	/**
	 * 创建一个单选对话框
	 * 
	 * @param context
	 * @param toast
	 *            提示消息
	 * @param dialogClickListener
	 *            点击监听
	 * @return
	 */
	public static android.app.Dialog showRadioDialog(Context context, String toast, final DialogClickListener dialogClickListener) {
		return ShowDialog(context, context.getString(R.string.message_tip).toString(), toast, dialogClickListener, RADIO_DIALOG);
	}

	/**
	 * 创建一个选择对话框
	 * 
	 * @param context
	 * @param toast
	 *            提示消息
	 * @param dialogClickListener
	 *            点击监听
	 * @return
	 */
	public static android.app.Dialog showSelectDialog(Context context, String toast, final DialogClickListener dialogClickListener) {
		return ShowDialog(context, context.getString(R.string.message_tip).toString(), toast, dialogClickListener, SELECT_DIALOG);
	}

	/**
	 * 创建一个选择对话框
	 * 
	 * @param context
	 * @param title
	 *            提示标题
	 * @param toast
	 *            提示消息
	 * @param dialogClickListener
	 *            点击监听
	 * @return
	 */
	public static android.app.Dialog showSelectDialog(Context context, String title, String toast, final DialogClickListener dialogClickListener) {
		return ShowDialog(context, title, toast, dialogClickListener, SELECT_DIALOG);
	}

	private static android.app.Dialog ShowDialog(Context context, String title, String toast, final DialogClickListener dialogClickListener, int DialogType) {
		final android.app.Dialog dialog = new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(false);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
		dialog.setContentView(view);
		((TextView) view.findViewById(R.id.point)).setText(title);
		((TextView) view.findViewById(R.id.toast)).setText(toast);
		if (DialogType == RADIO_DIALOG) {
		} else {
			view.findViewById(R.id.ok).setVisibility(View.GONE);
			view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
		}
		view.findViewById(R.id.bt_cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.cancel();
					}
				}, 200);
			}
		});
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				}, 200);
			}
		});
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				}, 200);
			}
		});
		Window mWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
			lp.width = getScreenHeight(context) / 10 * 8;
		} else {
			lp.width = getScreenWidth(context) / 10 * 8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}

	private static android.app.Dialog ShowDialog(Context context, String title, String[] items, final DialogItemClickListener dialogClickListener) {
		final android.app.Dialog dialog = new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(false);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_radio, null);
		dialog.setContentView(view);
		((TextView) view.findViewById(R.id.title)).setText(title);
		// 根据items动态创建
		LinearLayout parent = (LinearLayout) view.findViewById(R.id.dialogLayout);
		parent.removeAllViews();
		int length = items.length;
		for (int i = 0; i < items.length; i++) {
			LayoutParams params1 = new LayoutParams(-1, -2);
			params1.rightMargin = 1;
			final TextView tv = new TextView(context);
			tv.setLayoutParams(params1);
			tv.setTextSize(18);
			tv.setText(items[i]);
			tv.setTextColor(context.getResources().getColor(R.color.dialogTxtColor));
			int pad = context.getResources().getDimensionPixelSize(R.dimen.dp_10);
			tv.setPadding(pad, pad, pad, pad);
			tv.setSingleLine(true);
			tv.setGravity(Gravity.CENTER);
			if (i != length - 1)
				tv.setBackgroundResource(R.drawable.menudialog_center_selector);
			else
				tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);

			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					dialogClickListener.confirm(tv.getText().toString());
				}
			});
			parent.addView(tv);
			if (i != length - 1) {
				TextView divider = new TextView(context);
				LayoutParams params = new LayoutParams(-1, (int) 1);
				divider.setLayoutParams(params);
				divider.setBackgroundResource(android.R.color.darker_gray);
				parent.addView(divider);
			}
		}
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		Window mWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.width = getScreenWidth(context);
		mWindow.setGravity(Gravity.BOTTOM);
		// 添加动画
		mWindow.setWindowAnimations(R.style.dialogAnim);
		mWindow.setAttributes(lp);
		dialog.show();
		return dialog;
	}

	/** 获取屏幕分辨率宽 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/** 获取屏幕分辨率高 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public interface DialogClickListener {
		public abstract void confirm();

		public abstract void cancel();
	}

	public interface DialogItemClickListener {
		public abstract void confirm(String result);
	}

	public Snackbar showNormalSnackBar(String message , View baseView , int gravity){
		Snackbar snackbar = Snackbar.make(baseView, message, Snackbar.LENGTH_LONG);
		Snackbar.SnackbarLayout view = (Snackbar.SnackbarLayout) snackbar.getView();
		setSnackBarBackground(view);
//        setSite(snackbar, gravity);
		snackbar.show();
		return snackbar;
	}

	public static   void showSnackBarWithAction(String message , View baseView, boolean doAction ,String actionMsg, final SnackbarActionListener actionListener) {
		//"                                    "
		final Snackbar snackbar = Snackbar.make(baseView, message, Snackbar.LENGTH_LONG);
		Snackbar.SnackbarLayout view = (Snackbar.SnackbarLayout) snackbar.getView();
		view.setBackgroundColor(view.getResources().getColor(R.color.commRed));
		TextView textView = view.findViewById(R.id.snackbar_text);
		textView.setTextColor(0xff333333);
		textView.setTextSize(ClutterUtils.dp2px(view.getContext(),5));
		if (doAction) {
			// 设置动作按钮颜色
//        button.setBackgroundColor(0xffffffff);
//        snackbar.setActionTextColor(getResources().getColor(R.color.add_bg_color));
			Button button = view.findViewById(R.id.snackbar_action);
//        button.setPadding(dp10,0,dp5,0);
			button.setTextColor(0xffffffff);
//      添加icon图标
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER_VERTICAL ;
//        layoutParams.setMargins(0,dp5,0,dp5);
//        button.setLayoutParams(layoutParams);

			snackbar.setAction(actionMsg, new OnClickListener() {
				@Override
				public void onClick(View view) {


//            CommonUtils.showToastInUi(baseContext, "buzaitishi");
					if (actionListener != null)
						actionListener.doAction(snackbar);
				}
			});
		}
//		setSite(snackbar, gravity);

		snackbar.show();
	}

	private void setSnackBarBackground(Snackbar.SnackbarLayout view) {
		view.setBackgroundColor(view.getResources().getColor(R.color.commRed));
		TextView textView = view.findViewById(R.id.snackbar_text);
		textView.setTextColor(0xff213B1E);
		textView.setTextSize(ClutterUtils.dp2px(view.getContext(),5));
	}


	private void setSite (Snackbar snackbar,int gravity) {
		// 获取 snackbar 视图
		View snackbarView = snackbar.getView();
		ViewGroup.LayoutParams vl = snackbarView.getLayoutParams();
		CoordinatorLayout.LayoutParams cl = new CoordinatorLayout.LayoutParams(vl.width, vl.height);
//        cl.gravity = Gravity.CENTER_VERTICAL;
		cl.gravity = gravity;
		snackbarView.setLayoutParams(cl);
	}

	public interface SnackbarActionListener{
		void doAction(Snackbar snackbar);
	}
}