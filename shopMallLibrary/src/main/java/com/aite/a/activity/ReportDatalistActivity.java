package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ReportDatalisInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyShiGuangZhou;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 举报详情
 * 
 * @author Administrator
 *
 */
public class ReportDatalistActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_bcreturn, iv_ivon, iv_img1, iv_img2, iv_img3;
	private TextView tv_bctitle, tv_progress1, tv_progress2, tv_progress3,
			tv_storename, tv_gname, tv_theme, tv_title, tv_content, tv_return;
	private MyShiGuangZhou msgz_sgz;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private ReportDatalisInfo reportDatalisInfo;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case report_datalis_id:
				if (msg.obj != null) {
					reportDatalisInfo = (ReportDatalisInfo) msg.obj;
					tv_storename
							.setText(reportDatalisInfo.inform_info.inform_member_name);
					tv_gname.setText(reportDatalisInfo.goods_info.goods_name);
					tv_theme.setText(reportDatalisInfo.subject_info.inform_subject_type_name);
					tv_title.setText(reportDatalisInfo.subject_info.inform_subject_content);
					tv_content
							.setText(reportDatalisInfo.inform_info.inform_content);
					bitmapUtils.display(iv_ivon,
							reportDatalisInfo.goods_info.goods_image);
					if (reportDatalisInfo.inform_info.inform_pic1 != null
							&& !reportDatalisInfo.inform_info.inform_pic1
									.equals("")) {
						iv_img1.setVisibility(View.VISIBLE);
						bitmapUtils.display(iv_img1,
								reportDatalisInfo.inform_info.inform_pic1);
					} else {
						iv_img1.setVisibility(View.GONE);
					}
					if (reportDatalisInfo.inform_info.inform_pic2 != null
							&& !reportDatalisInfo.inform_info.inform_pic2
									.equals("")) {
						iv_img2.setVisibility(View.VISIBLE);
						bitmapUtils.display(iv_img2,
								reportDatalisInfo.inform_info.inform_pic2);
					} else {
						iv_img2.setVisibility(View.GONE);
					}
					if (reportDatalisInfo.inform_info.inform_pic3 != null
							&& !reportDatalisInfo.inform_info.inform_pic3
									.equals("")) {
						iv_img3.setVisibility(View.VISIBLE);
						bitmapUtils.display(iv_img3,
								reportDatalisInfo.inform_info.inform_pic3);
					} else {
						iv_img3.setVisibility(View.GONE);
					}
					if (reportDatalisInfo.inform_info.inform_state.equals("2")) {
						msgz_sgz.setProgress(3);
					}
				}
				break;
			case report_datalis_err:
				Toast.makeText(ReportDatalistActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		findViewById();
	}

	
	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		iv_ivon = (ImageView) findViewById(R.id.iv_ivon);
		iv_img1 = (ImageView) findViewById(R.id.iv_img1);
		iv_img2 = (ImageView) findViewById(R.id.iv_img2);
		iv_img3 = (ImageView) findViewById(R.id.iv_img3);
		tv_progress1 = (TextView) findViewById(R.id.tv_progress1);
		tv_progress2 = (TextView) findViewById(R.id.tv_progress2);
		tv_progress3 = (TextView) findViewById(R.id.tv_progress3);
		tv_storename = (TextView) findViewById(R.id.tv_storename);
		tv_gname = (TextView) findViewById(R.id.tv_gname);
		tv_theme = (TextView) findViewById(R.id.tv_theme);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_return = (TextView) findViewById(R.id.tv_return);
		msgz_sgz = (MyShiGuangZhou) findViewById(R.id.msgz_sgz);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_bctitle.setText(getString(R.string.distribution_center35));
		initView();
	}

	@Override
	protected void initView() {
		iv_bcreturn.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		String inform_id = getIntent().getStringExtra("inform_id");
		netRun.ReportDatalis(inform_id);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		}
	}
}
