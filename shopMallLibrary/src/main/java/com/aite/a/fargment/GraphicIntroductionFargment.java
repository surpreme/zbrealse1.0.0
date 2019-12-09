package com.aite.a.fargment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aite.a.activity.GoodsDatailsActivity;
import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseFargment;

public class GraphicIntroductionFargment extends BaseFargment {
	public View view;
	public WebView tv;
	private String goods_id;
	private GoodsDatailsActivity activity;
	@SuppressLint({"NewApi", "ValidFragment"})
	public GraphicIntroductionFargment() {
	}
	@SuppressLint({"NewApi", "ValidFragment"})
	public GraphicIntroductionFargment(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.graphic_introduction, container, false);
		tv = (WebView) view.findViewById(R.id.graphic_introduction);
		requestData();
		return view;
	}

	@Override
	protected void requestData() {
		init();
	}

	private void init() {
		activity= (GoodsDatailsActivity) getActivity();
		String strURL = goods_body + "&goods_id=" + activity.goods_id;
		Log.i("------------","strURL="+strURL);
		tv.loadUrl(strURL);
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		tv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
		WebSettings webSettings = tv.getSettings();
		webSettings.setDefaultFontSize(52);// 设置字体大小
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setUseWideViewPort(true);// 关键点
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setDisplayZoomControls(false);
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
		webSettings.setSupportZoom(true); // 支持缩放
		webSettings.setLoadWithOverviewMode(true);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;
		if (mDensity == 240) {
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		} else if (mDensity == 160) {
			webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
		} else if (mDensity == 120) {
			webSettings.setDefaultZoom(ZoomDensity.CLOSE);
		} else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		} else if (mDensity == DisplayMetrics.DENSITY_TV) {
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		} else {
			webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
		}

		/**
		 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
		 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
		 */
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
	}

	@Override
	protected void findViewById() {
	}

}
