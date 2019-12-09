package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;

import com.aiteshangcheng.a.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GoodsImgyAdapter extends BaseAdapter {
	private Activity activity;
	public List<GoodsImgList> imgLists;
	public WindowManager wm;

	public GoodsImgyAdapter(Activity activity) {
		super();
		this.activity = activity;
		imgLists = new ArrayList<GoodsImgList>();
		wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
	}

	@Override
	public int getCount() {
		return imgLists.size();
	}

	@Override
	public Object getItem(int position) {
		return imgLists.get(position);
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
					R.layout.griditem_addpic, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int gridItemWidth = wm.getDefaultDisplay().getWidth() / 3 - 50;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				gridItemWidth, gridItemWidth);
		holder.imageView1.setLayoutParams(params);
		if (imgLists.size() > 0) {
			GoodsImgList goodsImgList = imgLists.get(position);
			holder.imageView1.setImageBitmap(goodsImgList.bmp);
		}
		return convertView;
	}

	public void addImgData(Bitmap bmp, String path) {
		GoodsImgList imgList = new GoodsImgList(bmp, path);
		imgLists.add(imgList);
	}

	private class ViewHolder {
		ImageView imageView1;

		public ViewHolder(View v) {
			imageView1 = (ImageView) v.findViewById(R.id.imageView1);
		}

	}
}
