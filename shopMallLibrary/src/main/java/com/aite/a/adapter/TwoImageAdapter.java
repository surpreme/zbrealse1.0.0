package com.aite.a.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aite.a.activity.WebActivity;
import com.aite.a.model.CustomHomeInfo;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class TwoImageAdapter extends BaseAdapter {
	public List<CustomHomeInfo.home3.item> item;
	private Context mcontext;
	private int getw;
	public TwoImageAdapter(List<CustomHomeInfo.home3.item> item,Context mcontext,int getw) {
		this.item = item;
		this.mcontext=mcontext;
		this.getw=getw;
	}

	@Override
	public int getCount() {
		return item == null ? 0 : item.size();
	}

	@Override
	public Object getItem(int position) {
		return item == null ? null : item.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mcontext, R.layout.item_twoimage,
					null);
			new ViewHodler(convertView);
		}
		final ViewHodler holder = (ViewHodler) convertView.getTag();
		final CustomHomeInfo.home3.item item2 = item
				.get(position);
		// 获取图片真正的宽高
		Glide.with(mcontext).asBitmap().load(item2.image)// 强制Glide返回一个Bitmap对象
				.into(new SimpleTarget<Bitmap>() {

					@Override
					public void onResourceReady(Bitmap bitmap,
							Transition<? super Bitmap> arg1) {
						float width = bitmap.getWidth();
						float height = bitmap.getHeight();
						float bl = height / width;
						float he = (getw / 2) * bl;
						LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_img7
								.getLayoutParams();
						layoutParams.width = getw / 2;
						layoutParams.height = (int) (he);
						holder.iv_img7.setLayoutParams(layoutParams);
					}
				});
		Glide.with(mcontext).load(item2.image).into(holder.iv_img7);
		holder.iv_img7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent6 = new Intent(mcontext, WebActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("url", item2.data);
				bundle.putString("title", mcontext.getString(R.string.app_name));
				intent6.putExtras(bundle);
				mcontext.startActivity(intent6);
			}
		});
		return convertView;
	}

	class ViewHodler {
		ImageView iv_img7;

		public ViewHodler(View convertView) {
			iv_img7 = (ImageView) convertView.findViewById(R.id.iv_img7);
			convertView.setTag(this);
		}
	}
}