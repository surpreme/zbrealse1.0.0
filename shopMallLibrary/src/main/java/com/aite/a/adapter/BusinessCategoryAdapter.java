
package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;

import com.aiteshangcheng.a.R;
import com.aite.a.model.ManageCategoryList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BusinessCategoryAdapter extends BaseAdapter {
	private Activity activity;
	public List<ManageCategoryList> categoryLists;

	public BusinessCategoryAdapter(Activity activity) {
		super();
		this.activity = activity;
		categoryLists = new ArrayList<ManageCategoryList>();
	}

	@Override
	public int getCount() {
		return categoryLists.size();
	}

	@Override
	public Object getItem(int position) {
		return categoryLists.get(position);
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
					R.layout.business_category_item, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (categoryLists.size() > 0) {
			ManageCategoryList manageCategoryList = categoryLists.get(position);
			holder.one_category.setText(manageCategoryList.one_category);
			holder.two_category.setText(manageCategoryList.two_category);
			holder.three_category.setText(manageCategoryList.three_category);
			holder.category_del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					categoryLists.remove(position);
					notifyDataSetChanged();
				}
			});
		}
		return convertView;
	}

	public void setCategoryLists(List<ManageCategoryList> categoryLists) {
		this.categoryLists = categoryLists;
	}

	private class ViewHolder {
		TextView one_category;
		TextView two_category;
		TextView three_category;
		Button category_del;

		public ViewHolder(View v) {
			one_category = (TextView) v.findViewById(R.id.one_category);
			two_category = (TextView) v.findViewById(R.id.two_category);
			three_category = (TextView) v.findViewById(R.id.three_category);
			category_del = (Button) v.findViewById(R.id.category_del);
		}

	}
}
