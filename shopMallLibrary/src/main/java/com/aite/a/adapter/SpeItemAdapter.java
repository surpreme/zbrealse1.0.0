package com.aite.a.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecValues.SpecValue;
import com.aite.a.view.custom.CustomAdapter;

public class SpeItemAdapter extends CustomAdapter {
	public Context context;
	public boolean isClick = false;
	public List<SpecValue> specValues;

	public SpeItemAdapter(Context context, List<SpecValue> specValues) {
		super();
		this.context = context;
		this.specValues = specValues;
	}

	@Override
	public int getCount() {
		return specValues.size();
	}

	@Override
	public SpecValue getItem(int position) {
		return specValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		public TextView spec;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder mViewHolder;
		TextView spec = null;
		if (view == null) {
			// mViewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.add_cart_spe_content_item, parent, false);
			spec = (TextView) view.findViewById(R.id.pop_choice_black);
			// view.setTag(mViewHolder);
		} else {
			// mViewHolder = (ViewHolder) view.getTag();
		}
		spec.setText(specValues.get(position).name);
		if (specValues.get(position).isSelect == true) {
			spec.setBackgroundResource(R.drawable.yuanjiao_choice);
		} else {
			spec.setBackgroundResource(R.drawable.yuanjiao);
		}
		return view;
	}

	
	
//	private boolean isSelectt = true;
//	private int lastpos = -1;

	public void setLosition(int position) {
		for (int i = 0; i < specValues.size(); i++) {
			if (position == i&&!specValues.get(position).isSelect) {
				specValues.get(i).setSelect(true);
			}else {
				specValues.get(i).setSelect(false);
			}
		}
//		for (int i = 0; i < specValues.size(); i++) {
//			if (position == i) {
//				if (isSelectt) {
//					specValues.get(i).setSelect(true);
//					isSelectt = false;
//				} else {
//					specValues.get(i).setSelect(false);
//					if (lastpos != position) {
//						specValues.get(i).setSelect(true);
//					}
//					isSelectt = true;
//				}
//			} else {
//				specValues.get(i).setSelect(false);
//			}
//			lastpos = position;
//		}
	}

}