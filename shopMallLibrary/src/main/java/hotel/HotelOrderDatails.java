package hotel;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelOrderDatailsInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 酒店订单详情
 * 
 * @author Administrator
 *
 */
public class HotelOrderDatails extends BaseActivity implements OnClickListener {
	private ImageView _iv_back, iv_img;
	private TextView _tv_name, tv_time, tv_storenum, tv_state, tv_gname,
			tv_gprice, tv_sp, tv_shr, tv_lxfs, tv_storename, tv_storelxfs,
			tv_storeaddress;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	public HotelOrderDatailsInfo hotelOrderDatailsInfo;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case hotelorder_datails_id:
				// 详情
				if (msg.obj != null) {
					hotelOrderDatailsInfo = (HotelOrderDatailsInfo) msg.obj;
					tv_time.setText(getString(R.string.order_reminder1)+TimeStamp2Date(hotelOrderDatailsInfo.order_info.add_time, "yyyy-MM-dd HH:mm:ss"));
					tv_storenum.setText(getString(R.string.order_reminder2)+TimeStamp2Date(hotelOrderDatailsInfo.order_info.add_time, "yyyy-MM-dd HH:mm:ss"));
					tv_state.setText(hotelOrderDatailsInfo.order_info.state_desc);
					tv_gname.setText(hotelOrderDatailsInfo.order_info.hotel_name);
					tv_gprice.setText(getString(R.string.currency)+hotelOrderDatailsInfo.order_info.order_amount);
					tv_sp.setText(getString(R.string.currency)+hotelOrderDatailsInfo.order_info.order_amount);
					tv_shr.setText(getString(R.string.evaluation_tips29)+hotelOrderDatailsInfo.order_info.checkin_mobile);
					tv_lxfs.setText(getString(R.string.evaluation_tips30)+hotelOrderDatailsInfo.order_info.checkin_person);
					tv_storename.setText(getString(R.string.order_reminder93)+hotelOrderDatailsInfo.store_info.store_name);
					tv_storelxfs.setText(getString(R.string.evaluation_tips30)+hotelOrderDatailsInfo.store_info.store_phone);
					tv_storeaddress.setText(getString(R.string.order_reminder13)+hotelOrderDatailsInfo.store_info.store_address);
					bitmapUtils.display(iv_img, hotelOrderDatailsInfo.order_info.hotel_image);
				}
				break;
			case hotelorder_datails_err:
				Toast.makeText(HotelOrderDatails.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};
	private String order_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotelorderdatails);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_img = (ImageView) findViewById(R.id.iv_img);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_storenum = (TextView) findViewById(R.id.tv_storenum);
		tv_state = (TextView) findViewById(R.id.tv_state);
		tv_gname = (TextView) findViewById(R.id.tv_gname);
		tv_gprice = (TextView) findViewById(R.id.tv_gprice);
		tv_sp = (TextView) findViewById(R.id.tv_sp);
		tv_shr = (TextView) findViewById(R.id.tv_shr);
		tv_lxfs = (TextView) findViewById(R.id.tv_lxfs);
		tv_storename = (TextView) findViewById(R.id.tv_storename);
		tv_storelxfs = (TextView) findViewById(R.id.tv_storelxfs);
		tv_storeaddress = (TextView) findViewById(R.id.tv_storeaddress);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getString(R.string.order_datails));
		_iv_back.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		order_id = getIntent().getStringExtra("order_id");
		initData();
	}

	@Override
	protected void initData() {
		netRun.HotelOrderDatails(order_id);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id._iv_back) {
			finish();
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
