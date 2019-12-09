package com.aite.a.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.aite.a.model.ExchangeRecordInfo;
import com.aite.a.model.ExchangeRecordInfo.prodlist;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 兑换记录
 * 
 * @author Administrator
 *
 */
public class ExchangeRecordActivity extends BaseActivity implements
		OnClickListener {
	private TextView _tv_name;
	private ImageView _iv_back;
	private ListView lv_record;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private List<ExchangeRecordInfo> exchangeRecordInfo;
	private Map<String, Object> map;
	private String hasmore, page_total;
	private Madapter madapter;
	private Gadapter gadapter;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case myexchange_record_id:
				if (msg.obj != null) {
					map = new HashMap<String, Object>();
					map = (Map<String, Object>) msg.obj;
					exchangeRecordInfo = (List<ExchangeRecordInfo>) map
							.get("1");
					hasmore = (String) map.get("2");
					page_total = (String) map.get("3");
					if (madapter==null) {
						madapter = new Madapter(exchangeRecordInfo);
						lv_record.setAdapter(madapter);
					}else {
						madapter.uupdata(exchangeRecordInfo);
					}
					
				}
				break;
			case myexchange_record_err:
				Toast.makeText(ExchangeRecordActivity.this, getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			case jfcancel_order_id:
				if (msg.obj!=null) {
					String str=(String) msg.obj;
					if (str.equals("success")) {
						netRun.MyExchangeRecord("1");
					}else {
						Toast.makeText(ExchangeRecordActivity.this,str,
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case jfcancel_order_err:
				Toast.makeText(ExchangeRecordActivity.this,  getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_record);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		lv_record = (ListView) findViewById(R.id.lv_record);
		initView();
	}

	@Override
	protected void initView() {
		_iv_back.setOnClickListener(this);
		_tv_name.setText(getString(R.string.verification_prompt16));
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		netRun.MyExchangeRecord("1");
	}
	
	@Override
	protected void initData() {
		
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			finish();
		}
	}

	/**
	 * 楼层适配
	 * 
	 * @author Administrator
	 *
	 */
	private class Madapter extends BaseAdapter {
		List<ExchangeRecordInfo> exchangeRecordInfo;
		
		public void uupdata(List<ExchangeRecordInfo> exchangeRecordInfo){
			this.exchangeRecordInfo=exchangeRecordInfo;
			notifyDataSetChanged();
		}
		
		public Madapter(List<ExchangeRecordInfo> exchangeRecordInfo) {
			this.exchangeRecordInfo = exchangeRecordInfo;
		}

		@Override
		public int getCount() {
			return exchangeRecordInfo.size();
		}

		@Override
		public Object getItem(int position) {
			return exchangeRecordInfo == null ? null : exchangeRecordInfo
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ExchangeRecordActivity.this,
						R.layout.exchangerecord_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final ExchangeRecordInfo exchangeRecordInfo2 = exchangeRecordInfo
					.get(position);
			holder.tv_number.setText(getString(R.string.verification_prompt17)
					+ exchangeRecordInfo2.point_ordersn);
			holder.tv_state.setText(exchangeRecordInfo2.point_orderstatetext);
			holder.tv_total.setText(getString(R.string.verification_prompt18)
					+ exchangeRecordInfo2.point_allpoint);
			Gadapter gadapter = new Gadapter(exchangeRecordInfo2.prodlist,
					exchangeRecordInfo2.point_addtime);
			holder.mlv_glist.setAdapter(gadapter);
			if (exchangeRecordInfo2.point_orderallowcancel.equals("true")) {
				holder.tv_cancel.setVisibility(View.VISIBLE);
			}else {
				holder.tv_cancel.setVisibility(View.GONE);
			}
			holder.tv_toview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 查看
					Intent intent2 = new Intent(ExchangeRecordActivity.this,IntegarlOrderInfoActivity.class);
					intent2.putExtra("order_id", exchangeRecordInfo2.point_orderid);
					startActivity(intent2);
				}
			});
			holder.tv_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 取消
					netRun.CancelExchange(exchangeRecordInfo2.point_orderid);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_number, tv_state, tv_total, tv_cancel, tv_toview;
			MyListView mlv_glist;

			public ViewHolder(View convertView) {
				tv_number = (TextView) convertView.findViewById(R.id.tv_number);
				tv_state = (TextView) convertView.findViewById(R.id.tv_state);
				tv_total = (TextView) convertView.findViewById(R.id.tv_total);
				tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
				tv_toview = (TextView) convertView.findViewById(R.id.tv_toview);
				mlv_glist = (MyListView) convertView
						.findViewById(R.id.mlv_glist);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 商品适配
	 * 
	 * @author Administrator
	 *
	 */
	private class Gadapter extends BaseAdapter {
		List<prodlist> prodlist;
		String point_addtime="";

		public Gadapter(List<prodlist> prodlist, String point_addtime) {
			this.prodlist = prodlist;
			this.point_addtime = point_addtime;
		}

		@Override
		public int getCount() {
			return prodlist.size();
		}

		@Override
		public Object getItem(int position) {
			return prodlist == null ? null : prodlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ExchangeRecordActivity.this,
						R.layout.exchangerecord2_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ExchangeRecordInfo.prodlist prodlist2 = prodlist
					.get(position);
			bitmapUtils.display(holder.iv_gimg, prodlist2.point_goodsimage);
			holder.tv_jfgname.setText(prodlist2.point_goodsname);
			holder.tv_jfnum.setText(getString(R.string.verification_prompt19) + prodlist2.point_goodsnum);
			holder.tv_jfintegral.setText(getString(R.string.verification_prompt20) + prodlist2.point_goodspoints+"");
			holder.tv_jftime.setText(getString(R.string.verification_prompt21)
					+ TimeStamp2Date(point_addtime, "yyyy-MM-dd HH:mm"));
			return convertView;
		}

		class ViewHolder {
			ImageView iv_gimg;
			TextView tv_jfgname, tv_jfnum, tv_jfintegral, tv_jftime;

			public ViewHolder(View convertView) {
				iv_gimg = (ImageView) convertView.findViewById(R.id.iv_gimg);
				tv_jfgname = (TextView) convertView.findViewById(R.id.tv_jfgname);
				tv_jfnum = (TextView) convertView.findViewById(R.id.tv_jfnum);
				tv_jfintegral = (TextView) convertView
						.findViewById(R.id.tv_jfintegral);
				tv_jftime = (TextView) convertView.findViewById(R.id.tv_jftime);
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
