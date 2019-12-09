package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.model.BuySteOneInfo.StoreCartlList;
import com.aite.a.model.BuySteOneInfo.StoreCartlList.GoodsList2;
import com.aite.a.utils.lingshi;
import com.aiteshangcheng.a.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SureOrderGiftAdapter extends BaseAdapter {
	private Context mContext;
	private List<StoreCartlList> storeInfos = new ArrayList<StoreCartlList>();
	private Intent intent;

	public SureOrderGiftAdapter(Context mContext) {
		super();
		this.mContext = mContext;

	}

	/**
	 * 获取留言
	 * @return
	 */
	public String getMessage() {
		JSONArray array = new JSONArray();
		try {
			for (int i = 0; i < storeInfos.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("store_id", storeInfos.get(i).goods_list.get(0).store_id);
				jsonObject.put("context", storeInfos.get(i).leaveamessage == null ? "" : storeInfos.get(i).leaveamessage);
				array.put(jsonObject);
			}
			return array.toString();
		} catch (Exception e) {
			Log.i("---------------", "获取留言错误  " + e.getMessage());
		}
		return null;
	}

	@Override
	public int getCount() {
		return storeInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setStoreInfos(List<StoreCartlList> storeInfos) {
		this.storeInfos = storeInfos;
	}

	private boolean issame = true;
	int k = 0;

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder;
		if (v == null) {
			v = LayoutInflater.from(mContext).inflate(
					R.layout.sure_order_gift_item, parent, false);
			holder = new ViewHolder(v);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		final StoreCartlList storeCartlList = storeInfos.get(position);
		holder.store_name.setText(storeCartlList.store_name);
		if (storeCartlList.freight_message != null) {
			holder.freight_message.setVisibility(View.VISIBLE);
		} else {
			holder.freight_message.setVisibility(View.GONE);
		}
		holder.et_eaveamessage.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				storeCartlList.leaveamessage = s.toString();
			}
		});

		if (storeInfos.get(0).goods_list.get(0).goods_name
				.equals(storeCartlList.goods_list.get(0).goods_name)) {
			if (k>0) {
				issame = false;
			}
			k++;
		}
		if (issame) {
			for (int i = 0; i < storeCartlList.goods_list.size(); i++) {
				goods_names.add(storeCartlList.goods_list.get(i).goods_name);
				goods_url.add(storeCartlList.goods_list.get(i).goods_image_url);
				order_amount.add(storeCartlList.store_goods_total);
				shipping_fee.add(storeCartlList.freight);
				goods_num1.add(storeCartlList.goods_list.get(i).goods_num);
				goods_price.add(storeCartlList.goods_list.get(i).goods_price);
				store_name1.add(storeCartlList.store_name);
			}
			lingshi.getInstance().setOrder_amount(order_amount);
			lingshi.getInstance().setShipping_fee(shipping_fee);
			lingshi.getInstance().setStore_name1(store_name1);
			lingshi.getInstance().setGoods_names(goods_names);
			lingshi.getInstance().setGoods_url(goods_url);
			lingshi.getInstance().setGoods_price(goods_price);
			lingshi.getInstance().setGoods_num1(goods_num1);
		}

		lingshi.getInstance().setGoods_freight(storeCartlList.freight);
		lingshi.getInstance().setFreight_message(
				storeCartlList.store_goods_total);
		holder.freight_message.setText(storeCartlList.freight_message);
		holder.store_goods_total.setText(APPSingleton.getContext()
				.getString(R.string.total).toString()
				+ "：￥" + storeCartlList.store_goods_total);
		holder.goods_num.setText(APPSingleton.getContext()
				.getString(R.string.in_total).toString()
				+ calNum(storeCartlList.goods_list)
				+ APPSingleton.getContext().getString(R.string.a_goods)
						.toString());
		SureOrderAdapter sa = new SureOrderAdapter(mContext,
				storeCartlList.goods_list);

		holder.buy_goods_lv.setAdapter(sa);
		holder.buy_goods_lv.setOnItemClickListener(new listener(
				storeCartlList.goods_list));
		holder.store_name_rl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, StoreDetailsActivity.class);
				intent.putExtra("store_id",
						storeCartlList.goods_list.get(0).store_id);
				mContext.startActivity(intent);
			}
		});
		return v;
	}

	List<String> goods_names = new ArrayList<String>();
	List<String> goods_url = new ArrayList<String>();
	List<String> order_amount = new ArrayList<String>();
	List<String> shipping_fee = new ArrayList<String>();
	List<String> goods_num1 = new ArrayList<String>();
	List<String> goods_price = new ArrayList<String>();
	List<String> store_name1 = new ArrayList<String>();
	boolean isfirst = true;

	private class listener implements OnItemClickListener {
		List<GoodsList2> goodLists;

		public listener(List<GoodsList2> goods_list) {
			this.goodLists = goods_list;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
			Intent intent = new Intent(mContext,
					ProductDetailsActivity.class);
			intent.putExtra("goods_id", goodLists.get(i).goods_id);
			mContext.startActivity(intent);
		}
	};

	private String calNum(List<GoodsList2> cartList) {
		int num = 0;
		for (GoodsList2 goodList : cartList) {
			int i = Integer.valueOf(goodList.goods_num);
			num = num + i;
		}
		return String.valueOf(num);
	}

	private class ViewHolder {
		TextView goods_num;
		TextView store_goods_total;
		TextView store_name;
		TextView freight_message;
		ListView buy_goods_lv;
		EditText et_eaveamessage;
		RelativeLayout store_name_rl;
		TextView orderSnTv ;

		public ViewHolder(View v) {
			super();
			store_name = (TextView) v.findViewById(R.id.store_name);
			freight_message = (TextView) v.findViewById(R.id.freight_message);
			store_goods_total = (TextView) v
					.findViewById(R.id.store_goods_total);
			goods_num = (TextView) v.findViewById(R.id.goods_num);
			buy_goods_lv = (ListView) v.findViewById(R.id.buy_goods_lv);
			store_name_rl = (RelativeLayout) v.findViewById(R.id.store_name_rl);
			et_eaveamessage = (EditText) v.findViewById(R.id.et_eaveamessage);
			orderSnTv= v.findViewById(R.id.sure_ordersn_tv);
		}
	}

}
