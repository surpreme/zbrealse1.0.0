package com.aite.a.activity;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;





import com.aite.a.parse.NetRun;
import com.aite.a.utils.lingshi;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

public class SellerOrderprice extends Activity implements OnClickListener {
	private ImageView iv_touxiang, iv_fanhuifcf;
	private TextView tv_namees, tv_shuliang, tv_jian, tv_jia, tv_lastpri,
			tv_postage, tv_lastprice, tv_actualprice, tv_number, tv_time,
			tv_start, tv_jieshaoos;
	private EditText ed_price, ed_postage;
	private CheckBox cb_free;
	private Boolean isfree = false;
	private boolean ifjia = true;
	private NetRun netRun;
	private String order_id;
	private String price2;
	private String shipping_fee;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modifyprive_dialog);
		netRun = new NetRun(this, handler);
		findViewByIdd();
		initdata();
	}

	private void findViewByIdd() {
		iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
		iv_fanhuifcf = (ImageView) findViewById(R.id.iv_fanhuifcf);
		tv_namees = (TextView) findViewById(R.id.tv_namees);
		tv_shuliang = (TextView) findViewById(R.id.tv_shuliang);
		tv_jian = (TextView) findViewById(R.id.tv_jian);
		tv_jia = (TextView) findViewById(R.id.tv_jia);
		tv_lastpri = (TextView) findViewById(R.id.tv_lastpri);
		tv_postage = (TextView) findViewById(R.id.tv_postage);
		tv_lastprice = (TextView) findViewById(R.id.tv_lastprice);
		tv_actualprice = (TextView) findViewById(R.id.tv_actualprice);
		tv_number = (TextView) findViewById(R.id.tv_number);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_start = (TextView) findViewById(R.id.tv_start);
		tv_jieshaoos = (TextView) findViewById(R.id.tv_jieshaoos);
		ed_price = (EditText) findViewById(R.id.ed_price);
		ed_postage = (EditText) findViewById(R.id.ed_postage);
		cb_free = (CheckBox) findViewById(R.id.cb_free);
		iv_fanhuifcf.setOnClickListener(this);
		tv_start.setOnClickListener(this);
		tv_jian.setOnClickListener(this);
		tv_jia.setOnClickListener(this);
		cb_free.setOnCheckedChangeListener(cc);

	}
	/**
	 * 时间戳转时间
	 * 
	 * @param timestampString
	 *            时间戳
	 * @param formats
	 *            格式(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public String TimeStamp2Date(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat(formats)
				.format(new java.util.Date(timestamp));
		return date;
	}
	
	private void initdata() {
		int position = lingshi.getInstance().getPosition();
		List<Map<String, Object>> data = lingshi.getInstance().getData();
		String name = (String) data.get(position).get("name");
		String image = (String) data.get(position).get("image");
		price2 = (String) data.get(position).get("price2");
		String num = (String) data.get(position).get("num");
		String price = (String) data.get(position).get("price");
		String order_sn = (String) data.get(position).get("order_sn");
		shipping_fee = (String) data.get(position).get("shipping_fee");
		String add_time = (String) data.get(position).get("add_time");
		order_id = lingshi.getInstance().getOrder_id();
		BitmapUtils bitmapUtils = new BitmapUtils(this);

		bitmapUtils.display(iv_touxiang, image);
		tv_actualprice.setText("¥"+price);
		tv_namees.setText(price2);
		tv_shuliang.setText(num);
		tv_number.setText(order_sn);
		tv_postage.setText(shipping_fee);
		String date = TimeStamp2Date(add_time, "yyyy-MM-dd HH:mm:ss");
		tv_time.setText(date+"");
		tv_jieshaoos.setText(name);
		tv_lastpri.setText("¥" + price2);
		tv_postage.setText("¥" + shipping_fee);

		tv_lastprice.setText("¥"+price2);
		
		ed_price.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(ed_price.getText().toString())) {
					count();
				} else {
					tv_lastpri.setText("¥" + price2);
				}

			}
		});
		ed_postage.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String modify_price = ed_postage.getText().toString();
				if (!TextUtils.isEmpty(ed_postage.getText().toString())) {
					tv_postage.setText("¥" + modify_price);
				} else {
					tv_postage.setText("¥" + shipping_fee);
				}
			}
		});
	}

	private void count() {
		String goods_price = ed_price.getText().toString();
		if (ifjia) {
			float a = Float.parseFloat(goods_price);
			float b = Float.parseFloat(price2);
			// int a = Integer.valueOf(goods_price).intValue();
			// int b = Integer.valueOf(price2).intValue();
			float c = a + b;
			DecimalFormat decimalFormat=new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
			String d=decimalFormat.format(c);
//			String d = String.valueOf(c);
			tv_lastpri.setText("¥" + d);
		} else {
			float a = Float.parseFloat(goods_price);
			float b = Float.parseFloat(price2);
			// int a = Integer.valueOf(goods_price).intValue();
			// int b = Integer.valueOf(price2).intValue();
			float c = b - a;
			DecimalFormat decimalFormat=new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
			String d=decimalFormat.format(c);
//			String d = String.valueOf(c);
			tv_lastpri.setText("¥" + d);
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.iv_fanhuifcf){
			finish();
		}else if(v.getId()==R.id.tv_start){
			// 确认修改
			start();
		} else if (v.getId()==R.id.tv_jian) {
			// 减
			tv_jian.setBackgroundResource(R.drawable.dialog_modifyprivejian);
			tv_jia.setBackgroundResource(R.drawable.dialog_modifyprivejia2);
			ifjia = false;
			if (!TextUtils.isEmpty(ed_price.getText().toString())) {
				count();
			} else {
				tv_lastpri.setText("¥" + price2);
			}
		}else if(v.getId()==R.id.tv_jia){
			// 涨
			ifjia = true;
			tv_jian.setBackgroundResource(R.drawable.dialog_modifyprivejian2);
			tv_jia.setBackgroundResource(R.drawable.dialog_modifyprivejia);
			if (!TextUtils.isEmpty(ed_price.getText().toString())) {
				count();
			} else {
				tv_lastpri.setText("¥" + price2);
			}
		}
//		switch (v.getId()) {
//		case R.id.iv_fanhuifcf:
//			finish();
//			break;
//		case R.id.tv_start:
//			// 确认修改
//			start();
//			break;
//		case R.id.tv_jian:
//			// 减
//			tv_jian.setBackgroundResource(R.drawable.dialog_modifyprivejian);
//			tv_jia.setBackgroundResource(R.drawable.dialog_modifyprivejia2);
//			ifjia = false;
//			if (!TextUtils.isEmpty(ed_price.getText().toString())) {
//				count();
//			} else {
//				tv_lastpri.setText("¥" + price2);
//			}
//			break;
//		case R.id.tv_jia:
//			// 涨
//			ifjia = true;
//			tv_jian.setBackgroundResource(R.drawable.dialog_modifyprivejian2);
//			tv_jia.setBackgroundResource(R.drawable.dialog_modifyprivejia);
//			if (!TextUtils.isEmpty(ed_price.getText().toString())) {
//				count();
//			} else {
//				tv_lastpri.setText("¥" + price2);
//			}
//			break;
//		}
	}

	private void start() {
		String goods_price = ed_price.getText().toString();
		String modify_price = ed_postage.getText().toString();

		goods_price = TextUtils.isEmpty(ed_price.getText().toString()) ? "0"
				: goods_price;
		modify_price = TextUtils.isEmpty(ed_postage.getText().toString()) ? "0"
				: modify_price;
		// 修改价格
		if (ifjia) {
			float a = Float.parseFloat(goods_price);
			float b = Float.parseFloat(price2);
			// int a = Integer.valueOf(goods_price).intValue();
			// int b = Integer.valueOf(price2).intValue();
			float c = a + b;
			String s = String.valueOf(c);
			netRun.ModifyOrder(order_id, "goods_price", s);
		} else {
			float a = Float.parseFloat(goods_price);
			float b = Float.parseFloat(price2);
			// int a = Integer.valueOf(goods_price).intValue();
			// int b = Integer.valueOf(price2).intValue();
			float c = b - a;
			String s = String.valueOf(c);
			netRun.ModifyOrder(order_id, "goods_price", s);
		}
		// 修改运费
		if (isfree) {
			netRun.ModifyOrder(order_id, "modify_price", "0");
		} else {
			netRun.ModifyOrder(order_id, "modify_price", modify_price);
		}
		lingshi.getInstance().setIsupdate(true);
		finish();
	}

	private OnCheckedChangeListener cc = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				ed_postage.setText("0");
				ed_postage.setFocusable(false);
				ed_postage.setFocusableInTouchMode(false);
			} else {
				ed_postage.setFocusableInTouchMode(true);
				ed_postage.setFocusable(true);
			}
			isfree = isChecked;
		}
	};

}
