package com.aite.a.adapter;

import java.util.List;

import com.aite.a.activity.GoodsListActivity;
import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.model.CategoryList;
import com.aite.a.model.GoodList2;
import com.aite.a.model.GoodList2.goods_list;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
/**
 * 首页楼层
 * @author Administrator
 *
 */
public class JD_HomeGoodsAdapter extends BaseAdapter {

	private int[] louceng = new int[] { R.drawable.fc_1, R.drawable.fc_2,
			R.drawable.fc_3, R.drawable.fc_4, R.drawable.fc_5, R.drawable.fc_6, };
	private int[] jiantou = new int[] { R.drawable.fc_r_1, R.drawable.fc_r_2,
			R.drawable.fc_r_3, R.drawable.fc_r_4, R.drawable.fc_r_5,
			R.drawable.fc_r_6, };
	List<CategoryList> categoryOne;
	List<goods_list> list = null;
	List<GoodList2> goodlist2;
	private Context context;
	private MianGoodsAdapder2 myadapder2;
	private BitmapUtils bitmapUtils;

	public JD_HomeGoodsAdapter(Context context, List<CategoryList> categoryOne,
			List<GoodList2> goodlist2) {
		this.context = context;
		this.categoryOne = categoryOne;
		this.goodlist2 = goodlist2;
		bitmapUtils = new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		return categoryOne.size();
	}

	@Override
	public Object getItem(int position) {
		return categoryOne == null ? null : categoryOne.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 刷新适配器
	 */
	public void flushAdapter() {
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.maingoodsitem, null);
			new ViewHolder(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
			for (int i = 0; i < goodlist2.size(); i++) {
				if (categoryOne.get(position).getGc_name()
						.equals(goodlist2.get(i).gc_name)) {
					list = null;
					myadapder2 = null;
					list = goodlist2.get(i).goods_list;
					if (list.size() == 0) {
						continue;
					}
					holder.listtv_main_iv_nf.setText((position + 1) + "F");
					holder.listrl_main_iv_nf
							.setBackgroundResource(louceng[(position % 6)]);
					holder.listmain_iv_5f_right
							.setImageResource(jiantou[(position % 6)]);
					holder.listmain_tv_5f_name.setText(categoryOne.get(position)
							.getGc_name());
					myadapder2 = new MianGoodsAdapder2(list);
					holder.lv_hot_five_floor_gr.setAdapter(myadapder2);
					list = null;
					myadapder2 = null;
				}
			}
			holder.listmain_iv_5f_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							GoodsListActivity.class);
					intent.putExtra("gc_id", categoryOne.get(position).getGc_id());
					context.startActivity(intent);
				}
			});

		return convertView;
	}

	class ViewHolder {
		RelativeLayout listrl_main_iv_nf;
		TextView listtv_main_iv_nf, listmain_tv_5f_name;
		MyGridView lv_hot_five_floor_gr;
		ImageView listmain_iv_5f_right;
		LinearLayout ll_goodsitem;

		public ViewHolder(View convertView) {
			listrl_main_iv_nf = (RelativeLayout) convertView
					.findViewById(R.id.listrl_main_iv_nf);
			listtv_main_iv_nf = (TextView) convertView
					.findViewById(R.id.listtv_main_iv_nf);
			listmain_iv_5f_right = (ImageView) convertView
					.findViewById(R.id.listmain_iv_5f_right);
			listmain_tv_5f_name = (TextView) convertView
					.findViewById(R.id.listmain_tv_5f_name);
			lv_hot_five_floor_gr = (MyGridView) convertView
					.findViewById(R.id.lv_hot_five_floor_gr);
			ll_goodsitem = (LinearLayout) convertView
					.findViewById(R.id.ll_goodsitem);
			convertView.setTag(this);
		}
	}

	public class MianGoodsAdapder2 extends BaseAdapter {
		List<goods_list> list = null;

		public MianGoodsAdapder2(List<goods_list> list) {
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.good_recommend,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
//			bitmapUtils.display(holder.goods_image, list.get(position)
//					.getGoods_image_url());
			Glide.with(context).load(list.get(position).getGoods_image_url()).into(holder.goods_image);
			// 商品适配
			LayoutParams layoutParams = (LayoutParams) holder.goods_image
					.getLayoutParams();
			layoutParams.height = (int) ((getw() / 2) * 0.7);
			holder.goods_image.setLayoutParams(layoutParams);
			holder.goods_name.setText(list.get(position).getGoods_name());
			holder.goods_price.setText(list.get(position).getGoods_price());
			holder.goods_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							ProductDetailsActivity.class);
					intent.putExtra("goods_id", list.get(position)
							.getGoods_id());
					context.startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView goods_image;
			TextView goods_name, goods_price;

			public ViewHolder(View convertView) {
				goods_image = (ImageView) convertView
						.findViewById(R.id.goods_image);
				goods_name = (TextView) convertView
						.findViewById(R.id.goods_name);
				goods_price = (TextView) convertView
						.findViewById(R.id.goods_price);
				convertView.setTag(this);
			}
		}
	}

	private int getw() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}

}
