package com.aite.a.adapter;

import java.util.ArrayList;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aiteshangcheng.a.R;
import com.aite.a.model.BuySteOneInfo.StoreCartlList.GoodsList2;
import com.aite.a.view.MyListView;
import com.lidroid.xutils.BitmapUtils;

public class SureOrderAdapter extends BaseAdapter implements OnClickListener {
	private Context mContext;
	private List<GoodsList2> list = new ArrayList<GoodsList2>();
	private Intent intent;
	private BitmapUtils bitmapUtils;

	public SureOrderAdapter(Context mContext, List<GoodsList2> goods_list) {
		super();
		this.mContext = mContext;
		this.list = goods_list;
		bitmapUtils = new BitmapUtils(mContext);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public GoodsList2 getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.sure_order_item, parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.sure_order_iv_image);
			holder.priceView = (TextView) convertView
					.findViewById(R.id.sure_order_tv_price);
			holder.goodsDetailsView = (TextView) convertView
					.findViewById(R.id.sure_order_tv_goodsdetails);
			holder.goodsNumView = (TextView) convertView
					.findViewById(R.id.sure_order_tv_goods_num);
			holder.rlGoodsView = (RelativeLayout) convertView
					.findViewById(R.id.sure_order_rl_goods);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		GoodsList2 goodList = list.get(position);
		bitmapUtils.display(holder.imageView, goodList.goods_image_url);
		holder.priceView.setText("￥" + goodList.goods_price);
		holder.goodsDetailsView.setText(goodList.goods_name);
		holder.goodsNumView.setText("x" + goodList.goods_num);
		return convertView;
	}
	private class ViewHolder {
		ImageView imageView;
		TextView priceView, goodsDetailsView;
		TextView goodsNumView;
		RelativeLayout rlGoodsView;
		LinearLayout llItem;
		MyListView giftList;
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.sure_order_rl_goods:
//			intentOut();
//			break;
//		}
		if(v.getId()==R.id.sure_order_rl_goods){
			intentOut();
		}
	}


	/**
	 * 跳转
	 */
	private void intentOut() {
		intent = new Intent(mContext, ProductDetailsActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).overridePendingTransition(R.anim.tran_in,
				R.anim.tran_out);
	}
}
