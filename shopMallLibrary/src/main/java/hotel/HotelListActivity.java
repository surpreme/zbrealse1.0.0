package hotel;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelListInfo;
import com.aite.a.model.HotelListInfo.datas.list;
import com.aite.a.model.HotelListInfo.datas.price_list;
import com.aite.a.model.HotelListInfo.datas.stars_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyHotelPricepopu;
import com.aite.a.view.MyHotelSortpopu;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aiteshangcheng.a.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lidroid.xutils.BitmapUtils;

/**
 * 酒店列表
 * 
 * @author Administrator
 *
 */
public class HotelListActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_return, iv_search, iv_refresh;
	private EditText ev_search;
	private TextView tv_address, tv_nodata;
	private PullableListView lv_lis;
	private PullToRefreshLayout refresh_view;
	private LinearLayout ll_menu1, ll_menu2, ll_menu3, ll_menu4;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private HotelListInfo hotelListInfo;
	private MyAdapter myAdapter;
	private MyListener myListenr;
	private int curpage = 1, refreshtype = 0;
	private String points, cityid, htype, search_key, stime, etime,
			price = "0", stars = "0", brand = "0", sheshi = "0", promote = "0",
			price_sort = "0", juli_sort = "0", pingfen_sort = "0",
			distance = "0", address, AddrStr;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case hotel_list_id:
				// 列表
				if (msg.obj != null) {
					hotelListInfo = (HotelListInfo) msg.obj;
					if (hotelListInfo.datas.list == null
							|| hotelListInfo.datas.list.size() == 0) {
						tv_nodata.setVisibility(View.VISIBLE);
					} else {
						tv_nodata.setVisibility(View.GONE);
					}
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(hotelListInfo.datas.list);
						lv_lis.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(hotelListInfo.datas.list);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(hotelListInfo.datas.list);
						myListenr.loadMoreSucceed();
					}
				}
				break;
			case hotel_list_err:
				Toast.makeText(HotelListActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotellist);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_return = (ImageView) findViewById(R.id.iv_return);
		iv_search = (ImageView) findViewById(R.id.iv_search);
		iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
		ev_search = (EditText) findViewById(R.id.ev_search);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		lv_lis = (PullableListView) findViewById(R.id.lv_lis);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		ll_menu1 = (LinearLayout) findViewById(R.id.ll_menu1);
		ll_menu2 = (LinearLayout) findViewById(R.id.ll_menu2);
		ll_menu3 = (LinearLayout) findViewById(R.id.ll_menu3);
		ll_menu4 = (LinearLayout) findViewById(R.id.ll_menu4);
		initView();
	}

	@Override
	protected void initView() {
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		iv_return.setOnClickListener(this);
		iv_search.setOnClickListener(this);
		iv_refresh.setOnClickListener(this);
		ll_menu1.setOnClickListener(this);
		ll_menu2.setOnClickListener(this);
		ll_menu3.setOnClickListener(this);
		ll_menu4.setOnClickListener(this);

		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		Intent intent2 = getIntent();
		points = intent2.getStringExtra("points");
		cityid = intent2.getStringExtra("cityid");
		htype = intent2.getStringExtra("htype");
		search_key = intent2.getStringExtra("search_key");
		stime = intent2.getStringExtra("stime");
		etime = intent2.getStringExtra("etime");
		AddrStr = intent2.getStringExtra("AddrStr");
		address = intent2.getStringExtra("address");
		tv_address.setText(getString(R.string.find_reminder191) + AddrStr);
		netRun.HotelList(points, cityid, htype, search_key, stime, price,
				stars, brand, sheshi, promote, price_sort, juli_sort,
				pingfen_sort, curpage + "", distance);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_return) {
			finish();
		} else if (id == R.id.iv_search) {// 搜索
		} else if (id == R.id.iv_refresh) {// 刷新
			Positioning();
		} else if (id == R.id.ll_menu1) {// 筛选
			Intent intent3 = new Intent(HotelListActivity.this,
					HotelScreeningActivity.class);
			intent3.putExtra("data", (Serializable) hotelListInfo.datas);
			startActivityForResult(intent3, 1);
		} else if (id == R.id.ll_menu2) {// 位置区域
			Intent intent2 = new Intent(HotelListActivity.this,
					HotelChooseAddress.class);
			intent2.putExtra("type", 1);
			startActivityForResult(intent2, 0);
		} else if (id == R.id.ll_menu3) {// 价格星级
			showPopup(hotelListInfo.datas.price_list,
					hotelListInfo.datas.stars_list);
		} else if (id == R.id.ll_menu4) {// 推荐排序
			showPopup2();
		}
	}

	/**
	 * 价格星级
	 * 
	 * @param price_list
	 * @param stars_list
	 */
	private void showPopup(List<price_list> price_list,
			List<stars_list> stars_list) {
		MyHotelPricepopu myPricepopu = new MyHotelPricepopu(
				HotelListActivity.this, price_list, stars_list);
		myPricepopu.setdata(new MyHotelPricepopu.data() {

			@Override
			public void onItemClick(String p, String l) {
				System.out.println("----------------价格  " + p + "星级 " + l);
				price = p;
				stars = l;
				refreshtype = 1;
				curpage = 1;
				netRun.HotelList(points, cityid, htype, search_key, stime,
						price, stars, brand, sheshi, promote, price_sort,
						juli_sort, pingfen_sort, curpage + "", distance);
			}
		});
		myPricepopu.showAtLocation(ll_menu3, Gravity.BOTTOM, 0, 0);// 设置显示位置
	}

	/**
	 * 推荐排序
	 *
	 */
	private void showPopup2() {
		MyHotelSortpopu myPricepopu = new MyHotelSortpopu(
				HotelListActivity.this);
		myPricepopu.setdata(new MyHotelSortpopu.data() {

			@Override
			public void onItemClick(List<String> data) {
				if (data != null && data.size() != 0) {
					switch (data.get(0)) {
					case "0":
						// 推荐
						price_sort = "0";
						juli_sort = "0";
						pingfen_sort = "0";
						break;
					case "1":
						// 价格
						price_sort = data.get(1);
						juli_sort = "0";
						pingfen_sort = "0";
						break;
					case "2":
						// 距离
						juli_sort = data.get(1);
						price_sort = "0";
						pingfen_sort = "0";
						break;
					case "3":
						// 评分
						pingfen_sort = data.get(1);
						price_sort = "0";
						juli_sort = "0";
						break;
					}
					refreshtype = 1;
					curpage = 1;
					netRun.HotelList(points, cityid, htype, search_key, stime,
							price, stars, brand, sheshi, promote, price_sort,
							juli_sort, pingfen_sort, curpage + "", distance);
				}
			}
		});
		myPricepopu.showAtLocation(ll_menu3, Gravity.BOTTOM, 0, 0);// 设置显示位置
	}

	/**
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<list> list;

		public MyAdapter(List<list> list) {
			this.list = list;
		}

		/**
		 * 刷新
		 * 
		 * @param list
		 */
		public void updata(List<list> list) {
			if (list == null) {
				return;
			}
			this.list = list;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(List<list> list) {
			if (list == null) {
				return;
			}
			this.list.addAll(list);
			notifyDataSetChanged();
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(HotelListActivity.this,
						R.layout.item_hotellist, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final HotelListInfo.datas.list list2 = list
					.get(position);
			bitmapUtils.display(holder.iv_img, list2.hotel_imgurl);
			holder.tv_hotelname.setText(list2.hotel_name);
			holder.tv_score.setText(list2.avg_total == null ? getString(R.string.find_reminder192)
					: list2.avg_total);
			holder.tv_level.setText(list2.eval_total + getString(R.string.find_reminder193)
					+ list2.hotel_stars_name);
			holder.tv_address.setText(list2.hotel_address);
			holder.tv_distance.setText(getString(R.string.find_reminder194) + list2.distance + getString(R.string.find_reminder164));
			holder.tv_price.setText(list2.price);
			holder.rl_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 详情
					Intent intent2 = new Intent(HotelListActivity.this,
							HotelDatailsActivity.class);
					intent2.putExtra("hotel_id", list2.hotel_id);
					intent2.putExtra("points", points);
					intent2.putExtra("stime", stime);
					intent2.putExtra("etime", etime);
					startActivity(intent2);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_img;
			TextView tv_hotelname, tv_score, tv_level, tv_address, tv_distance,
					tv_price;
			RelativeLayout rl_item;

			public ViewHolder(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				tv_hotelname = (TextView) convertView
						.findViewById(R.id.tv_hotelname);
				tv_score = (TextView) convertView.findViewById(R.id.tv_score);
				tv_level = (TextView) convertView.findViewById(R.id.tv_level);
				tv_address = (TextView) convertView
						.findViewById(R.id.tv_address);
				tv_distance = (TextView) convertView
						.findViewById(R.id.tv_distance);
				tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				rl_item = (RelativeLayout) convertView
						.findViewById(R.id.rl_item);
				convertView.setTag(this);
			}
		}
	}

	/********* 定位 ************/
	private void Positioning() {
		LocationClient locationClient = new LocationClient(
				HotelListActivity.this.getApplicationContext());

		// 设置定位条件
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 是否打开GPS
		option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
		option.setIsNeedAddress(true);
		locationClient.setLocOption(option);
		// 注册位置监听器
		locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				points = location.getLongitude() + "," + location.getLatitude();
				tv_address.setText(getString(R.string.find_reminder191) + location.getAddrStr());
				address = location.getCity();
				AddrStr = location.getAddrStr();
				// System.out.println("-----------------AddrStr  "
				// + location.getAddrStr());
				// System.out.println("-----------------City  "
				// + location.getCity());
				// System.out.println("-----------------Longitude  "
				// + location.getLongitude());
				// System.out.println("-----------------Latitude  "
				// + location.getLatitude());
			}
		});
		locationClient.start();
	}

	/**
	 * 刷新监听
	 * 
	 * @author Administrator
	 *
	 */
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
			netRun.HotelList(points, cityid, htype, search_key, stime, price,
					stars, brand, sheshi, promote, price_sort, juli_sort,
					pingfen_sort, curpage + "", distance);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (hotelListInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.HotelList(points, cityid, htype, search_key, stime,
						price, stars, brand, sheshi, promote, price_sort,
						juli_sort, pingfen_sort, curpage + "", distance);
			} else {
				loadMoreSucceed();
				Toast.makeText(HotelListActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			// 地址
			if (data != null) {
				address = data.getStringExtra("address");
				cityid = data.getStringExtra("addressid");
				refreshtype = 1;
				curpage = 1;
				netRun.HotelList(points, cityid, htype, search_key, stime,
						price, stars, brand, sheshi, promote, price_sort,
						juli_sort, pingfen_sort, curpage + "", distance);
			}
			break;
		case 1:
			// 筛选
			if (data != null) {
				htype = data.getStringExtra("htype");
				brand = data.getStringExtra("brand");
				distance = data.getStringExtra("distance");
				sheshi = data.getStringExtra("sheshi");
				refreshtype = 1;
				curpage = 1;
				netRun.HotelList(points, cityid, htype, search_key, stime,
						price, stars, brand, sheshi, promote, price_sort,
						juli_sort, pingfen_sort, curpage + "", distance);
			}
			break;
		}
	}
}
