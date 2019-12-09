package com.qq.xgdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.qq.xgdemo.ExtendedListView.OnPositionChangedListener;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGLocalMessage;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.XGPushService;

public class DiagnosisActivity extends BaseActivity implements OnPositionChangedListener {

	private DummyAdapter adapter;
	Handler handler = null;
	Message m = null;
	private ExtendedListView mListView;
	long currentTimeMillis = 0;
	String token;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diagnosis);

		XGPushConfig.enableDebug(getApplicationContext(), true);

		mListView = (ExtendedListView) findViewById(android.R.id.list);
		adapter = new DummyAdapter();
		adapter.setData(new ArrayList<String>());
		mListView.setAdapter(adapter);
		mListView.setCacheColorHint(Color.TRANSPARENT);
		mListView.setOnPositionChangedListener(this);
		handler = new HandlerExtension(DiagnosisActivity.this);

		updateProgress(APPSingleton.getContext().getString(R.string.one_key_update).toString());

		CommonWorkingThread.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				step0();
			}
		}, 100L);

		findViewById(R.id.arrow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private class DummyAdapter extends BaseAdapter {

		List<String> adapterData;

		@Override
		public int getCount() {
			return (null == adapterData ? 0 : adapterData.size());
		}

		public List<String> getData() {
			return adapterData;
		}

		public void setData(List<String> pushInfoList) {
			adapterData = pushInfoList;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(DiagnosisActivity.this).inflate(R.layout.diagnosis_list_item, parent, false);
			}

			TextView textView = (TextView) convertView;
			textView.setText(position + " --> " + adapterData.get(position));

			return convertView;
		}
	}

	@Override
	public void onPositionChanged(ExtendedListView listView, int firstVisiblePosition, View scrollBarPanel) {
		((TextView) scrollBarPanel).setText("Position " + firstVisiblePosition);
	}

	private void step0() {
		long timeMillis = System.currentTimeMillis();
		try {
			String path = "http://www.baidu.com";
			HttpGet httpGet = new HttpGet(path);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResp = httpClient.execute(httpGet);
			if (httpResp.getStatusLine().getStatusCode() == 200) {
				updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_network_well).toString());
				step1();
			} else {
				updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_network_error).toString());
				stepLast();
			}
		} catch (Throwable e) {
			updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_check_network).toString() + e.getMessage());
			stepLast();
		}
	}

	private void step1() {
		long timeMillis = System.currentTimeMillis();
		Process p;
		try {
			p = Runtime.getRuntime().exec("ping -c 3 -w 30 183.61.46.193");
			int status = p.waitFor();

			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			System.out.println("Return ============" + buffer.toString());

			if (status == 0) {
				updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_network_well).toString());
				step2();
			} else {
				updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_network_error).toString());
				stepLast();
			}
		} catch (Throwable e) {
			updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_error).toString() + e.getMessage());
			stepLast();
		}
	}

	private void step2() {
		currentTimeMillis = System.currentTimeMillis();
		XGPushManager.unregisterPush(getApplicationContext(), new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				updateProgress((System.currentTimeMillis() - currentTimeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_logon_re_success).toString());
				step3();
				XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {
					@Override
					public void onSuccess(Object data, int flag) {
						token = data.toString();
						updateProgress((System.currentTimeMillis() - currentTimeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_logon_success).toString());
						step4();
					}

					@Override
					public void onFail(Object data, int errCode, String msg) {
						StringBuffer sb = new StringBuffer((System.currentTimeMillis() - currentTimeMillis) + APPSingleton.getContext().getString(R.string.ms_zd_logon_fail).toString()).append(msg).append(errCode).append("!\r\n");
						sb.append(errCodeHandle(errCode));
						updateProgress("+++ register push failed. token:" + data);
					}
				});
			}

			@Override
			public void onFail(Object data, int errCode, String msg) {
				StringBuffer sb = new StringBuffer((System.currentTimeMillis() - currentTimeMillis) + APPSingleton.getContext().getString(R.string.ms_zd_re_logon_error).toString()).append(msg).append(errCode).append("!\r\n");
				sb.append(errCodeHandle(errCode));
				updateProgress(sb.toString());
			}
		});

	}

	private void step3() {
		long timeMillis = System.currentTimeMillis();
		ActivityManager am = (ActivityManager) this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServiceInfos = am.getRunningServices(Integer.MAX_VALUE);

		if (runningServiceInfos == null || runningServiceInfos.size() < 1) {
			updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_not_start).toString());
			return;
		}

		String PushServiceName = XGPushService.class.getName();
		for (RunningServiceInfo serviceInfo : runningServiceInfos) {
			if (PushServiceName.equals(serviceInfo.service.getClassName())) {
				updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_start_success).toString());
				return;
			}
		}

		updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_not_start).toString());
	}

	private void step4() {
		long timeMillis = System.currentTimeMillis();
		XGLocalMessage local_msg = new XGLocalMessage();
		local_msg.setType(1);
		local_msg.setTitle(APPSingleton.getContext().getString(R.string.notice_local).toString());
		local_msg.setContent(APPSingleton.getContext().getString(R.string.notice_local).toString());
		local_msg.setDate("20141119");
		local_msg.setHour("22");
		local_msg.setMin("34");
		XGPushManager.addLocalNotification(getApplicationContext(), local_msg);
		updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_add_notice_success).toString());
		step5();
	}

	private void step5() {
		long timeMillis = System.currentTimeMillis();
		XGPushManager.clearLocalNotifications(getApplicationContext());
		updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_clean_notice_success).toString());
		step6();
	}

	private void step6() {
		long timeMillis = System.currentTimeMillis();
		XGPushManager.setTag(getApplicationContext(), "DiagnosisTag");
		updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_add_label_success).toString());
		XGPushManager.deleteTag(getApplicationContext(), "DiagnosisTag");
		updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_delete_label_success).toString());
		step7();
	}

	private void step7() {
		long timeMillis = System.currentTimeMillis();
		int ret_code = 0;
		try {
			com.qq.xgdemo.Message message = new com.qq.xgdemo.Message();
			message.setType(message.TYPE_NOTIFICATION);
			Style style = new Style(1);
			style = new Style(3, 1, 0, 1, 0);
			ClickAction action = new ClickAction();
			action.setActionType(ClickAction.TYPE_URL);
			action.setUrl("http://xg.qq.com");
			Map<String, Object> custom = new HashMap<String, Object>();
			custom.put("key1", "value1");
			custom.put("key2", 2);
			message.setTitle(APPSingleton.getContext().getString(R.string.network_notice_test).toString());
			message.setContent(APPSingleton.getContext().getString(R.string.network_notice_test).toString());
			message.setStyle(style);
			message.setAction(action);
			message.setCustom(custom);
			TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
			message.addAcceptTime(acceptTime1);
			String secretKey = (String) getMetaData(getApplicationContext(), "XG_V2_SECRET_KEY", "");
			long accID = (Integer) getMetaData(getApplicationContext(), "XG_V2_ACCESS_ID", "0");
			XingeApp xinge = new XingeApp(accID, secretKey);
			JSONObject ret = xinge.pushSingleDevice(token, message);
			ret_code = ret.getInt("ret_code");
		} catch (Throwable e) {
			updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_request_fail).toString() + e.getMessage());
		}
		if (ret_code == 0) {
			updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_request_success).toString());
		} else {
			updateProgress((System.currentTimeMillis() - timeMillis) + APPSingleton.getContext().getString(R.string.ms_xg_request_fail).toString() + errCodeHandle(ret_code));
		}
		stepLast();
	}

	private void stepLast() {
		updateProgress(APPSingleton.getContext().getString(R.string.one_key_over).toString());
		updateProgress(APPSingleton.getContext().getString(R.string.send_to_manager).toString());
		updateProgress(APPSingleton.getContext().getString(R.string.cut_to_manager).toString());
	}

	private static class HandlerExtension extends Handler {
		WeakReference<DiagnosisActivity> mActivity;

		HandlerExtension(DiagnosisActivity activity) {
			mActivity = new WeakReference<DiagnosisActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			DiagnosisActivity theActivity = mActivity.get();
			if (theActivity == null) {
				theActivity = new DiagnosisActivity();
			}
			if (msg != null) {
				Log.w(Constants.LogTag, msg.obj.toString());
				ExtendedListView mListView = (ExtendedListView) theActivity.findViewById(android.R.id.list);
				DummyAdapter adapter = (DummyAdapter) mListView.getAdapter();
				adapter.getData().add(msg.obj.toString());
				adapter.notifyDataSetChanged();
			}
		}
	}

	private static String errCodeHandle(int errCode) {
		switch (errCode) {
		case -1:
			return APPSingleton.getContext().getString(R.string.parameter_error_validate).toString();
		case -2:
			return APPSingleton.getContext().getString(R.string.requst_timestamp_error).toString();
		case -3:
			return APPSingleton.getContext().getString(R.string.sign_code_error).toString();
		case 7:
			return APPSingleton.getContext().getString(R.string.bind_more_10).toString();
		case 14:
			return APPSingleton.getContext().getString(R.string.token_error).toString();
		case 15:
			return APPSingleton.getContext().getString(R.string.xg_server_busy).toString();
		case 19:
			return APPSingleton.getContext().getString(R.string.oprate_error).toString();
		case 20:
			return APPSingleton.getContext().getString(R.string.authentication_error).toString();
		case 40:
			return APPSingleton.getContext().getString(R.string.push_token_error).toString();
		case 48:
			return APPSingleton.getContext().getString(R.string.push_account_error).toString();
		case 63:
			return APPSingleton.getContext().getString(R.string.label_system_busy).toString();
		case 71:
			return APPSingleton.getContext().getString(R.string.apns_server_busy).toString();
		case 73:
			return APPSingleton.getContext().getString(R.string.message_char_more).toString();
		case 76:
			return APPSingleton.getContext().getString(R.string.requst_busy).toString();
		case 100:
			return APPSingleton.getContext().getString(R.string.apns_certificate_error).toString();
		case 2:
			return APPSingleton.getContext().getString(R.string.param_error).toString();
		case 10000:
			return APPSingleton.getContext().getString(R.string.start_error).toString();
		case 10001:
			return APPSingleton.getContext().getString(R.string.oprate_type_error).toString();
		case 10002:
			return APPSingleton.getContext().getString(R.string.execute_logon_error).toString();
		case 10003:
			return APPSingleton.getContext().getString(R.string.permission_error).toString();
		case 10004:
			return APPSingleton.getContext().getString(R.string.so_error).toString();
		case 10100:
			return APPSingleton.getContext().getString(R.string.network_not_in_use).toString();
		case 10101:
			return APPSingleton.getContext().getString(R.string.create_net_error).toString();
		case 10102:
			return APPSingleton.getContext().getString(R.string.request_handle_close).toString();
		case 10103:
			return APPSingleton.getContext().getString(R.string.request_handle_server_close).toString();
		case 10104:
			return APPSingleton.getContext().getString(R.string.request_handle_client_error).toString();
		case 10105:
			return APPSingleton.getContext().getString(R.string.request_handle_time_out).toString();
		case 10106:
			return APPSingleton.getContext().getString(R.string.request_handle_wait_out).toString();
		case 10107:
			return APPSingleton.getContext().getString(R.string.request_handle_receive_out).toString();
		case 10108:
			return APPSingleton.getContext().getString(R.string.server_return_error).toString();
		case 10109:
			return APPSingleton.getContext().getString(R.string.unknown_exception).toString();
		case 10110:
			return APPSingleton.getContext().getString(R.string.create_handler_null).toString();
		default:
			return "";
		}
	}

	private static Object getMetaData(Context paramContext, String name, Object defaultValue) throws NameNotFoundException {
		PackageManager packageManager = paramContext.getPackageManager();
		ApplicationInfo applicationInfo = packageManager.getApplicationInfo(paramContext.getPackageName(), PackageManager.GET_META_DATA);
		if (applicationInfo != null) {
			Object obj = applicationInfo.metaData.get(name);
			if (obj != null) {
				return obj;
			}
		}
		return defaultValue;
	}

	private void updateProgress(String mobj) {
		m = handler.obtainMessage();
		m.obj = mobj;
		m.sendToTarget();
	}

	@Override
	public void ReGetData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
