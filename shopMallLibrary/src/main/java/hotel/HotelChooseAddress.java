package hotel;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelChooseAddressInfo;
import com.aite.a.model.HotelChooseAddressInfo.citylist;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

/**
 * 酒店地址选择
 * 
 * @author Administrator
 *
 */
public class HotelChooseAddress extends BaseActivity implements OnClickListener {

	private ImageView iv_return;
	private ListView lv_list;
	private NetRun netRun;
	private List<HotelChooseAddressInfo> data;
	private MyAdapter myAdapter;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case city_list_id:
				if (msg.obj != null) {
					data = (List<HotelChooseAddressInfo>) msg.obj;
					myAdapter = new MyAdapter(data);
					lv_list.setAdapter(myAdapter);
				}
				break;
			case city_list_err:
				Toast.makeText(HotelChooseAddress.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_chooseaddress);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_return = (ImageView) findViewById(R.id.iv_return);
		lv_list = (ListView) findViewById(R.id.lv_list);
		initView();
	}

	@Override
	protected void initView() {
		iv_return.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		initData();
	}

	@Override
	protected void initData() {
		type = getIntent().getIntExtra("type", 0);
		netRun.CityList();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_return) {
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
		List<HotelChooseAddressInfo> data;

		public MyAdapter(List<HotelChooseAddressInfo> data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data == null ? null : data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(HotelChooseAddress.this,
						R.layout.item_hoteladdress, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			HotelChooseAddressInfo hotelChooseAddressInfo = data.get(position);
			holder.tv_name.setText(hotelChooseAddressInfo.name);
			ChatAdapter chatAdapter=new ChatAdapter(hotelChooseAddressInfo.citylist);
			holder.mv_chit.setAdapter(chatAdapter);
			return convertView;
		}

		class ViewHolder {
			TextView tv_name;
			MyGridView mv_chit;

			public ViewHolder(View convertView) {
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				mv_chit = (MyGridView) convertView.findViewById(R.id.mv_chit);
				convertView.setTag(this);
			}
		}
	}
	
	/**
	 * 子列表
	 * 
	 * @author Administrator
	 *
	 */
	private class ChatAdapter extends BaseAdapter {
		List<citylist> citylist;
		public ChatAdapter(List<citylist> citylist) {
			this.citylist=citylist;
		}
		
		@Override
		public int getCount() {
			return citylist.size();
		}
		
		@Override
		public Object getItem(int position) {
			return citylist == null ? null : citylist.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(HotelChooseAddress.this,
						R.layout.item_hotelchataddress, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final HotelChooseAddressInfo.citylist citylist2 = citylist.get(position);
			holder.tv_address.setText(citylist2.area_name);
			holder.tv_address.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (citylist2.area_name.matches("[a-zA-Z]+")) {
						lv_list.smoothScrollToPositionFromTop(position+2, 0);
					}else {
						if (type==0) {
							Intent intent2 = new Intent(HotelChooseAddress.this,HotelHomeActivity.class);
							intent2.putExtra("address", citylist2.area_name);
							setResult(0, intent2);
							finish();
						}else if (type==1){
							Intent intent2 = new Intent(HotelChooseAddress.this,HotelListActivity.class);
							intent2.putExtra("address", citylist2.area_name);
							intent2.putExtra("addressid", citylist2.area_id);
							setResult(1, intent2);
							finish();
						}
					}
				}
			});
			return convertView;
		}
		
		class ViewHolder {
			TextView tv_address;
			
			public ViewHolder(View convertView) {
				tv_address = (TextView) convertView.findViewById(R.id.tv_address);
				convertView.setTag(this);
			}
		}
	}

}
