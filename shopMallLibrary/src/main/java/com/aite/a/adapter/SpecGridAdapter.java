package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aiteshangcheng.a.R;
import com.aite.a.model.SpectItemModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
/**
 * 规格参数
 * @author xiaoyu
 *
 */
public class SpecGridAdapter extends BaseAdapter {
	
	private List<SpectItemModel> lists = new ArrayList<SpectItemModel>();
	private Context context;
	private static Map<Integer, Boolean> isChecked;
	public int positions;
	@SuppressLint("UseSparseArrays")
	public SpecGridAdapter(List<SpectItemModel> lists, Context context) {
		super();
		this.lists = lists;
		this.context = context;
		isChecked = new HashMap<Integer, Boolean>();
		for (int i = 0; i < lists.size(); i++) {
			if (i == 0) {
				isChecked.put(i, true);
				positions = i;
			}
			isChecked.put(i, false);//保存选中状态
			setIsChecked(isChecked);
		}
	}

	
	public static Map<Integer, Boolean> getIsChecked() {
		return isChecked;
	}


	public static void setIsChecked(Map<Integer, Boolean> isChecked) {
		SpecGridAdapter.isChecked = isChecked;
	}


	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "UseSparseArrays", "NewApi" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		SpectItemModel model = lists.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.spec_item, 
					parent, false);
			holder = new ViewHolder();
			holder.spec_btn = (Button) convertView.findViewById(R.id.spec_btn);
			holder.spec_btn.setText(model.getLabel());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.spec_btn.setOnClickListener(new CbOnclickListener(model, holder.spec_btn, position));
		if (getIsChecked().get(position)) {
			holder.spec_btn.setBackground(context.getResources().getDrawable(R.drawable.btn_style_circle_white_nopress));
		}else{
			holder.spec_btn.setBackground(context.getResources().getDrawable(R.drawable.btn_style_circle_black_nopress));
		}
		return convertView;
	}
	private class ViewHolder{
		Button spec_btn;
}
	private class CbOnclickListener implements OnClickListener{
		private SpectItemModel model;
		private Button spec_btn;
		private int position;
		

		public CbOnclickListener(SpectItemModel model, Button spec_btn, int position) {
			super();
			this.model = model;
			this.spec_btn = spec_btn;
			this.position = position;
		}


		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			isChecked.put(position, true);
			positions = position;
			for (int i = 0; i < lists.size(); i++) {
				if (i != position) {
					isChecked.put(i, false);
				}
			}
			notifyDataSetChanged();
		}
		
	}
}
