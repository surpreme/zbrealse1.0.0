package com.aite.a.view;

import java.util.List;

import com.aite.a.model.ConsultingTypeinfo;
import com.aiteshangcheng.a.R;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MyConsultingpopu extends PopupWindow implements OnClickListener {
	private Activity mActivity = null;
	private ListView lv_menu;
	private List<ConsultingTypeinfo> consultingTypeinfo;
	private MenuAdapter menuAdapter;

	public MyConsultingpopu(Activity activity,
			List<ConsultingTypeinfo> consultingTypeinfo) {
		mActivity = activity;
		this.consultingTypeinfo = consultingTypeinfo;
		
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		setHeight(LayoutParams.WRAP_CONTENT);
		View view = View.inflate(mActivity, R.layout.popu_textmenu, null);
		lv_menu = (ListView) view.findViewById(R.id.lv_menu);
		
		// 设置SelectPicPopupWindow的View
		setContentView(view);
		// 设置点击视图之外的地方是否取消当前的PopupWindow
		setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		
		menuAdapter = new MenuAdapter(consultingTypeinfo);
		lv_menu.setAdapter(menuAdapter);
		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				android.view.WindowManager.LayoutParams lp = mActivity
						.getWindow().getAttributes();
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
				android.view.WindowManager.LayoutParams lp = mActivity
						.getWindow().getAttributes();
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

	private class MenuAdapter extends BaseAdapter {
		List<ConsultingTypeinfo> consultingTypeinfo;

		public MenuAdapter(List<ConsultingTypeinfo> consultingTypeinfo) {
			this.consultingTypeinfo = consultingTypeinfo;
		}

		@Override
		public int getCount() {
			return consultingTypeinfo.size();
		}

		@Override
		public Object getItem(int position) {
			return consultingTypeinfo == null ? null : consultingTypeinfo
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.popu_textitem,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			
			holder.tv_popu1.setText(consultingTypeinfo.get(position).mct_name);
			holder.tv_popu1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mhuidiao.onItemClick(consultingTypeinfo.get(position));
					dismiss();
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_popu1;
			public ViewHolder(View convertView) {
				tv_popu1 = (TextView) convertView.findViewById(R.id.tv_popu1);
				convertView.setTag(this);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// dismiss();
		// if (mhuidiao != null)
		// switch (v.getId()) {
		// case R.id.imageView1:
		// mhuidiao.onItemClick(this, 1, v);
		// break;
		// }
	}

	private huidiao mhuidiao = null;

	public void sethuidiao(huidiao p) {
		mhuidiao = p;
	}

	public interface huidiao {
		void onItemClick(ConsultingTypeinfo data);
	}
}
