package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.Servicedetailsinfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 服务详情
 * 
 * @author Administrator
 *
 */
public class ServiceDetails extends BaseActivity implements OnClickListener {
	private ImageView iv_goodsimg, _iv_back, _iv_right;
	private TextView tv_servicename, tv_price, tv_number, tv_contact, tv_buy,
			tv_storename, tv_speed, tv_quality, tv_attitude, _tv_name;
	private RelativeLayout rl_storename;
	private LinearLayout ll_speed, ll_quality, ll_attitude;
	private NetRun netRun;
	private BitmapUtils bitmaputils;
	private String goods_id;
	private Servicedetailsinfo servicedetailsinfo;
	private String store_qq = null;
	private WebView webView;
	private boolean firstLoading = false;
	private boolean isVisibleTop = false;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case service_details_id:
				if (msg.obj != null) {
					servicedetailsinfo = (Servicedetailsinfo) msg.obj;
					setdetails(servicedetailsinfo);
				}
				break;
			case service_details_err:
				Toast.makeText(ServiceDetails.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case service_collection_id:
				if (msg.obj != null) {
					String re = (String) msg.obj;
					if (re.equals("收藏成功")) {
						_iv_right.setImageResource(R.drawable.service_collection2);
					}
					Toast.makeText(ServiceDetails.this, re, Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case service_collection_err:
				Toast.makeText(ServiceDetails.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicedetails);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_goodsimg = (ImageView) findViewById(R.id.iv_goodsimg);
		tv_servicename = (TextView) findViewById(R.id.tv_servicename);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_number = (TextView) findViewById(R.id.tv_number);
		tv_contact = (TextView) findViewById(R.id.tv_contact);
		tv_buy = (TextView) findViewById(R.id.tv_buy);
		tv_storename = (TextView) findViewById(R.id.tv_storename);
		tv_speed = (TextView) findViewById(R.id.tv_speed);
		tv_quality = (TextView) findViewById(R.id.tv_quality);
		tv_attitude = (TextView) findViewById(R.id.tv_attitude);
		rl_storename = (RelativeLayout) findViewById(R.id.rl_storename);
		ll_speed = (LinearLayout) findViewById(R.id.ll_speed);
		ll_quality = (LinearLayout) findViewById(R.id.ll_quality);
		ll_attitude = (LinearLayout) findViewById(R.id.ll_attitude);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_iv_right = (ImageView) findViewById(R.id._iv_right);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		webView = (WebView) findViewById(R.id.web);
		initView();
	}
	

	@Override
	protected void initView() {
		iv_goodsimg.setOnClickListener(this);
		tv_contact.setOnClickListener(this);
		tv_buy.setOnClickListener(this);
		rl_storename.setOnClickListener(this);
		ll_speed.setOnClickListener(this);
		ll_quality.setOnClickListener(this);
		ll_attitude.setOnClickListener(this);
		_iv_back.setOnClickListener(this);
		_iv_right.setOnClickListener(this);
		
		
		initData();
	}

	@Override
	protected void initData() {
		netRun = new NetRun(this, handler);
		bitmaputils = new BitmapUtils(this);
		goods_id = getIntent().getStringExtra("goods_id");
		netRun.Servicedetails(goods_id);
	}

	/**
	 * 初始化商品详情
	 */
	private void setdetails(Servicedetailsinfo servicedetailsinfo) {
		bitmaputils.display(iv_goodsimg,
				servicedetailsinfo.goodsdel.goods_image_url);
		tv_servicename.setText(servicedetailsinfo.goodsdel.goods_name);
		tv_price.setText(getString(R.string.currency)
				+ servicedetailsinfo.goodsdel.goods_price);
		tv_number.setText(servicedetailsinfo.goodsdel.goods_salenum);
		tv_storename.setText(servicedetailsinfo.storedel.store_name);
		tv_speed.setText(servicedetailsinfo.storedel.store_deliverycredit);
		tv_quality.setText(servicedetailsinfo.storedel.store_desccredit);
		tv_attitude.setText(servicedetailsinfo.storedel.store_servicecredit);
		_tv_name.setText(getString(R.string.servicedetails));
		store_qq = servicedetailsinfo.storedel.store_qq;
		if (servicedetailsinfo.goodsdel.is_favorites.equals("0")) {
			_iv_right.setImageResource(R.drawable.service_collection);
		}else {
			_iv_right.setImageResource(R.drawable.service_collection2);
		}
		init(servicedetailsinfo.goodsdel.goods_url);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.tv_contact){
			// 联系他
			try {
				if (store_qq != null && !store_qq.equals("")) {
					String url = "mqqwpa://im/main_chat?chat_type=wpa&uin="
							+ store_qq;
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
				} else {
					Toast.makeText(ServiceDetails.this,
							getString(R.string.nocustomerservice),
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Toast.makeText(ServiceDetails.this,
						getString(R.string.nocustomerservice2),
						Toast.LENGTH_SHORT).show();
			}
		}else if(v.getId()== R.id.tv_buy){
			if (servicedetailsinfo.goodsdel.goods_id != null
					&& !servicedetailsinfo.goodsdel.goods_id.equals("")) {
				intent = new Intent(this, ProductDetailsActivity.class);
				intent.putExtra("goods_id",
						servicedetailsinfo.goodsdel.goods_id);
				startActivity(intent);
			} else {
				Toast.makeText(ServiceDetails.this,
						getString(R.string.thereisnogoods), Toast.LENGTH_SHORT)
						.show();
			}
		}else if(v.getId()==R.id.rl_storename){
			// 服务商
			if (servicedetailsinfo.storedel.store_id != null
					&& !servicedetailsinfo.storedel.store_id.equals("")) {
				Intent intent = new Intent(ServiceDetails.this,
						StoreHomePageActivity.class);
				intent.putExtra("store_id",
						servicedetailsinfo.storedel.store_id);
				startActivity(intent);

//				Intent intent = new Intent(ServiceDetails.this,
//						StoreDetailsActivity.class);
//				intent.putExtra("store_id",
//						servicedetailsinfo.storedel.store_id);
//				startActivity(intent);
			} else {
				Toast.makeText(ServiceDetails.this,
						getString(R.string.thereisnostore), Toast.LENGTH_SHORT)
						.show();
			}
		}else if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id._iv_right){
			// 收藏
			netRun.ServiceCollection(goods_id);
		}

//		switch (v.getId()) {
//		case R.id.iv_goodsimg:
//			// 选择图片
//			break;
//		case R.id.tv_contact:
//			// 联系他
//			try {
//				if (store_qq != null && !store_qq.equals("")) {
//					String url = "mqqwpa://im/main_chat?chat_type=wpa&uin="
//							+ store_qq;
//					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//				} else {
//					Toast.makeText(ServiceDetails.this,
//							getString(R.string.nocustomerservice),
//							Toast.LENGTH_SHORT).show();
//				}
//			} catch (Exception e) {
//				Toast.makeText(ServiceDetails.this,
//						getString(R.string.nocustomerservice2),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.tv_buy:
//			// 立即购买
////			if (servicedetailsinfo.goodsdel.goods_id != null
////			&& !servicedetailsinfo.goodsdel.goods_id.equals("")) {
////				Intent intent3 = new Intent(this,
////						BcServiceConfirmActivity.class);
////				intent3.putExtra("fid", servicedetailsinfo.goodsdel.goods_id);
////				intent3.putExtra("img",
////						servicedetailsinfo.goodsdel.goods_image_url);
////				intent3.putExtra("name",
////						servicedetailsinfo.goodsdel.goods_name);
////				intent3.putExtra("price",
////						servicedetailsinfo.goodsdel.goods_price);
////				intent3.putExtra("storename",
////						servicedetailsinfo.storedel.store_name);
////				startActivity(intent3);
////			}else {
////				Toast.makeText(ServiceDetails.this,
////						getString(R.string.thereisnogoods), Toast.LENGTH_SHORT)
////						.show();
////			}
//			if (servicedetailsinfo.goodsdel.goods_id != null
//					&& !servicedetailsinfo.goodsdel.goods_id.equals("")) {
//				intent = new Intent(this, ProductDetailsActivity.class);
//				intent.putExtra("goods_id",
//						servicedetailsinfo.goodsdel.goods_id);
//				startActivity(intent);
//			} else {
//				Toast.makeText(ServiceDetails.this,
//						getString(R.string.thereisnogoods), Toast.LENGTH_SHORT)
//						.show();
//			}
//			break;
//		case R.id.rl_storename:
//			// 服务商
//			if (servicedetailsinfo.storedel.store_id != null
//					&& !servicedetailsinfo.storedel.store_id.equals("")) {
//				Intent intent = new Intent(ServiceDetails.this,
//						StoreHomePageActivity.class);
//				intent.putExtra("store_id",
//						servicedetailsinfo.storedel.store_id);
//				startActivity(intent);
//
////				Intent intent = new Intent(ServiceDetails.this,
////						StoreDetailsActivity.class);
////				intent.putExtra("store_id",
////						servicedetailsinfo.storedel.store_id);
////				startActivity(intent);
//			} else {
//				Toast.makeText(ServiceDetails.this,
//						getString(R.string.thereisnostore), Toast.LENGTH_SHORT)
//						.show();
//			}
//			break;
//		case R.id.ll_speed:
//			// 工作速度
//			break;
//		case R.id.ll_quality:
//			// 工作质量
//			break;
//		case R.id.ll_attitude:
//			// 工作态度
//			break;
//		case R.id._iv_back:
//			// 退出
//			finish();
//			break;
//		case R.id._iv_right:
//			// 收藏
//			netRun.ServiceCollection(goods_id);
//			break;
//		}
	}

	
	private void init(String url) {
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
		//屏幕适配
		webSettings.setUseWideViewPort(true); 
		webSettings.setLoadWithOverviewMode(true); 

		
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
					webView.loadUrl("javascript:wave()");
				}
			});
		}
	};
//
	WebViewClient viewClient = new WebViewClient() {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
				view.loadUrl(url);
			}
			if (firstLoading == false) {
//				if (title_ll.getVisibility() == View.VISIBLE)
//					title_ll.setVisibility(View.GONE);
				firstLoading = true;
			}
//
			return true;

		}

	};
//
	WebChromeClient wvcc = new WebChromeClient() {
		@Override
		public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
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
//			tv_title_name.setText(title);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();// 返回上一页面
				return true;
			} else {
//				if (title_ll.getVisibility() == View.VISIBLE) {
					finish();
//					return false;
//				}
//				title_ll.setVisibility(View.VISIBLE);
				isVisibleTop = false;
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (isVisibleTop == true) {
			isVisibleTop = false;
//			title_ll.setVisibility(View.VISIBLE);
		} else {
			isVisibleTop = true;
//			title_ll.setVisibility(View.GONE);
		}
		return super.onPrepareOptionsMenu(menu);
	}

}
