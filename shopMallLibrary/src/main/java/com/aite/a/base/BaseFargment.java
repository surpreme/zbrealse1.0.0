package com.aite.a.base;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Handler;
import androidx.fragment.app.Fragment;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;

public abstract class BaseFargment extends Fragment implements Mark {
	/**
	 * 签名
	 */
	protected String mySign = null;
	protected String baseStr = null;
	protected static final String SIGN_ = "sign";
	/**
	 * 进度条对话框
	 */
	protected JSONObject jsonObject;
	public ViewUtils viewUtils;
	public HttpUtils httpUtils;
	public BitmapUtils bitmapUtils;
	public RequestParams params;

	protected String mImageUrl = null;
	protected View view;
	protected Calendar c = Calendar.getInstance();
	protected String time;

	protected ProgressDialog mdialog;

	/**
	 * 得到时间
	 */
	protected void getTime() {
		time = (c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));

	}

	// sort_by
	/**
	 * 排序规则
	 */
	protected static String SORT_BY = "";
	/**
	 * 基类的Handler
	 */
	protected Handler mHandler;

	/**
	 * 绑定控件id
	 */
	protected void findViewById() {
	}

	/**
	 * 请求数据
	 */
	protected void requestData() {
	}

	// 获得国际化语言
	protected String getI18n(int resId) {
		String result = null;
		try {
			result = getString(resId).toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

}
