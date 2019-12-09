package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.RedPackageActivityListInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 红包列表
 * 
 * @author Administrator
 *
 */
public class RedPackageActivityList extends BaseActivity implements
		OnClickListener {

	private TextView _tv_name;
	private ImageView _iv_back;
	private ListView lv_list;
	private NetRun netRun;
	private List<RedPackageActivityListInfo> list;
	private MyAdapter myAdapter;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case redpackage_list_id:
				if (msg.obj != null) {
					list = (List<RedPackageActivityListInfo>) msg.obj;
					myAdapter = new MyAdapter(list);
					lv_list.setAdapter(myAdapter);
				}
				break;
			case redpackage_list_err:
				Toast.makeText(RedPackageActivityList.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redpackageactivity);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		lv_list = (ListView) findViewById(R.id.lv_list);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getString(R.string.redpackageactivity));
		_iv_back.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		initData();
	}

	@Override
	protected void initData() {
		netRun.RedPackageActivityList();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		netRun.RedPackageActivityList();
		Log.i("----RedPackage", " onRestart" );
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
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<RedPackageActivityListInfo> list;

		public MyAdapter(List<RedPackageActivityListInfo> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list == null ? null : list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(RedPackageActivityList.this,
						R.layout.item_hblist, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final RedPackageActivityListInfo redPackageActivityListInfo = list
					.get(position);

			holder.tv_name.setText(redPackageActivityListInfo.title);
			if (redPackageActivityListInfo.amount_type.equals("0")) {
				holder.tv_money.setText(getString(R.string.order_reminder85)
						+ redPackageActivityListInfo.amount);
			}else {
				holder.tv_money.setText(getString(R.string.order_reminder86)
						+ redPackageActivityListInfo.min_amount + "--"
						+ redPackageActivityListInfo.max_amount + getString(R.string.yuan));
			}
			
			holder.tv_validity.setText(getString(R.string.order_reminder87)
					+ TimeStamp2Date(redPackageActivityListInfo.start_date,
							"yyyy-MM-dd")
					+ "--"
					+ TimeStamp2Date(redPackageActivityListInfo.end_date,
							"yyyy-MM-dd"));
			if (redPackageActivityListInfo.state!=null&&redPackageActivityListInfo.state.equals("3")) {
				holder.tv_money2.setVisibility(View.VISIBLE);
				holder.tv_lq.setVisibility(View.VISIBLE);
				holder.tv_money2.setText("￥"
						+ redPackageActivityListInfo.member_amount);
			}else {
				holder.tv_money2.setVisibility(View.GONE);
				holder.tv_lq.setVisibility(View.GONE);
			}
			if (redPackageActivityListInfo.state.equals("1")) {
				holder.tv_dtails.setText(getString(R.string.order_reminder88));
				holder.tv_dtails.setBackgroundColor(0xfffd3b44);
			}else if (redPackageActivityListInfo.state.equals("2")) {
				holder.tv_dtails.setText(getString(R.string.order_reminder89));
				holder.tv_dtails.setBackgroundColor(0xffeeeeee);
			}else if (redPackageActivityListInfo.state.equals("3")) {
				holder.tv_dtails.setText(getString(R.string.evaluation_tips64));
				holder.tv_dtails.setBackgroundColor(0xffff5000);
			}
			holder.tv_dtails.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (redPackageActivityListInfo.state.equals("1")) {
						//详情
						Intent intent2 = new Intent(RedPackageActivityList.this,
								RedPackageDatails.class);
						intent2.putExtra("name", redPackageActivityListInfo.title);
						intent2.putExtra("id", redPackageActivityListInfo.id);
						startActivity(intent2);
					}else if (redPackageActivityListInfo.state.equals("3")) {
						//详情
						Intent intent2 = new Intent(RedPackageActivityList.this,
								RedPackageDatails.class);
						intent2.putExtra("name", redPackageActivityListInfo.title);
						intent2.putExtra("money", redPackageActivityListInfo.member_amount);
						intent2.putExtra("time", TimeStamp2Date(redPackageActivityListInfo.add_time, "yyyy-MM-dd HH:mm:ss"));
						startActivity(intent2);
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_name, tv_dtails, tv_money, tv_validity, tv_money2,tv_lq;

			public ViewHolder(View convertView) {
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_dtails = (TextView) convertView.findViewById(R.id.tv_dtails);
				tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				tv_validity = (TextView) convertView
						.findViewById(R.id.tv_validity);
				tv_money2 = (TextView) convertView.findViewById(R.id.tv_money2);
				tv_lq = (TextView) convertView.findViewById(R.id.tv_lq);
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
