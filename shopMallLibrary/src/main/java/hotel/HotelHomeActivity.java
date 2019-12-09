package hotel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelChooseAddressInfo;
import com.aite.a.model.HotelHomeInfo;
import com.aite.a.model.HotelHomeInfo.adv;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyDatepopu;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lidroid.xutils.BitmapUtils;

/**
 * 酒店首页
 * 
 * @author Administrator
 *
 */
public class HotelHomeActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_return;
	private TextView tv_name, tv_menu1, tv_menu2, tv_menu3, tv_destination,
			tv_starttime, tv_daynumber, tv_endtime;
	private View v_slider1, v_slider2, v_slider3;
	private EditText et_keywords;
	private LinearLayout ll_starttime, ll_endtime, ll_mapsearch, ll_search;
	private HotelHomeInfo hotelHomeInfo;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private MyAdapter myAdapter;
	private MyGridView mv_navigation;
	private String address = "", AddrStr = "", City = "", Longitude = "",
			Latitude = "", htype = getString(R.string.find_reminder176);
	private List<HotelChooseAddressInfo> data;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case hotel_home_id:
				// 酒店首页
				if (msg.obj != null) {
					hotelHomeInfo = (HotelHomeInfo) msg.obj;
					myAdapter = new MyAdapter(hotelHomeInfo.adv);
					mv_navigation.setAdapter(myAdapter);
					netRun.CityList();
				}
				break;
			case hotel_home_err:
				Toast.makeText(HotelHomeActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case city_list_id:
				// 地址
				if (msg.obj != null) {
					data = (List<HotelChooseAddressInfo>) msg.obj;
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_return = (ImageView) findViewById(R.id.iv_return);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
		tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
		tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
		tv_destination = (TextView) findViewById(R.id.tv_destination);
		tv_starttime = (TextView) findViewById(R.id.tv_starttime);
		tv_daynumber = (TextView) findViewById(R.id.tv_daynumber);
		tv_endtime = (TextView) findViewById(R.id.tv_endtime);
		v_slider1 = findViewById(R.id.v_slider1);
		v_slider2 = findViewById(R.id.v_slider2);
		v_slider3 = findViewById(R.id.v_slider3);
		et_keywords = (EditText) findViewById(R.id.et_keywords);
		ll_starttime = (LinearLayout) findViewById(R.id.ll_starttime);
		ll_endtime = (LinearLayout) findViewById(R.id.ll_endtime);
		ll_mapsearch = (LinearLayout) findViewById(R.id.ll_mapsearch);
		ll_search = (LinearLayout) findViewById(R.id.ll_search);
		mv_navigation = (MyGridView) findViewById(R.id.mv_navigation);
		initView();
	}

	@Override
	protected void initView() {
		tv_name.setText(getString(R.string.find_reminder181));
		iv_return.setOnClickListener(this);
		tv_menu1.setOnClickListener(this);
		tv_menu2.setOnClickListener(this);
		tv_menu3.setOnClickListener(this);
		tv_destination.setOnClickListener(this);
		ll_starttime.setOnClickListener(this);
		ll_endtime.setOnClickListener(this);
		ll_mapsearch.setOnClickListener(this);
		ll_search.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		netRun.HotelHome();
		Positioning();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_return) {
			finish();
		} else if (id == R.id.tv_menu1) {// 国外酒店
			htype = getString(R.string.find_reminder176);
			setMenu(tv_menu1, v_slider1);
		} else if (id == R.id.tv_menu2) {// 连锁品牌
			htype = getString(R.string.find_reminder177);
			setMenu(tv_menu2, v_slider2);
		} else if (id == R.id.tv_menu3) {// 民宿客栈
			htype = getString(R.string.find_reminder178);
			setMenu(tv_menu3, v_slider3);
		} else if (id == R.id.tv_destination) {// 目的地
			Intent intent2 = new Intent(HotelHomeActivity.this,
					HotelChooseAddress.class);
			startActivityForResult(intent2, 0);
		} else if (id == R.id.ll_starttime) {// 入住
			showPopup(tv_starttime);
		} else if (id == R.id.ll_endtime) {// 离店
			showPopup(tv_endtime);
		} else if (id == R.id.ll_mapsearch) {// 地图搜索
		} else if (id == R.id.ll_search) {// 搜索
			if (getAddressid(address) == null) {
				Toast.makeText(HotelHomeActivity.this, getString(R.string.find_reminder179),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (AddrStr != null && !AddrStr.equals("")) {
				String search_key = et_keywords.getText().toString();
				String stime = tv_starttime.getText().toString();
				String etime = tv_endtime.getText().toString();
				if (TextUtils.isEmpty(search_key)) {
					search_key = "";
				}
				if (TextUtils.isEmpty(stime)) {
					stime = "";
				}
				if (TextUtils.isEmpty(etime)) {
					etime = "";
				}
				Intent intent3 = new Intent(HotelHomeActivity.this,
						HotelListActivity.class);
				intent3.putExtra("points", Longitude + "," + Latitude);
				intent3.putExtra("cityid", getAddressid(address));
				intent3.putExtra("htype", htype);
				intent3.putExtra("search_key", search_key);
				intent3.putExtra("stime", stime);
				intent3.putExtra("etime", etime);
				intent3.putExtra("AddrStr", AddrStr);
				intent3.putExtra("address", address);
				startActivity(intent3);
			} else {
				Toast.makeText(HotelHomeActivity.this, getString(R.string.find_reminder180),
						Toast.LENGTH_SHORT).show();
			}

//			getPersimmions();
		}
	}

	/**
	 * 获取地址id
	 * 
	 * @return
	 */
	private String getAddressid(String str) {
		if (data == null) {
			return null;
		}
		for (int i = 0; i < data.size(); i++) {
			HotelChooseAddressInfo hotelChooseAddressInfo = data.get(i);
			for (int j = 0; j < hotelChooseAddressInfo.citylist.size(); j++) {
				if (str.equals(hotelChooseAddressInfo.citylist.get(j).area_name)) {
					return hotelChooseAddressInfo.citylist.get(j).area_id;
				}
			}
		}
		return null;
	}

	@TargetApi(23)
	private void getPersimmions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(HotelHomeActivity.this, getString(R.string.find_reminder180),
						Toast.LENGTH_SHORT).show();
			} else {
				String search_key = et_keywords.getText().toString();
				String stime = tv_starttime.getText().toString();
				String etime = tv_endtime.getText().toString();
				if (TextUtils.isEmpty(search_key)) {
					search_key = "";
				}
				if (TextUtils.isEmpty(stime)) {
					stime = "";
				}
				if (TextUtils.isEmpty(etime)) {
					etime = "";
				}
				Intent intent3 = new Intent(HotelHomeActivity.this,
						HotelListActivity.class);
				intent3.putExtra("points", Longitude + "," + Latitude);
				intent3.putExtra("cityid", getAddressid(address));
				intent3.putExtra("htype", htype);
				intent3.putExtra("search_key", search_key);
				intent3.putExtra("stime", stime);
				intent3.putExtra("etime", etime);
				intent3.putExtra("AddrStr", AddrStr);
				intent3.putExtra("address", address);
				startActivity(intent3);
			}
		}
	}

	/********* 定位 ************/
	private void Positioning() {
		LocationClient locationClient = new LocationClient(
				HotelHomeActivity.this.getApplicationContext());

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
				address = location.getCity();
				AddrStr = location.getAddrStr();
				Longitude = location.getLongitude() + "";
				Latitude = location.getLatitude() + "";
				if (address==null||address.equals("")) {
					tv_destination.setText(getString(R.string.choose));
				}else {
					tv_destination.setText(address);
				}
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
	 * 时间弹窗
	 *
	 * @param v
	 */
	private void showPopup(final TextView v) {
		final MyDatepopu myDatepopu = new MyDatepopu(HotelHomeActivity.this);
		myDatepopu.setdate(new MyDatepopu.date() {
			@Override
			public void onItemClick(String year) {
				v.setText(year);
				// myDatepopu.dismiss();
				String string = tv_starttime.getText().toString();
				String string2 = tv_endtime.getText().toString();
				if (string != null && !string.equals("") && string2 != null
						&& !string2.equals("")) {
					String str = daysBetween(string, string2) + "";
					if (str.contains("-") || str.equals("0")) {
						tv_daynumber.setText("0");
						Toast.makeText(HotelHomeActivity.this, getString(R.string.find_reminder165),
								Toast.LENGTH_SHORT).show();
					} else {
						tv_daynumber.setText(str);
					}
				}
			}
		});
		myDatepopu.showAtLocation(ll_search, Gravity.BOTTOM, 0, 0);// 设置显示位置
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();

			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 改变菜单状态
	 * 
	 * @param txt
	 * @param v
	 */
	private void setMenu(TextView txt, View v) {
		tv_menu1.setTextColor(Color.BLACK);
		tv_menu2.setTextColor(Color.BLACK);
		tv_menu3.setTextColor(Color.BLACK);
		v_slider1.setVisibility(View.GONE);
		v_slider2.setVisibility(View.GONE);
		v_slider3.setVisibility(View.GONE);
		v.setVisibility(View.VISIBLE);
		txt.setTextColor(0xff0092DD);
	}

	/**
	 * 导航
	 * 
	 * @author Administrator
	 *
	 */
	class MyAdapter extends BaseAdapter {
		List<adv> adv;

		public MyAdapter(List<adv> adv) {
			this.adv = adv;
		}

		@Override
		public int getCount() {
			return adv.size();
		}

		@Override
		public Object getItem(int position) {
			return adv == null ? null : adv.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(HotelHomeActivity.this,
						R.layout.item_hotel, null);
				new ViewHoldler(convertView);
			}
			ViewHoldler holder = (ViewHoldler) convertView.getTag();
			HotelHomeInfo.adv adv2 = adv.get(position);
			if (adv2 != null && adv2.adv_img != null && adv2.adv_title != null) {
				bitmapUtils.display(holder.iv_img, adv2.adv_img);
				holder.tv_name.setText(adv2.adv_title);
			}
			return convertView;
		}

		class ViewHoldler {
			TextView tv_name;
			ImageView iv_img;

			public ViewHoldler(View convertView) {
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				convertView.setTag(this);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			if (data != null) {
				address = data.getStringExtra("address");
				tv_destination.setText(address);
			}
			break;
		}
	}

}
