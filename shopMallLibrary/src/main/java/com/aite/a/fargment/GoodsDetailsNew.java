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
public class GoodsDetailsNew extends ViewTransition implements Mark, OnHeaderRefreshListener, OnFooterRefreshListener {

	private int goodsListItem;
	/**
	 * 记录加载的数据
	 */
	private static int PAGE = 1;
	private NewGoodListAdapter newGoodListAdapter;
	private View view;
	private PullToRefreshView mPullToRefreshView;
	private GridView goodsList;
	public List<GoodList> newlist = new ArrayList<GoodList>();
	private NetRun netRun;
	private String key = "";
	private String keyword, gc_id, store_id;
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case goods_list_id:
				if (msg.obj == null) {
					CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.act_no_data_load).toString());
				} else {
					newGoodListAdapter.addAll((List<GoodList>) msg.obj);
					newGoodListAdapter.notifyDataSetChanged();
				}
				break;
			case goods_list_err:
				CommonTools.showShortToast(getActivity(), APPSingleton.getContext().getString(R.string.act_net_error).toString());
				break;
			case goods_list_start:
				break;
			}
		};
	};

	public GoodsDetailsNew(String store_id, String keyword, String gc_id) {
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
		goodsList.setNumColumns(1);
		setTransition(this);
		goodsListItem = R.layout.goods_list_item;
		newGoodListAdapter = new NewGoodListAdapter();
		goodsList.setAdapter(newGoodListAdapter);
		goodsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("goods_id", newlist.get(position).getGoods_id());
				intent.setClass(getActivity(), ProductDetailsActivity.class);
				startActivity(intent);
			}
		});
	}

	protected void requestData() {
		netRun = new NetRun(getActivity(), handler);
		netRun.getGoodsList(key, "1", COUNT, "1", keyword, gc_id, store_id);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				PAGE = 1;
				newGoodListAdapter.clear();
				netRun.getGoodsList(key, "1", COUNT, "1", keyword, gc_id, store_id);
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 2000);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				PAGE = PAGE + 1;
				String show_add = (PAGE + "").trim();
				netRun.getGoodsList(key, "1", COUNT, show_add, keyword, gc_id, store_id);
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);
	}

	@Override
	public void onDestroy() {
		newGoodListAdapter.clear();
		super.onDestroy();
	}

	public class NewGoodListAdapter extends BaseAdapter {
		private BitmapUtils bitmapUtils;

		public NewGoodListAdapter() {
			super();
			bitmapUtils = new BitmapUtils(getActivity());
		}

		@Override
		public int getCount() {
			return newlist.size();
		}

		@Override
		public Object getItem(int position) {
			return newlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			ViewHolder holder = null;
			//切换时有些item不为空导致加载item不正确
			v=null;
			if (v == null) {
				v = LayoutInflater.from(getActivity()).inflate(goodsListItem, parent, false);
				holder = new ViewHolder(v);
				v.setTag(holder);
			} else {
				holder = (ViewHolder) v.getTag();
			}

			GoodList object = newlist.get(position);
			bitmapUtils.display(holder.imageView, object.getGoods_image_url().toString());
			holder.contentView.setText(object.getGoods_name());
			holder.priceView.setText(object.getGoods_price());
			return v;
		}

		public class ViewHolder {
			public ViewHolder(View v) {
				super();
				imageView = (ImageView) v.findViewById(R.id.list_iv_image);
				contentView = (TextView) v.findViewById(R.id.list_tv_content);
				priceView = (TextView) v.findViewById(R.id.list_tv_price);
			}

			ImageView imageView;
			TextView contentView;
			TextView priceView;
		}

		public void clear() {
			newlist.clear();
			notifyDataSetChanged();
		}

		/**
		 * 添加数据
		 * 
		 * @param actives
		 */
		public void addAll(List<GoodList> actives) {
			newlist.addAll(actives);
			notifyDataSetChanged();
		}
	}

	@Override
	public void Transition(boolean Transition) {
		if (Transition == true) {
			goodsList.setNumColumns(1);
			goodsListItem = R.layout.goods_list_item;
			newGoodListAdapter.notifyDataSetChanged();
		} else {
			goodsList.setNumColumns(2);
			goodsListItem = R.layout.goods_list_item2;
			newGoodListAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void Change(boolean ASC) {
		// TODO Auto-generated method stub

	}
}
