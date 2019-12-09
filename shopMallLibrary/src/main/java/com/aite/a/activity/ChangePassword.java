package com.aite.a.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.EditTextWithDel;

/**
 * 修改密码
 * 
 * @author xiaoyu
 * 
 */
public class ChangePassword extends BaseActivity implements OnClickListener {
	// 验证码
	private EditTextWithDel et_last_pwd, et_new_pwd, et_confrim_pwd;
	private Button btn_submit;
	private NetRun netRun;
	private TextView change_passwrod_tv_last_password,
			change_passwrod_tv_new_password, change_passwrod_tv_sure_password;
	private String new_pwd;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case change_password_id:
				if (msg.obj.equals("1")) {
					SharedPreferences sfp = getSharedPreferences("LoginActivity", MODE_PRIVATE);
					Editor edit = sfp.edit();
					edit.putString("password", "");
					edit.commit();
					Intent intent2 = new Intent(ChangePassword.this,LoginActivity.class);
					startActivity(intent2);
					finish();
					State.User = null;
					State.UserKey = null;
					intent = new Intent(PERSONAL_);
					ChangePassword.this.sendBroadcast(intent);
					CommonTools.showShortToast(ChangePassword.this,
							getI18n(R.string.update_success));
				} else {
					CommonTools.showShortToast(ChangePassword.this,
							msg.obj.toString());
				}
				mdialog.dismiss();
				break;
			case change_password_err:
				mdialog.dismiss();
				CommonTools.showShortToast(ChangePassword.this,
						getI18n(R.string.systembusy));
				break;
			case change_password_start:
//				mdialog.setMessage(getI18n(R.string.updating));
				mdialog.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		netRun = new NetRun(this, handler);
		findViewById();
	}

	@Override
	protected void findViewById() {
		btn_submit = (Button) findViewById(R.id.change_btn_submit);
		et_last_pwd = (EditTextWithDel) findViewById(R.id.change_et_last_password);
		et_new_pwd = (EditTextWithDel) findViewById(R.id.change_et_new_password);
		et_confrim_pwd = (EditTextWithDel) findViewById(R.id.change_et_sure_password);
		tv_title_name = (TextView) findViewById(R.id._tv_name);
		iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_right = (ImageView) findViewById(R.id._iv_right);
		change_passwrod_tv_last_password = (TextView) findViewById(R.id.change_passwrod_tv_last_password);
		change_passwrod_tv_new_password = (TextView) findViewById(R.id.change_passwrod_tv_new_password);
		change_passwrod_tv_sure_password = (TextView) findViewById(R.id.change_passwrod_tv_sure_password);
		initView();
	}

	@Override
	protected void initView() {
		btn_submit.setOnClickListener(this);
		tv_title_name.setText(getI18n(R.string.update_password));
		iv_back.setOnClickListener(this);
		iv_right.setOnClickListener(this);
		iv_right.setVisibility(View.GONE);
//		iv_right.setBackgroundResource(R.drawable.guanbi);
		change_passwrod_tv_sure_password.post(new Runnable() {

			@Override
			public void run() {
				width = change_passwrod_tv_sure_password.getWidth();
				runOnUiThread(new Runnable() {
					public void run() {
						client(change_passwrod_tv_last_password);
						client(change_passwrod_tv_new_password);
					}
				});
			}
		});
	}
	@Override
	protected void initData() {
		String old_pwd = et_last_pwd.getText().toString();
		 new_pwd = et_new_pwd.getText().toString();
		String confrim_pwd = et_confrim_pwd.getText().toString();
		if (TextUtils.isEmpty(old_pwd)) {
			CommonTools
					.showShortToast(this, getI18n(R.string.old_password_not));
			return;
		}
		if (TextUtils.isEmpty(new_pwd)) {
			CommonTools
					.showShortToast(this, getI18n(R.string.new_password_not));
			return;
		}
		if (TextUtils.isEmpty(confrim_pwd)) {
			CommonTools.showShortToast(this,
					getI18n(R.string.repeat_password_not));
			return;
		}
		if (new_pwd.length() < 6) {
			CommonTools.showShortToast(this,
					getI18n(R.string.new_password_length_not_6));
			return;
		}
		if (new_pwd.length() > 24) {
			CommonTools.showShortToast(this,
					getI18n(R.string.new_password_length_not_24));
			return;
		}
		if (!new_pwd.equals(confrim_pwd)) {
			CommonTools.showShortToast(this,
					getI18n(R.string.two_password_not_consistent));
			return;
		}
		netRun.reSetPassWord(old_pwd, new_pwd);
	}

	private int width;

	private void client(TextView view) {
		LayoutParams para = (LayoutParams) view.getLayoutParams();
		para.width = width;
		view.setLayoutParams(para);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.change_btn_submit:
//				initData();
//				break;
//			case R.id._iv_back:
//				finish();
//				overrPre();
//				break;
//		}
		if(v.getId()==R.id.change_btn_submit){
			initData();
		}else if(v.getId()==R.id._iv_back){
			finish();
			overrPre();
		}

	}

	@Override
	public void ReGetData() {
		initData();
	}
}
