package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.MyRedPackageInfo;
import com.aite.a.model.MyRedPackageInfo.datas;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aiteshangcheng.a.R;

/**
 * 我的红包
 * 
 * @author Administrator
 *
 */
public class RedPackageActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_bcreturn;
	private TextView tv_bctitle, tv_nodata, tv_txt1, tv_txt2, tv_txt3;
	private PullToRefreshLayout refresh_view;
	private PullableListView lv_list;
	private int refreshtype = 0, curpage = 1;
	private MyListener myListenr;
	private NetRun netRun;
	private MyAdapter myAdapter;
	private MyRedPackageInfo myRedPackageInfo;
	private String withdrawal_state = "";
	private View v_bt1, v_bt2, v_bt3;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case my_redpackage_id:
				if (msg.obj != null) {
					myRedPackageInfo = (MyRedPackageInfo) msg.obj;
					myAdapter = new MyAdapter(myRedPackageInfo.datas);
					lv_list.setAdapter(myAdapter);
					if (myRedPackageInfo.datas == null
							|| myRedPackageInfo.datas.size() == 0) {
						tv_nodata.setVisibility(View.VISIBLE);
					} else {
						tv_nodata.setVisibility(View.GONE);
					}
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(myRedPackageInfo.datas);
						lv_list.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(myRedPackageInfo.datas);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(myRedPackageInfo.datas);
						myListenr.loadMoreSucceed();
					}
				}
				break;
			case my_redpackage_err:
				Toast.makeText(RedPackageActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case to_predeposit_id:
				if (msg.obj != null) {
					List<String> list = (List<String>) msg.obj;
					if (list.size() == 1) {
						Toast.makeText(RedPackageActivity.this, list.get(0),
								Toast.LENGTH_SHORT).show();
					} else if (list.size() == 2) {
						Toast.makeText(RedPackageActivity.this, list.get(1),
								Toast.LENGTH_SHORT).show();
						netRun.MyRedPackage("20", curpage + "",
								withdrawal_state);
					}
				}
				break;
			case to_predeposit_err:
				Toast.makeText(RedPackageActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case to_wx_id:
				if (msg.obj != null) {
					List<String> list = (List<String>) msg.obj;
					if (list.size() == 1) {
						Toast.makeText(RedPackageActivity.this, list.get(0),
								Toast.LENGTH_SHORT).show();
					} else if (list.size() == 2) {
						Toast.makeText(RedPackageActivity.this, list.get(1),
								Toast.LENGTH_SHORT).show();
						netRun.MyRedPackage("20", curpage + "",
								withdrawal_state);
					}
				}
				break;
			case to_wx_err:
				Toast.makeText(RedPackageActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redpackage);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		tv_txt1 = (TextView) findViewById(R.id.tv_txt1);
		tv_txt2 = (TextView) findViewById(R.id.tv_txt2);
		tv_txt3 = (TextView) findViewById(R.id.tv_txt3);
		v_bt1 = findViewById(R.id.v_bt1);
		v_bt2 = findViewById(R.id.v_bt2);
		v_bt3 = findViewById(R.id.v_bt3);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		lv_list = (PullableListView) findViewById(R.id.lv_list);
		tv_bctitle.setText(getString(R.string.myredpackage));
		initView();
	}

	@Override
	protected void initView() {
		iv_bcreturn.setOnClickListener(this);
		tv_txt1.setOnClickListener(this);
		tv_txt2.setOnClickListener(this);
		tv_txt3.setOnClickListener(this);
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		netRun = new NetRun(this, handler);
		initData();
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		netRun.MyRedPackage("20", curpage + "", withdrawal_state);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()== R.id.tv_txt1){
			// 全部
			refreshtype = 1;
			withdrawal_state = "";
			setmenu(tv_txt1, v_bt1);
			curpage = 1;
			netRun.MyRedPackage("20", curpage + "", withdrawal_state);
		}else if(v.getId()==R.id.tv_txt2){
			// 未提现
			refreshtype = 1;
			withdrawal_state = "1";
			setmenu(tv_txt2, v_bt2);
			curpage = 1;
			netRun.MyRedPackage("20", curpage + "", withdrawal_state);
		}else if(v.getId()== R.id.tv_txt3){
			// 已提现
			refreshtype = 1;
			withdrawal_state = "2";
			setmenu(tv_txt3, v_bt3);
			curpage = 1;
			netRun.MyRedPackage("20", curpage + "", withdrawal_state);
		}

//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_txt1:
//			// 全部
//			refreshtype = 1;
//			withdrawal_state = "";
//			setmenu(tv_txt1, v_bt1);
//			curpage = 1;
//			netRun.MyRedPackage("20", curpage + "", withdrawal_state);
//			break;
//		case R.id.tv_txt2:
//			// 未提现
//			refreshtype = 1;
//			withdrawal_state = "1";
//			setmenu(tv_txt2, v_bt2);
//			curpage = 1;
//			netRun.MyRedPackage("20", curpage + "", withdrawal_state);
//			break;
//		case R.id.tv_txt3:
//			// 已提现
//			refreshtype = 1;
//			withdrawal_state = "2";
//			setmenu(tv_txt3, v_bt3);
//			curpage = 1;
//			netRun.MyRedPackage("20", curpage + "", withdrawal_state);
//			break;
//		}
	}

	private void setmenu(TextView txt, View v) {
		tv_txt1.setTextColor(Color.BLACK);
		tv_txt2.setTextColor(Color.BLACK);
		tv_txt3.setTextColor(Color.BLACK);
		v_bt1.setBackgroundColor(0xffF4F4F4);
		v_bt2.setBackgroundColor(0xffF4F4F4);
		v_bt3.setBackgroundColor(0xffF4F4F4);
		txt.setTextColor(Color.RED);
		v.setBackgroundColor(Color.RED);
	}

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

		@Override
		public int getCount() {
			return datas == null ? 0 : datas.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(RedPackageActivity.this,
						R.layout.item_redpackage, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			final MyRedPackageInfo.datas datas2 = datas
					.get(position);

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.rl_item
					.getLayoutParams();
			layoutParams.height = (int) (getw() * 0.62);
			holder.rl_item.setLayoutParams(layoutParams);
			if (datas2.withdrawal_state_desc != null
					&& !datas2.withdrawal_state_desc.equals("null")) {
				holder.tv_money.setText(getString(R.string.order_reminder83) + datas2.amount + "（"
						+ datas2.withdrawal_state_desc + "--"
						+ datas2.cash_sn_desc + "）");
			} else {
				holder.tv_money.setText(getString(R.string.order_reminder83) + datas2.amount);
			}

			holder.tv_time.setText(getString(R.string.order_reminder84)
					+ TimeStamp2Date(datas2.add_time, "yyyy-MM-dd HH:mm:ss"));
			holder.tv_rob.setText(datas2.activity_title);
			if (datas2.withdrawal_state.equals("2")) {
				holder.ll_btn.setVisibility(View.GONE);
			} else {
				holder.ll_btn.setVisibility(View.VISIBLE);
			}

			holder.rl_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 详情
					Intent intent2 = new Intent(RedPackageActivity.this,
							RedPackageDatails.class);
					intent2.putExtra("name", datas2.activity_title);
					intent2.putExtra("money", datas2.amount);
					intent2.putExtra(
							"time",
							TimeStamp2Date(datas2.add_time,
									"yyyy-MM-dd HH:mm:ss"));
					startActivity(intent2);
				}
			});
			holder.tv_ye.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 余额
					netRun.ToPredeposit(datas2.id);
				}
			});
			holder.tv_wx.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 微信
					netRun.ToWX(datas2.id);
				}
			});
			return convertView;
		}

		class ViewHodler {
			RelativeLayout rl_item;
			TextView tv_money, tv_rob, tv_time, tv_ye, tv_wx;
			LinearLayout ll_btn;

			public ViewHodler(View convertView) {
				rl_item = (RelativeLayout) convertView
						.findViewById(R.id.rl_item);
				tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				tv_rob = (TextView) convertView.findViewById(R.id.tv_rob);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_ye = (TextView) convertView.findViewById(R.id.tv_ye);
				tv_wx = (TextView) convertView.findViewById(R.id.tv_wx);
				ll_btn = (LinearLayout) convertView.findViewById(R.id.ll_btn);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	private int getw() {
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
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
			netRun.MyRedPackage("20", curpage + "", withdrawal_state);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (myRedPackageInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.MyRedPackage("20", curpage + "", withdrawal_state);
			} else {
				loadMoreSucceed();
				Toast.makeText(RedPackageActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
