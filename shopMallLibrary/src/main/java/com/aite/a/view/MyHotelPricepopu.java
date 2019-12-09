package com.aite.a.view;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aite.a.model.HotelListInfo.datas.price_list;
import com.aite.a.model.HotelListInfo.datas.stars_list;
import com.aiteshangcheng.a.R;

/**
 * 价格星级筛选
 * 
 * @author Administrator
 *
 */
public class MyHotelPricepopu extends PopupWindow implements OnClickListener,
		TextWatcher {
	private Activity mActivity = null;
	private MyGridView mv_price, mv_level;
	private EditText ed_startprice, ed_endprice;
	private TextView tv_determine;
	private Padapter padapter;
	private Xadapter xadapter;

	public MyHotelPricepopu(Activity activity, List<price_list> price_list,
			List<stars_list> stars_list) {
		mActivity = activity;
		WindowManager wm = mActivity.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		// setHeight((int) (height * 0.6));
		setHeight(LayoutParams.WRAP_CONTENT);
		View view = View.inflate(mActivity, R.layout.popu_hotelprice, null);
		mv_price = (MyGridView) view.findViewById(R.id.mv_price);
		mv_level = (MyGridView) view.findViewById(R.id.mv_level);
		ed_startprice = (EditText) view.findViewById(R.id.ed_startprice);
		ed_endprice = (EditText) view.findViewById(R.id.ed_endprice);
		tv_determine = (TextView) view.findViewById(R.id.tv_determine);
		// 设置SelectPicPopupWindow的View
		setContentView(view);
		// 设置点击视图之外的地方是否取消当前的PopupWindow
		setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		tv_determine.setOnClickListener(this);
		// 价格
		if (isadd(price_list)) {
			com.aite.a.model.HotelListInfo.datas.price_list price_list2 = new com.aite.a.model.HotelListInfo.datas.price_list();
			price_list2.ischoose = true;
			price_list2.range_id = "0";
			price_list2.range_name = mActivity.getString(R.string.near_reminder13);
			price_list2.range_start = "0";
			price_list2.range_end = "0";
			price_list.add(0, price_list2);
		}
		padapter = new Padapter(price_list);
		mv_price.setAdapter(padapter);
		// 星级
		if (isadd2(stars_list)) {
			com.aite.a.model.HotelListInfo.datas.stars_list stars_list2 = new com.aite.a.model.HotelListInfo.datas.stars_list();
			stars_list2.id = "0";
			stars_list2.name = mActivity.getString(R.string.near_reminder13);
			stars_list2.state = "0";
			stars_list2.type = "0";
			stars_list2.sort = "0";
			stars_list2.imgurl = "0";
			stars_list2.ischoose = true;
			stars_list.add(0, stars_list2);
		}
		xadapter = new Xadapter(stars_list);
		mv_level.setAdapter(xadapter);
		// 价格输入
		ed_startprice.addTextChangedListener(this);
		ed_endprice.addTextChangedListener(this);

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

	/**
	 * 是否需要添加
	 * 
	 * @return
	 */
	private boolean isadd(List<price_list> price_list) {
		for (int i = 0; i < price_list.size(); i++) {
			if (price_list.get(i).range_name.equals(mActivity.getString(R.string.near_reminder13))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否需要添加
	 * 
	 * @return
	 */
	private boolean isadd2(List<stars_list> stars_list) {
		for (int i = 0; i < stars_list.size(); i++) {
			if (stars_list.get(i).name.equals(mActivity.getString(R.string.near_reminder13))) {
				return false;
			}
		}
		return true;
	}

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

	/**
	 * 价格适配
	 * 
	 * @author Administrator
	 *
	 */
	private class Padapter extends BaseAdapter {
		List<price_list> price;

		public Padapter(List<price_list> price) {
			this.price = price;
		}

		/**
		 * 获取选中id
		 * 
		 * @return
		 */
		private String getchooseid() {
			for (int i = 0; i < price.size(); i++) {
				if (price.get(i).ischoose) {
					return price.get(i).range_start + "-"
							+ price.get(i).range_end;
				}
			}
			return "";
		}

		/**
		 * 修改选中
		 */
		private void setchoose(int id) {
			for (int i = 0; i < price.size(); i++) {
				price.get(i).ischoose = false;
			}
			if (id != -1) {
				ed_startprice.setText("");
				ed_endprice.setText("");
				price.get(id).ischoose = true;
			}
			padapter.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return price.size();
		}

		@Override
		public Object getItem(int position) {
			return price == null ? null : price.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity,
						R.layout.item_hotel_price, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			price_list price_list2 = price.get(position);
			holder.tv_item.setText(price_list2.range_name);
			if (price_list2.ischoose) {
				holder.tv_item.setTextColor(Color.WHITE);
				holder.tv_item.setBackgroundColor(0xff23A1E2);
			} else {
				holder.tv_item.setTextColor(0xff808080);
				holder.tv_item.setBackgroundColor(Color.WHITE);
			}
			holder.tv_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setchoose(position);
				}
			});

			return convertView;
		}

		class ViewHodler {
			TextView tv_item;

			public ViewHodler(View convertView) {
				tv_item = (TextView) convertView.findViewById(R.id.tv_item);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 星级适配
	 * 
	 * @author Administrator
	 *
	 */
	private class Xadapter extends BaseAdapter {
		List<stars_list> stars;

		public Xadapter(List<stars_list> stars) {
			this.stars = stars;
		}

		/**
		 * 获取选中id
		 * 
		 * @return
		 */
		private String getchooseid() {
			for (int i = 0; i < stars.size(); i++) {
				if (stars.get(i).ischoose) {
					return stars.get(i).id;
				}
			}
			return "";
		}

		@Override
		public int getCount() {
			return stars.size();
		}

		@Override
		public Object getItem(int position) {
			return stars == null ? null : stars.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity,
						R.layout.item_hotel_price, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			stars_list stars_list2 = stars.get(position);
			holder.tv_item.setText(stars_list2.name);
			if (stars_list2.ischoose) {
				holder.tv_item.setTextColor(Color.WHITE);
				holder.tv_item.setBackgroundColor(0xff23A1E2);
			} else {
				holder.tv_item.setTextColor(0xff808080);
				holder.tv_item.setBackgroundColor(Color.WHITE);
			}
			holder.tv_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < stars.size(); i++) {
						stars.get(i).ischoose = false;
					}
					stars.get(position).ischoose = true;
					xadapter.notifyDataSetChanged();
				}
			});
			return convertView;
		}

		class ViewHodler {
			TextView tv_item;

			public ViewHodler(View convertView) {
				tv_item = (TextView) convertView.findViewById(R.id.tv_item);
				convertView.setTag(this);
			}
		}
	}

	private data mdata = null;

	public void setdata(data p) {
		mdata = p;
	}

	public interface data {
		void onItemClick(String price, String level);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_determine:
//			// 确定
//			String str="";
//			if (!padapter.getchooseid().equals("")) {
//				str=padapter.getchooseid();
//			}else {
//				String string = ed_startprice.getText().toString();
//				String string2 = ed_endprice.getText().toString();
//				if (TextUtils.isEmpty(string)||TextUtils.isEmpty(string2)) {
//					str="0";
//				}else {
//					int parseInt = Integer.parseInt(string);
//					int parseInt2 = Integer.parseInt(string2);
//					if (parseInt<parseInt2) {
//						str=string+"-"+string2;
//					}else{
//						str=string2+"-"+string;
//					}
//				}
//			}
//			mdata.onItemClick(str, xadapter.getchooseid());
//			dismiss();
//			break;
//		}
		if(v.getId()==R.id.tv_determine){
			// 确定
			String str="";
			if (!padapter.getchooseid().equals("")) {
				str=padapter.getchooseid();
			}else {
				String string = ed_startprice.getText().toString();
				String string2 = ed_endprice.getText().toString();
				if (TextUtils.isEmpty(string)||TextUtils.isEmpty(string2)) {
					str="0";
				}else {
					int parseInt = Integer.parseInt(string);
					int parseInt2 = Integer.parseInt(string2);
					if (parseInt<parseInt2) {
						str=string+"-"+string2;
					}else{
						str=string2+"-"+string;
					}
				}
			}
			mdata.onItemClick(str, xadapter.getchooseid());
			dismiss();
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		padapter.setchoose(-1);
	}
}
