package com.aite.a.adapter;

import java.util.List;

import com.aite.a.activity.WebActivity;
import com.aite.a.model.SpecialAdList;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 广告适配
 * 
 * @author Administrator
 *
 */
public class JD_AdvertisingAdapter extends BaseAdapter {

	private Context mactivity;
	private List<SpecialAdList> List;
	private BitmapUtils bitmapUtils;

	public JD_AdvertisingAdapter(Context mactivity, List<SpecialAdList> List) {
		this.mactivity = mactivity;
		this.List = List;
		bitmapUtils = new BitmapUtils(mactivity);
	}

	@Override
	public int getCount() {
		return List.size();
	}

	@Override
	public Object getItem(int position) {
		return List == null ? null : List.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mactivity, R.layout.item_jd_advertising,
					null);
			new ViewHolder(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.tv_txt.setText(List.get(position).adv_title);
		bitmapUtils.display(holder.iv_img, List.get(position).adv_img);
		holder.iv_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mactivity, WebActivity.class);
				intent.putExtra("url", List.get(position).adv_url);
				intent.putExtra("title", List.get(position).adv_title);
				mactivity.startActivity(intent);
			}
		});
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_img.getLayoutParams();
		layoutParams.height=(int) (getw()*0.4);
		holder.iv_img.setLayoutParams(layoutParams);
		return convertView;
	}

	class ViewHolder {
		TextView tv_txt;
		ImageView iv_img;

		public ViewHolder(View convertView) {
			tv_txt = (TextView) convertView.findViewById(R.id.tv_txt);
			iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
			convertView.setTag(this);
		}
	}
	
	private int getw(){
		WindowManager wm = (WindowManager) mactivity.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}
}
