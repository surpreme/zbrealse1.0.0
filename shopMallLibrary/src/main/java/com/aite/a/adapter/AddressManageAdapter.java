package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.activity.EdiAddressActivity;
import com.aite.a.model.AddressInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.Dialog;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

public class AddressManageAdapter extends BaseAdapter {

	private Context context;
	private List<AddressInfo> addressInfos;
	private NetRun netRun;
	private Handler handler;
	
	public AddressManageAdapter(Context context,Handler handler) {
		super();
		this.handler=handler;
		netRun=new NetRun(context, handler);
		this.context = context;
		this.addressInfos = new ArrayList<AddressInfo>();
	}

	@Override
	public int getCount() {
		return addressInfos.size();
	}

	@Override
	public AddressInfo getItem(int position) {
		// TODO Auto-generated method stub
		return addressInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		TextView contact;
		TextView telephone;
		TextView mobile_phone;
		TextView address;
		RelativeLayout rl_delete,rl_update;
		CheckBox cb_acquiescence;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		final ViewHolder holder = new ViewHolder();
		v = LayoutInflater.from(context).inflate(R.layout.the_goods_address, parent, false);
		if (addressInfos != null) {
			final AddressInfo addressInfo = addressInfos.get(position);
			holder.contact = (TextView) v.findViewById(R.id.contact);
			holder.telephone = (TextView) v.findViewById(R.id.telephone);
			holder.mobile_phone = (TextView) v.findViewById(R.id.mobile_phone);
			holder.address = (TextView) v.findViewById(R.id.address);
			holder.rl_delete = (RelativeLayout) v.findViewById(R.id.rl_delete);
			holder.rl_update = (RelativeLayout) v.findViewById(R.id.rl_update);
			holder.cb_acquiescence = (CheckBox) v.findViewById(R.id.cb_acquiescence);
			holder.contact.setText(APPSingleton.getContext().getText(R.string.contact).toString() +"\t\t"+ addressInfo.getTrue_name());
			if (addressInfo.getTel_phone() != null)
				holder.telephone.setText(context.getString(R.string.m_ph)+ (addressInfo.getTel_phone()==null?"":addressInfo.getTel_phone()));
			if (addressInfo.getMob_phone() != null)
				holder.mobile_phone.setText(context.getString(R.string.mobilephone)+ addressInfo.getMob_phone());
			holder.address.setText(context.getString(R.string.order_reminder158)+ addressInfo.getArea_info() + addressInfo.getAddress());
			if (addressInfo.getIs_default().equals("1")) {
				holder.cb_acquiescence.setChecked(true);
			}else{
				holder.cb_acquiescence.setChecked(false);
			}
			holder.cb_acquiescence.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 选中
					for (int i = 0; i < addressInfos.size(); i++) {
						addressInfos.get(i).is_default="0";
					}
					addressInfos.get(position).is_default="1";
					netRun.DefaultAddress(addressInfo.address_id);
					notifyDataSetChanged();
				}
			});
			holder.rl_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 删除
					Dialog.showSnackBarWithAction("确定要删除吗?", holder.rl_delete, true, "是的", new Dialog.SnackbarActionListener() {
						@Override
						public void doAction(Snackbar snackbar) {
					netRun.dataleAddress(addressInfo.getAddress_id());

						}
					});
				}
			});
			holder.rl_update.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 编辑
					/*EdiAddressActivity addressActivity = new EdiAddressActivity();
					addressActivity.handler.sendMessage(addressActivity.handler
							.obtainMessage(20003,
									addressInfo));*/
					Bundle bundle = new Bundle();
					bundle.putSerializable("addressInfo",
							addressInfos.get(position));
					bundle.putBoolean("edit",true);
					Intent intent = new Intent(context,EdiAddressActivity.class);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});
			
		}
		return v;
	}

	public void setAddressInfos(List<AddressInfo> addressInfos) {
		this.addressInfos = addressInfos;
	}

}
