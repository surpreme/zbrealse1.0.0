package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import com.aite.a.base.Mark;
import com.aite.a.model.WorkerDataInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发送站内信
 * */
public class SendLetterActivity extends Activity implements Mark {

	private Context mContext;
	private RelativeLayout rel_send_object;
	private TextView tv_worker, tv_bctitle, btn_send;
	private NetRun mNetRun;
	private List<WorkerDataInfo> workerDataInfoList;
	private List<String> workerList;
	private String type = "";
	private ImageView iv_bcreturn;
	private EditText ed_inputname, et_input_content;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			
			case send_webmessage_id:
				// 发送站内信
				if (msg.obj != null) {
					String str = (String) msg.obj;
					Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
					if (str.equals(getString(R.string.order_reminder127))) {
						finish();
					}
				}
				break;
			case send_webmessage_err:
				Toast.makeText(mContext,
						getResources().getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onResume() {
		if (getIntent() != null) {
			type = getIntent().getStringExtra("type");
			if (type.equals("designer")) {
				/**
				 * 设置为横屏
				 */
				if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}
		super.onResume();
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_send_letter);
		init();
		setListener();
	}

	private void init() {
		mContext = SendLetterActivity.this;
		rel_send_object = (RelativeLayout) findViewById(R.id.rel_send_object);
		tv_worker = (TextView) findViewById(R.id.tv_worker);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		btn_send = (TextView) findViewById(R.id.btn_send);
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		ed_inputname = (EditText) findViewById(R.id.ed_inputname);
		et_input_content = (EditText) findViewById(R.id.et_input_content);
		tv_bctitle.setText(R.string.to_send_station_letter);
		workerList = new ArrayList<String>();
		mNetRun = new NetRun(mContext, handler);
	}

	private void setListener() {
		rel_send_object.setOnClickListener(clickListener);
		iv_bcreturn.setOnClickListener(clickListener);
		btn_send.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if(v.getId()==R.id._iv_back){
				finish();
			}else if(v.getId()==R.id.btn_send){
				// 发送
				String string = et_input_content.getText().toString();
				String name = ed_inputname.getText().toString();
				if (TextUtils.isEmpty(string) || TextUtils.isEmpty(name)) {
					Toast.makeText(SendLetterActivity.this,
							getString(R.string.errr), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				ChooseType(string, name);
			}

//			switch (v.getId()) {
//			case R.id.rel_send_object:// 选择工人
//
//				break;
//			case R.id._iv_back:
//				finish();
//				break;
//			case R.id.btn_send:
//				// 发送
//				String string = et_input_content.getText().toString();
//				String name = ed_inputname.getText().toString();
//				if (TextUtils.isEmpty(string) || TextUtils.isEmpty(name)) {
//					Toast.makeText(SendLetterActivity.this,
//							getString(R.string.errr), Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				ChooseType(string, name);
//				break;
//			}
		}
	};

	private String str;


	/**
	 * 选择类型
	 */
	private void ChooseType(final String str1, final String str2) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this,
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		dialog.setTitle(getString(R.string.message_type));
		String[] items = new String[] { getString(R.string.direct_messages),
				getString(R.string.leave_a_message) };
		dialog.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				switch (which) {
				case 0:
					// 私信
					mNetRun.SendWebmessage(str1, str2, "0");
					break;
				case 1:
					// 留言
					mNetRun.SendWebmessage(str1, str2, "2");
					break;
				}
			}
		});
		dialog.setNegativeButton(getString(R.string.cancel), null);
		dialog.create().show();
	}

}
