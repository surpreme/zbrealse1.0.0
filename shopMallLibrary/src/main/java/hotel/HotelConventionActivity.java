package hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelPayDatailsInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 房间详情
 * 
 * @author Administrator
 *
 */
public class HotelConventionActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_back, iv_img;
	private TextView tv_name, tv_address, tv_roommodel, tv_bedmodel,
			tv_breakfast, tv_networks, tv_describe, tv_time1, tv_time2,
			tv_roomnumber, tv_roomtime, tv_price, tv_submit;
	private Spinner sp_nuber;
	private EditText et_name, et_phone, et_email;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private String room_id, stime, etime;
	private HotelPayDatailsInfo hotelPayDatailsInfo;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case hotelpay_datails_id:
				if (msg.obj != null) {
					hotelPayDatailsInfo = (HotelPayDatailsInfo) msg.obj;
					bitmapUtils.display(iv_img,
							hotelPayDatailsInfo.hotel_info.hotel_imgurl);
					tv_name.setText(hotelPayDatailsInfo.hotel_info.hotel_name);
					tv_address.setText(getString(R.string.order_reminder31)
							+ hotelPayDatailsInfo.hotel_info.hotel_address);
					tv_roommodel.setText(getString(R.string.find_reminder138)
							+ hotelPayDatailsInfo.room_info.room_name);
					String bedtype = hotelPayDatailsInfo.priceinfo.bedtype == null ? ""
							: hotelPayDatailsInfo.priceinfo.bedtype;
					String breakfast = hotelPayDatailsInfo.priceinfo.breakfast == null ? ""
							: hotelPayDatailsInfo.priceinfo.breakfast;
					String is_wifi = hotelPayDatailsInfo.room_info.is_wifi == null ? getString(R.string.find_reminder139)
							: hotelPayDatailsInfo.room_info.is_wifi.equals("1") ? getString(R.string.find_reminder140)
									: getString(R.string.find_reminder139);
					tv_bedmodel.setText(getString(R.string.find_reminder141) + bedtype);
					tv_breakfast.setText(getString(R.string.find_reminder142) + breakfast);
					tv_networks.setText(getString(R.string.find_reminder143) + is_wifi);
					tv_describe
							.setText(hotelPayDatailsInfo.room_info.room_desc == null ? ""
									: hotelPayDatailsInfo.room_info.room_desc);
					tv_time1.setText(TimeStamp2Date(
							hotelPayDatailsInfo.priceinfo.start_time,
							"yyyy-MM-dd"));
					tv_time2.setText(TimeStamp2Date(
							hotelPayDatailsInfo.priceinfo.end_time,
							"yyyy-MM-dd"));
					tv_roomnumber
							.setText(hotelPayDatailsInfo.priceinfo.housenum);
					tv_price.setText(hotelPayDatailsInfo.hotel_info.price);
				}
				break;
			case hotelpay_datails_err:
				Toast.makeText(HotelConventionActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case hotelsubmit_order_id:
				// 酒店订单提交
				if (msg.obj != null) {
					Map<String, Object> map = (Map<String, Object>) msg.obj;
					String state = (String) map.get("state");
					String con = (String) map.get("con");
					if (state.equals("1")) {
						Toast.makeText(HotelConventionActivity.this, con,
								Toast.LENGTH_SHORT).show();
					} else if (state.equals("2")) {
						Intent intent2 = new Intent(HotelConventionActivity.this,HotelPayActivity.class);
						intent2.putExtra("id", con);
						startActivity(intent2);
						finish();
						Toast.makeText(HotelConventionActivity.this, getString(R.string.find_reminder144),
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case hotelsubmit_order_err:
				Toast.makeText(HotelConventionActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_convention);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_img = (ImageView) findViewById(R.id.iv_img);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_roommodel = (TextView) findViewById(R.id.tv_roommodel);
		tv_bedmodel = (TextView) findViewById(R.id.tv_bedmodel);
		tv_breakfast = (TextView) findViewById(R.id.tv_breakfast);
		tv_networks = (TextView) findViewById(R.id.tv_networks);
		tv_describe = (TextView) findViewById(R.id.tv_describe);
		tv_time1 = (TextView) findViewById(R.id.tv_time1);
		tv_time2 = (TextView) findViewById(R.id.tv_time2);
		tv_roomnumber = (TextView) findViewById(R.id.tv_roomnumber);
		tv_roomtime = (TextView) findViewById(R.id.tv_roomtime);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_submit = (TextView) findViewById(R.id.tv_submit);
		sp_nuber = (Spinner) findViewById(R.id.sp_nuber);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_email = (EditText) findViewById(R.id.et_email);
		initView();
	}

	@Override
	protected void initView() {
		iv_back.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		Intent intent2 = getIntent();
		room_id = intent2.getStringExtra("room_id");
		stime = intent2.getStringExtra("stime");
		etime = intent2.getStringExtra("etime");
		initData();
	}

	@Override
	protected void initData() {
		List<String> menu = new ArrayList<String>();
		menu.add("1"+getString(R.string.find_reminder145));
		menu.add("2"+getString(R.string.find_reminder145));
		menu.add("3"+getString(R.string.find_reminder145));
		menu.add("4"+getString(R.string.find_reminder145));
		menu.add("5"+getString(R.string.find_reminder145));
		menu.add("6"+getString(R.string.find_reminder145));
		menu.add("7"+getString(R.string.find_reminder145));
		menu.add("8"+getString(R.string.find_reminder145));
		menu.add("9"+getString(R.string.find_reminder145));
		menu.add("10"+getString(R.string.find_reminder145));
		SpinnerAdapter adapter = new SpinnerAdapter(
				HotelConventionActivity.this,
				android.R.layout.simple_spinner_item, menu);
		sp_nuber.setAdapter(adapter);

		// sp_serchtype.getSelectedItem().toString()
		// netRun.RoomDatails(room_id, stime, etime);
		netRun.HotelPayDatails(room_id, stime, etime);
	}

	@Override
	public void ReGetData() {
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_back) {
			finish();
		} else if (id == R.id.tv_submit) {// 提交
			String checkin_mobile = et_name.getText().toString();
			String checkin_person = et_phone.getText().toString();
			if (TextUtils.isEmpty(checkin_mobile)) {
				Toast.makeText(HotelConventionActivity.this, getString(R.string.find_reminder146),
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(checkin_person)) {
				Toast.makeText(HotelConventionActivity.this, getString(R.string.find_reminder147),
						Toast.LENGTH_SHORT).show();
				return;
			}
			netRun.HotelSubmitOrder(stime, etime, stime,
					(sp_nuber.getSelectedItemId() + 1) + "", room_id,
					checkin_mobile, checkin_person);
		}
	}

	/**
	 * 房间数量
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
