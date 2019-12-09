package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.PreferentialSuitInfo;
import com.aite.a.model.PreferentialSuitInfo.goods_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 优惠套装
 * 
 * @author Administrator
 *
 */
public class PreferentialSuitActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_bcreturn;
	private TextView tv_bctitle, tv_nodata;
	private PullableListView lv_lis;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private int curpage = 1, refreshtype = 0;
	private MyAdapter myAdapter;
	private MyListener myListenr;
	private PullToRefreshLayout refresh_view;
	private String goods_id;
	private List<PreferentialSuitInfo> preferentialSuitInfo;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case get_bundling_id:
				if (msg.obj != null) {
					preferentialSuitInfo = (List<PreferentialSuitInfo>) msg.obj;
					if (preferentialSuitInfo == null
							|| preferentialSuitInfo.size() == 0) {
						tv_nodata.setVisibility(View.VISIBLE);
					} else {
						tv_nodata.setVisibility(View.GONE);
					}
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(preferentialSuitInfo);
						lv_lis.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(preferentialSuitInfo);
						myListenr.refreshSucceed();
					}
				}
				break;
			case get_bundling_err:
				Toast.makeText(PreferentialSuitActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case add_cart_id:
				//添加购物车
				if (msg.obj!=null) {
					String str=(String) msg.obj;
					if (str.equals("1")) {
						Toast.makeText(PreferentialSuitActivity.this,
						getString(R.string.add_success), Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(PreferentialSuitActivity.this,
								str, Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case add_cart_err:
				Toast.makeText(PreferentialSuitActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferential_suit);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		lv_lis = (PullableListView) findViewById(R.id.lv_lis);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		initView();
	}

	@Override
	protected void initView() {
		tv_bctitle.setText(getString(R.string.order_reminder73));
		iv_bcreturn.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		goods_id = getIntent().getStringExtra("goods_id");
		initData();
	}

	@Override
	protected void initData() {
		netRun.GetBundling(goods_id);
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			finish();
		}
	}

	/**
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<PreferentialSuitInfo> preferentialSuitInfo;

		public MyAdapter(List<PreferentialSuitInfo> preferentialSuitInfo) {
			this.preferentialSuitInfo = preferentialSuitInfo;
		}

		/**
		 * 刷新
		 * 
		 */
		public void updata(List<PreferentialSuitInfo> preferentialSuitInfo) {
			if (preferentialSuitInfo == null) {
				return;
			}
			this.preferentialSuitInfo = preferentialSuitInfo;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return preferentialSuitInfo.size();
		}

		@Override
		public Object getItem(int position) {
			return preferentialSuitInfo == null ? null : preferentialSuitInfo
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
				convertView = View.inflate(PreferentialSuitActivity.this,
						R.layout.item_preferential_suit, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final PreferentialSuitInfo preferentialSuitInfo2 = preferentialSuitInfo
					.get(position);
			holder.tv_name.setText(preferentialSuitInfo2.name);
			List<goods_list> goods_list = preferentialSuitInfo2.goods_list;
			holder.tv_tzprice.setText(getString(R.string.order_reminder74) + preferentialSuitInfo2.price);
			holder.tv_price.setText(getString(R.string.order_reminder75) + preferentialSuitInfo2.cost_price);
			holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
			holder.tv_prep.setText(getString(R.string.order_reminder76)
					+ getPrice(preferentialSuitInfo2.cost_price,
							preferentialSuitInfo2.price));
			holder.tv_freight.setText(getString(R.string.order_reminder77) + preferentialSuitInfo2.freight);
			MyAdapter2 myAdapter2 = new MyAdapter2(goods_list);
			holder.lv_goods.setAdapter(myAdapter2);
			holder.tv_addcart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 加入购物车
					netRun.TCaddInCart(preferentialSuitInfo2.id);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_name, tv_tzprice, tv_price, tv_prep, tv_freight,
					tv_addcart;
			MyListView lv_goods;

			public ViewHolder(View convertView) {
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_tzprice = (TextView) convertView
						.findViewById(R.id.tv_tzprice);
				tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				tv_prep = (TextView) convertView.findViewById(R.id.tv_prep);
				tv_freight = (TextView) convertView
						.findViewById(R.id.tv_freight);
				tv_addcart = (TextView) convertView
						.findViewById(R.id.tv_addcart);
				lv_goods = (MyListView) convertView.findViewById(R.id.lv_goods);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 商品列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter2 extends BaseAdapter {
		List<goods_list> goods_list;

		public MyAdapter2(List<goods_list> goods_list) {
			this.goods_list = goods_list;
		}

		@Override
		public int getCount() {
			return goods_list.size();
		}

		@Override
		public Object getItem(int position) {
			return goods_list == null ? null : goods_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(PreferentialSuitActivity.this,
						R.layout.item_preferential_suit2, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final goods_list goods_list2 = goods_list.get(position);
			bitmapUtils.display(holder.iv_img, goods_list2.image);
			holder.tv_name.setText(goods_list2.name);
			holder.tv_price1.setText(getString(R.string.order_reminder75) + goods_list2.shop_price);
			holder.tv_price2.setText(getString(R.string.order_reminder78) + goods_list2.price);
			holder.rl_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("goods_id", goods_list2.id);
					intent.setClass(PreferentialSuitActivity.this,
							ProductDetailsActivity.class);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_img;
			TextView tv_name, tv_price1, tv_price2;
			RelativeLayout rl_item;
			
			public ViewHolder(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_price1 = (TextView) convertView.findViewById(R.id.tv_price1);
				tv_price2 = (TextView) convertView.findViewById(R.id.tv_price2);
				rl_item=(RelativeLayout) convertView.findViewById(R.id.rl_item);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 计算节省
	 */
	private String getPrice(String s1, String s2) {
		try {
			return (Float.parseFloat(s1) - Float.parseFloat(s2)) + "";
		} catch (Exception e) {
		}
		return "";
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
			// curpage = 1;
			netRun.GetBundling(goods_id);
			// netRun.ReportList(curpage + "", select_inform_state);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			Toast.makeText(PreferentialSuitActivity.this,
					getString(R.string.act_no_data_load), Toast.LENGTH_SHORT)
					.show();
			myListenr.loadMoreSucceed();
			// if (reportListInfo.hasmore.equals("true")) {
			// refreshtype = 2;
			// curpage++;
			// netRun.ReportList(curpage + "", select_inform_state);
			// } else {
			// loadMoreSucceed();
			// Toast.makeText(PreferentialSuitActivity.this,
			// getString(R.string.act_no_data_load),
			// Toast.LENGTH_SHORT).show();
			// }
		}
	}

}
