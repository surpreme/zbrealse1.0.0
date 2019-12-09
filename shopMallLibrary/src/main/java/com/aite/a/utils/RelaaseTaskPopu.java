package com.aite.a.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.aite.a.model.ReleaseTaskInfo;
import com.aite.a.model.ReleaseTaskInfo.class2;
import com.aite.a.model.ReleaseTaskInfo.class2.class3;
import com.aiteshangcheng.a.R;

/**
 * 服务三级分类选择
 * 
 * @author Administrator
 *
 */
public class RelaaseTaskPopu extends PopupWindow implements OnClickListener {
	private Activity mActivity = null;
	private ListView lv_nearpopulist, lv_nearpopulist2, lv_nearpopulist3;
	private MyAdapter1 myadapte1;
	private MyAdapter2 myadapte2;
	private MyAdapter3 myadapte3;
	private List<ReleaseTaskInfo> releaseTaskInfo;
	private boolean issorting;
	private List<String> sorting;

	public RelaaseTaskPopu(Activity activity,
			List<ReleaseTaskInfo> releaseTaskInfo, int type, Boolean issorting) {
		this.issorting = issorting;
		mActivity = activity;
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		setHeight(LayoutParams.WRAP_CONTENT);
		WindowManager wm = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		View view = View.inflate(mActivity, R.layout.nearpopu, null);
		lv_nearpopulist = (ListView) view.findViewById(R.id.lv_nearpopulist);
		lv_nearpopulist2 = (ListView) view.findViewById(R.id.lv_nearpopulist2);
		lv_nearpopulist3 = (ListView) view.findViewById(R.id.lv_nearpopulist3);
		if (issorting) {
			sorting = new ArrayList<String>();
			sorting.add(mActivity.getString(R.string.credit));
			sorting.add(mActivity.getString(R.string.rate));
			sorting.add(mActivity.getString(R.string.turnover));
			sorting.add(mActivity.getString(R.string.near_reminder9));
			MyAdapter myadapte = new MyAdapter(sorting);
			lv_nearpopulist.setAdapter(myadapte);
		} else {
			this.releaseTaskInfo = releaseTaskInfo;
			myadapte1 = new MyAdapter1(releaseTaskInfo);
			lv_nearpopulist.setAdapter(myadapte1);
		}
		// lv_nearpopulist.setOnItemClickListener(listener);
		// lv_nearpopulist2.setOnItemClickListener(listener2);
		// lv_nearpopulist3.setOnItemClickListener(listener3);

		// 改变二级菜单的宽度
		ViewGroup.LayoutParams layoutParam2 = lv_nearpopulist2
				.getLayoutParams();
		layoutParam2.width = (width / 3) * 2;
		lv_nearpopulist2.setLayoutParams(layoutParam2);
		// 改变三级菜单的宽度
		ViewGroup.LayoutParams layoutParams3 = lv_nearpopulist3
				.getLayoutParams();
		layoutParams3.width = width / 3;
		lv_nearpopulist3.setLayoutParams(layoutParams3);
		// 设置SelectPicPopupWindow的View
		setContentView(view);
		// 设置点击视图之外的地方是否取消当前的PopupWindow
		setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);

		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = mActivity
						.getWindow().getAttributes();
				lp.alpha = 1f;
				mActivity.getWindow().setAttributes(lp);
			}
		});
		// 外部可被操作
		setOutsideTouchable(false);
		// setFocusable(false);

		switch (type) {
		case 1:
			setAnimationStyle(R.style.AnimBottomPopupp2);
			break;
		case 2:
			setAnimationStyle(R.style.AnimBottomPopupp3);
			break;
		}

	}

	Handler h = new Handler() {
		// 显示玩popup后，改变背景透明度
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				WindowManager.LayoutParams lp = mActivity
						.getWindow().getAttributes();
				lp.alpha = 0.8f;
				mActivity.getWindow().setAttributes(lp);
				break;
			case 1:
				dismiss();
				break;
			}
		};
	};
	/**
	 * 一级菜单监听
	 */
	public OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// touming(lv_nearpopulist2, R.anim.pingyi);
			// lv_nearpopulist2.setAdapter(myadapter);
			// lv_nearpopulist2.setVisibility(View.VISIBLE);
			// lv_nearpopulist3.setVisibility(View.GONE);
		}
	};
	/**
	 * 二级菜单监听
	 */
	public OnItemClickListener listener2 = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// touming(lv_nearpopulist3, R.anim.pingyi);
			// lv_nearpopulist3.setAdapter(myadapter);
			// lv_nearpopulist3.setVisibility(View.VISIBLE);
		}
	};
	/**
	 * 三级菜单监听
	 */
	public OnItemClickListener listener3 = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// dismiss();
		}
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

	@Override
	public void onClick(View v) {

	}

	// 分类回调
	public calsshuidiao1 calsshuidiao1mhuidiao1 = null;

	public void setcalsshuidiao1(calsshuidiao1 p1) {
		calsshuidiao1mhuidiao1 = p1;
	}

	public interface calsshuidiao1 {
		void onItemClick(String class1_id, String clas2_id, String clas3_id,
						 String class1_name, String clas2_name, String clas3_name);
	}

	private String class1_id, clas2_id, clas3_id, class1_name, clas2_name,
			clas3_name;
	
	// 排序回调
	public Sortinghd sortinghd = null;

	public void setSortinghd(Sortinghd hd) {
		sortinghd = hd;
	}

	public interface Sortinghd {
		void onsortingClick(String class1_id);
	}

	/**
	 * 一级适配
	 * 
	 * @author Administrator
	 *
	 */
	public class MyAdapter1 extends BaseAdapter {
		List<ReleaseTaskInfo> releaseTaskInfo;

		public MyAdapter1(List<ReleaseTaskInfo> releaseTaskInfo) {
			this.releaseTaskInfo = releaseTaskInfo;
		}

		@Override
		public int getCount() {
			return releaseTaskInfo.size();
		}

		@Override
		public Object getItem(int position) {

			return releaseTaskInfo == null ? null : releaseTaskInfo
					.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.nearpopuitem,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_cityitem.setText(releaseTaskInfo.get(position).gc_name);
			holder.tv_cityitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					class1_id = releaseTaskInfo.get(position).gc_id;
					class1_name = releaseTaskInfo.get(position).gc_name;
					touming(lv_nearpopulist2, R.anim.pingyi);
					myadapte2 = new MyAdapter2(
							releaseTaskInfo.get(position).class2);
					lv_nearpopulist2.setAdapter(myadapte2);
					lv_nearpopulist2.setVisibility(View.VISIBLE);
					lv_nearpopulist3.setVisibility(View.GONE);
					// dismiss();
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_cityitem;

			public ViewHolder(View convertView) {
				tv_cityitem = (TextView) convertView
						.findViewById(R.id.tv_cityitem);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 排序
	 * 
	 * @author Administrator
	 *
	 */
	public class MyAdapter extends BaseAdapter {
		List<String> sorting;

		public MyAdapter(List<String> sorting) {
			this.sorting = sorting;
		}

		@Override
		public int getCount() {
			return sorting.size();
		}

		@Override
		public Object getItem(int position) {

			return sorting == null ? null : sorting.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.nearpopuitem,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_cityitem.setText(sorting.get(position));
			holder.tv_cityitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					sortinghd.onsortingClick(sorting.get(position));
					dismiss();
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_cityitem;

			public ViewHolder(View convertView) {
				tv_cityitem = (TextView) convertView
						.findViewById(R.id.tv_cityitem);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 二级适配
	 * 
	 * @author Administrator
	 *
	 */
	public class MyAdapter2 extends BaseAdapter {
		List<class2> class2;

		public MyAdapter2(List<class2> class2) {
			this.class2 = class2;
		}

		@Override
		public int getCount() {
			return class2.size();
		}

		@Override
		public Object getItem(int position) {

			return class2 == null ? null : class2.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.nearpopuitem,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_cityitem.setText(class2.get(position).gc_name);
			holder.tv_cityitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					clas2_id = class2.get(position).gc_id;
					clas2_name = class2.get(position).gc_name;
					myadapte3 = new MyAdapter3(class2.get(position).class3);
					touming(lv_nearpopulist3, R.anim.pingyi);
					lv_nearpopulist3.setAdapter(myadapte3);
					lv_nearpopulist3.setVisibility(View.VISIBLE);
					// dismiss();
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_cityitem;

			public ViewHolder(View convertView) {
				tv_cityitem = (TextView) convertView
						.findViewById(R.id.tv_cityitem);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 三级级适配
	 * 
	 * @author Administrator
	 *
	 */
	public class MyAdapter3 extends BaseAdapter {
		List<class3> class3;

		public MyAdapter3(List<class3> class3) {
			this.class3 = class3;
		}

		@Override
		public int getCount() {
			return class3.size();
		}

		@Override
		public Object getItem(int position) {

			return class3 == null ? null : class3.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.nearpopuitem,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_cityitem.setText(class3.get(position).gc_name);
			holder.tv_cityitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					clas3_id = class3.get(position).gc_id;
					clas3_name = class3.get(position).gc_name;
					calsshuidiao1mhuidiao1.onItemClick(class1_id, clas2_id,
							clas3_id, class1_name, clas2_name, clas3_name);
					dismiss();
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_cityitem;

			public ViewHolder(View convertView) {
				tv_cityitem = (TextView) convertView
						.findViewById(R.id.tv_cityitem);
				convertView.setTag(this);
			}
		}
	}

	private void touming(ListView view, int x) {
		Animation animation = AnimationUtils.loadAnimation(mActivity, x);
		view.startAnimation(animation);
	}

}
