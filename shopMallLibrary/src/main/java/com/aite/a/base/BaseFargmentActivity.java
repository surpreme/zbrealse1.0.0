package com.aite.a.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.AppManager;
import com.aiteshangcheng.a.R;
import com.aite.a.view.MyViewPager;
import com.lidroid.xutils.BitmapUtils;

/**
 * 碎片界面基类
 * 
 * @author xiaoyu
 * 
 */
public abstract class BaseFargmentActivity extends FragmentActivity implements Mark {
	protected Intent intent;
	protected InputMethodManager imm;
	private TelephonyManager tManager;
	public BitmapUtils bitmapUtils;
	protected int offset; // 间隔
	protected int cursorWidth; // 游标的长度
	protected ImageView cursor = null;
	// 当前页卡编号
	protected int currIndex = 0;
	protected MyViewPager viewPager;
	protected ProgressDialog mdialog;

	/**
	 * 绑定控件id
	 */
	protected abstract void findViewById();

	/**
	 * 根据tagd的数量初始化游标的位置
	 */
	protected abstract void initCursor(int tagNum);

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		AppManager.getInstance().addActivity(this);
		mdialog = new ProgressDialog(this);
		mdialog.setProgressStyle(mdialog.STYLE_SPINNER);
		mdialog.setCancelable(false);
		int inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
		getWindow().setSoftInputMode(inputMode);
		tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 游标滑动颜色变化方法
	 */
	protected void indexCursorAndColor(int position, TextView tv_0, TextView tv_1, TextView tv_2, TextView tv_3) {
		int one = offset * 2 + cursorWidth;
		int two = one * 2;
		int three = one * 3;
		Animation animation = null;
		switch (currIndex) {
		case 0:
			if (position == 1) {
				animation = new TranslateAnimation(0, one, 0, 0);
				getPositionTextColor1(tv_0, tv_1, tv_2, tv_3);
			} else if (position == 2) {
				getPositionTextColor2(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(0, two, 0, 0);
			} else if (position == 3) {
				getPositionTextColor3(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(0, three, 0, 0);
			}
			break;

		case 1:
			if (position == 0) {
				getPositionTextColor0(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(one, 0, 0, 0);
			} else if (position == 2) {
				getPositionTextColor2(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(one, two, 0, 0);
			} else if (position == 3) {
				getPositionTextColor3(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(one, three, 0, 0);
			}
			break;

		case 2:
			if (position == 0) {
				getPositionTextColor0(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(two, 0, 0, 0);
			} else if (position == 1) {
				getPositionTextColor1(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(two, one, 0, 0);
			} else if (position == 3) {

				animation = new TranslateAnimation(two, three, 0, 0);
				getPositionTextColor3(tv_0, tv_1, tv_2, tv_3);
			}

			break;
		case 3:
			if (position == 0) {
				getPositionTextColor0(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(three, 0, 0, 0);
			} else if (position == 1) {
				getPositionTextColor1(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(three, one, 0, 0);
			} else if (position == 2) {
				getPositionTextColor2(tv_0, tv_1, tv_2, tv_3);
				animation = new TranslateAnimation(three, two, 0, 0);
			}
			break;
		}
		currIndex = position;
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		cursor.startAnimation(animation);
	}

	protected void getPositionTextColor0(TextView tv_0, TextView tv_1, TextView tv_2, TextView tv_3) {
		tv_0.setTextColor(getResources().getColor(R.color.cursor_text));
		tv_1.setTextColor(Color.BLACK);
		tv_2.setTextColor(Color.BLACK);
		tv_3.setTextColor(Color.BLACK);
	}

	protected void getPositionTextColor1(TextView tv_0, TextView tv_1, TextView tv_2, TextView tv_3) {
		tv_1.setTextColor(getResources().getColor(R.color.cursor_text));
		tv_0.setTextColor(Color.BLACK);
		tv_2.setTextColor(Color.BLACK);
		tv_3.setTextColor(Color.BLACK);
	}

	protected void getPositionTextColor2(TextView tv_0, TextView tv_1, TextView tv_2, TextView tv_3) {
		tv_2.setTextColor(getResources().getColor(R.color.cursor_text));
		tv_0.setTextColor(Color.BLACK);
		tv_3.setTextColor(Color.BLACK);
		tv_1.setTextColor(Color.BLACK);
	}

	protected void getPositionTextColor3(TextView tv_0, TextView tv_1, TextView tv_2, TextView tv_3) {
		tv_3.setTextColor(getResources().getColor(R.color.cursor_text));
		tv_0.setTextColor(Color.BLACK);
		tv_1.setTextColor(Color.BLACK);
		tv_2.setTextColor(Color.BLACK);
	}

	/**
	 * 返回
	 */
	protected void overrPre() {
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

	/**
	 * 向前
	 */
	protected void overrIn() {
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	// 获得国际化语言
	protected String getI18n(int resId) {
		return getString(resId).toString();
	}
}
