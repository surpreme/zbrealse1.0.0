package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aite.a.activity.ProductDetailsActivity;
import com.aiteshangcheng.a.R;
import com.aite.a.base.Mark;
import com.aite.a.base.ViewTransition;
import com.aite.a.model.GoodList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.PullToRefreshView;
import com.aite.a.view.PullToRefreshView.OnFooterRefreshListener;
import com.aite.a.view.PullToRefreshView.OnHeaderRefreshListener;
import com.lidroid.xutils.BitmapUtils;

/**
 * 新品
 * 
 * @author xiaoyu
 * 
 */
@SuppressLint("ValidFragment")
public class GoodsDetailsPrice extends ViewTransition implements Mark, OnHeaderRefreshListener, OnFooterRefreshListener {
	/**
	 * 记录加载的数据
	 */
	private static int PAGE = 1;
	private PullToRefreshView mPullToRefreshView;
	private int goodsListItem;
	private PriceGoodListAdapter priceGoodListAdapter;
	private String order = "1";
	private String key = "3";
	private View view;
	private GridView goodsList;
	public List<GoodList> Pricelist = new ArrayList<GoodList>();
	private boolean flush = false;
	private NetRun netRun;
	private String keyword, gc_id, store_id;
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case goods_list_id:
				if (msg.obj == null) {
					CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.act_no_data_load).toString());
				} else {
					if (flush == true) {
						priceGoodListAdapter.addAll((List<GoodList>) msg.obj);
						priceGoodListAdapter.notifyDataSetChanged();

					} else {
						Pricelist = (List<GoodList>) msg.obj;
						priceGoodListAdapter = new PriceGoodListAdapter();
						goodsList.setAdapter(priceGoodListAdapter);
					}
				}
				break;
			case goods_list_err:
				CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.systembusy).toString());
				break;
			case goods_list_start:
				break;
			}
		};
	};

	public GoodsDetailsPrice(String store_id, String keyword, String gc_id) {
		this.store_id = store_id;
		this.keyword = keyword;
		this.gc_id = gc_id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.goods_list_all, container, false);
		findViewById();
		requestData();
		return view;
	}

	protected void findViewById() {
		goodsList = (GridView) view.findViewById(R.id.good_list_lv);
		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.good_list_refreshView);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		setSort(this);
		goodsList.setNumColumns(1);
		setTransition(this);
		goodsListItem = R.layout.goods_list_item;
		priceGoodListAdapter = new PriceGoodListAdapter();
		goodsList.setAdapter(priceGoodListAdapter);
		goodsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("goods_id", Pricelist.get(position).getGoods_id());
				intent.setClass(getActivity(), ProductDetailsActivity.class);
				startActivity(intent);
			}
		});
	}

	protected void requestData() {
		netRun = new NetRun(getActivity(), handler);
		Change(false);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				flush = true;
				PAGE = 1;
				priceGoodListAdapter.clear();
				netRun.getGoodsList(key, order, COUNT, "1", keyword, gc_id, store_id);
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 2000);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				flush = true;
				PAGE = PAGE + 1;
				String show_add = (PAGE + "").trim();
				netRun.getGoodsList(key, order, COUNT, show_add, keyword, gc_id, store_id);
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);
	}

	@Override
	public void onDestroy() {
		priceGoodListAdapter.clear();
		super.onDestroy();
	}

	public class PriceGoodListAdapter extends BaseAdapter {
		private BitmapUtils bitmapUtils;

		@Override
		public int getCount() {
			return Pricelist.size();
		}

		@Override
		public Object getItem(int position) {
			return Pricelist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (Pricelist != null) {
				convertView = LayoutInflater.from(getActivity()).inflate(goodsListItem, parent, false);
				holder.imageView = (ImageView) convertView.findViewById(R.id.list_iv_image);
				holder.contentView = (TextView) convertView.findViewById(R.id.list_tv_content);
				holder.priceView = (TextView) convertView.findViewById(R.id.list_tv_price);

				GoodList object = Pricelist.get(position);
				bitmapUtils = new BitmapUtils(getActivity());
				bitmapUtils = new BitmapUtils(getActivity());
				bitmapUtils.display(holder.imageView, object.getGoods_image_url().toString());
				holder.contentView.setText(object.getGoods_name().toString());
				holder.priceView.setText(object.getGoods_price().toString());
			} else {
				CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.act_no_data_load).toString());
			}
			return convertView;
		}

		public class ViewHolder {
			ImageView imageView;
			TextView contentView;
			TextView priceView;
		}

		public void clear() {
			Pricelist.clear();
			notifyDataSetChanged();
		}

		/**
		 * 添加数据
		 * 
		 * @param actives
		 */
		public void addAll(List<GoodList> actives) {
			Pricelist.addAll(actives);
			notifyDataSetChanged();
		}
	}

	@Override
	public void Change(boolean ASC) {
		if (ASC == true) {
			flush = false;
			order = "1";
			netRun.getGoodsList(key, order, COUNT, "1", keyword, gc_id, store_id);
			priceGoodListAdapter.notifyDataSetChanged();
		} else {
			flush = false;
			order = "2";
			netRun.getGoodsList(key, order, COUNT, "1", keyword, gc_id, store_id);
			priceGoodListAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void Transition(boolean Transition) {
		if (Transition == true) {
			goodsList.setNumColumns(1);
			goodsListItem = R.layout.goods_list_item;
			priceGoodListAdapter.notifyDataSetChanged();
		} else {
			goodsList.setNumColumns(2);
			goodsListItem = R.layout.goods_list_item2;
			priceGoodListAdapter.notifyDataSetChanged();
		}
	}

}
