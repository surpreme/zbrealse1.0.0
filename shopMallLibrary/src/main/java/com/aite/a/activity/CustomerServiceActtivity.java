package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.CustomerServiceinfo;
import com.aite.a.model.CustomerServiceinfo.datas.consult_list;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

/**
 * 咨询列表
 * 
 * @author Administrator
 *
 */
public class CustomerServiceActtivity extends BaseActivity implements
		OnClickListener {
	private TextView tv_customerservice, _tv_name;
	private ListView lv_consultinglist;
	private ImageView _iv_back;
	private NetRun netRun;
	private CustomerServiceinfo customerServiceinfo;
	private CustomerAdapter customerAdapter;
	private int pos = -1;
	private int pager = 1;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case customerservice_list_id:
				if (msg.obj != null) {
					customerServiceinfo = (CustomerServiceinfo) msg.obj;
					if (customerAdapter==null) {
						
						customerAdapter = new CustomerAdapter(customerServiceinfo.datas.consult_list);
						lv_consultinglist.setAdapter(customerAdapter);
					}else {
						customerAdapter.addList(customerServiceinfo.datas.consult_list);
					}
				}
				break;
			case customerservice_list_err:
				Toast.makeText(CustomerServiceActtivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case delete_customer_id:
				if (msg.obj != null) {
					String re = (String) msg.obj;
					if (re.equals("1")) {
						Toast.makeText(CustomerServiceActtivity.this,
								getString(R.string.customerservice31),
								Toast.LENGTH_SHORT).show();
						if (pos != -1) {
							customerServiceinfo.datas.consult_list.remove(pos);
							customerAdapter.notifyDataSetChanged();
						}
					}
				}
				break;
			case delete_customer_err:
				Toast.makeText(CustomerServiceActtivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customerservice);
		findViewById();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void findViewById() {
		tv_customerservice = (TextView) findViewById(R.id.tv_customerservice);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		lv_consultinglist = (ListView) findViewById(R.id.lv_consultinglist);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_iv_back.setOnClickListener(this);
		tv_customerservice.setOnClickListener(this);
		lv_consultinglist.setOnScrollListener(listener);
		_tv_name.setText(getString(R.string.integral_prompt19));
		initView();
	}

	private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

			if (lv_consultinglist.getLastVisiblePosition() == (lv_consultinglist
					.getCount() - 1)) {
				if (customerServiceinfo.hasmore.equals("true")) {
					pager++;
					netRun.CustomerServiceList(pager+"");
				}else {
					Toast.makeText(CustomerServiceActtivity.this, getString(R.string.customerservice32), Toast.LENGTH_SHORT).show();
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		customerAdapter=null;
		netRun.CustomerServiceList(pager+"");
	}

	@Override
	protected void initData() {
		// netRun.CustomerServiceList("1");
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_customerservice:
//			// 平台客服
//			startActivity(new Intent(CustomerServiceActtivity.this,
//					ConsultingActivity.class));
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.tv_customerservice){
			// 平台客服
			startActivity(new Intent(CustomerServiceActtivity.this,
					ConsultingActivity.class));
		}
	}

	/**
	 * 咨询列表
	 * 
	 * @author Administrator
	 *
	 */
	private class CustomerAdapter extends BaseAdapter {
		List<consult_list> customerServiceinfo;

		public CustomerAdapter(List<consult_list> customerServiceinfo) {
			this.customerServiceinfo = customerServiceinfo;
		}
		/**
         * 添加一个集合数据到适配器
         *
         */
        public void addList(List<consult_list> customerServiceinfo) {
            if (this.customerServiceinfo == null) {
            	this.customerServiceinfo = new ArrayList<consult_list>();
            }
            this.customerServiceinfo.addAll(customerServiceinfo);
            flushAdapter();
        }
        /**
         * 刷新适配器
         */
        public void flushAdapter() {
            notifyDataSetChanged();
        }
		@Override
		public int getCount() {

			return customerServiceinfo.size();
		}

		@Override
		public Object getItem(int position) {

			return customerServiceinfo == null ? null : customerServiceinfo
					.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(CustomerServiceActtivity.this,
						R.layout.customer_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_content
					.setText(customerServiceinfo.get(position).mc_content);
			holder.tv_time.setText(TimeStamp2Date(
					customerServiceinfo.get(position).mc_addtime,
					"yyyy-MM-dd HH:mm:ss"));
			if (customerServiceinfo.get(position).is_reply.equals("1")) {
				holder.tv_state.setText(getString(R.string.integral_prompt9));
			} else {
				holder.tv_state.setText(getString(R.string.integral_prompt10));
			}
			holder.tv_examine.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 查看
					// startActivity(new Intent(CustomerServiceActtivity.this,
					// ConsultingActivity.class));
					Intent intent2 = new Intent(CustomerServiceActtivity.this,
							ConsultingActivity.class);
					intent2.putExtra("mc_id",
							customerServiceinfo.get(position).mc_id);
					startActivity(intent2);
				}
			});
			holder.tv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 删除
					pos = position;
					netRun.DeleteCustomer(customerServiceinfo.get(position).mc_id);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_content, tv_time, tv_state, tv_examine, tv_delete;

			public ViewHolder(View convertView) {
				tv_content = (TextView) convertView
						.findViewById(R.id.tv_content);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_state = (TextView) convertView.findViewById(R.id.tv_state);
				tv_examine = (TextView) convertView
						.findViewById(R.id.tv_examine);
				tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
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
