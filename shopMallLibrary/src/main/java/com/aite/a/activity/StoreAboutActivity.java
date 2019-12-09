package com.aite.a.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.StoreInfoo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BooleanLogin;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.lingshi;
import com.lidroid.xutils.BitmapUtils;

/**
 * 店铺信息
 * 
 * @author CAOYOU
 * 
 */
public class StoreAboutActivity extends BaseActivity implements OnClickListener {
	private ImageView store_image;
	private TextView store_name;
	private TextView store_zy;
	private Button collect_store;
	private TextView area_info, tv_xiangqing;
	private TextView store_time;
	private TextView evaluation_content1;
	private TextView evaluation_content2;
	private TextView evaluation_content3;
	private RatingBar evaluation_grade1;
	private RatingBar evaluation_grade2;
	private RatingBar evaluation_grade3;
	private TextView store_qq, tv_contactservices;
	private TextView store_ww;
	private TextView store_phone;
	private String store_id;
	private StoreInfoo storeInfo = new StoreInfoo();;
	private View _iv_back;
	private TextView _tv_name;
	private NetRun netRun;
	private RelativeLayout rl_contactservices;
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case store_detils_id:
				if (msg.obj != null) {
					storeInfo = (StoreInfoo) msg.obj;
					initData();
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_about);
		netRun = new NetRun(this, handler);
		findViewById();
		initView();
		indat();
	}

	/**
	 * 请求店铺信息
	 */
	private void indat() {
		String store_id1 = getIntent().getStringExtra("store_id");
		netRun.getStoreDetails(store_id1);
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		store_image = (ImageView) findViewById(R.id.store_image);
		store_name = (TextView) findViewById(R.id.store_name);
		store_zy = (TextView) findViewById(R.id.store_zy);
		collect_store = (Button) findViewById(R.id.collect_store2);
		area_info = (TextView) findViewById(R.id.area_info);
		store_time = (TextView) findViewById(R.id.store_time);
		evaluation_content1 = (TextView) findViewById(R.id.evaluation_content1);
		evaluation_content2 = (TextView) findViewById(R.id.evaluation_content2);
		evaluation_content3 = (TextView) findViewById(R.id.evaluation_content3);
		tv_contactservices = (TextView) findViewById(R.id.tv_contactservices);
		evaluation_grade1 = (RatingBar) findViewById(R.id.evaluation_grade1);
		evaluation_grade2 = (RatingBar) findViewById(R.id.evaluation_grade2);
		evaluation_grade3 = (RatingBar) findViewById(R.id.evaluation_grade3);
		store_qq = (TextView) findViewById(R.id.store_qq);
		store_ww = (TextView) findViewById(R.id.store_ww);
		store_phone = (TextView) findViewById(R.id.store_phone);
		_tv_name.setText(getI18n(R.string.shop_introduction));
		tv_xiangqing = (TextView) findViewById(R.id.tv_xiangqing);
		rl_contactservices = (RelativeLayout) findViewById(R.id.rl_contactservices);
	}

	@Override
	protected void initData() {
		// storeInfo = (StoreInfoo) getIntent().getExtras().get("storeInfo");
		store_id = storeInfo.getStore_id();
		bitmapUtils.display(store_image, storeInfo.getStore_label());
		store_name.setText(storeInfo.getStore_name());
		store_zy.setText(storeInfo.getStore_zy());
		collect_store.setOnClickListener(this);
		tv_xiangqing.setOnClickListener(this);
		rl_contactservices.setOnClickListener(this);
		area_info.setText(Html.fromHtml("<font size='14px' color='#888888'>"
				+ getI18n(R.string.location_1) + "</font>\t\t\t"
				+ storeInfo.getArea_info()));
		store_time.setText(Html.fromHtml("<font size='14px' color='#888888'>"
				+ getI18n(R.string.open_shop_date) + "</font>\t\t"
				+ storeInfo.getStore_time()));
		if (storeInfo.getCredits().get(0).getText() != null)
			evaluation_content1
					.setText(storeInfo.getCredits().get(0).getText());
		if (storeInfo.getCredits().get(1).getText() != null)
			evaluation_content2
					.setText(storeInfo.getCredits().get(1).getText());
		if (storeInfo.getCredits().get(2).getText() != null)
			evaluation_content3
					.setText(storeInfo.getCredits().get(2).getText());
		if (storeInfo.getCredits().get(0).getCredit() != null)
			evaluation_grade1.setRating(Float.valueOf(storeInfo.getCredits()
					.get(0).getCredit()));
		if (storeInfo.getCredits().get(1).getCredit() != null)
			evaluation_grade2.setRating(Float.valueOf(storeInfo.getCredits()
					.get(1).getCredit()));
		if (storeInfo.getCredits().get(2).getCredit() != null)
			evaluation_grade3.setRating(Float.valueOf(storeInfo.getCredits()
					.get(2).getCredit()));
		store_qq.setText(Html
				.fromHtml("<font size='14px' color='#888888'>QQ：</font>\t\t\t\t"
						+ storeInfo.getStore_qq()));
		if (!storeInfo.getStore_qq().equals("")) {
			tv_contactservices.setVisibility(View.VISIBLE);
		}
		store_ww.setText(Html.fromHtml("<font size='14px' color='#888888'>"
				+ getI18n(R.string.wangwang) + "</font>\t\t\t\t"
				+ storeInfo.getStore_ww()));
		store_phone.setText(Html.fromHtml("<font size='14px' color='#888888'>"
				+ getI18n(R.string.phone) + "</font>\t\t\t\t"
				+ storeInfo.getStore_phone()));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()== R.id.collect_store){
			if (BooleanLogin.getInstance().hasLogin(this)) {
				netRun.addFavorites(store_id, "store");
			}
		}else if(v.getId()== R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.tv_xiangqing){
			Intent intent = new Intent(StoreAboutActivity.this,
					StoreDetailsActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("store_id", storeInfo.store_id);
			intent.putExtras(bundle);
			startActivity(intent);
		}else if (v.getId()== R.id.rl_contactservices){
			String store_qq = lingshi.getInstance().getStore_qq();
			if (store_qq != null) {
				String url = "mqqwpa://im/main_chat?chat_type=wpa&uin=" + store_qq;
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			} else {
				CommonTools
						.showShortToast(StoreAboutActivity.this, getString(R.string.nocustomerservice));
			}
		}


//		switch (v.getId()) {
//		case R.id.collect_store:
//			if (BooleanLogin.getInstance().hasLogin(this)) {
//				netRun.addFavorites(store_id, "store");
//			}
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_xiangqing:
//			Intent intent = new Intent(StoreAboutActivity.this,
//					StoreDetailsActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putString("store_id", storeInfo.store_id);
//			intent.putExtras(bundle);
//			startActivity(intent);
//			break;
//		case R.id.rl_contactservices:
//			String store_qq = lingshi.getInstance().getStore_qq();
//			if (store_qq != null) {
//				String url = "mqqwpa://im/main_chat?chat_type=wpa&uin=" + store_qq;
//				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//			} else {
//				CommonTools
//						.showShortToast(StoreAboutActivity.this, getString(R.string.nocustomerservice));
//			}
//			break;
//		}
	}

	@Override
	protected void initView() {
		_iv_back.setOnClickListener(this);
		bitmapUtils = new BitmapUtils(StoreAboutActivity.this);
	}

	@Override
	public void ReGetData() {
		initData();
	}

}
