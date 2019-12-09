package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import com.aite.a.base.Mark;
import com.aite.a.model.CustomerDataInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CustomToolBar;
import com.aite.a.view.CustomToolBar.onLeftBtnClickListener;
import com.aite.a.view.CustomToolBar.onRightBtnClickListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 客户列表
 * */
public class ClientListActivity extends Activity implements Mark,
		OnClickListener {

	private Context mContext;
	private CustomToolBar mCustomToolBar;
	private ListView mClientList;
	private NetRun mNetRun;
	private LinearLayout ll_customer_list, ll_null_customer;
	private List<CustomerDataInfo> listCustomerr;
	private Button vt_serch;
	private EditText et_input;
	private ClientListAdapter clientListAdapter;
	private BitmapUtils bitmapUtils;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case customer_list_id:
				if (msg.obj != null) {
					listCustomerr = (List<CustomerDataInfo>) msg.obj;
					if (!listCustomerr.isEmpty()) {
						Log.d("ss", msg.obj + "  !null");
						ll_customer_list.setVisibility(View.VISIBLE);
						ll_null_customer.setVisibility(View.GONE);
						clientListAdapter = new ClientListAdapter(listCustomerr);
						mClientList.setAdapter(clientListAdapter);
					} else {
						Log.d("ss", msg.obj + "  null");
						ll_customer_list.setVisibility(View.GONE);
						ll_null_customer.setVisibility(View.VISIBLE);
					}
				} else {
					Toast.makeText(mContext,
							getResources().getString(R.string.systembusy),
							Toast.LENGTH_SHORT).show();
				}
				break;
			case customer_list_err:
				Toast.makeText(mContext,
						getString(R.string.act_net_error).toString(),
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_client_list);
		init();// 初始化
		setListener();
	}

	private void init() {
		mContext = ClientListActivity.this;
		mCustomToolBar = (CustomToolBar) findViewById(R.id.custom_toolbar);
		mClientList = (ListView) findViewById(R.id.lv_client);
		ll_customer_list = (LinearLayout) findViewById(R.id.ll_customer_list);
		ll_null_customer = (LinearLayout) findViewById(R.id.ll_null_customer);
		vt_serch = (Button) findViewById(R.id.vt_serch);
		et_input = (EditText) findViewById(R.id.et_input);
		mNetRun = new NetRun(mContext, handler);
		bitmapUtils=new BitmapUtils(this);
		vt_serch.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mNetRun.CustomerList("", "", "");
	}

	private void setListener() {
		mCustomToolBar.setOnLeftBtnClickListener(new onLeftBtnClickListener() {

			@Override
			public void onLeftBtnClick() {
				// 返回
				finish();
			}
		});
		mCustomToolBar
				.setOnRightBtnClickListener(new onRightBtnClickListener() {

					@Override
					public void onRightBtnClick() {
						// 添加
						Intent intent = new Intent(mContext,
								AddClientActivity.class);
						startActivity(intent);
					}
				});
	}

	private class ClientListAdapter extends BaseAdapter {

		
		List<CustomerDataInfo> listCustomer;

		public ClientListAdapter(List<CustomerDataInfo> listCustomer) {
			this.listCustomer = listCustomer;
			
		}

		@Override
		public int getCount() {
			return listCustomer.size();
		}

		@Override
		public Object getItem(int position) {
			return listCustomer.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				LayoutInflater inflater = ((Activity) mContext)
						.getLayoutInflater();
				convertView = inflater.inflate(
						R.layout.worker_client_list_item, null);
				viewHolder = new ViewHolder();

				viewHolder.mCustomerPhoto = (ImageView) convertView
						.findViewById(R.id.iv_photo);
				viewHolder.customerName = (TextView) convertView
						.findViewById(R.id.tv_name);
				viewHolder.customerPhone = (TextView) convertView
						.findViewById(R.id.tv_phone);
				viewHolder.tv_type_addr = (TextView) convertView
						.findViewById(R.id.tv_type_addr);

				CustomerDataInfo customerDataInfo = listCustomer.get(position);
				bitmapUtils.display(viewHolder.mCustomerPhoto, customerDataInfo.member_avatar_url);
				viewHolder.customerName.setText(customerDataInfo.customer_name);
				viewHolder.customerPhone
						.setText(customerDataInfo.customer_mobile);
				viewHolder.tv_type_addr.setText(customerDataInfo.area_info);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}
	}

	static class ViewHolder {
		ImageView mCustomerPhoto;
		TextView customerName;
		TextView customerPhone;
		TextView tv_type_addr;
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.vt_serch:
//			// 搜索
//			String string = et_input.getText().toString();
//			if (TextUtils.isEmpty(string) || string.equals("")) {
//				clientListAdapter=new ClientListAdapter(listCustomerr);
//			}
//			List<CustomerDataInfo> list = new ArrayList<CustomerDataInfo>();
//			for (int i = 0; i < listCustomerr.size(); i++) {
//				CustomerDataInfo customerDataInfo = listCustomerr.get(i);
//				if (customerDataInfo.customer_mobile.contains(string)) {
//					list.add(customerDataInfo);
//				}
//			}
//			clientListAdapter=new ClientListAdapter(list);
//			System.out.println("----------------这里   "+list.size());
//			mClientList.setAdapter(clientListAdapter);
////			clientListAdapter.search(string);
//			break;
//		}
		if(v.getId()==R.id.vt_serch){
			// 搜索
			String string = et_input.getText().toString();
			if (TextUtils.isEmpty(string) || string.equals("")) {
				clientListAdapter=new ClientListAdapter(listCustomerr);
			}
			List<CustomerDataInfo> list = new ArrayList<CustomerDataInfo>();
			for (int i = 0; i < listCustomerr.size(); i++) {
				CustomerDataInfo customerDataInfo = listCustomerr.get(i);
				if (customerDataInfo.customer_mobile.contains(string)) {
					list.add(customerDataInfo);
				}
			}
			clientListAdapter=new ClientListAdapter(list);
			System.out.println("----------------这里   "+list.size());
			mClientList.setAdapter(clientListAdapter);
		}
	}

}
