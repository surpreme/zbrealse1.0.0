package com.aiteshangcheng.a.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.aite.a.base.Mark;
import com.aite.a.model.Wxlogininfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.lingshi;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler,Mark {
	// 微信APPid
	private static final String APP_id = "wxc8d6770ec7bde16a";
	private IWXAPI api;
	private Boolean islogin;
	private NetRun netRun;
	private Wxlogininfo wxinfo;
	private static final String Secret="5a4f8ce2d7c60a7fd99514df0826b67b";
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case wxlogin_id:
				wxinfo=(Wxlogininfo) msg.obj;
				netRun.wxlogin2(APP_id, wxinfo.getRefresh_token());
				break;
			case wxlogin_err:
				CommonTools.showShortToast(WXEntryActivity.this, msg.toString());
				break;
			case wx2login_id:
				wxinfo=(Wxlogininfo) msg.obj;
				netRun.wxlogin3(wxinfo.getAccess_token2(), wxinfo.getOpenid2());
				break;
			case wx2login_err:
				CommonTools.showShortToast(WXEntryActivity.this, msg.toString());
				break;
			case wx3login_id:
				wxinfo=(Wxlogininfo) msg.obj;
				//TODO   此处获得用户信息
				break;
			case wx3login_err:
				
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		islogin = lingshi.getInstance().getIslogin();
		netRun=new NetRun(this, handler);
		/************ 注册微信 ****************/
		api = WXAPIFactory.createWXAPI(this, APP_id, true);
		api.registerApp(APP_id);
		/***********************************/
		StartWXLogin();
	}

	/**
	 * 调起微信登录
	 */
	public void StartWXLogin() {
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "wechat_sdk_demo_test";
		api.handleIntent(getIntent(), this);
		//防止重复进入授权界面
		if (islogin) {
			api.sendReq(req);
		}
		lingshi.getInstance().setIslogin(false);
		finish();
	}

	@Override
	public void onReq(BaseReq arg0) {
		
	}

	@Override
	public void onResp(BaseResp code) {
		switch (code.errCode) {
		case 0:
			String code1 = ((SendAuth.Resp) code).code;
			netRun.wxlogin(code1, APP_id, Secret);
			System.out.println("-------------用户同意");
			break;
		case -4:
			System.out.println("-------------用户拒绝");
			break;
		case -2:
			System.out.println("-------------用户取消");
			break;
		}
	}
}
