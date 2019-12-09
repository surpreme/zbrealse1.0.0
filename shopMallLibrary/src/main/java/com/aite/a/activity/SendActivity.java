package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.Confirmsend;
import com.aite.a.parse.NetRun;
import com.aite.a.view.EditTextWithDel;
/**
 * 确认发货
 * @author Administrator
 *
 */
public class SendActivity extends BaseActivity implements OnClickListener {
	private Button bt_determinesend;
	private EditTextWithDel ed_order_no, ed_logisticsnumber, ed_sendnote,
			ed_couriernumber, ed_thegoodsname, ed_thegoodsregion,
			ed_thegoodsregiondetailed, ed_thegoodsphone, ed_thegoodslandline,
			ed_oneselfnumber, ed_thegoodsnumber;
	private NetRun netRun;
	private ImageView _iv_back;
	private TextView _tv_name;
	private Confirmsend confirmsendinfo;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case confirm_send_id:
				if (msg.obj != null) {
					confirmsendinfo = (Confirmsend) msg.obj;
					initView();
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendsetting);
		findViewById();

	}

	@Override
	public void ReGetData() {
	}

	@Override
	protected void findViewById() {
		bt_determinesend = (Button) findViewById(R.id.bt_determinesend);
		ed_order_no = (EditTextWithDel) findViewById(R.id.ed_order_no);
		ed_logisticsnumber = (EditTextWithDel) findViewById(R.id.ed_logisticsnumber);
		ed_sendnote = (EditTextWithDel) findViewById(R.id.ed_sendnote);
		ed_couriernumber = (EditTextWithDel) findViewById(R.id.ed_couriernumber);
		ed_thegoodsname = (EditTextWithDel) findViewById(R.id.ed_thegoodsname);
		ed_thegoodsregion = (EditTextWithDel) findViewById(R.id.ed_thegoodsregion);
		ed_thegoodsregiondetailed = (EditTextWithDel) findViewById(R.id.ed_thegoodsregiondetailed);
		ed_thegoodsphone = (EditTextWithDel) findViewById(R.id.ed_thegoodsphone);
		ed_thegoodslandline = (EditTextWithDel) findViewById(R.id.ed_thegoodslandline);
		ed_oneselfnumber = (EditTextWithDel) findViewById(R.id.ed_oneselfnumber);
		ed_thegoodsnumber = (EditTextWithDel) findViewById(R.id.ed_thegoodsnumber);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_tv_name = (TextView) findViewById(R.id._tv_name);

		bt_determinesend.setOnClickListener(this);
		_iv_back.setOnClickListener(this);
		initData();
	}

	@Override
	protected void initView() {
		ed_order_no.setText(confirmsendinfo.datas.order_info.order_sn+"");
		ed_thegoodsname.setText(confirmsendinfo.datas.order_info.buyer_name+"");
		ed_thegoodsregion.setText(confirmsendinfo.datas.order_info.extend_order_common.reciver_info.area+"");
		ed_thegoodsregiondetailed.setText(confirmsendinfo.datas.order_info.extend_order_common.reciver_info.address+"");
		ed_thegoodsphone.setText(confirmsendinfo.datas.order_info.extend_order_common.reciver_info.mob_phone+"");
		ed_thegoodslandline.setText(confirmsendinfo.datas.order_info.extend_order_common.reciver_info.tel_phone+"");
	}

	@Override
	protected void initData() {
		netRun = new NetRun(this, handler);
		String order_id = getIntent().getStringExtra("order_id");
		netRun.confirmsend(order_id);
		_tv_name.setText(getI18n(R.string.determinesend));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}
//		switch (v.getId()) {
//		case R.id.bt_determinesend:
//
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		}
	}
}
