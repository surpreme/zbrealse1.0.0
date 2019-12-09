package hotel;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelOrderListInfo;
import com.aite.a.model.HotelOrderListInfo.datas.order_group_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 酒店订单列表
 * 
 * @author Administrator
 *
 */
public class HotelOrderListActivity extends BaseActivity implements
		OnClickListener {
	private TextView _tv_name, tv_menu1, tv_menu2, tv_menu3, tv_menu4,
			tv_nodata;
	private ImageView _iv_right;
	private View v_bt1, v_bt2, v_bt3, v_bt4;
	private PullToRefreshLayout refresh_view;
	private PullableListView lv_list;
	private int refreshtype = 0, curpage = 1;
	private String type = "0", page = "20";
	private MyListener myListenr;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private MyAdapter myAdapter;
	private HotelOrderListInfo hotelOrderListInfo;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case hotelorder_list_id:
				// 订单列表
				if (msg.obj != null) {
					hotelOrderListInfo = (HotelOrderListInfo) msg.obj;
					if (hotelOrderListInfo.datas.order_group_list == null
							|| hotelOrderListInfo.datas.order_group_list.size() == 0) {
						tv_nodata.setVisibility(View.VISIBLE);
					} else {
						tv_nodata.setVisibility(View.GONE);
					}
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(
								hotelOrderListInfo.datas.order_group_list);
						lv_list.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter
								.updata(hotelOrderListInfo.datas.order_group_list);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter
								.adddata(hotelOrderListInfo.datas.order_group_list);
						myListenr.loadMoreSucceed();
					}
				}
				break;
			case hotelorder_list_err:
				Toast.makeText(HotelOrderListActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotelorderlist);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
		tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
		tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
		tv_menu4 = (TextView) findViewById(R.id.tv_menu4);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		_iv_right = (ImageView) findViewById(R.id._iv_back);
		v_bt1 = findViewById(R.id.v_bt1);
		v_bt2 = findViewById(R.id.v_bt2);
		v_bt3 = findViewById(R.id.v_bt3);
		v_bt4 = findViewById(R.id.v_bt4);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		lv_list = (PullableListView) findViewById(R.id.lv_list);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getString(R.string.hotel_order));
		_iv_right.setOnClickListener(this);
		tv_menu1.setOnClickListener(this);
		tv_menu2.setOnClickListener(this);
		tv_menu3.setOnClickListener(this);
		tv_menu4.setOnClickListener(this);
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		netRun.HotelOrderList(type, page, curpage + "");
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id._iv_back) {
			finish();
		} else if (id == R.id.tv_menu1) {// 全部
			type = "0";
			curpage = 1;
			netRun.HotelOrderList(type, page, curpage + "");
			setMenu(tv_menu1, v_bt1);
		} else if (id == R.id.tv_menu2) {// 待支付
			type = "1";
			curpage = 1;
			netRun.HotelOrderList(type, page, curpage + "");
			setMenu(tv_menu2, v_bt2);
		} else if (id == R.id.tv_menu3) {// 待确认
			type = "2";
			curpage = 1;
			netRun.HotelOrderList(type, page, curpage + "");
			setMenu(tv_menu3, v_bt3);
		} else if (id == R.id.tv_menu4) {// 待入驻
			type = "3";
			curpage = 1;
			netRun.HotelOrderList(type, page, curpage + "");
			setMenu(tv_menu4, v_bt4);
		}
	}

	/**
	 * 改变选中状态
	 * 
	 * @param txt
	 * @param v
	 */
	private void setMenu(TextView txt, View v) {
		tv_menu1.setTextColor(0xff6D6D6D);
		tv_menu2.setTextColor(0xff6D6D6D);
		tv_menu3.setTextColor(0xff6D6D6D);
		tv_menu4.setTextColor(0xff6D6D6D);
		v_bt1.setVisibility(View.INVISIBLE);
		v_bt2.setVisibility(View.INVISIBLE);
		v_bt3.setVisibility(View.INVISIBLE);
		v_bt4.setVisibility(View.INVISIBLE);
		txt.setTextColor(Color.RED);
		v.setVisibility(View.VISIBLE);
	}

	/**
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<order_group_list> order_group_list;

		public MyAdapter(List<order_group_list> order_group_list) {
			this.order_group_list = order_group_list;
		}

		/**
		 * 刷新
		 *
		 */
		public void updata(List<order_group_list> order_group_list) {
			if (order_group_list == null) {
				return;
			}
			this.order_group_list = order_group_list;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(List<order_group_list> order_group_list) {
			if (order_group_list == null) {
				return;
			}
			this.order_group_list.addAll(order_group_list);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return order_group_list == null ? 0 : order_group_list.size();
		}

		@Override
		public Object getItem(int position) {
			return order_group_list == null ? null : order_group_list
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(HotelOrderListActivity.this,
						R.layout.item_hotelorderlist, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			final order_group_list order_group_list2 = order_group_list
					.get(position);
			holder.tv_time.setText(getString(R.string.order_reminder1)
					+ TimeStamp2Date(order_group_list2.add_time,
							"yyyy-MM-dd HH:mm:ss"));
			holder.tv_storename.setText(getString(R.string.order_reminder93)
					+ order_group_list2.store_name);
			holder.tv_storenum.setText(getString(R.string.order_reminder2) + order_group_list2.order_sn);
			holder.tv_state.setText(order_group_list2.state_desc);
			holder.tv_price.setText(getString(R.string.find_reminder200)
					+ order_group_list2.order_amount);
			holder.tv_pay.setText(getString(R.string.find_reminder201)+"（￥" + order_group_list2.order_amount
					+ "）");
			holder.tv_gname.setText(order_group_list2.hotel_name);
			holder.tv_gprice.setText(order_group_list2.order_amount);
			bitmapUtils.display(holder.iv_img, order_group_list2.hotel_image);

			if (order_group_list2.if_cancel.equals("true")) {
				holder.tv_ordercancel.setVisibility(View.VISIBLE);
			} else {
				holder.tv_ordercancel.setVisibility(View.GONE);
			}
			if (order_group_list2.if_evaluation.equals("true")) {
				holder.tv_evaluation.setVisibility(View.VISIBLE);
			} else {
				holder.tv_evaluation.setVisibility(View.GONE);
			}
			if (order_group_list2.if_pay.equals("true")) {
				holder.tv_pay.setVisibility(View.VISIBLE);
			} else {
				holder.tv_pay.setVisibility(View.GONE);
			}
			holder.tv_ordercancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 取消订单
					netRun.HotelOrderCancel(order_group_list2.order_id);
				}
			});
			holder.tv_evaluation.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 评论
					
				}
			});
			holder.tv_pay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 支付
					
				}
			});
			holder.tv_orderdatails.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 订单详情
					Intent intent2 = new Intent(HotelOrderListActivity.this,
							HotelOrderDatails.class);
					intent2.putExtra("order_id", order_group_list2.order_id);
					startActivity(intent2);
				}
			});
			return convertView;
		}

		class ViewHodler {
			TextView tv_time, tv_storename, tv_storenum, tv_state, tv_price,
					tv_evaluation, tv_confirm, tv_ordercancel, tv_orderdatails,
					tv_pay, tv_gname, tv_gprice;
			ImageView iv_img;

			public ViewHodler(View convertView) {
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_storename = (TextView) convertView
						.findViewById(R.id.tv_storename);
				tv_storenum = (TextView) convertView
						.findViewById(R.id.tv_storenum);
				tv_state = (TextView) convertView.findViewById(R.id.tv_state);
				tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				tv_evaluation = (TextView) convertView
						.findViewById(R.id.tv_evaluation);
				tv_confirm = (TextView) convertView
						.findViewById(R.id.tv_confirm);
				tv_ordercancel = (TextView) convertView
						.findViewById(R.id.tv_ordercancel);
				tv_orderdatails = (TextView) convertView
						.findViewById(R.id.tv_orderdatails);
				tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
				tv_gname = (TextView) convertView.findViewById(R.id.tv_gname);
				tv_gprice = (TextView) convertView.findViewById(R.id.tv_gprice);
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				convertView.setTag(this);
			}
		}
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
			netRun.HotelOrderList(type, page, curpage + "");
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (hotelOrderListInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.HotelOrderList(type, page, curpage + "");
			} else {
				loadMoreSucceed();
				Toast.makeText(HotelOrderListActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
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
