package com.aite.a.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.EditTextWithDel;
/**
 * 忘记密码
 * @author Administrator
 *
 */
public class FrogetPassWord extends BaseActivity implements OnClickListener {
	private EditTextWithDel et_phone, et_email;
	private Button btn_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_password);
		findViewById();
	}

	@Override
	protected void findViewById() {
		et_email = (EditTextWithDel) findViewById(R.id.register_et_email);
		et_phone = (EditTextWithDel) findViewById(R.id.register_et_phone);
		btn_submit = (Button) findViewById(R.id.register_btn_submit);
		tv_title_name = (TextView) findViewById(R.id._tv_name);
		iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_right = (ImageView) findViewById(R.id._iv_right);
		initView();
	}

	@Override
	protected void initView() {
		btn_submit.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		iv_right.setOnClickListener(this);
		tv_title_name.setText(getI18n(R.string.reset_password));
		iv_right.setBackgroundResource(R.drawable.guanbi);
		initData();
	}

	@Override
	protected void initData() {
		String name = et_phone.getText().toString();
		String email = et_email.getText().toString();
		if (TextUtils.isEmpty(name)) {
			CommonTools.showShortToast(this, getI18n(R.string.username_empty));
			return;
		}
		if (CommonTools.isMobile(name) != null) {
			CommonTools.showShortToast(this, CommonTools.isMobile(name));
			return;
		}
		if (TextUtils.isEmpty(email) == false) {
			CommonTools.showShortToast(this, getI18n(R.string.email_empty));
			return;
		}
		if (CommonTools.checkEmail(email) == false) {
			CommonTools.showShortToast(this, getI18n(R.string.email_error));
			return;
		}
		// params = new RequestParams();
		// mdialog = new ProgressDialog(this);
		// mdialog.setMessage("获取密码中...");
		// params.addHeader("username", name);
		// params.addHeader("email", email);
		// httpUtils.send(HttpMethod.POST, ServerUrl.USER_GETPWD_, params,
		// new RequestCallBack<String>() {
		//
		// @Override
		// public void onSuccess(ResponseInfo<String> responseInfo) {
		// mdialog.dismiss();
		//
		// try {
		// jsonObject = new JSONObject(responseInfo.result);
		// JSONObject status = jsonObject
		// .getJSONObject("status");
		// int succeed = status.getInt("succeed");
		// if (succeed == 0) {
		// String error_desc = status
		// .getString("error_desc");
		// CommonTools.showShortToast(FrogetPassWord.this,
		// "重置失败:" + error_desc);
		// } else {
		// CommonTools.showShortToast(FrogetPassWord.this,
		// "重置成功,密码稍后发到您的邮箱");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// @Override
		// public void onFailure(HttpException error, String msg) {
		// }
		//
		// @Override
		// public void onStart() {
		// super.onStart();
		// mdialog.show();
		// }
		// });

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			onBackPressed();
//			break;
//		case R.id._iv_right:
//			onBackPressed();
//			break;
//		case R.id.register_btn_submit:
//			initData();
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			onBackPressed();
		}else if(v.getId()==R.id._iv_right){
			onBackPressed();
		}else if (v.getId()==R.id.register_btn_submit) {
			initData();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overrPre();
	}

	@Override
	public void ReGetData() {
		initData();
	}
}
