package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.adapter.AddressManageAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.AddressInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;

/**
 * 地址管理
 * 
 * @author CAOYOU
 * 
 */
public class AddressManageActivity extends BaseActivity {
	private ListView lv;
	private Button add;
	private ImageView _iv_back;
	private TextView _tv_name;
	private Context context = AddressManageActivity.this;
	private List<AddressInfo> addressInfos = new ArrayList<AddressInfo>();
	private NetRun netRun;
	protected AddressManageAdapter adapter;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case address_list_id:
				addressInfos = (List<AddressInfo>) msg.obj;
				adapter.setAddressInfos(addressInfos);
				adapter.notifyDataSetChanged();
				if (addressInfos.size() == 0)
					CommonTools.showShortToast(AddressManageActivity.this,
							getI18n(R.string.act_no_data));
				mdialog.dismiss();
				break;
			case address_list_err:
				mdialog.dismiss();
				CommonTools.showShortToast(AddressManageActivity.this,
						getI18n(R.string.act_net_error));
				break;
			case address_list_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case drop_consignee_id:
				if (msg.obj.equals("1")) {
					CommonTools.showShortToast(AddressManageActivity.this,
							getI18n(R.string.act_del_success));
					initData();
				} else {
					CommonTools.showShortToast(AddressManageActivity.this,
							getI18n(R.string.act_del_fail));
				}
				mdialog.dismiss();
				break;
			case drop_consignee_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case drop_consignee_err:
				mdialog.dismiss();
				CommonTools.showShortToast(AddressManageActivity.this,
						getI18n(R.string.act_net_error));
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.the_goods_address_list);
		netRun = new NetRun(this, handler);
		findViewById();
		initView();
		initData();
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.add:
//				Intent intent = new Intent();
//				intent.setClass(AddressManageActivity.this,
//						EdiAddressActivity.class);
//				startActivity(intent);
//				break;
//			case R.id._iv_back:
//				finish();
//				break;
//			}
			if(v.getId()==R.id.add){
				Intent intent = new Intent();
				intent.setClass(AddressManageActivity.this,
						EdiAddressActivity.class);
				startActivity(intent);
			}else if(v.getId()==R.id._iv_back){
				finish();
			}
		}
	};

	@Override
	protected void findViewById() {
		add = (Button) findViewById(R.id.add);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		add.setOnClickListener(clickListener);
		_iv_back.setOnClickListener(clickListener);
		_tv_name.setText(getI18n(R.string.address_manage));
		lv = (ListView) findViewById(R.id.listview);
		lv.setOnItemClickListener(onItemClickListener);
		lv.setOnItemLongClickListener(LongClickListener);
	}

	@Override
	protected void initView() {
		adapter = new AddressManageAdapter(this,handler);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void initData() {
		netRun.getAddress();
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			if (bundle == null) {
				return;
			}
			if (bundle.getString("cb_out").equals("out")) {
				bundle.putString("addres", "addres");
				bundle.putString("address_id", addressInfos.get(position)
						.getAddress_id());
				bundle.putString("city_id", addressInfos.get(position)
						.getCity_id());
				bundle.putString("area_id", addressInfos.get(position)
						.getArea_id());
				bundle.putString("address_name", addressInfos.get(position)
						.getTrue_name());
				bundle.putString("address_phone", addressInfos.get(position)
						.getMob_phone());
				bundle.putString("address", addressInfos.get(position)
						.getArea_info()
						+ "-"
						+ addressInfos.get(position).getAddress());
				intent.putExtras(bundle);
				AddressManageActivity.this.setResult(Addres_Result, intent);
				AddressManageActivity.this.finish();
			}

		}
	};

	OnItemLongClickListener LongClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			AlertDialog.Builder ad = new AlertDialog.Builder(context);
			ad.setTitle(getI18n(R.string.selector));// 设置对话框标题
			ad.setItems(new String[] { getI18n(R.string.update),
					getI18n(R.string.del) },
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								EdiAddressActivity addressActivity = new EdiAddressActivity();
								addressActivity.handler.sendMessage(addressActivity.handler
										.obtainMessage(Addres_,
												addressInfos.get(position)));
								Bundle bundle = new Bundle();
								bundle.putSerializable("addressInfo",
										addressInfos.get(position));
								openActivity(EdiAddressActivity.class, bundle);
								break;
							case 1:
								netRun.dataleAddress(addressInfos.get(position)
										.getAddress_id());

								break;
							}
						}
					});
			ad.show();
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			if (bundle == null) {
				return false;
			}
			if (bundle.getString("cb_out").equals("out")) {
				initData();
				return true;
			} else {
				return false;
			}
		}
	};

	@Override
	protected void onResume() {
		initData();
		super.onResume();
	}

	@Override
	public void ReGetData() {
		initData();
	};

}
