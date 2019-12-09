package com.aite.a.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.VouchersListInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 代金券列表
 * 
 * @author Administrator
 *
 */
public class VouchersListActivity extends BaseActivity implements
		OnClickListener {
	private TextView _tv_name, tv_menu1, tv_menu2, tv_menu3, tv_menu4;
	private ImageView _iv_back;
	private ListView lv_list;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private AllAdapter allAdapter;
	private VouchersListInfo vouchersListInfo;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case vouchers_list_id:
				if (msg.obj != null) {
					vouchersListInfo = (VouchersListInfo) msg.obj;
					allAdapter = new AllAdapter(
							vouchersListInfo.datas.voucher_list);
					lv_list.setAdapter(allAdapter);
				}
				break;
			case vouchers_list_err:
				Toast.makeText(VouchersListActivity.this, getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voucherslist_activity);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
		tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
		tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
		tv_menu4 = (TextView) findViewById(R.id.tv_menu4);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		lv_list = (ListView) findViewById(R.id.lv_list);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getString(R.string.order_reminder152));
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		tv_menu1.setOnClickListener(this);
		tv_menu2.setOnClickListener(this);
		tv_menu3.setOnClickListener(this);
		tv_menu4.setOnClickListener(this);
		_iv_back.setOnClickListener(this);
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		netRun.VouchersList("0");
	}

	@Override
	protected void initData() {

	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.tv_menu1){
			setmenucolor(tv_menu1);
			netRun.VouchersList("0");
		}else if (v.getId()==R.id.tv_menu2){
			setmenucolor(tv_menu2);
			netRun.VouchersList("1");
		}else  if (v.getId()==R.id.tv_menu3){
			setmenucolor(tv_menu3);
			netRun.VouchersList("2");
		}else if(v.getId()==R.id.tv_menu4){
			setmenucolor(tv_menu4);
			netRun.VouchersList("3");
		}else if (v.getId()==R.id._iv_back){
			finish();
		}

//		switch (v.getId()) {
//		case R.id.tv_menu1:
//			setmenucolor(tv_menu1);
//			netRun.VouchersList("0");
//			break;
//		case R.id.tv_menu2:
//			setmenucolor(tv_menu2);
//			netRun.VouchersList("1");
//			break;
//		case R.id.tv_menu3:
//			setmenucolor(tv_menu3);
//			netRun.VouchersList("2");
//			break;
//		case R.id.tv_menu4:
//			setmenucolor(tv_menu4);
//			netRun.VouchersList("3");
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		}
	}

	private void setmenucolor(TextView v) {
		tv_menu1.setBackgroundColor(0xffeeeeee);
		tv_menu2.setBackgroundColor(0xffeeeeee);
		tv_menu3.setBackgroundColor(0xffeeeeee);
		tv_menu4.setBackgroundColor(0xffeeeeee);
		v.setBackgroundColor(Color.WHITE);
	}

	/**
	 * 全部
	 * 
	 * @author Administrator
	 *
	 */
	private class AllAdapter extends BaseAdapter {
		List<VouchersListInfo.datas.voucher_list> voucher_list;

		public AllAdapter(List<VouchersListInfo.datas.voucher_list> voucher_list) {
			this.voucher_list = voucher_list;
		}

		@Override
		public int getCount() {
			return voucher_list.size();
		}

		@Override
		public Object getItem(int position) {
			return voucher_list == null ? null : voucher_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(VouchersListActivity.this,
						R.layout.vouchers_item, null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final VouchersListInfo.datas.voucher_list voucher_list2 = voucher_list.get(position);
			bitmapUtils.display(holder.iv_img,
					voucher_list2.voucher_t_customimg);
			holder.storename.setText(voucher_list2.store_name + getString(R.string.order_reminder153)
					+ voucher_list2.voucher_desc + ")");
			holder.tv_desc.setText(voucher_list2.voucher_desc);
			holder.validity.setText(getString(R.string.order_reminder87)
					+ TimeStamp2Date(voucher_list2.voucher_start_date,
							"yyyy-MM-dd")
					+ " ~ "
					+ TimeStamp2Date(voucher_list2.voucher_end_date,
							"yyyy-MM-dd"));
			holder.bt_use.setText(voucher_list2.voucher_state_text);
			if (voucher_list2.voucher_state_text.equals(getString(R.string.order_reminder154))){
				holder.bt_use.setBackgroundResource(R.drawable.integral_6);
				holder.bt_use.setTextColor(Color.WHITE);
			}else{
				holder.bt_use.setBackgroundResource(R.drawable.integral_7);
				holder.bt_use.setTextColor(Color.BLACK);
			}
			holder.bt_use.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 立即使用
//					if (holder.bt_use.getText().toString().equals(getString(R.string.order_reminder154))) {
						Intent intent = new Intent(VouchersListActivity.this,
								StoreDetailsActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("store_id", voucher_list2.store_id);
						intent.putExtras(bundle);
						startActivity(intent);
//					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView  storename, validity,tv_desc,bt_use,tv_type;
			ImageView iv_img;

			public ViewHolder(View convertView) {
				storename = (TextView) convertView.findViewById(R.id.storename);
				validity = (TextView) convertView.findViewById(R.id.validity);
				bt_use = (TextView) convertView.findViewById(R.id.bt_use);
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
				tv_type = (TextView) convertView.findViewById(R.id.tv_type);
				convertView.setTag(this);
			}
		}
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
}
