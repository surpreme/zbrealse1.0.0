package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.aite.a.activity.SellerOrderprice;
import com.aite.a.activity.SendActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.SellerOrderList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.lingshi;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

public class SellerOrderAdapter extends BaseAdapter implements Mark {
	private List<SellerOrderList> orderList = new ArrayList<SellerOrderList>();
	private Context context;
	private NetRun netRun;

	public SellerOrderAdapter(Context context, Handler handler) {
		super();
		this.context = context;
		netRun = new NetRun(context, handler);
	}

	@Override
	public int getCount() {
		return orderList.size();
	}

	@Override
	public Object getItem(int position) {
		return orderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addOrderList(List<SellerOrderList> orderList) {
		this.orderList.addAll(orderList);
	}

	private class ViewHolder {
		private TextView order_number;
		private Button order_item_bt1, order_item_bt2, order_item_bt3, bt_send;
		private ListView lv_goods_list;
		private TextView amount;
		private TextView tv_statedata;
		private TextView goods_pricedata;
		private TextView goods_price;

		public ViewHolder(View view) {
			order_number = (TextView) view.findViewById(R.id.order_number);
			order_item_bt1 = (Button) view.findViewById(R.id.order_item_bt1);
			order_item_bt2 = (Button) view.findViewById(R.id.order_item_bt2);
			order_item_bt3 = (Button) view.findViewById(R.id.order_item_bt3);
			bt_send = (Button) view.findViewById(R.id.bt_send);
			lv_goods_list = (ListView) view.findViewById(R.id.lv_goods_list);
			amount = (TextView) view.findViewById(R.id.amount);
			tv_statedata = (TextView) view.findViewById(R.id.tv_statedata);
			goods_pricedata = (TextView) view
					.findViewById(R.id.goods_pricedata);
			goods_price = (TextView) view.findViewById(R.id.goods_price);
		}
	}

	private Intent intenttt = null;

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.seller_order_item, parent, false);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if (orderList.size() > 0) {

			SellerOrderList list = orderList.get(position);
			String state = list.order_state;
			String fee = list.shipping_fee;
			String order_sn = list.order_sn;
			String order_amount = list.order_amount;

			// String goods_name = list.goods_list.get(position).goods_name;
			// String goods_image = list.goods_list.get(position).goods_image;
			// intenttt.putExtra("goods_name", goods_name);
			// intenttt.putExtra("goods_image", goods_image);
			holder.order_number.setText(context.getString(R.string.order_no)+ order_sn);
			holder.amount.setText(order_amount);
			holder.goods_pricedata.setText(fee);
			holder.goods_price.setText(context.getString(R.string.total_one_yf));
			if (state.equals("0")) {
				holder.tv_statedata.setText(context.getString(R.string.distribution_center47));
			} else if (state.equals("10")) {
				holder.tv_statedata.setText(context.getString(R.string.member_centre35));
			} else if (state.equals("20")) {
				holder.tv_statedata.setText(context.getString(R.string.m_paid));
			} else if (state.equals("30")) {
				holder.tv_statedata.setText(context.getString(R.string.delivered));
			} else if (state.equals("40")) {
				holder.tv_statedata.setText(context.getString(R.string.member_centre48));
			}
			setGoods(list, holder);
			if (Boolean.valueOf(list.if_lock) == true) {
				holder.order_item_bt1.setVisibility(View.GONE);
				// holder.order_item_bt2.setVisibility(View.GONE);
				holder.order_item_bt3.setVisibility(View.GONE);
			} else {
				holder.order_item_bt1.setVisibility(View.VISIBLE);
				// holder.order_item_bt2.setVisibility(View.VISIBLE);
				holder.order_item_bt3.setVisibility(View.VISIBLE);
			}
			if (Boolean.valueOf(list.if_modify_price) == true) {
				holder.order_item_bt1.setVisibility(View.VISIBLE);
				// holder.order_item_bt2.setVisibility(View.VISIBLE);
			} else {
				holder.order_item_bt1.setVisibility(View.GONE);
				// holder.order_item_bt2.setVisibility(View.GONE);
			}
			if (Boolean.valueOf(list.if_cancel) == true) {
				holder.order_item_bt3.setVisibility(View.VISIBLE);
			} else {
				holder.order_item_bt3.setVisibility(View.GONE);
			}
			if (Boolean.valueOf(list.if_send) == true) {
//				holder.bt_send.setVisibility(View.VISIBLE);
			} else {
				holder.bt_send.setVisibility(View.GONE);
			}
		}
		holder.order_item_bt1.setText(context.getString(R.string.update_price));
		// holder.order_item_bt2.setText(APPSingleton.getContext()
		// .getString(R.string.update_freight).toString());
		holder.order_item_bt3.setText(context.getString(R.string.cancel_order));
		holder.order_item_bt1.setOnClickListener(OnClick(position));
		// holder.order_item_bt2.setOnClickListener(OnClick(position));
		holder.order_item_bt3.setOnClickListener(OnClick(position));
		holder.bt_send.setOnClickListener(OnClick(position));

		return view;
	}

	private void setGoods(SellerOrderList list, ViewHolder holder) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (SellerOrderList.GoodsList goodsList : list.goods_list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", goodsList.goods_name);
			map.put("image", goodsList.image_60_url);
			map.put("price", goodsList.goods_pay_price);
			map.put("num", context.getString(R.string.counts_d)
					+ ":x" + goodsList.goods_num);
			map.put("order_sn", list.order_sn);
			map.put("shipping_fee", list.shipping_fee);
			map.put("add_time", list.add_time);
			map.put("price2", goodsList.goods_pay_price);
			data.add(map);
			lingshi.getInstance().setData(map);
		}
		SimpleAdapter goodsAdapter = new SimpleAdapter(context, data,
				R.layout.order_goods_list_item, new String[] { "image", "name",
						"num", "price" }, new int[] { R.id.order_item_iv_image,
						R.id.order_item_tv_details, R.id.order_item_tv_num,
						R.id.order_item_tv_price });
		goodsAdapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Object) {
					ImageView i = (ImageView) view;
					new BitmapUtils(context).display(i, data.toString());
					return true;
				}
				return false;
			}
		});
		holder.lv_goods_list.setAdapter(goodsAdapter);
	}

	private OnClickListener OnClick(final int position) {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId()== R.id.order_item_bt1){
					lingshi.getInstance().setPosition(position);
					lingshi.getInstance().setOrder_id(
							orderList.get(position).order_id);
					intenttt = new Intent(context, SellerOrderprice.class);
					context.startActivity(intenttt);
				}else if(v.getId()==R.id.order_item_bt3){
					dialog(context.getString(R.string.order_reminder164),
							"order_cancel", orderList.get(position).order_id);
				}else if(v.getId()==R.id.bt_send){
					Intent intent = new Intent(context,
							SendActivity.class);
					intent.putExtra("order_id", orderList.get(position).order_id);
					context.startActivity(intent);
				}


//				switch (v.getId()) {
//				case R.id.order_item_bt1:
//					lingshi.getInstance().setPosition(position);
//					lingshi.getInstance().setOrder_id(
//							orderList.get(position).order_id);
//					intenttt = new Intent(context, SellerOrderprice.class);
//					context.startActivity(intenttt);
//					// dialog(APPSingleton.getContext()
//					// .getString(R.string.update_price_unit).toString(),
//					// "goods_price", orderList.get(position).order_id);
//					break;
//				// case R.id.order_item_bt2:
//				// dialog(APPSingleton.getContext().getString(R.string.update_freight_unit),
//				// "modify_price", orderList.get(position).order_id);
//				// break;
//				case R.id.order_item_bt3:
//					dialog(context.getString(R.string.order_reminder164),
//							"order_cancel", orderList.get(position).order_id);
//					break;
//				case R.id.bt_send:
//					Intent intent = new Intent(context,
//							SendActivity.class);
//					intent.putExtra("order_id", orderList.get(position).order_id);
//					context.startActivity(intent);
//					// netRun.ModifyOrder(orderList.get(position).order_id,
//					// "order_send","");
//					break;
//				}
			}
		};
		return listener;
	}

	protected void dialog(String title, final String state_type,
			final String order_id) {
		Builder builder = new Builder(context);
		final EditText withDel = new EditText(context);
		builder.setTitle(context.getString(R.string.tip));
		builder.setMessage(title);
		if (!state_type.endsWith("order_cancel"))
			builder.setView(withDel);
		builder.setPositiveButton(
				context.getString(R.string.release_goods94), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						netRun.ModifyOrder(order_id, state_type, withDel
								.getText().toString());
						dialog.dismiss();

					}
				});
		builder.setNegativeButton(
				context.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	public void clear() {
		orderList.clear();
		notifyDataSetChanged();
	}
}