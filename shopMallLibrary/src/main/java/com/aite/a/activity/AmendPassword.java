package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aiteshangcheng.a.R;

/**
 * 修改密码
 * @author Administrator
 *
 */
public class AmendPassword extends BaseActivity implements OnClickListener {
	private TextView _tv_name, tv_yyregistered;
	private ImageView _iv_back;
	private EditText ed_inputpsw, ed_inputusername, ed_inputqrpsw;
	private NetRun netRun;

	private String new_pwd;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case change_password_id:
				if (msg.obj.equals("1")) {
					SharedPreferences sfp = getSharedPreferences(
							"LoginActivity", MODE_PRIVATE);
					Editor edit = sfp.edit();
					edit.putString("password", "");
					edit.commit();
					Intent intent2 = new Intent(AmendPassword.this,
							LoginActivity.class);
					startActivity(intent2);
					finish();
					State.User = null;
					State.UserKey = null;
					CommonTools.showShortToast(AmendPassword.this,
							getI18n(R.string.update_success));
				} else {
					CommonTools.showShortToast(AmendPassword.this,
							msg.obj.toString());
				}
				mdialog.dismiss();
				break;
			case change_password_err:
				mdialog.dismiss();
				CommonTools.showShortToast(AmendPassword.this,
						getI18n(R.string.act_net_error));
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
		setContentView(R.layout.activity_amendpsw);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_yyregistered = (TextView) findViewById(R.id.tv_yyregistered);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		ed_inputpsw = (EditText) findViewById(R.id.ed_inputpsw);
		ed_inputusername = (EditText) findViewById(R.id.ed_inputusername);
		ed_inputqrpsw = (EditText) findViewById(R.id.ed_inputqrpsw);
		_iv_back.setOnClickListener(this);
		tv_yyregistered.setOnClickListener(this);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getString(R.string.update_password));
		netRun=new NetRun(this, handler);
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_yyregistered:
//			// 提交
//			AmendPas();
//			break;
//		}
		if(R.id._iv_back==v.getId()){
			finish();
		}else if(R.id.tv_yyregistered==v.getId()){
			// 提交
			AmendPas();
		}
	}

	
	private void AmendPas(){
		String old_pwd = ed_inputpsw.getText().toString();
		 new_pwd = ed_inputusername.getText().toString();
		String confrim_pwd = ed_inputqrpsw.getText().toString();
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
	
	@Override
	public void ReGetData() {

	}

}
