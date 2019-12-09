package com.aite.a.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.model.HotelListInfo1;
import com.aiteshangcheng.a.R;

/**
 * 价格星级筛选
 * 
 * @author Administrator
 *
 */
public class MyHotelSortpopu extends PopupWindow implements OnClickListener {
	private Activity mActivity = null;
	private ListView lv_sort;
	private MyAdapter myAdapter;

	public MyHotelSortpopu(Activity activity) {
		mActivity = activity;
		WindowManager wm = mActivity.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
//		setHeight((int) (height * 0.6));
		 setHeight(LayoutParams.WRAP_CONTENT);
		View view = View.inflate(mActivity, R.layout.popu_hotelsort, null);
		lv_sort = (ListView) view.findViewById(R.id.lv_sort);
		// 设置SelectPicPopupWindow的View
		setContentView(view);
		// 设置点击视图之外的地方是否取消当前的PopupWindow
		setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);

		List<HotelListInfo1> list = new ArrayList<HotelListInfo1>();
		list.add(new HotelListInfo1(mActivity.getString(R.string.near_reminder14), true));
		list.add(new HotelListInfo1(mActivity.getString(R.string.near_reminder15), false));
		list.add(new HotelListInfo1(mActivity.getString(R.string.near_reminder16), false));
		list.add(new HotelListInfo1(mActivity.getString(R.string.near_reminder17), false));
		list.add(new HotelListInfo1(mActivity.getString(R.string.near_reminder18), false));
		myAdapter=new MyAdapter(list);
		lv_sort.setAdapter(myAdapter);
		
		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = mActivity.getWindow()
						.getAttributes();
				lp.alpha = 1f;
				mActivity.getWindow().setAttributes(lp);
			}
		});
		// 设置PopupWindow弹出窗体动画效果
		setAnimationStyle(R.style.AnimBottomPopup);

	}

	Handler h = new Handler() {
		// 显示玩popup后，改变背景透明度
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				WindowManager.LayoutParams lp = mActivity.getWindow()
						.getAttributes();
				lp.alpha = 0.8f;
				mActivity.getWindow().setAttributes(lp);
				break;
			}
		};
	};

	private void showEvent() {
		h.sendEmptyMessageDelayed(0, 500);
	}

	@Override
	public void showAsDropDown(View anchor) {
		super.showAsDropDown(anchor);
		showEvent();
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		super.showAsDropDown(anchor, xoff, yoff);
		showEvent();
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		showEvent();
	}

	private class MyAdapter extends BaseAdapter {
		List<HotelListInfo1> list;

		public MyAdapter(List<HotelListInfo1> list) {
			this.list = list;
		}

		public void setchoose(int id) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).ischosse = false;
			}
			list.get(id).ischosse = true;
			notifyDataSetChanged();
			mdata.onItemClick(myAdapter.getchoose());
			dismiss();
		}

		public List<String> getchoose() {
			List<String> data = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).ischosse) {
					switch (i) {
					case 0:
						data.add("0");
						data.add("0");
						break;
					case 1:
						data.add("1");
						data.add("2");
						break;
					case 2:
						data.add("1");
						data.add("1");
						break;
					case 3:
						data.add("2");
						data.add("1");
						break;
					case 4:
						data.add("3");
						data.add("2");
						break;
					}
				}
			}
			return data;
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
				convertView = View.inflate(mActivity, R.layout.item_hotelsort,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			HotelListInfo1 hotelListInfo1 = list.get(position);
			holder.tv_name.setText(hotelListInfo1.name);
			holder.cb_choose.setChecked(hotelListInfo1.ischosse);
			holder.cb_choose.setClickable(false);
			holder.cb_choose.setEnabled(false);
			holder.rl_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 选中
					setchoose(position);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_name;
			CheckBox cb_choose;
			RelativeLayout rl_item;

			public ViewHolder(View convertView) {
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				cb_choose = (CheckBox) convertView.findViewById(R.id.cb_choose);
				rl_item = (RelativeLayout) convertView
						.findViewById(R.id.rl_item);
				convertView.setTag(this);
			}
		}
	}

	private data mdata = null;

	public void setdata(data p) {
		mdata = p;
	}

	public interface data {
		void onItemClick(List<String> data);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_determine:
//			// 确定
//			 mdata.onItemClick(myAdapter.getchoose());
//			dismiss();
//			break;
//		}
		if(v.getId()==R.id.tv_determine){
			// 确定
			mdata.onItemClick(myAdapter.getchoose());
			dismiss();
		}
	}

}
