package com.aite.a.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.activity.ComplaintsApplyForActivity;
import com.aite.a.activity.OrderRefundActivity;
import com.aite.a.activity.ProductDetailsActivity;
import com.aiteshangcheng.a.R;
import com.aite.a.model.OrderGroupList.OrderList.ExtendOrderGoods;
import com.lidroid.xutils.BitmapUtils;

/**
 * 订单适配器
 * 
 * @author xiaoyu
 * 
 */
public class OrderGoodsListAdapter extends BaseAdapter{
	private Context mContext;
	private List<ExtendOrderGoods> goodList2s;
	private BitmapUtils bitmapUtils;
	private String if_complain;

	public OrderGoodsListAdapter(Context mContext, List<ExtendOrderGoods> extend_order_goods,String if_complain) {
		super();
		this.mContext = mContext;
		this.goodList2s = extend_order_goods;
		this.if_complain = if_complain;
		bitmapUtils = new BitmapUtils(mContext);
	}

	@Override
	public int getCount() {
		return goodList2s.size();
	}

	@Override
	public Object getItem(int position) {
		return goodList2s.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.order_goods_list_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ExtendOrderGoods goodList = goodList2s.get(position);
		holder.details.setText(goodList.goods_name);
		bitmapUtils.display(holder.imageView, goodList.goods_image_url);
		holder.num.setText(APPSingleton.getContext().getString(R.string.counts) + ":x" + goodList.goods_num);
		holder.factPay.setText(APPSingleton.getContext().getString(R.string.price).toString() + goodList.goods_price);
		if (goodList.refund.equals("1")){
			holder.tv_refundreturn.setVisibility(View.VISIBLE);
		}else{
			holder.tv_refundreturn.setVisibility(View.GONE);
		}
		if (Boolean.valueOf(if_complain) == true){
			holder.tv_complaint.setVisibility(View.VISIBLE);
		}else{
			holder.tv_complaint.setVisibility(View.GONE);
		}
		holder.rl_intent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ProductDetailsActivity.class);
				intent.putExtra("goods_id", goodList.goods_id);
				mContext.startActivity(intent);
				((Activity) mContext).overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
//				Intent intent = new Intent(mContext,OrderDataActivity.class);
//				mContext.startActivity(intent);
			}
		});
		holder.tv_refundreturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intenttk2 = new Intent(mContext, OrderRefundActivity.class);
				intenttk2.putExtra("order_id", goodList.order_id);
				intenttk2.putExtra("goods_id", goodList.rec_id);
				mContext.startActivity(intenttk2);
			}
		});
		holder.tv_complaint.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// 投诉
				Intent intent2 = new Intent(mContext,ComplaintsApplyForActivity.class);
				intent2.putExtra("goods_id", goodList.rec_id);
				intent2.putExtra("order_id", goodList.order_id);
				mContext.startActivity(intent2);
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
		TextView details, num, factPay,tv_refundreturn,tv_complaint;
		RelativeLayout rl_intent;

		public ViewHolder(View v) {
			super();
			imageView = (ImageView) v.findViewById(R.id.order_item_iv_image);
			details = (TextView) v.findViewById(R.id.order_item_tv_details);
			num = (TextView) v.findViewById(R.id.order_item_tv_num);
			factPay = (TextView) v.findViewById(R.id.order_item_tv_price);
			tv_refundreturn = (TextView) v.findViewById(R.id.tv_refundreturn);
			tv_complaint = (TextView) v.findViewById(R.id.tv_complaint);
			rl_intent = (RelativeLayout) v.findViewById(R.id.order_list_rl_);
		}
	}
}
