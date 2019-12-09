package com.aite.a.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.base.Mark;
import com.aite.a.model.CartListInfo2;
import com.aite.a.model.CartListInfo2.cart_list;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

public class Cart2Adapter extends BaseAdapter implements Mark {
	private Activity activity;
	public List<List<cart_list>> cartdata;
	private BitmapUtils bitmapUtils;
	private NetRun netRun;
	private Handler handler;
	private ProgressDialog mdialog;

	@SuppressLint("HandlerLeak")
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case up_cart_num_id:
				if (msg.obj.equals("1")) {
					handler.sendMessage(handler.obtainMessage(0));
					handler.sendEmptyMessage(3);
					CommonTools.showShortToast(activity, APPSingleton
							.getContext().getString(R.string.update_success)
							.toString());
				} else {
					CommonTools.showShortToast(activity, APPSingleton
							.getContext().getString(R.string.update_fail)
							.toString());
				}
				mdialog.dismiss();
				break;
			case up_cart_num_err:
				mdialog.dismiss();
				CommonTools.showShortToast(activity, APPSingleton.getContext()
						.getString(R.string.systembusy).toString());
				break;
			case up_cart_num_start:
//				mdialog.setMessage(APPSingleton.getContext()
//						.getString(R.string.act_waiting).toString());
				mdialog.show();
				break;
			case drop_cart_id:
				if (msg.obj.equals("1")) {
					handler.sendMessage(handler.obtainMessage(1));
					handler.sendMessage(handler.obtainMessage(0));
					CommonTools.showShortToast(activity, APPSingleton
							.getContext().getString(R.string.act_del_success)
							.toString());
				} else {
					CommonTools.showShortToast(activity, APPSingleton
							.getContext().getString(R.string.act_del_fail)
							.toString());
				}
				mdialog.dismiss();
				break;
			case drop_cart_err:
				mdialog.dismiss();
				CommonTools.showShortToast(activity, APPSingleton.getContext()
						.getString(R.string.systembusy).toString());
				break;
			case drop_cart_start:
//				mdialog.setMessage(APPSingleton.getContext()
//						.getString(R.string.act_waiting).toString());
				mdialog.show();
				break;
			case 0:
				handler.sendMessage(handler.obtainMessage(3));
				break;

			}
		}
	};

	public Cart2Adapter(Activity activity, Handler handler) {
		this.activity = activity;
		this.handler = handler;
		netRun = new NetRun(activity, handler2);
		bitmapUtils = new BitmapUtils(activity);
		mdialog = new ProgressDialog(activity);
		mdialog.setProgressStyle(mdialog.STYLE_SPINNER);
	}

	@Override
	public int getCount() {
		if (cartdata == null) {
			return 0;
		}
		return cartdata.size();
	}

	@Override
	public Object getItem(int position) {
		return cartdata == null ? null : cartdata.get(position);
	}

	public void setCartList(List<List<cart_list>> cartdata) {
		this.cartdata = cartdata;
	}

	public List<List<cart_list>> getCartList() {
		return cartdata;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		TextView tv_merchantsnames;
		MyListView mlv_goods;

		public ViewHolder(View v) {
			tv_merchantsnames = (TextView) v
					.findViewById(R.id.tv_merchantsnames);
			mlv_goods = (MyListView) v.findViewById(R.id.mlv_goods);
			v.setTag(this);
		}
	}

	
	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		if (v == null) {
			v = LayoutInflater.from(activity).inflate(R.layout.cart_item2,
					parent, false);
			new ViewHolder(v);
		}
		ViewHolder holder = (ViewHolder) v.getTag();
		List<CartListInfo2.cart_list> list = cartdata
				.get(position);
		holder.tv_merchantsnames.setText(activity.getString(R.string.order_reminder93) + list.get(0).store_name);
		Gadapter gadapter = new Gadapter(list);
		holder.mlv_goods.setAdapter(gadapter);
		return v;
	}

	/**
	 * 商品匹配
	 * 
	 * @author Administrator
	 *
	 */
	public class Gadapter extends BaseAdapter {
		List<CartListInfo2.cart_list> list;

		public Gadapter(List<CartListInfo2.cart_list> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list == null ? null : list.get(position);
		}

		
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
				convertView = View.inflate(activity, R.layout.cart_goodsitem,
						null);
				new ViewHolder(convertView);
//			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final cart_list cart_list2 = list.get(position);
			bitmapUtils.display(holder.iv_gimg, cart_list2.goods_image_url);
			holder.cart_item_cb.setChecked(cart_list2.isChoosed);
			holder.tv_goodsname.setText(cart_list2.goods_name);
			holder.tv_gprice.setText("¥" + cart_list2.goods_price);
			holder.add_sub_tv_num.setText(cart_list2.goods_num);
			holder.iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 删除
					netRun.delCartGoods(cart_list2.cart_id);
				}
			});
			holder.add_sub_btn_sub.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 减少
					int goodsNum = Integer.valueOf(cart_list2.goods_num);
					if (goodsNum != 1) {
						goodsNum = goodsNum - 1;
						netRun.upCartGoodsNum(cart_list2.cart_id,
								String.valueOf(goodsNum), cart_list2, holder);
					}
				}
			});
			holder.add_sub_btn_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 增加
					int goodsNum2 = Integer.valueOf(cart_list2.goods_num);
					goodsNum2 = goodsNum2 + 1;
					netRun.upCartGoodsNum(cart_list2.cart_id,
							String.valueOf(goodsNum2), cart_list2, holder);
				}
			});
			holder.cart_item_cb
					.setOnCheckedChangeListener(new CheckBoxChangedListener(cart_list2));
			return convertView;
		}

		public class ViewHolder {
			CheckBox cart_item_cb;
			ImageView iv_gimg, iv_del;
			TextView tv_goodsname;
			public TextView tv_gprice;
			public TextView add_sub_tv_num;
			Button add_sub_btn_sub, add_sub_btn_add;

			public ViewHolder(View convertView) {
				cart_item_cb = (CheckBox) convertView
						.findViewById(R.id.cart_item_cb);
				iv_gimg = (ImageView) convertView.findViewById(R.id.iv_gimg);
				iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
				tv_goodsname = (TextView) convertView
						.findViewById(R.id.tv_goodsname);
				tv_gprice = (TextView) convertView.findViewById(R.id.tv_gprice);
				add_sub_tv_num = (TextView) convertView
						.findViewById(R.id.add_sub_tv_num);
				add_sub_btn_sub = (Button) convertView
						.findViewById(R.id.add_sub_btn_sub);
				add_sub_btn_add = (Button) convertView
						.findViewById(R.id.add_sub_btn_add);
				convertView.setTag(this);
			}
		}
	}

	// CheckBox选择改变监听器
	private final class CheckBoxChangedListener implements
			OnCheckedChangeListener {
		CartListInfo2.cart_list cart_list;
		public CheckBoxChangedListener(CartListInfo2.cart_list cart_list) {
			this.cart_list = cart_list;
		}

		@Override
		public void onCheckedChanged(CompoundButton cb, boolean flag) {
			cart_list.isChoosed = flag;
			// 如果物品被选中，则更改商品总额
			handler.sendMessage(handler.obtainMessage(0));
			// 如果所有的物品全部被选中，则全选按钮也默认被选中
			handler.sendMessage(handler.obtainMessage(2, isAllSelected()));
		}
	}
	
	/**
	 * 判断是否购物车中所有的商品全部被选中
	 * 
	 * @return true所有条目全部被选中 false还有条目没有被选中
	 */
	public boolean isAllSelected() {
		boolean flag = true;
		for (int i = 0; i < cartdata.size(); i++) {
			for (int j = 0; j < cartdata.get(i).size(); j++) {
				if (cartdata.get(i).get(j).isChoosed != true) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
}