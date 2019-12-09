package com.aiteshangcheng.a.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.AppManager;
import com.aite.a.activity.PayListActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.WxpayInfo;
import com.aite.a.parse.JsonParse;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.HttpUtil;
import com.aiteshangcheng.a.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private IWXAPI api;
	private TextView tv_pay_message;
	private Button bt_cancel;
	private View v_fengexian;
	private Button bt_pay;
	private String goods;
	private String describe;
	private String price;
	private String pay_sn;
	private String payment_code;
	private boolean isvr;
	private AppRegister appRegister;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wx_pay);
		AppManager.getInstance().addActivity(this);
		initData();
		findView();
		GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
		getPrepayId.execute();
	}

	/**
	 * 调起微信登录
	 */
	public void StartWXLogin() {
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "wechat_sdk_demo_test";
		api.sendReq(req);
	}

	private void initData() {
//		api = WXAPIFactory.createWXAPI(this, "wxc8d6770ec7bde16a", true);
		api = WXAPIFactory.createWXAPI(this, "wx41f275ef24e19565", false);
//        api = WXAPIFactory.createWXAPI(context, MainUtil.WeChatPayId);
		api.registerApp("wx41f275ef24e19565");
//		api.handleIntent(getIntent(), this);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {

			goods = bundle.getString("goods");
			describe = bundle.getString("describe");
			price = bundle.getString("price");
			pay_sn = bundle.getString("pay_sn");
			payment_code = bundle.getString("payment_code");
			isvr=bundle.getBoolean("isvr");
		}
	}

	private void findView() {
		tv_pay_message = (TextView) findViewById(R.id.tv_pay_message);
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		bt_pay = (Button) findViewById(R.id.bt_pay);
		v_fengexian = (View) findViewById(R.id.v_fengexian);
		
		bt_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
				getPrepayId.execute();
			}
		});

		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tv_pay_message.getText().toString().equals(APPSingleton.getContext().getString(R.string.pay_success))){
					Intent intent = new Intent(WXPayEntryActivity.this,
							PayListActivity.class);
					setResult(10010, intent);
				}
				finish();
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.i("-----------------------","微信6 getType="+resp.getType()+"  errCode="+resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			handler.sendEmptyMessage(resp.errCode);
		}
	}

	private class GetPrepayIdTask extends
			AsyncTask<Void, Void, Map<String, String>> {

		@Override
		protected void onPreExecute() {
			bt_cancel.setVisibility(View.GONE);
			bt_pay.setVisibility(View.GONE);
			v_fengexian.setVisibility(View.GONE);
			tv_pay_message.setVisibility(View.VISIBLE);
			tv_pay_message.setText(APPSingleton.getContext()
					.getString(R.string.generate_payment_order).toString());
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			if (result != null) {   //重服务器获取支付信息后,调用微信支付
				PayReq req = new PayReq();
				req.appId = result.get("appId");
				req.partnerId = result.get("partnerId");
				req.prepayId = result.get("prepayId");
				req.packageValue = result.get("packageValue");
				req.nonceStr = result.get("nonceStr");
				req.timeStamp = result.get("timeStamp");
				List<NameValuePair> pairs = new LinkedList<NameValuePair>();
				pairs.add(new BasicNameValuePair("appid", req.appId));
				pairs.add(new BasicNameValuePair("noncestr", req.nonceStr));
				pairs.add(new BasicNameValuePair("package", req.packageValue));
				pairs.add(new BasicNameValuePair("partnerid", req.partnerId));
				pairs.add(new BasicNameValuePair("prepayid", req.prepayId));
				pairs.add(new BasicNameValuePair("timestamp", req.timeStamp));
				req.sign = genAppSign(pairs, result.get("wxkey"));
				api.registerApp(result.get("appId"));
				boolean b = api.sendReq(req);
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		/**
		 * 支付参数
		 * 
		 * @param params
		 * @return
		 */
		@Override
		protected Map<String, String> doInBackground(Void... params) {
			try {
				//从服务器获取支付信息
				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("body", getBody());
//				map.put("out_trade_no", pay_sn);
//				map.put("total_fee", price);
//				String content = HttpUtil.postRequest(Mark.prepay_wxpay, map);
//				return getPayData((WxpayInfo) JsonParse.wxpay(content));

				map.put("pay_sn", pay_sn);
				map.put("payment_code", payment_code);
				map.put("key", Mark.State.UserKey);
				String content=null;
				if (isvr){
					Log.i("-----------------------","支付信息请求: "+Mark.zfb_pay2+"&pay_sn="+pay_sn+"&payment_code="+payment_code+"&key="+Mark.State.UserKey);
					content = HttpUtil.getRequest(Mark.zfb_pay2+"&pay_sn="+pay_sn+"&payment_code="+payment_code+"&key="+Mark.State.UserKey);
				}else{
					Log.i("--------------支付信息请求:"," "+Mark.zfb_pay+"&pay_sn="+pay_sn+"&payment_code="+payment_code+"&key="+Mark.State.UserKey);
					content = HttpUtil.getRequest(Mark.zfb_pay+"&pay_sn="+pay_sn+"&payment_code="+payment_code+"&key="+Mark.State.UserKey);
				}
				Log.i("----------------支付请求返回:"," "+content);
				return getPayData((WxpayInfo) JsonParse.wxpay(content));
			} catch (Exception e) {
				e.printStackTrace();
				handler.sendMessage(handler.obtainMessage(400, APPSingleton
						.getContext().getString(R.string.get_order_fail)
						.toString()));
			}
			return null;
		}
	}

	private String genAppSign(List<NameValuePair> params, String appkey) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(appkey);
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		return appSign;
	}

	public String getBody() {
		return CommonTools.getAppName(this)
				+ APPSingleton.getContext().getString(R.string.pay_no)
						.toString() + pay_sn;
	}


	//处理请求的支付信息
	public Map<String, String> getPayData(WxpayInfo wxpayInfo) {
		appRegister = new AppRegister(wxpayInfo.appid);
		IntentFilter myIntentFilter = new IntentFilter();
		registerReceiver(appRegister, myIntentFilter);
		if (wxpayInfo.error != null && !wxpayInfo.error.equals("")) {
			handler.sendMessage(handler.obtainMessage(400, wxpayInfo.error));
			return null;
		}
		if (wxpayInfo.err_code_des != null
				&& !wxpayInfo.err_code_des.equals("")) {
			handler.sendMessage(handler.obtainMessage(400,
					wxpayInfo.err_code_des));
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("appId", wxpayInfo.appid);
		map.put("wxkey", wxpayInfo.wxkey);
		map.put("partnerId", wxpayInfo.mch_id);
		map.put("prepayId", wxpayInfo.prepay_id);
		map.put("packageValue", "Sign=WXPay");
		map.put("nonceStr", wxpayInfo.nonce_str);
		map.put("timeStamp", String.valueOf(genTimeStamp()));
		return map;
	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String mag = "";
			switch (msg.what) {
			case 0:
				mag = APPSingleton.getContext().getString(R.string.pay_success)
						.toString();
				bt_cancel.setVisibility(View.VISIBLE);
				bt_pay.setVisibility(View.GONE);
				v_fengexian.setVisibility(View.GONE);
				bt_cancel.setText(APPSingleton.getContext()
						.getString(R.string.confirm).toString());
				break;
			case -1:
				mag = APPSingleton.getContext()
						.getString(R.string.signature_error).toString();
				bt_cancel.setVisibility(View.VISIBLE);
				bt_pay.setVisibility(View.GONE);
				v_fengexian.setVisibility(View.GONE);
				bt_cancel.setText(APPSingleton.getContext()
						.getString(R.string.confirm).toString());
				break;
			case -2:
				mag = APPSingleton.getContext()
						.getString(R.string.user_cancel_payment).toString();
				bt_pay.setVisibility(View.VISIBLE);
				bt_cancel.setVisibility(View.VISIBLE);
				v_fengexian.setVisibility(View.VISIBLE);
				bt_cancel.setText(APPSingleton.getContext()
						.getString(R.string.cancel).toString());
				bt_pay.setText(APPSingleton.getContext()
						.getString(R.string.re_payment).toString());
				break;
			default:
				if (msg.obj == null) {
					mag = APPSingleton.getContext()
							.getString(R.string.payment_fail).toString();
				} else {
					mag = (String) msg.obj;
				}
				bt_pay.setVisibility(View.VISIBLE);
				bt_cancel.setVisibility(View.VISIBLE);
				v_fengexian.setVisibility(View.VISIBLE);
				bt_cancel.setText(APPSingleton.getContext()
						.getString(R.string.cancel).toString());
				bt_pay.setText(APPSingleton.getContext()
						.getString(R.string.re_payment).toString());
				break;
			}
			tv_pay_message.setText(mag);
		};
	};

//	private long genTimeStamp() {
//		return System.currentTimeMillis() / 1000;
//	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (appRegister!=null) {
			unregisterReceiver(appRegister);
		}
	}


	/**
	 * 向微信服务器发起的支付请求
	 */
	public void pay(String prepayId) {
		PayReq req = new PayReq();
		req.appId = "wx41f275ef24e19565";//APPID
//		req.partnerId = MainUtil.partnerid;//商户号
		req.prepayId = prepayId;//预付款ID
		req.nonceStr = getRandomString(10);//随机数
		req.timeStamp = genTimeStamp()+"";//时间戳
		req.packageValue = "Sign=WXPay";//固定值Sign=WXPay

		List<NameValuePair> pairs = new LinkedList<NameValuePair>();
		pairs.add(new BasicNameValuePair("appid", req.appId));
		pairs.add(new BasicNameValuePair("noncestr", req.nonceStr));//随机数
		pairs.add(new BasicNameValuePair("package", req.packageValue));//固定值Sign=WXPay
		pairs.add(new BasicNameValuePair("partnerid", req.partnerId));//商户号
		pairs.add(new BasicNameValuePair("prepayid", req.prepayId));//预付款ID
		pairs.add(new BasicNameValuePair("timestamp", req.timeStamp));//时间戳
		req.sign = genAppSign(pairs);
//        req.sign = sign;//签名
		api.sendReq(req);
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		return appSign;
	}

	//获得随机数
	public String getRandomString(int length) {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());

	}

	//获得时间戳
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
}