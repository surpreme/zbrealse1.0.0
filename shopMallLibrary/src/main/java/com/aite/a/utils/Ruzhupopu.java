package com.aite.a.utils;

import java.util.HashMap;
import java.util.Map;

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

import com.aiteshangcheng.a.R;

public class Ruzhupopu extends PopupWindow implements OnClickListener {
	private Activity mActivity = null;
	private ListView lv_nearpopulist, lv_nearpopulist2, lv_nearpopulist3;
	private MyAdapter myadapter;
	private int popwstyle = 0;
	private String level1menu;
	private String level2menu;
	private String level3menu;
	private String level1class = null;
	private String level2class = null;
	private String level3class = null;
	private Map<String, String> it=new HashMap<String, String>();

	String[] datas5 = new String[] { "-请选择-", "食品特产", "服饰鞋帽", "母婴用品", "家居家居",
			"家用电器", "护理化妆", "日用百货", "电子数码" };
	String[] datas4 = new String[] { "-请选择-", "旗舰店", "品牌店", "专卖店", "个人店铺" };
	String[] datas3 = new String[] { "-请选择-", "系统默认", "白金店铺", "钻石店铺",
			"官方旗舰店 品牌直营店", "黄金店铺" };
	String[] datas2 = new String[] { "-请选择-", "电信区域代理", "网通区域代理" };

	String[] datas = new String[] { "-请选择-", "北京", "天津", "河北省", "山西省",
			"内蒙古自治区", "辽宁省", "吉林省", "黑龙江省", "上海" };

	/**
	 * 入驻地址选择弹出框
	 * 
	 * @param activity
	 *            上下文
	 * @param popwstyle
	 *            0地址选择  1代理  2店铺等级  3店铺分类  4经营类目
	 */
	public Ruzhupopu(Activity activity, final int popwstyle) {
		switch (popwstyle) {
		case 0:
			myadapter = new MyAdapter(datas);
			break;
		case 1:
			myadapter = new MyAdapter(datas2);
			break;
		case 2:
			myadapter = new MyAdapter(datas3);
			break;
		case 3:
			myadapter = new MyAdapter(datas4);
			break;
		case 4:
			myadapter = new MyAdapter(datas5);
			break;
		}
		mActivity = activity;
		this.popwstyle = popwstyle;
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
		lv_nearpopulist.setAdapter(myadapter);
		lv_nearpopulist.setOnItemClickListener(listener);
		lv_nearpopulist2.setOnItemClickListener(listener2);
		lv_nearpopulist3.setOnItemClickListener(listener3);

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
				switch (popwstyle) {
				case 0:
					maddress.onClick(level1menu, level2menu, level3menu);
					break;
				}

				WindowManager.LayoutParams lp = mActivity
						.getWindow().getAttributes();
				lp.alpha = 1f;
				mActivity.getWindow().setAttributes(lp);
			}
		});

		// 外部可被操作
		setOutsideTouchable(false);
		setAnimationStyle(R.style.AnimBottomPopup);
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
			switch (popwstyle) {
			case 0:
				if (position != 0) {
					touming(lv_nearpopulist2, R.anim.pingyi);
					lv_nearpopulist2.setAdapter(myadapter);
					lv_nearpopulist2.setVisibility(View.VISIBLE);
					lv_nearpopulist3.setVisibility(View.GONE);
					myadapter.settextcolor(parent, position);
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					level1menu = v.getText().toString();
				} else {
					level1menu = null;
					dismiss();
				}
				break;
			case 1:
				if (position != 0) {
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					magency.onClickl(v.getText().toString());
				} else {
					magency.onClickl(null);
				}
				dismiss();
				break;
			case 2:
				if (position != 0) {
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					mshoplevel.onClickls(v.getText().toString());
				} else {
					mshoplevel.onClickls(null);
				}
				dismiss();
				break;
			case 3:
				if (position != 0) {
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					mshopclass.onClicklsl(v.getText().toString());
				} else {
					mshopclass.onClicklsl(null);
				}
				dismiss();
				break;
			case 4:
				if (position != 0) {
					touming(lv_nearpopulist2, R.anim.pingyi);
					lv_nearpopulist2.setAdapter(myadapter);
					lv_nearpopulist2.setVisibility(View.VISIBLE);
					lv_nearpopulist3.setVisibility(View.GONE);
					myadapter.settextcolor(parent, position);
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					level1class = v.getText().toString();
				}
				break;
			}

		}
	};
	/**
	 * 二级菜单监听
	 */
	public OnItemClickListener listener2 = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (popwstyle) {
			case 0:
				if (position != 0) {
					touming(lv_nearpopulist3, R.anim.pingyi);
					lv_nearpopulist3.setAdapter(myadapter);
					lv_nearpopulist3.setVisibility(View.VISIBLE);
					myadapter.settextcolor(parent, position);
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					level2menu = v.getText().toString();
				} else {
					level2menu = null;
					dismiss();
				}
				break;
			case 4:
				if (position != 0) {
					touming(lv_nearpopulist3, R.anim.pingyi);
					lv_nearpopulist3.setAdapter(myadapter);
					lv_nearpopulist3.setVisibility(View.VISIBLE);
					myadapter.settextcolor(parent, position);
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					level2class = v.getText().toString();
				}
				break;
			}
		}
	};
	/**
	 * 三级菜单监听
	 */
	public OnItemClickListener listener3 = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (popwstyle) {
			case 0:
				if (position != 0) {
					myadapter.settextcolor(parent, position);
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					level3menu = v.getText().toString();
				} else {
					level3menu = null;
				}
				break;
			case 4:
				if (position != 0) {
					myadapter.settextcolor(parent, position);
					TextView v = (TextView) parent.getChildAt(position)
							.findViewById(R.id.tv_cityitem);
					level3class = v.getText().toString();
					if (level1class != null && level2class != null
							&& level3class != null) {
						// TODO
						it.put("level1class", level1class);
						it.put("level2class", level2class);
						it.put("level3class", level3class);
						it.put("level4class", "删除");
						mbusinessclass.onbusiness(it);
					}
				}
				break;
			}
			dismiss();
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

	public class MyAdapter extends BaseAdapter {
		String[] data;

		public MyAdapter(String[] data) {
			this.data = data;
		}

		/**
		 * 修改选中的item的字体颜色
		 * 
		 * @param parent
		 * @param position
		 */
		public void settextcolor(AdapterView<?> parent, int position) {
			for (int i = 0; i < data.length; i++) {
				TextView v = (TextView) parent.getChildAt(i).findViewById(
						R.id.tv_cityitem);
				v.setTextColor(0xff000000);
			}
			TextView v = (TextView) parent.getChildAt(position).findViewById(
					R.id.tv_cityitem);
			v.setTextColor(0xffFF8C00);
		}

		@Override
		public int getCount() {

			return data.length;
		}

		@Override
		public Object getItem(int position) {

			return data[position];
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
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			if (position == 0) {
				holder.tv_cityitem.setTextColor(0xffFF8C00);
			}
			holder.tv_cityitem.setText(data[position]);
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

	private address maddress = null;

	public void setaddress(address a) {
		maddress = a;
	}

	/**
	 * 地址回调
	 * 
	 * @author Administrator
	 *
	 */
	public interface address {
		void onClick(String level1menu, String level2menu, String level3menu);
	}

	private agency magency = null;

	public void setagency(agency ag) {
		magency = ag;
	}

	/**
	 * 区域代理回调
	 * 
	 * @author Administrator
	 *
	 */
	public interface agency {
		void onClickl(String agency);
	}

	private shoplevel mshoplevel = null;

	public void setshoplevel(shoplevel sll) {
		mshoplevel = sll;
	}

	/**
	 * 店铺等级回调
	 * 
	 * @author Administrator
	 *
	 */
	public interface shoplevel {
		void onClickls(String level);
	}

	private shopclass mshopclass = null;

	public void setshopclass(shopclass sc) {
		mshopclass = sc;
	}

	/**
	 * 店铺分类回调
	 * 
	 * @author Administrator
	 *
	 */
	public interface shopclass {
		void onClicklsl(String fclass);
	}

	private businessclass mbusinessclass = null;

	public void setbusinessclass(businessclass bc) {
		mbusinessclass = bc;
	}

	/**
	 * 经营类目回调
	 * 
	 * @author Administrator
	 *
	 */
	public interface businessclass {
		void onbusiness(Map<String, String> item);
	}

	private void touming(ListView view, int x) {
		Animation animation = AnimationUtils.loadAnimation(mActivity, x);
		view.startAnimation(animation);
	}
}
