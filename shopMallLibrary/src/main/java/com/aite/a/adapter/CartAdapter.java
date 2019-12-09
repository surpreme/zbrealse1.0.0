package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.activity.ProductDetailsActivity;
import com.aiteshangcheng.a.R;
import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.CartListInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.lidroid.xutils.BitmapUtils;

public class CartAdapter extends BaseAdapter implements Mark {
	private Activity activity;
	public List<CartListInfo> cartList = new ArrayList<CartListInfo>();
	private BitmapUtils bitmapUtils;
	private NetRun netRun;
	private Handler handler;
	private ProgressDialog mdialog;
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case up_cart_num_id:
				if (msg.obj.equals("1")) {
					handler.sendMessage(handler.obtainMessage(0));
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
						.getString(R.string.act_net_error).toString());
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
						.getString(R.string.act_net_error).toString());
				break;
			case drop_cart_start:
//				mdialog.setMessage(APPSingleton.getContext()
//						.getString(R.string.act_waiting).toString());
				mdialog.show();
				break;

			}
		}
	};

	public CartAdapter(Activity activity, Handler handler) {
		this.activity = activity;
		this.handler = handler;
		netRun = new NetRun(activity, handler2);
		bitmapUtils = new BitmapUtils(activity);
		mdialog = new ProgressDialog(activity);
		mdialog.setProgressStyle(mdialog.STYLE_SPINNER);
		initDate();
	}

	@Override
	public int getCount() {
		return cartList.size();
	}

	@Override
	public Object getItem(int position) {
		return cartList.get(position);
	}

	public void setCartList(List<CartListInfo> cartList) {
		this.cartList = cartList;
	}

	public List<CartListInfo> getCartList() {
		return cartList;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		public CheckBox cb;
		public TextView details;
		public ImageView image;
		public TextView price;
		public TextView num;
		public Button sub;
		public Button add;
		public Button delete;
		public Button cart_item_tv_delete;
		public LinearLayout ll_shuliang, enterstore;
		public TextView tv_merchantsnames;
		public TextView tv_amountfcf1;
		public RelativeLayout ll_item;
		public LinearLayout ll_shopp;

		public ViewHolder(View v) {
			cb = (CheckBox) v.findViewById(R.id.cart_item_cb);
			details = (TextView) v.findViewById(R.id.cart_item_tv_details);
			price = (TextView) v.findViewById(R.id.cart_item_tv_price);
			image = (ImageView) v.findViewById(R.id.cart_item_iv_image);
			num = (TextView) v.findViewById(R.id.add_sub_tv_num);
			tv_amountfcf1 = (TextView) v.findViewById(R.id.tv_amountfcf1);
			sub = (Button) v.findViewById(R.id.add_sub_btn_sub);
			add = (Button) v.findViewById(R.id.add_sub_btn_add);
			delete = (Button) v.findViewById(R.id.cart_item_tv_delete);
			tv_merchantsnames = (TextView) v
					.findViewById(R.id.tv_merchantsnames);
			enterstore = (LinearLayout) v.findViewById(R.id.enterstore);
			ll_item = (RelativeLayout) v.findViewById(R.id.ll_item);
			ll_shopp = (LinearLayout) v.findViewById(R.id.ll_shopp);
		}
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		ViewHolder holder = null;

		if (v == null) {
			v = LayoutInflater.from(activity).inflate(R.layout.cart_item,
					parent, false);
			holder = new ViewHolder(v);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}

		if (cartList.size() > 0) {

			final CartListInfo info = cartList.get(position);
			String id = info.getStore_id();
			//Item高度适配
			LayoutParams layoutParams = holder.ll_item.getLayoutParams();
			layoutParams.height=(int) (getScreenWidth(activity)/3.5);
			holder.ll_item.setLayoutParams(layoutParams);
			//店铺名称栏适配
			LayoutParams Params = holder.ll_shopp.getLayoutParams();
			Params.height=(int) ((int) (getScreenWidth(activity)/3.5)/4);
			holder.ll_shopp.setLayoutParams(Params);
			
			holder.details.setText(info.getGoods_name());
			holder.price.setText("¥" + info.getGoods_price());
			holder.num.setText(info.getGoods_num());
			holder.tv_amountfcf1.setText("X" + info.getGoods_num());
			holder.tv_merchantsnames.setText(info.getStore_name() + "  >");
			// holder.details.setTextColor(activity.getResources().getColor(
			// R.color.detail_bgColor));
			bitmapUtils.display(holder.image, info.getGoods_image_url());
			holder.sub.setOnClickListener(onClickListener(info, holder));
			holder.add.setOnClickListener(onClickListener(info, holder));
			holder.delete.setOnClickListener(onClickListener(info, holder));
			holder.cb.setTag(position);
			holder.cb.setChecked(info.isChoosed());
			holder.cb.setOnCheckedChangeListener(new CheckBoxChangedListener(
					position));

			final Button cart_item_tv_delete = (Button) v
					.findViewById(R.id.cart_item_tv_delete);
			final LinearLayout ll_shuliang = (LinearLayout) v
					.findViewById(R.id.ll_shuliang);
			final TextView price = (TextView) v
					.findViewById(R.id.cart_item_tv_price);
			final TextView tv_editor = (TextView) v
					.findViewById(R.id.tv_editor);

			tv_editor.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!a) {
						tv_editor.setText(activity.getString(R.string.complete));
						ll_shuliang.setVisibility(View.VISIBLE);
						cart_item_tv_delete.setVisibility(View.VISIBLE);
						price.setVisibility(View.GONE);
						a = true;
					} else {
						tv_editor.setText(activity.getString(R.string.release_goods7));
						ll_shuliang.setVisibility(View.GONE);
						cart_item_tv_delete.setVisibility(View.GONE);
						price.setVisibility(View.VISIBLE);
						a = false;
					}
				}
			});

			holder.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity,
							ProductDetailsActivity.class);
					intent.putExtra("goods_id", info.getGoods_id());
					activity.startActivity(intent);
				}
			});
		}
		holder.enterstore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String store_id = cartList.get(position).getStore_id();
				Intent intent = new Intent(activity, StoreDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("store_id", store_id);
				intent.putExtras(bundle);
				activity.startActivity(intent);
			}
		});
		return v;
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < cartList.size(); i++) {
			cartList.get(i).setChoosed(false);
		}
	}

	Boolean a = false;

	private OnClickListener onClickListener(final CartListInfo info,
			final ViewHolder holder) {
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(v.getId()==R.id.cart_item_tv_delete){
					netRun.delCartGoods(info.getCart_id());
				}else if(v.getId()== R.id.add_sub_btn_sub){
					int goodsNum = Integer.valueOf(info.getGoods_num());
					if (goodsNum != 1) {
						goodsNum = goodsNum - 1;
						netRun.upCartGoodsNum(info.getCart_id(),
								String.valueOf(goodsNum), info, holder);
					}
				}else if(v.getId()==R.id.add_sub_btn_add){
					int goodsNum2 = Integer.valueOf(info.getGoods_num());
					goodsNum2 = goodsNum2 + 1;
					netRun.upCartGoodsNum(info.getCart_id(),
							String.valueOf(goodsNum2), info, holder);
				}



//				switch (v.getId()) {
//				case R.id.cart_item_iv_image:
//
//					break;
//				case R.id.cart_item_tv_delete:
//					netRun.delCartGoods(info.getCart_id());
//					break;
//				case R.id.add_sub_btn_sub:
//					int goodsNum = Integer.valueOf(info.getGoods_num());
//					if (goodsNum != 1) {
//						goodsNum = goodsNum - 1;
//						netRun.upCartGoodsNum(info.getCart_id(),
//								String.valueOf(goodsNum), info, holder);
//					}
//					break;
//				case R.id.add_sub_btn_add:
//					int goodsNum2 = Integer.valueOf(info.getGoods_num());
//					goodsNum2 = goodsNum2 + 1;
//					netRun.upCartGoodsNum(info.getCart_id(),
//							String.valueOf(goodsNum2), info, holder);
//					break;
//				}
			}
		};
		return onClickListener;
	}

	// CheckBox选择改变监听器
	private final class CheckBoxChangedListener implements
			OnCheckedChangeListener {
		int position;

		public CheckBoxChangedListener(int position) {
			this.position = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton cb, boolean flag) {
			cartList.get(position).setChoosed(flag);
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
	private boolean isAllSelected() {
		boolean flag = true;
		for (int i = 0; i < cartList.size(); i++) {
			if (cartList.get(i).isChoosed() != true) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}
}