package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegralOneInfo;
import com.aite.a.model.IntegralOneInfo.pointprod_arr.pointprod_list;
import com.aite.a.model.IntegralWinInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 确认积分订单
 * 
 * @author Administrator
 *
 */
public class ConfirmIntegralActivity extends BaseActivity implements
		OnClickListener {

	private TextView _tv_name, tv_name, tv_address, tv_phone, tv_present,
			 tv_totalscore, tv_ensure;
	private ImageView _iv_back;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private IntegralOneInfo integralOneInfo;
	private RelativeLayout rl_address,rl_1;
	private ListView lv_glist;
	private Cadapter cadapter;
	private String city_id, area_id,address_id;
	private IntegralWinInfo integralWinInfo;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case integral_one_id:
				if (msg.obj != null) {
					integralOneInfo = (IntegralOneInfo) msg.obj;
					if (integralOneInfo.address_list != null
							&& integralOneInfo.address_list.size() != 0) {
						rl_address.setVisibility(View.VISIBLE);
						tv_name.setText(getString(R.string.nameadd)
								+ integralOneInfo.address_list.get(0).true_name);
						tv_address.setText(getString(R.string.detail_address2)
								+ integralOneInfo.address_list.get(0).area_info
								+ " "
								+ integralOneInfo.address_list.get(0).address);
						tv_phone.setText(getString(R.string.complaint_prompt39)
								+ integralOneInfo.address_list.get(0).mob_phone);
						city_id = integralOneInfo.address_list.get(0).city_id;
						area_id = integralOneInfo.address_list.get(0).area_id;
						address_id = integralOneInfo.address_list.get(0).address_id;
					} else {
						rl_address.setVisibility(View.GONE);
					}
					tv_totalscore.setText(getString(R.string.integral_prompt1)
							+ integralOneInfo.pointprod_arr.pgoods_pointall);
					cadapter = new Cadapter(
							integralOneInfo.pointprod_arr.pointprod_list);
					lv_glist.setAdapter(cadapter);
				}
				break;
			case integral_one_err:
				Toast.makeText(ConfirmIntegralActivity.this, getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			case integral_two_id:
				if (msg.obj!=null) {
					String str=(String) msg.obj;
					netRun.IntegralThree(str);
				}
				break;
			case integral_two_err:
				Toast.makeText(ConfirmIntegralActivity.this, getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			case integral_three_id:
				if (msg.obj!=null) {
					integralWinInfo=(IntegralWinInfo) msg.obj;
					Intent intent2 = new Intent(ConfirmIntegralActivity.this,IntegralWinActivity.class);
					intent2.putExtra("jf", integralWinInfo.point_allpoint);
					startActivity(intent2);
					finish();
				}
				break;
			case integral_three_err:
				Toast.makeText(ConfirmIntegralActivity.this, getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmintegral);
		findViewById();
	}

	
	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		rl_address = (RelativeLayout) findViewById(R.id.rl_address);
		lv_glist = (ListView) findViewById(R.id.lv_glist);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_present = (TextView) findViewById(R.id.tv_present);
		tv_totalscore = (TextView) findViewById(R.id.tv_totalscore);
		tv_ensure = (TextView) findViewById(R.id.tv_ensure);
		rl_1=(RelativeLayout) findViewById(R.id.rl_1);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getString(R.string.integral_prompt2));
		_iv_back.setOnClickListener(this);
		tv_present.setOnClickListener(this);
		rl_1.setOnClickListener(this);
		tv_ensure.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		netRun.IntegralOne();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()== R.id.tv_present){
			// 选择地址
			Intent intent = new Intent(this, AddressManageActivity.class);
			intent.putExtra("cb_out", "out");
			startActivityForResult(intent, Addres_Result);
		}else if(v.getId()==R.id.rl_1){
			// 选择地址
			Intent intent2 = new Intent(this, AddressManageActivity.class);
			intent2.putExtra("cb_out", "out");
			startActivityForResult(intent2, Addres_Result);
		}else if(v.getId()==R.id.tv_ensure){
			// 确定
			System.out.println("------------地址  "+area_id+"   "+city_id);
			netRun.IntegralTwo(address_id);
		}

//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_present:
//			// 选择地址
//			Intent intent = new Intent(this, AddressManageActivity.class);
//			intent.putExtra("cb_out", "out");
//			startActivityForResult(intent, Addres_Result);
//			break;
//		case R.id.rl_1:
//			// 选择地址
//			Intent intent2 = new Intent(this, AddressManageActivity.class);
//			intent2.putExtra("cb_out", "out");
//			startActivityForResult(intent2, Addres_Result);
//			break;
//		case R.id.tv_ensure:
//			// 确定
//			System.out.println("------------地址  "+area_id+"   "+city_id);
//			netRun.IntegralTwo(address_id);
//			break;
//		}
	}

	/**
	 * 商品列表
	 * 
	 * @author Administrator
	 *
	 */
	private class Cadapter extends BaseAdapter {
		List<pointprod_list> pointprod_list;

		public Cadapter(List<pointprod_list> pointprod_list) {
			this.pointprod_list = pointprod_list;
		}

		@Override
		public int getCount() {
			return pointprod_list.size();
		}

		@Override
		public Object getItem(int position) {
			return pointprod_list == null ? null : pointprod_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ConfirmIntegralActivity.this,
						R.layout.confirmintegral_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			pointprod_list pointprod_list2 = pointprod_list.get(position);

			bitmapUtils.display(holder.iv_gimg, pointprod_list2.pgoods_image);
			holder.tv_gname.setText(pointprod_list2.pgoods_name);
			holder.tv_gnum.setText(getString(R.string.integral_prompt3)+ pointprod_list2.quantity);
			holder.tv_gprice.setText(getString(R.string.integral_prompt4)+ pointprod_list2.onepoints);
			return convertView;
		}

		class ViewHolder {
			ImageView iv_gimg;
			TextView tv_gname, tv_gnum, tv_gprice;

			public ViewHolder(View convertView) {
				iv_gimg = (ImageView) convertView.findViewById(R.id.iv_gimg);
				tv_gname = (TextView) convertView.findViewById(R.id.tv_gname);
				tv_gnum = (TextView) convertView.findViewById(R.id.tv_gnum);
				tv_gprice = (TextView) convertView.findViewById(R.id.tv_gprice);
				convertView.setTag(this);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Addres_Result:
			// 获取 activity的返回数据
			if (data != null) {
				if (data.getStringExtra("addres").equals("addres")) {
					Bundle bundle = data.getExtras();
					tv_name.setText(bundle.getString("address_name"));
					tv_phone.setText(bundle.getString("address_phone"));
					tv_address.setText(bundle.getString("address"));
					city_id = bundle.getString("city_id");
					area_id = bundle.getString("area_id");
					address_id = bundle.getString("address_id");
				}
			}
			break;
		}
	}
}
