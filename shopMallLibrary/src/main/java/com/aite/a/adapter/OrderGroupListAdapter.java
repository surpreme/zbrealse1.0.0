package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aiteshangcheng.a.R;
import com.aite.a.activity.PayListActivity;
import com.aite.a.model.OrderGroupList;
import com.aite.a.model.OrderGroupList.OrderList;
import com.aite.a.model.OrderGroupList.OrderList.ExtendOrderGoods;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.StringUtils;
import com.aite.a.utils.lingshi;
import com.aite.a.view.MyListView;

/**
 * 订单适配器
 * 
 * @author xiaoyu
 * 
 */
public class OrderGroupListAdapter extends BaseAdapter {
	private NetRun netRun;
	private Activity activity;
	private List<OrderGroupList> mOrderList = new ArrayList<OrderGroupList>();
	private List<OrderGroupList> mOrderList2 = new ArrayList<OrderGroupList>();
	private List<OrderGroupList> mOrderList3 = new ArrayList<OrderGroupList>();
	private List<OrderGroupList> mOrderList4 = new ArrayList<OrderGroupList>();
	private List<OrderGroupList> mOrderList5 = new ArrayList<OrderGroupList>();
	private int what;
	private Handler handler;

	public OrderGroupListAdapter(Activity activity, int what, Handler handler) {
		super();
		this.activity = activity;
		this.what = what;
		this.handler = handler;
		netRun = new NetRun(activity, handler);
	}

	@Override
	public int getCount() {
		switch (what) {
		case 0:
			return mOrderList.size();
		case 1:
			return mOrderList2.size();
		case 2:
			return mOrderList3.size();
		case 3:
			return mOrderList4.size();
		case 4:
			return mOrderList5.size();
		}
		return 0;
	}

	public void clear() {
		switch (what) {
		case 0:
			mOrderList.clear();
			break;
		case 1:
			mOrderList2.clear();
			break;
		case 2:
			mOrderList3.clear();
			break;
		case 3:
			mOrderList4.clear();
			break;
		case 4:
			mOrderList5.clear();
			break;
		}
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		switch (what) {
		case 0:
			return mOrderList.get(position);
		case 1:
			return mOrderList2.get(position);
		case 2:
			return mOrderList3.get(position);
		case 3:
			return mOrderList4.get(position);
		case 4:
			return mOrderList5.get(position);
		}
		return null;
	}

	public void setmOrderList(List<OrderGroupList> mOrderList) {
		this.mOrderList = mOrderList;
	}

	public void addOrderList(List<OrderGroupList> mOrderList) {
		switch (what) {
		case 0:
			this.mOrderList.addAll(mOrderList);
			break;
		case 1:
			this.mOrderList2.addAll(mOrderList);
			break;
		case 2:
			this.mOrderList3.addAll(mOrderList);
			break;
		case 3:
			this.mOrderList4.addAll(mOrderList);
			break;
		case 4:
			this.mOrderList5.addAll(mOrderList);
			break;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.order_list_group_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final OrderGroupList orderList = setOrderGroupList(position);
		holder.add_time.setText(activity.getString(R.string.order_reminder2) + "\t" + orderList.order_sn);
		final OrderListAdapter orderAdapter = new OrderListAdapter(activity,
				orderList.order_list, what, handler);
		holder.group.setAdapter(orderAdapter);
		holder.group.setDividerHeight(0);

		holder.bt_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 确认支付
				goodsPay(orderList, orderList.pay_sn);
			}
		});
		holder.tv_deleteorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取消订单
				for (int i = 0; i < orderList.order_list.size(); i++) {
					netRun.deteleOrder(orderList.order_list.get(i).order_id);
				}
			}
		});
		setPayButton(holder, orderList);
		return convertView;

	}

	private OrderGroupList setOrderGroupList(int position) {
		switch (what) {
		case 0:
			return mOrderList.get(position);
		case 1:
			return mOrderList2.get(position);
		case 2:
			return mOrderList3.get(position);
		case 3:
			return mOrderList4.get(position);
		case 4:
			return mOrderList5.get(position);
		}
		return null;
	}

	private void setPayButton(ViewHolder holder, OrderGroupList orderList) {
		int state = 0;
		if (what == 0) {
			for (OrderList list : orderList.order_list) {
				int i = Integer.valueOf(list.order_state);
				if (i == 10) {
					state = 10;
				}
			}
			if (state == 10) {
				holder.bt_pay.setVisibility(View.VISIBLE);
				holder.tv_deleteorder.setVisibility(View.VISIBLE);
			} else {
				holder.bt_pay.setVisibility(View.GONE);
				holder.tv_deleteorder.setVisibility(View.GONE);
			}
		} else if (what == 1) {
			holder.bt_pay.setVisibility(View.VISIBLE);
			holder.tv_deleteorder.setVisibility(View.VISIBLE);
		} else {
			holder.bt_pay.setVisibility(View.GONE);
			holder.tv_deleteorder.setVisibility(View.GONE);
		}
	}

	List<String> goods_namess = new ArrayList<String>();
	List<String> goods_url = new ArrayList<String>();
	List<String> order_amount = new ArrayList<String>();
	List<String> shipping_fee = new ArrayList<String>();
	List<String> goods_num1 = new ArrayList<String>();
	List<String> goods_price = new ArrayList<String>();
	List<String> store_name1 = new ArrayList<String>();

	private void goodsPay(OrderGroupList orderList, String pay_sn) {
		int goods_num = 0;
		List<String> goods_names = new ArrayList<String>();
		for (OrderList orderGroupList : orderList.order_list) {
			goods_num = goods_num + orderGroupList.extend_order_goods.size();

			order_amount.add(orderGroupList.order_amount);
			shipping_fee.add(orderGroupList.shipping_fee);
			store_name1.add(orderGroupList.store_name);
			for (int i = 0; i < orderGroupList.extend_order_goods.size(); i++) {
				ExtendOrderGoods goodsList = orderGroupList.extend_order_goods
						.get(i);
				goods_namess.add(goodsList.goods_name);
				goods_url.add(goodsList.goods_image_url);
				goods_num1.add(goodsList.goods_num);
				goods_price.add(goodsList.goods_price);
				goods_names.add(goodsList.goods_name);
			}
		}
		lingshi.getInstance().setGoods_names(goods_namess);
		lingshi.getInstance().setGoods_url(goods_url);
		lingshi.getInstance().setOrder_amount(order_amount);
		lingshi.getInstance().setShipping_fee(shipping_fee);
		lingshi.getInstance().setGoods_num1(goods_num1);
		lingshi.getInstance().setGoods_price(goods_price);
		lingshi.getInstance().setStore_name1(store_name1);

		Intent intent = new Intent(activity, PayListActivity.class);
		intent.putExtra("goods", StringUtils.listToString(goods_names, "+++++"));
		intent.putExtra("describe",
				APPSingleton.getContext().getString(R.string.no).toString());
		intent.putExtra("price", orderList.pay_amount);
		intent.putExtra("pay_sn", pay_sn);
		intent.putExtra("goods_num", String.valueOf(goods_num));
		activity.startActivity(intent);
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t\r\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	private static class ViewHolder {
		MyListView group;
		LinearLayout order_group_item_ll;
		TextView add_time ;
		TextView tv_deleteorder;
		Button bt_pay;

		public ViewHolder(View v) {
			super();
			group = (MyListView) v.findViewById(R.id.order_list_group);
			order_group_item_ll = (LinearLayout) v
					.findViewById(R.id.order_group_item_ll);
			add_time = (TextView) v.findViewById(R.id.add_time);
			tv_deleteorder = (TextView) v.findViewById(R.id.tv_deleteorder);
			bt_pay = (Button) v.findViewById(R.id.bt_pay);
		}

	}

}
