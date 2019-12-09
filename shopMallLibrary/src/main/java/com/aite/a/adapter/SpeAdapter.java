package com.aite.a.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aiteshangcheng.a.R;
import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecList;
import com.aite.a.model.GoodsDetailsInfo.Spec.SpecValues;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.custom.CustomListView;
import com.aite.a.view.custom.CustomOnItemClickListener;

public class SpeAdapter extends BaseAdapter {
	private Context context;
	public List<SpecValues> spes;
	private Handler handler;
	private GoodsDetailsInfo detailsInfo;

	public SpeAdapter(Context context, Handler handler) {
		super();
		this.context = context;
		this.handler = handler;
		this.spes = new ArrayList<SpecValues>();
	}

	@Override
	public int getCount() {
		return spes.size();
	}

	@Override
	public SpecValues getItem(int position) {
		return spes.get(position);
	}
	
	/**
	 * 刷新适配器
	 */
	public void flushAdapter() {
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSpes(GoodsDetailsInfo detailsInfo) {
		this.detailsInfo = detailsInfo;
		this.spes = detailsInfo.spec.spec_value;
		
	}

	public static class ViewHolder {
		public TextView spe_name;
		public CustomListView spe_list;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder mViewHolder;
		if (view == null) {
			mViewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.add_cart_spe_item, parent, false);
			mViewHolder.spe_name = (TextView) view.findViewById(R.id.spe_name);
			mViewHolder.spe_list = (CustomListView) view.findViewById(R.id.spe_grid);
			view.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) view.getTag();
		}
		final SpecValues spe = spes.get(position);
		mViewHolder.spe_name.setText(spe.name);
		final SpeItemAdapter adapter = new SpeItemAdapter(context, spe.spec_value);
		mViewHolder.spe_list.setAdapter(adapter);
		mViewHolder.spe_list.setDividerWidth(20);
		mViewHolder.spe_list.setDividerHeight(20);
		mViewHolder.spe_list.setOnItemClickListener(new CustomOnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (spe.spec_value.get(arg2).id != null) {
					if (isEquals2(detailsInfo.spec.spec_list, spe.spec_value.get(arg2).id) == false) {
						CommonTools.showShortToast(context, APPSingleton.getContext().getString(R.string.specification_not_exist).toString());
						return;
					}
				}
				adapter.setLosition(arg2);
				adapter.notifyDataSetChanged();
				handler.sendEmptyMessage(0);
			}
		});

		return view;
	}

	/**
	 * 判断规格列表（spec_list）是否包含所选择 的规格
	 * 
	 * @param spec_list
	 * @param id
	 * @return
	 */
	public Boolean isEquals2(List<SpecList> spec_list, String id) {
		for (SpecList list : spec_list) {
			if (list.id.contains(id)) {
				return true;
			}
		}
		return false;
	}

}