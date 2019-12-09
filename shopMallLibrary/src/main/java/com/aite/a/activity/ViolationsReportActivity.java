package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ReportListInfo;
import com.aite.a.model.ReportListInfo.datas;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 违规举报列表
 * 
 * @author Administrator
 *
 */
public class ViolationsReportActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_bcreturn;
	private TextView tv_bctitle, tv_search, tv_nodata;
	private Spinner sp_type;
	private PullableListView lv_lis;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private int curpage = 1, refreshtype = 0;
	private ReportListInfo reportListInfo;
	private MyAdapter myAdapter;
	private String select_inform_state = "0";
	private MyListener myListenr;
	private PullToRefreshLayout refresh_view;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case report_list_id:
				if (msg.obj != null) {
					reportListInfo = (ReportListInfo) msg.obj;
					if (reportListInfo.datas == null
							|| reportListInfo.datas.size() == 0) {
						tv_nodata.setVisibility(View.VISIBLE);
					} else {
						tv_nodata.setVisibility(View.GONE);
					}
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(reportListInfo.datas);
						lv_lis.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(reportListInfo.datas);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(reportListInfo.datas);
						myListenr.loadMoreSucceed();
					}
				}
				break;
			case report_list_err:
				Toast.makeText(ViolationsReportActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case cancel_report_id:
				// 取消举报
				if (msg.obj != null) {
					String str = (String) msg.obj;
					if (str.equals(getString(R.string.cancel_successful))) {
						myAdapter.deleteItem(deleteid);
					}
					Toast.makeText(ViolationsReportActivity.this, str,
							Toast.LENGTH_SHORT).show();
				}
				break;
			case cancel_report_err:
				Toast.makeText(ViolationsReportActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_violations_report);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		tv_search = (TextView) findViewById(R.id.tv_search);
		sp_type = (Spinner) findViewById(R.id.sp_type);
		lv_lis = (PullableListView) findViewById(R.id.lv_lis);
		tv_bctitle.setText(getString(R.string.distribution_center29));
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		initView();
	}

	@Override
	protected void initView() {
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		iv_bcreturn.setOnClickListener(this);
		tv_search.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);

		List<String> menu = new ArrayList<String>();
		menu.add(getString(R.string.order_reminder139));
		menu.add(getString(R.string.order_reminder140));
		menu.add(getString(R.string.order_reminder141));
		// 定义适配器
		// sp_type.getSelectedItem().toString()
		SpinnerAdapter adapter = new SpinnerAdapter(
				ViolationsReportActivity.this,
				android.R.layout.simple_spinner_item, menu);
		sp_type.setAdapter(adapter);
		initData();
	}

	@Override
	protected void initData() {
		netRun.ReportList("1", select_inform_state);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()== R.id.tv_search){
			// 搜索
			if (sp_type.getSelectedItem().toString().equals(getString(R.string.order_reminder139))) {
				select_inform_state = "0";
			} else if (sp_type.getSelectedItem().toString().equals(getString(R.string.order_reminder140))) {
				select_inform_state = "1";
			} else if (sp_type.getSelectedItem().toString().equals(getString(R.string.order_reminder141))) {
				select_inform_state = "2";
			}
			netRun.ReportList("1", select_inform_state);
		}
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_search:
//			// 搜索
//			if (sp_type.getSelectedItem().toString().equals(getString(R.string.order_reminder139))) {
//				select_inform_state = "0";
//			} else if (sp_type.getSelectedItem().toString().equals(getString(R.string.order_reminder140))) {
//				select_inform_state = "1";
//			} else if (sp_type.getSelectedItem().toString().equals(getString(R.string.order_reminder141))) {
//				select_inform_state = "2";
//			}
//			netRun.ReportList("1", select_inform_state);
//			break;
//		}
	}

	public class MyListener implements OnRefreshListener {

		private PullToRefreshLayout pullToRefreshLayout;

		private Handler refreshHandler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case pull_down_refresh_success:// 下拉刷新成功
					if (pullToRefreshLayout != null) {
						pullToRefreshLayout
								.refreshFinish(PullToRefreshLayout.SUCCEED);
					}
					break;
				case pull_up_loaded_success:// 上拉加载成功
					if (pullToRefreshLayout != null) {
						pullToRefreshLayout
								.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}
					break;
				}
			};
		};

		/**
		 * 下拉式刷新成功
		 * */
		public void refreshSucceed() {
			refreshHandler.sendEmptyMessageDelayed(pull_down_refresh_success,
					400);
		}

		/**
		 * 上拉加载成功
		 * */
		public void loadMoreSucceed() {
			refreshHandler.sendEmptyMessageDelayed(pull_up_loaded_success, 400);
		}

		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
			// 下拉刷新操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			refreshtype = 1;
			curpage = 1;
			netRun.ReportList(curpage + "", select_inform_state);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (reportListInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.ReportList(curpage + "", select_inform_state);
			} else {
				loadMoreSucceed();
				Toast.makeText(ViolationsReportActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private int deleteid = 0;

	/**
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<datas> datas;

		public MyAdapter(List<datas> datas) {
			this.datas = datas;
		}

		/**
		 * 刷新
		 */
		public void updata(List<datas> datas) {
			if (datas == null) {
				return;
			}
			this.datas = datas;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(List<datas> datas) {
			if (datas == null) {
				return;
			}
			this.datas.addAll(datas);
			notifyDataSetChanged();
		}

		/**
		 * 取消举报
		 * 
		 * @param id
		 */
		public void deleteItem(int id) {
			this.datas.remove(id);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas == null ? null : datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ViolationsReportActivity.this,
						R.layout.item_reportlist, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final ReportListInfo.datas datas2 = datas
					.get(position);
			bitmapUtils.display(holder.iv_icon, datas2.inform_goods_image);
			holder.tv_name.setText(datas2.inform_goods_name);
			holder.tv_store.setText(datas2.inform_store_name);
			holder.tv_time.setText(getString(R.string.distribution_center32)
					+ " "
					+ TimeStamp2Date(datas2.inform_datetime, "yyyy-MM-dd"));
			String str = datas2.inform_state.equals("1") ? getString(R.string.order_reminder140) : getString(R.string.order_reminder141);
			holder.tv_state.setText(getString(R.string.distribution_center33)
					+ " " + str);
			String str2 = datas2.inform_handle_type.equals("1") ? getString(R.string.order_reminder142)
					: datas2.inform_handle_type.equals("2") ? getString(R.string.order_reminder143)
							: datas2.inform_handle_type.equals("3") ? getString(R.string.order_reminder144)
									: "";
			holder.tv_results.setText(getString(R.string.distribution_center34)
					+ " " + str2);
			holder.tv_info.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 查看
					Intent intent2 = new Intent(ViolationsReportActivity.this,
							ReportDatalistActivity.class);
					intent2.putExtra("inform_id", datas2.inform_id);
					startActivity(intent2);
				}
			});
			holder.tv_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 取消
					netRun.CancelReport(datas2.inform_id);
					deleteid = position;
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_icon;
			TextView tv_name, tv_store, tv_time, tv_state, tv_results, tv_info,
					tv_cancel;

			public ViewHolder(View convertView) {
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_store = (TextView) convertView.findViewById(R.id.tv_store);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_state = (TextView) convertView.findViewById(R.id.tv_state);
				tv_results = (TextView) convertView
						.findViewById(R.id.tv_results);
				tv_info = (TextView) convertView.findViewById(R.id.tv_info);
				tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
				iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 搜索类型
	 * 
	 * @author Administrator
	 *
	 */
	private class SpinnerAdapter extends ArrayAdapter<String> {
		Context context;
		List<String> items = new ArrayList<String>();

		public SpinnerAdapter(final Context context,
				final int textViewResourceId, final List<String> items) {
			super(context, textViewResourceId, items);
			this.items = items;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items.get(position));
			tv.setGravity(Gravity.CENTER);
			// tv.setTextColor(Color.BLUE);
			// tv.setTextSize(12, TypedValue.COMPLEX_UNIT_SP);
			tv.setTextSize(12);
			int px2dip = dip2px(context, 10);
			tv.setPadding(0, px2dip, 0, px2dip);
			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}
			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items.get(position));
			tv.setGravity(Gravity.CENTER);
			// tv.setTextColor(Color.BLUE);
			tv.setTextSize(12);
			// int px2dip = dip2px(context, 10);
			// tv.setPadding(0, px2dip, 0, px2dip);
			return convertView;
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
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
