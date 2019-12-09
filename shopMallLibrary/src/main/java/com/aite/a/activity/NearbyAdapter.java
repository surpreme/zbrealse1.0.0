package com.aite.a.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.model.NearbyList.NearbyStoreList;
import com.aite.a.view.custom.MyCircleImage;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

public class NearbyAdapter extends BaseAdapter {
	private List<NearbyStoreList> nearbyStoreLists = new ArrayList<NearbyStoreList>();
	private Context context;
	private BitmapUtils bitmapUtils;

	public NearbyAdapter(Context context) {
		super();
		this.context = context;
		bitmapUtils = new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		return nearbyStoreLists.size();
	}

	@Override
	public Object getItem(int position) {
		return nearbyStoreLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setNearbyStoreLists(List<NearbyStoreList> nearbyStoreLists) {
		this.nearbyStoreLists = nearbyStoreLists;
	}

	public class ViewHolder {
		public ViewHolder(View v) {
			store_img =  v.findViewById(R.id.nearby_store_img);
			store_neme = (TextView) v.findViewById(R.id.nearby_store_neme);
			store_distance = (TextView) v
					.findViewById(R.id.nearby_store_distance);
			store_zy = (TextView) v.findViewById(R.id.nearby_store_zy);
			store_area = (TextView) v.findViewById(R.id.nearby_store_area);
			ll_itemm=(LinearLayout) v.findViewById(R.id.ll_itemm);
		}

		public MyCircleImage store_img;
//		public CircleImageView store_img;
		public TextView store_neme;
		public TextView store_distance;
		public TextView store_zy;
		public TextView store_area;
		public LinearLayout ll_itemm;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder = null;
		if (v == null) {
			v = LayoutInflater.from(context).inflate(
					R.layout.nearby_store_item, parent, false);
			holder = new ViewHolder(v);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		final NearbyStoreList list = nearbyStoreLists.get(position);
		String distance = null;
		double distance2 = Double.valueOf(list.distance);
		if (distance2 > 1000) {
			distance = String.valueOf((int) (distance2 / 1000))
					+ APPSingleton.getContext().getString(R.string.km)
							.toString();
		} else {
			distance = (String) ((int) distance2 + APPSingleton.getContext()
					.getString(R.string.m).toString());
		}
		
		if (list.store_label != null && !list.store_label.equals("")) {
//			bitmapUtils.display(holder.store_img, list.store_label);
			Glide.with(v).load(list.store_label).into(holder.store_img);
		} else {
			holder.store_img.setImageResource(R.drawable.zanwushuju);
//			holder.store_img.setBackgroundResource(R.drawable.zanwushuju);
		}
		LayoutParams layoutParams = holder.ll_itemm.getLayoutParams();
		layoutParams.height=(int) (getScreenWidth(context)/3.5);
		holder.ll_itemm.setLayoutParams(layoutParams);
		LayoutParams Params = holder.store_img.getLayoutParams();
		Params.height=(int) (getScreenWidth(context)/4.5);
		Params.width=(int) (getScreenWidth(context)/4.5);
		holder.store_img.setLayoutParams(Params);
		holder.store_neme.setText(list.store_name);
		holder.store_distance.setText(distance);
		holder.store_zy.setText(list.store_zy);
		holder.store_area.setText(list.area_info);
		holder.store_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, StoreDetailsActivity.class);
				intent.putExtra("store_id", list.store_id);
				context.startActivity(intent);
			}
		});
		holder.store_neme.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, StoreDetailsActivity.class);
				intent.putExtra("store_id", list.store_id);
				context.startActivity(intent);
			}
		});
		return v;
	}
	 public static int getScreenWidth(Context context) {  
	        WindowManager manager = (WindowManager) context  
	                .getSystemService(Context.WINDOW_SERVICE);  
	        Display display = manager.getDefaultDisplay();  
	        return display.getWidth();  
	    }  
}
