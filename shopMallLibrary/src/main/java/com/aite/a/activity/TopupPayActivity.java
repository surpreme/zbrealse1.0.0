package com.aite.a.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

/**
 * 充值支付
 * 
 * @author Administrator
 *
 */
public class TopupPayActivity extends BaseActivity implements OnClickListener {

	private TextView tv_ordersn, tv_money, tv_confirm, _tv_name;
	private ImageView iv_zfb, iv_wx, _iv_back;
	private NetRun netRun;
	private String paytype = "zfb";
	private String pay_sn;

	private Handler handler = new Handler() {

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topuppay);
		findViewById();
	}

	@Override
	protected void findViewById() {
		tv_ordersn = (TextView) findViewById(R.id.tv_ordersn);
		tv_money = (TextView) findViewById(R.id.tv_money);
		tv_confirm = (TextView) findViewById(R.id.tv_confirm);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		iv_zfb = (ImageView) findViewById(R.id.iv_zfb);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_wx = (ImageView) findViewById(R.id.iv_wx);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getString(R.string.order_reminder134));
		iv_wx.setOnClickListener(this);
		iv_zfb.setOnClickListener(this);
		tv_confirm.setOnClickListener(this);
		_iv_back.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		pay_sn = getIntent().getStringExtra("pay_sn");
		initData();
	}

	@Override
	protected void initData() {
		netRun.TopupPay(pay_sn);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.iv_wx){
			// 微信
			paytype = "wx";
			iv_wx.setImageResource(R.drawable.topup_paywx2);
			iv_zfb.setImageResource(R.drawable.topup_payzfb1);
		}else if(v.getId()== R.id.iv_zfb){
			// 支付宝
			paytype = "zfb";
			iv_wx.setImageResource(R.drawable.topup_paywx1);
			iv_zfb.setImageResource(R.drawable.topup_payzfb2);
		}else if(v.getId()==R.id._iv_back){
			finish();
		}


//		switch (v.getId()) {
//		case R.id.iv_wx:
//			// 微信
//			paytype = "wx";
//			iv_wx.setImageResource(R.drawable.topup_paywx2);
//			iv_zfb.setImageResource(R.drawable.topup_payzfb1);
//			break;
//		case R.id.iv_zfb:
//			// 支付宝
//			paytype = "zfb";
//			iv_wx.setImageResource(R.drawable.topup_paywx1);
//			iv_zfb.setImageResource(R.drawable.topup_payzfb2);
//			break;
//		case R.id.tv_confirm:
//			// 确定
//			break;
//		case R.id._iv_back:
//			// 退出
//			finish();
//			break;
//		}
	}
}
