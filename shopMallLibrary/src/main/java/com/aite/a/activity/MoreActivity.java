package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.AppManager;
import com.aite.a.ExamineUpdate;
import com.aite.a.HomeTabActivity;
import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;

import chat.utils.TXImUtils;

/**
 * 更多
 * 
 * @author xiaoyu
 * 
 */
public class MoreActivity extends BaseActivity implements OnClickListener, Mark {

	private RelativeLayout rl_guide, rl_check, rl_about, rl_image, rl_xieyi;
	private Button btn_exit;
	private Context context = MoreActivity.this;
	private NetRun netRun;
	//关于跳转的网址
	private String zhuye="http://cnaite.cn/";
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case userOut_id:
				if (msg.obj.equals("1")) {
					State.UserKey = null;
					State.User = null;
					AppManager.getInstance().killAllActivity();
					openActivity(HomeTabActivity.class);
				} else {
					State.UserKey = null;
					State.User = null;
					AppManager.getInstance().killAllActivity();
					openActivity(HomeTabActivity.class);
				}
				mdialog.dismiss();
				break;
			case userOut_err:
				mdialog.dismiss();
				CommonTools.showShortToast(MoreActivity.this, getI18n(R.string.systembusy));
				break;
			case userOut_start:
//				mdialog.setMessage(getI18n(R.string.safe_exit));
				mdialog.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity);
		netRun = new NetRun(context, handler);
		findViewById();
		initData();
	}

	@Override
	protected void findViewById() {
		rl_xieyi = (RelativeLayout) findViewById(R.id.more_rl_xieyi);
		rl_image = (RelativeLayout) findViewById(R.id.more_rl_image);
		btn_exit = (Button) findViewById(R.id.more_btn_exit);
		rl_about = (RelativeLayout) findViewById(R.id.more_rl_about);
		rl_check = (RelativeLayout) findViewById(R.id.more_rl_check_up);
		rl_guide = (RelativeLayout) findViewById(R.id.more_rl_guide);
		tv_title_name = (TextView) findViewById(R.id._tv_name);
		iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_right = (ImageView) findViewById(R.id._iv_right);
		initView();
	}

	@Override
	protected void initView() {
		rl_xieyi.setOnClickListener(this);
		rl_image.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
		rl_about.setOnClickListener(this);
		rl_check.setOnClickListener(this);
		rl_guide.setOnClickListener(this);
		tv_title_name.setText(getI18n(R.string.more));
		iv_back.setOnClickListener(this);
		iv_right.setOnClickListener(this);
//		iv_right.setBackgroundResource(R.drawable.guanbi);
		iv_right.setVisibility(View.GONE);
		mHandler = new Handler();
	}

	@Override
	protected void initData() {
		if (State.UserKey == null) {
			btn_exit.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.more_rl_image){
			mdialog.show();
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mdialog.dismiss();
				}
			}, 2000);
		}else if(v.getId()==R.id.more_btn_exit){
			netRun.userOut();
			//清除表（账号密码等）
			SharedPreferences sp = getSharedPreferences("LoginActivity", MODE_PRIVATE);
			editor=sp.edit();
			editor.putString("password", "");
			editor.commit();
			TXImUtils.TXimLogout();//腾讯退出登陆
		}else if(v.getId()== R.id.more_rl_about){
			Intent intent33 = new Intent(MoreActivity.this, WebActivity.class);
			intent33.putExtra("url", "http://aitecc.com/wap/index.php?act=article&op=article_show_index&article_id=22");
			startActivity(intent33);
		}else if(v.getId()==R.id.more_rl_check_up){
			new ExamineUpdate(this, mdialog, true);
		}else if(v.getId()== R.id.more_rl_guide){
			intent = new Intent(this, GuideActivity.class);
			startActivity(intent);
			finish();
			overrIn();
		}else if(R.id._iv_back==v.getId()){
			finish();
			overrPre();
		}else if(R.id._iv_right==v.getId()){
			finish();
			overrPre();
		}

//		switch (v.getId()) {
//		case R.id.more_rl_xieyi:// 用户协议
//			break;
//		case R.id.more_rl_image:
////			mdialog.setMessage(getI18n(R.string.clean_cache));
//			mdialog.show();
//			mHandler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					mdialog.dismiss();
//				}
//			}, 2000);
//			break;
//		case R.id.more_btn_exit:
//			netRun.userOut();
//			//清除表（账号密码等）
//			SharedPreferences sp = getSharedPreferences("LoginActivity", MODE_PRIVATE);
//			editor=sp.edit();
//			editor.putString("password", "");
//			editor.commit();
//			TXImUtils.TXimLogout();//腾讯退出登陆
//			//TODO
//			break;
//		case R.id.more_rl_about:// 关于
////			Uri uri = Uri.parse(zhuye);
////			Intent it = new Intent(Intent.ACTION_VIEW, uri);
////			startActivity(it);
//			Intent intent33 = new Intent(MoreActivity.this, WebActivity.class);
//			intent33.putExtra("url", "http://aitecc.com/wap/index.php?act=article&op=article_show_index&article_id=22");
//			startActivity(intent33);
//			break;
//		case R.id.more_rl_check_up:// 检查更新
//			new ExamineUpdate(this, mdialog, true);
//			break;
//		case R.id.more_rl_guide:
//			intent = new Intent(this, GuideActivity.class);
//			startActivity(intent);
//			finish();
//			overrIn();
//			break;
//		case R.id._iv_back:
//			finish();
//			overrPre();
//			break;
//		case R.id._iv_right:
//			finish();
//			overrPre();
//			break;
//
//		}

	}
	
	private Editor editor;
	@Override
	public void ReGetData() {
	}

}
