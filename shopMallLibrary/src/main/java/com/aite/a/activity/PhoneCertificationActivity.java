package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.PhoneCertificationInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 手机认证
 * 
 * @author Administrator
 *
 */
public class PhoneCertificationActivity extends BaseActivity implements
		OnClickListener {
	private TextView _tv_name, tv_state1, tv_state2, tv_state3, tv_state4,
			tv_operation, tv_operation2, tv_operation3, tv_operation4,
			tv_securitylevel;
	private ImageView _iv_back;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private PhoneCertificationInfo phoneCertificationInfo;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case account_security_id:
				if (msg.obj != null) {
					phoneCertificationInfo = (PhoneCertificationInfo) msg.obj;
					if (phoneCertificationInfo.security_level != null) {
						int parseInt = Integer
								.parseInt(phoneCertificationInfo.security_level);
						if (parseInt < 1) {
							tv_securitylevel.setText(getString(R.string.order_reminder55));
						} else if (parseInt == 2) {
							tv_securitylevel.setText(getString(R.string.order_reminder56));
						} else if (parseInt > 2) {
							tv_securitylevel.setText(getString(R.string.order_reminder57));
						}
						if (phoneCertificationInfo.member_email_bind != null) {
							if (phoneCertificationInfo.member_email_bind
									.equals("1")) {
								tv_state2.setTextColor(0xff3BB6FF);
								tv_state2.setText(getString(R.string.order_reminder58));
								tv_operation2.setText(getString(R.string.order_reminder60));
							} else {
								tv_state2.setTextColor(0xffffa500);
								tv_state2.setText(getString(R.string.order_reminder59));
								tv_operation2.setText(getString(R.string.verification_prompt11));
							}
						}
						if (phoneCertificationInfo.member_mobile_bind != null) {
							if (phoneCertificationInfo.member_mobile_bind
									.equals("1")) {
								tv_state3.setTextColor(0xff3BB6FF);
								tv_state3.setText(getString(R.string.order_reminder58));
								tv_operation3.setText(getString(R.string.order_reminder61));
							} else {
								tv_state3.setTextColor(0xffffa500);
								tv_state3.setText(getString(R.string.order_reminder59));
								tv_operation3.setText(getString(R.string.verification_prompt13));
							}
						}
						if (phoneCertificationInfo.member_paypwd != null) {
							tv_state4.setTextColor(0xff3BB6FF);
							tv_state4.setText(getString(R.string.order_reminder58));
							tv_operation4.setText(getString(R.string.update_password));
						} else {
							tv_state4.setTextColor(0xffffa500);
							tv_state4.setText(getString(R.string.order_reminder59));
							tv_operation4.setText(getString(R.string.order_reminder62));
						}

					}
				}
				break;
			case account_security_err:
				Toast.makeText(PhoneCertificationActivity.this, getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonecertification);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_state1 = (TextView) findViewById(R.id.tv_state1);
		tv_state2 = (TextView) findViewById(R.id.tv_state2);
		tv_state3 = (TextView) findViewById(R.id.tv_state3);
		tv_state4 = (TextView) findViewById(R.id.tv_state4);
		tv_operation = (TextView) findViewById(R.id.tv_operation);
		tv_operation2 = (TextView) findViewById(R.id.tv_operation2);
		tv_operation3 = (TextView) findViewById(R.id.tv_operation3);
		tv_operation4 = (TextView) findViewById(R.id.tv_operation4);
		tv_securitylevel = (TextView) findViewById(R.id.tv_securitylevel);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_iv_back.setOnClickListener(this);
		tv_operation.setOnClickListener(this);
		tv_operation2.setOnClickListener(this);
		tv_operation3.setOnClickListener(this);
		tv_operation4.setOnClickListener(this);
		initView();
	}

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		_tv_name.setText(getString(R.string.order_reminder63));
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		netRun.YYAccountSecurity();
	}

	@Override
	protected void initData() {

	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()== R.id.tv_operation){
			// 修改密码
			Intent intent = new Intent(PhoneCertificationActivity.this,
					AmendPassword.class);
			startActivity(intent);
		}else if(v.getId()==R.id.tv_operation2){
			// 绑定邮箱
			if (phoneCertificationInfo.member_email_bind!=null&&phoneCertificationInfo.member_email_bind.equals("1")){
				Intent intent2 = new Intent(PhoneCertificationActivity.this,
						EditBindingActivity.class);
				intent2.putExtra("type", "1");
				startActivity(intent2);
			}else{
				Intent intent2 = new Intent(PhoneCertificationActivity.this,
						BindingActivity.class);
				intent2.putExtra("type", "1");
				intent2.putExtra("isbinding",phoneCertificationInfo.member_email_bind);
				startActivity(intent2);
			}
		}else if(v.getId()==R.id.tv_operation3){
			// 手机绑定
			if (phoneCertificationInfo.member_mobile_bind!=null&&phoneCertificationInfo.member_mobile_bind.equals("1")){
				Intent intent2 = new Intent(PhoneCertificationActivity.this,
						EditBindingActivity.class);
				intent2.putExtra("type", "2");
				startActivity(intent2);
			}else{
				Intent intent3 = new Intent(PhoneCertificationActivity.this,
						BindingActivity.class);
				intent3.putExtra("type", "2");
				intent3.putExtra("isbinding",phoneCertificationInfo.member_mobile_bind);
				startActivity(intent3);
			}
		} else if (v.getId()==R.id.tv_operation4) {
			// 支付密码
			Intent intent4 = new Intent(PhoneCertificationActivity.this,
					PayPswActivity.class);
			intent4.putExtra("type", "2");
			startActivity(intent4);
		}

//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_operation:
//			// 修改密码
//			Intent intent = new Intent(PhoneCertificationActivity.this,
//					AmendPassword.class);
//			startActivity(intent);
//			break;
//		case R.id.tv_operation2:
//			// 绑定邮箱
//			if (phoneCertificationInfo.member_email_bind!=null&&phoneCertificationInfo.member_email_bind.equals("1")){
//				Intent intent2 = new Intent(PhoneCertificationActivity.this,
//						EditBindingActivity.class);
//				intent2.putExtra("type", "1");
//				startActivity(intent2);
//			}else{
//				Intent intent2 = new Intent(PhoneCertificationActivity.this,
//						BindingActivity.class);
//				intent2.putExtra("type", "1");
//				intent2.putExtra("isbinding",phoneCertificationInfo.member_email_bind);
//				startActivity(intent2);
//			}
//			break;
//		case R.id.tv_operation3:
//			// 手机绑定
//			if (phoneCertificationInfo.member_mobile_bind!=null&&phoneCertificationInfo.member_mobile_bind.equals("1")){
//				Intent intent2 = new Intent(PhoneCertificationActivity.this,
//						EditBindingActivity.class);
//				intent2.putExtra("type", "2");
//				startActivity(intent2);
//			}else{
//				Intent intent3 = new Intent(PhoneCertificationActivity.this,
//						BindingActivity.class);
//				intent3.putExtra("type", "2");
//				intent3.putExtra("isbinding",phoneCertificationInfo.member_mobile_bind);
//				startActivity(intent3);
//			}
//			break;
//		case R.id.tv_operation4:
//			// 支付密码
//			Intent intent4 = new Intent(PhoneCertificationActivity.this,
//					PayPswActivity.class);
//			intent4.putExtra("type", "2");
//			startActivity(intent4);
//			break;
//		}
	}
}
