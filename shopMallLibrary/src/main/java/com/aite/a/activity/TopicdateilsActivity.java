package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.TopicdateilsInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;
/**
 * 话题详情
 * @author Administrator
 *
 */
public class TopicdateilsActivity extends BaseActivity implements OnClickListener{
	private TextView _tv_name,tv_biaoti,tv_topicname,tv_topictime,tv_centent;
	private ImageView _iv_back,iv_title;
	private NetRun netRun;
	private BitmapUtils bitmaputils2;
	private TopicdateilsInfo topicdateilsInfo;
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case topic_dateils_id:
				if (msg.obj!=null) {
					topicdateilsInfo=(TopicdateilsInfo) msg.obj;
					initv(topicdateilsInfo);
				}
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topicdetails);
		findViewById();
	}
	@Override
	public void ReGetData() {
		

	}

	@Override
	protected void findViewById() {
		_tv_name=(TextView) findViewById(R.id._tv_name);
		tv_biaoti=(TextView) findViewById(R.id.tv_biaoti);
		tv_topicname=(TextView) findViewById(R.id.tv_topicname);
		tv_topictime=(TextView) findViewById(R.id.tv_topictime);
		tv_centent=(TextView) findViewById(R.id.tv_centent);
		_iv_back=(ImageView) findViewById(R.id._iv_back);
		iv_title=(ImageView) findViewById(R.id.iv_title);
		initView();
	}
	/**
	 * 初始数据
	 */
	private void initv(TopicdateilsInfo topicdateilsInfo){
		_tv_name.setText(getString(R.string.hautixiangqing));
		tv_biaoti.setText(topicdateilsInfo.theme_name);
		tv_topicname.setText(topicdateilsInfo.circle_name);
		tv_topictime.setText(TimeStamp2Date(topicdateilsInfo.theme_addtime,"yyyy-MM-dd HH:mm:ss"));
		tv_centent.setText(topicdateilsInfo.theme_content);
		bitmaputils2=new BitmapUtils(TopicdateilsActivity.this);
		bitmaputils2.display(iv_title, topicdateilsInfo.member_avatar);
	}
	
	@Override
	protected void initView() {
		netRun=new NetRun(this, handler);
		String theme_id = getIntent().getStringExtra("theme_id");
		netRun.Topicdateils(theme_id);
		_iv_back.setOnClickListener(this);
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
//		}
		if(v.getId()== R.id._iv_back){
			finish();
		}
	}
	/**
	 * 时间戳转时间
	 * 
	 * @param timestampString
	 * @param formats
	 * @return
	 */
	public String TimeStamp2Date(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat(formats)
				.format(new java.util.Date(timestamp));
		return date;
	}
}
