package hotel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.WebActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelDatailsInfo;
import com.aite.a.model.HotelDatailsInfo.pic_list;
import com.aite.a.model.HotelDatailsInfo.room_list;
import com.aite.a.model.HotelDatailsInfo.room_list.pricelist;
import com.aite.a.parse.NetRun;
import com.aite.a.view.ListeningScrollView.ScrollYListener;
import com.aite.a.view.MyAdGallery.MyOnItemClickListener;
import com.aite.a.view.ListeningScrollView;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyDialog;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 酒店详情
 * 
 * @author Administrator
 *
 */
public class HotelDatailsActivity extends BaseActivity implements
		OnClickListener, ScrollYListener {
	private ImageView iv_back, iv_bus;
	private TextView tv_title, tv_score, tv_comments, tv_address, tv_distance,
			tv_datails, tv_daynumber, tv_serch, tv_name, tv_level,
			tv_starttime, tv_endtime, tv_return, tv_trait1, tv_trait2,
			tv_money, tv_convention, tv_iscancel;
	private RelativeLayout rl_topimg, rl_pl;
	private MyAdGallery adgallery;
	private LinearLayout ll_starttime, ll_endtime, ovalLayout;
	private MyListView mv_room;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private LinearLayout.LayoutParams layoutParams;
	private String hotel_id = "", points = "", stime = "", etime = "";
	private HotelDatailsInfo hotelDatailsInfo;
	private MyAdapter myAdapter;
	private ListeningScrollView sv_gd;
	private WebView web;
	private MyDialog myDialog;
	private View inflate;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case hotel_datails_id:
				// 酒店详情
				if (msg.obj != null) {
					hotelDatailsInfo = (HotelDatailsInfo) msg.obj;
					setAD(hotelDatailsInfo.pic_list);
					tv_name.setText(hotelDatailsInfo.info_hotel.hotel_name);
					tv_level.setText(hotelDatailsInfo.info_hotel.hotel_stars_name);
					if (hotelDatailsInfo.info_hotel.avg_total == null
							|| hotelDatailsInfo.info_hotel.avg_total
									.equals("0")) {
						tv_score.setText(getString(R.string.find_reminder161));
					} else {
						tv_score.setText(hotelDatailsInfo.info_hotel.avg_total
								+ getString(R.string.minus));
					}
					if (hotelDatailsInfo.info_hotel.eval_total == null
							|| hotelDatailsInfo.info_hotel.eval_total
									.equals("0")) {
						tv_comments.setText(getString(R.string.find_reminder162));
					} else {
						tv_comments
								.setText(getString(R.string.examine)
										+ hotelDatailsInfo.info_hotel.eval_total
										+ getString(R.string.evaluation_tips5));
					}
					tv_address
							.setText(hotelDatailsInfo.info_hotel.hotel_area_info
									+ hotelDatailsInfo.info_hotel.hotel_address);
					tv_distance.setText(getString(R.string.find_reminder163) + hotelDatailsInfo.distance
							+ getString(R.string.find_reminder164));
					tv_starttime.setText(hotelDatailsInfo.stime);
					tv_endtime.setText(hotelDatailsInfo.etime);
					// 设置载入页面自适应手机屏幕，居中显示
					WebSettings mWebSettings = web.getSettings();
					mWebSettings.setUseWideViewPort(true);
					mWebSettings.setLoadWithOverviewMode(true);
					web.loadUrl(hotelDatailsInfo.info_hotel.hotel_bodyurl);

					myAdapter = new MyAdapter(hotelDatailsInfo.room_list);
					mv_room.setAdapter(myAdapter);

					if (hotelDatailsInfo.stime != null
							&& !hotelDatailsInfo.stime.equals("")
							&& hotelDatailsInfo.etime != null
							&& !hotelDatailsInfo.etime.equals("")) {
						String str = daysBetween(hotelDatailsInfo.stime,
								hotelDatailsInfo.etime) + "";
						if (str.contains("-") || str.equals("0")) {
							tv_daynumber.setText("0");
							Toast.makeText(HotelDatailsActivity.this, getString(R.string.find_reminder165),
									Toast.LENGTH_SHORT).show();
						} else {
							tv_daynumber.setText(str);
						}
					}
				}
				break;
			case hotel_datails_err:
				Toast.makeText(HotelDatailsActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hoteldetails);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_bus = (ImageView) findViewById(R.id.iv_bus);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_score = (TextView) findViewById(R.id.tv_score);
		tv_comments = (TextView) findViewById(R.id.tv_comments);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		tv_datails = (TextView) findViewById(R.id.tv_datails);
		tv_daynumber = (TextView) findViewById(R.id.tv_daynumber);
		tv_serch = (TextView) findViewById(R.id.tv_serch);
		tv_level = (TextView) findViewById(R.id.tv_level);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_starttime = (TextView) findViewById(R.id.tv_starttime);
		tv_endtime = (TextView) findViewById(R.id.tv_endtime);
		rl_topimg = (RelativeLayout) findViewById(R.id.rl_topimg);
		rl_pl = (RelativeLayout) findViewById(R.id.rl_pl);
		adgallery = (MyAdGallery) findViewById(R.id.adgallery);
		ll_starttime = (LinearLayout) findViewById(R.id.ll_starttime);
		ll_endtime = (LinearLayout) findViewById(R.id.ll_endtime);
		ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);
		mv_room = (MyListView) findViewById(R.id.mv_room);
		sv_gd = (ListeningScrollView) findViewById(R.id.sv_gd);
		web = (WebView) findViewById(R.id.web);

		inflate = View.inflate(this, R.layout.dialog_hotel_convention, null);
		tv_return = (TextView) inflate.findViewById(R.id.tv_return);
		tv_trait1 = (TextView) inflate.findViewById(R.id.tv_trait1);
		tv_trait2 = (TextView) inflate.findViewById(R.id.tv_trait2);
		tv_money = (TextView) inflate.findViewById(R.id.tv_money);
		tv_convention = (TextView) inflate.findViewById(R.id.tv_convention);
		tv_iscancel = (TextView) inflate.findViewById(R.id.tv_iscancel);
		mv_room.setFocusable(false);
		initView();
	}

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		iv_back.setOnClickListener(this);
		tv_comments.setOnClickListener(this);
		tv_return.setOnClickListener(this);
		tv_datails.setOnClickListener(this);
		tv_convention.setOnClickListener(this);
		sv_gd.setScrollYViewListener(this);
		layoutParams = (LinearLayout.LayoutParams) rl_topimg.getLayoutParams();
		Intent intent2 = getIntent();
		hotel_id = intent2.getStringExtra("hotel_id");
		points = intent2.getStringExtra("points");
		stime = intent2.getStringExtra("stime");
		etime = intent2.getStringExtra("etime");
		initData();
	}

	@Override
	protected void initData() {
		netRun.HotelDatails(hotel_id, points, stime, etime);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		int vId = v.getId();
		if (vId == R.id.iv_back) {
			finish();
		} else if (vId == R.id.tv_comments) {// 评价
			Intent intent2 = new Intent(HotelDatailsActivity.this,
					HotelCommentsActivity.class);
			intent2.putExtra("hotel_id", hotel_id);
			startActivity(intent2);
		} else if (vId == R.id.tv_datails) {// 更多
			Bundle bundle = new Bundle();
			bundle.putString("url", hotelDatailsInfo.info_hotel.hotel_bodyurl);
			bundle.putString("title", getString(R.string.find_reminder166));
			openActivity(WebActivity.class, bundle);
		} else if (vId == R.id.tv_return) {
			if (myDialog != null) {
				myDialog.dismiss();
			}
		} else if (vId == R.id.tv_convention) {// 预约
			if (myDialog != null) {
				myDialog.dismiss();
			}
			Intent intent3 = new Intent(HotelDatailsActivity.this,
					HotelConventionActivity.class);
			intent3.putExtra("room_id",
					hotelDatailsInfo.room_list.get(id).room_id);
			intent3.putExtra("stime", hotelDatailsInfo.stime);
			intent3.putExtra("etime", hotelDatailsInfo.etime);
			startActivity(intent3);
		}
	}

	private int id = 0;

	/**
	 * 房间列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<room_list> room_list;

		public MyAdapter(List<room_list> room_list) {
			this.room_list = room_list;
		}

		@Override
		public int getCount() {
			return room_list.size();
		}

		@Override
		public Object getItem(int position) {
			return room_list == null ? null : room_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(HotelDatailsActivity.this,
						R.layout.item_roomlist, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			final room_list room_list2 = room_list.get(position);
			bitmapUtils.display(holder.iv_img, room_list2.room_imgurls);
			holder.tv_name.setText(room_list2.room_name);
			holder.tv_con.setText(room_list2.room_desc);
			if (room_list2.ischoose) {
				holder.iv_state.setImageResource(R.drawable.hotel_room1);
				holder.mv_offer.setVisibility(View.VISIBLE);
			} else {
				holder.iv_state.setImageResource(R.drawable.hotel_room2);
				holder.mv_offer.setVisibility(View.GONE);
			}
			holder.rl_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (room_list2.ischoose) {
						room_list2.ischoose = false;
					} else {
						room_list2.ischoose = true;
					}
					notifyDataSetChanged();
				}
			});
			MyAdapter2 myAdapter2 = new MyAdapter2(room_list2.pricelist);
			holder.mv_offer.setAdapter(myAdapter2);
			return convertView;
		}

		class ViewHodler {
			ImageView iv_img, iv_state;
			TextView tv_name, tv_con;
			MyListView mv_offer;
			RelativeLayout rl_item;

			public ViewHodler(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				iv_state = (ImageView) convertView.findViewById(R.id.iv_state);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_con = (TextView) convertView.findViewById(R.id.tv_con);
				rl_item = (RelativeLayout) convertView
						.findViewById(R.id.rl_item);
				mv_offer = (MyListView) convertView.findViewById(R.id.mv_offer);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 报价列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter2 extends BaseAdapter {
		List<pricelist> pricelist;

		public MyAdapter2(List<pricelist> pricelist) {
			this.pricelist = pricelist;
		}

		@Override
		public int getCount() {
			return pricelist.size();
		}

		@Override
		public Object getItem(int position) {
			return pricelist == null ? null : pricelist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(HotelDatailsActivity.this,
						R.layout.item_roomlist2, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			final room_list.pricelist pricelist2 = pricelist
					.get(position);
			holder.tv_describe.setText(pricelist2.breakfast);
			final String str = pricelist2.is_cancle.equals("1") ? getString(R.string.find_reminder167)
					: getString(R.string.find_reminder168);
			holder.tv_describe2.setText(pricelist2.breakfast + "|"
					+ pricelist2.bedtype + "|" + str);
			holder.tv_price.setText(pricelist2.housenum);
			holder.tv_order.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 预约
					id = position;
					ConventionDialog(pricelist2.breakfast, pricelist2.bedtype,
							str, pricelist2.housenum);
				}
			});
			return convertView;
		}

		class ViewHodler {
			TextView tv_describe, tv_describe2, tv_price, tv_order;

			public ViewHodler(View convertView) {
				tv_describe = (TextView) convertView
						.findViewById(R.id.tv_describe);
				tv_describe2 = (TextView) convertView
						.findViewById(R.id.tv_describe2);
				tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				tv_order = (TextView) convertView.findViewById(R.id.tv_order);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 预约
	 */
	protected void ConventionDialog(String str1, String str2, String str3,
			String str4) {
		tv_trait1.setText(str1);
		tv_trait2.setText(str2);
		tv_iscancel.setText(str3);
		tv_money.setText("￥" + str4);
		if (myDialog == null) {
			myDialog = new MyDialog(HotelDatailsActivity.this, getw() - 100,
					550, inflate, R.style.loading_dialog);
			myDialog.show();
		} else {
			myDialog.show();
		}
	}

	/**
	 * 获取屏幕宽度
	 *
	 * @return
	 */
	private int getw() {
		WindowManager wm = (WindowManager) HotelDatailsActivity.this
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		return width;
	}

	/**
	 * 设置广告轮播
	 * 
	 */
	protected void setAD(List<pic_list> pic_list) {
		List<String> listAd = new ArrayList<String>();
		for (int i = 0; i < pic_list.size(); i++) {
			listAd.add(pic_list.get(i).img_url);
		}
		String[] ADurl = listAd.toArray(new String[listAd.size()]);

		if (adgallery.mUris == null)
			adgallery.start(this, ADurl, null, 3000, ovalLayout,
					R.drawable.jd_quan, R.drawable.jd_heng);
		adgallery.setMyOnItemClickListener(new MyOnItemClickListener() {

			@Override
			public void onItemClick(int curIndex) {
			}
		});
	}

	@Override
	public void onScrollChanged(int y) {
		int a = (int) ((y / (float) rl_pl.getTop()) * 100);
		a = a > 100 ? 100 : a;
		a = a < 0 ? 0 : a;
		int dip2px = dip2px(HotelDatailsActivity.this, a);
		layoutParams.setMargins(0, dip2px, 0, 0);
		rl_topimg.setLayoutParams(layoutParams);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
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
}
