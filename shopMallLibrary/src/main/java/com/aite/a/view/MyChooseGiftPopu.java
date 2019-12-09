package com.aite.a.view;

import com.aite.a.base.Mark;
import com.aite.a.model.GoodsListInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MyChooseGiftPopu extends PopupWindow implements Mark,
		OnClickListener {
	private Activity mActivity = null;
	private PullToRefreshLayout refresh_view;
	private PullableListView lv_list;
	private int refreshtype = 0, curpage = 1;
	private MyListener myListenr;
	private BitmapUtils bitmapUtils;
	private MyAdapter myAdapter;
	private EditText ed_input1;
	private TextView tv_search;
	private NetRun netRun;
	private GoodsListInfo goodsListInfo;
	private String name="";
	private int id;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case search_goods_id:
				// 搜索商品列表
				if (msg.obj != null) {
					goodsListInfo = (GoodsListInfo) msg.obj;
					// myAdapter=new MyAdapter(goodsListInfo);
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(goodsListInfo);
						lv_list.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(goodsListInfo);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(goodsListInfo);
						myListenr.loadMoreSucceed();
					}
				}
				break;
			case search_goods_err:
				Toast.makeText(mActivity,
						mActivity.getString(R.string.systembusy),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	public MyChooseGiftPopu(Activity activity, int id) {
		this.mActivity = activity;
		this.id = id;
		WindowManager wm = mActivity.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight((int) (height * 0.6));
		// setHeight(LayoutParams.WRAP_CONTENT);
		View view = View.inflate(mActivity, R.layout.popu_choosegift, null);
		refresh_view = (PullToRefreshLayout) view
				.findViewById(R.id.refresh_view);
		lv_list = (PullableListView) view.findViewById(R.id.lv_list);
		ed_input1 = (EditText) view.findViewById(R.id.ed_input1);
		tv_search = (TextView) view.findViewById(R.id.tv_search);
		tv_search.setOnClickListener(this);
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		netRun = new NetRun(mActivity, handler);
		bitmapUtils = new BitmapUtils(mActivity);
		netRun.SearchGoods(name, curpage + "");
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
		public void handleMessage(Message msg) {
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

	private date mdate = null;

	public void setdate(date p) {
		mdate = p;
	}

	public interface date {
		void onItemClick(com.aite.a.model.GoodsListInfo.goods_list goods, int id);
	}

	/**
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		GoodsListInfo goodsListInfo;

		public MyAdapter(GoodsListInfo goodsListInfo) {
			this.goodsListInfo = goodsListInfo;
		}

		/**
		 * 刷新
		 * 
		 * @param list
		 */
		public void updata(GoodsListInfo goodsListInfo) {
			if (goodsListInfo == null) {
				return;
			}
			this.goodsListInfo = goodsListInfo;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(GoodsListInfo goodsListInfo) {
			if (goodsListInfo == null) {
				return;
			}
			this.goodsListInfo.goods_list.addAll(goodsListInfo.goods_list);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (goodsListInfo == null) {
				return 0;
			} else if (goodsListInfo.goods_list == null) {
				return 0;
			} else {
				return goodsListInfo.goods_list.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return goodsListInfo.goods_list == null ? null
					: goodsListInfo.goods_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mActivity,
						R.layout.item_choosegoods, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			final GoodsListInfo.goods_list goods_list = goodsListInfo.goods_list
					.get(position);
			bitmapUtils.display(holder.iv_gimg, goods_list.goods_image);
			holder.tv_name.setText(goods_list.goods_name);
			holder.tv_price.setText(mActivity.getString(R.string.recommended5)
					+ goods_list.goods_price);
			holder.tv_xz.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 添加
					mdate.onItemClick(goods_list, id);
				}
			});
			return convertView;
		}

		class ViewHodler {
			ImageView iv_gimg;
			TextView tv_name, tv_price, tv_xz;

			public ViewHodler(View convertView) {
				iv_gimg = (ImageView) convertView.findViewById(R.id.iv_gimg);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				tv_xz = (TextView) convertView.findViewById(R.id.tv_xz);
				convertView.setTag(this);
			}
		}
	}

	public class MyListener implements OnRefreshListener {

		private PullToRefreshLayout pullToRefreshLayout;

		private Handler refreshHandler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case pull_down_refresh_success:// 下拉刷新成功
					if (pullToRefreshLayout != null) {
						pullToRefreshLayout
								.refreshFinish(PullToRefreshLayout.SUCCEED);
					}
					break;
				case pull_up_loaded_success:// 上拉加载成功
					if (pullToRefreshLayout != null) {
						pullToRefreshLayout
								.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}
					break;
				}
			};
		};

		/**
		 * 下拉式刷新成功
		 * */
		public void refreshSucceed() {
			refreshHandler.sendEmptyMessageDelayed(pull_down_refresh_success,
					400);
		}

		/**
		 * 上拉加载成功
		 * */
		public void loadMoreSucceed() {
			refreshHandler.sendEmptyMessageDelayed(pull_up_loaded_success, 400);
		}

		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
			// 下拉刷新操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			refreshtype = 1;
			curpage = 1;
			netRun.SearchGoods(name, curpage + "");
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (goodsListInfo != null
					&& goodsListInfo.list_page.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.SearchGoods(name, curpage + "");
			} else {
				loadMoreSucceed();
				Toast.makeText(mActivity,
						mActivity.getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.tv_search){
			// 搜索
			String s = ed_input1.getText().toString();
			if (TextUtils.isEmpty(s)) {
				s = "";
			}
			name = s;
			refreshtype = 1;
			curpage = 1;
			netRun.SearchGoods(name, curpage + "");
		}

//		switch (v.getId()) {
//		case R.id.tv_search:
//			// 搜索
//			String s = ed_input1.getText().toString();
//			if (TextUtils.isEmpty(s)) {
//				s = "";
//			}
//			name = s;
//			refreshtype = 1;
//			curpage = 1;
//			netRun.SearchGoods(name, curpage + "");
//			break;
//		}
	}

}
