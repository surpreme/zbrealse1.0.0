package com.aite.a.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;

public class Web2Activity extends BaseActivity implements OnClickListener {
	private WebView webView;
	private String url;
	private boolean firstLoading = false;
	private boolean isVisibleTop = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web2_activity);
		findViewById();
		initView();
		initData();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void findViewById() {
		webView = (WebView) findViewById(R.id.web);
	}

	@Override
	protected void initView() {
	}

	@Override
	protected void initData() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			url = bundle.getString("url");
		}
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	private void init() {
		// WebView加载web资源
		webView.loadUrl(url);
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		webView.setWebViewClient(viewClient);
		webView.addJavascriptInterface(object, "demo");
		// 设置setWebChromeClient对象
		webView.setWebChromeClient(wvcc);
		WebSettings webSettings = webView.getSettings();
		// webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(false);

	}// 改写物理按键——返回的逻辑

	Object object = new Object() {
		public void clickOnAndroid() {
			mHandler.post(new Runnable() {
				public void run() {
					Web2Activity.this.finish();
					// webView.loadUrl("javascript:wave()");
				}
			});
		}
	};

	WebViewClient viewClient = new WebViewClient() {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
				view.loadUrl(url);
			}
			if (firstLoading == false) {
				firstLoading = true;
			}

			return true;

		}

	};

	WebChromeClient wvcc = new WebChromeClient() {
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			// message就是wave函数里alert的字符串，这样你就可以在android客户端里对这个数据进行处理
			result.confirm();
			return true;
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			if (newProgress == 100) {
				// 网页加载完成
				mdialog.dismiss();
			} else {
				// 加载中
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
			}
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			// tv_title_name.setText(title);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
//				webView.goBack();// 返回上一页面
				Web2Activity.this.finish();
				return false;
			} else {
//				isVisibleTop = false;
				Web2Activity.this.finish();
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (isVisibleTop == true) {
			isVisibleTop = false;
		} else {
			isVisibleTop = true;
		}
		return super.onPrepareOptionsMenu(menu);
	}

}
