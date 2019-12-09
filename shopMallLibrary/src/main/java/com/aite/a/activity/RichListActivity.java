package com.aite.a.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.RichInfo;
import com.aite.a.model.RichInfo.datas.list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 富豪榜
 * 
 * @author Administrator
 *
 */
public class RichListActivity extends BaseActivity implements OnClickListener{
	private ImageView iv_bcreturn;
	private TextView tv_bctitle, tv_nodata;
	private PullToRefreshLayout refresh_view;
	private PullableListView lv_list;
	private int refreshtype = 0, curpage = 1;
	private MyListener myListenr;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private RichInfo richInfo;
	private MyAdapter myAdapter;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case rich_list_id:
				if (msg.obj!=null) {
					richInfo=(RichInfo) msg.obj;
//					myAdapter=new MyAdapter(richInfo.datas.list);
//					lv_list.setAdapter(myAdapter);
					if (richInfo.datas.list == null
							|| richInfo.datas.list.size() == 0) {
						tv_nodata.setVisibility(View.VISIBLE);
					} else {
						tv_nodata.setVisibility(View.GONE);
					}
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(richInfo.datas.list);
						lv_list.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(richInfo.datas.list);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(richInfo.datas.list);
						myListenr.loadMoreSucceed();
					}
					
				}
				break;
			case rich_list_err:
				Toast.makeText(RichListActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_richlist);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		lv_list = (PullableListView) findViewById(R.id.lv_list);
		tv_bctitle.setText(getString(R.string.rich));
		initView();
	}

	@Override
	protected void initView() {
		iv_bcreturn.setOnClickListener(this);
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		netRun.RichList("20", curpage+"");
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
		List<list> list;

		public MyAdapter(List<list> list) {
			this.list = list;
		}

		/**
		 * 刷新
		 * 
		 * @param list
		 */
		public void updata(List<list> list) {
			if (list == null) {
				return;
			}
			this.list = list;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(List<list> list) {
			if (list == null) {
				return;
			}
			this.list.addAll(list);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return list == null ? 0 : list.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(RichListActivity.this,
						R.layout.item_richlist, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			RichInfo.datas.list list2 = list.get(position);
			bitmapUtils.display(holder.cv_img, list2.avator);
			holder.tv_name.setText(list2.nickname+"["+list2.member_name+"]");
			holder.tv_recommended.setText(getString(R.string.order_reminder124)+list2.parent_name);
			holder.tv_consumption.setText(getString(R.string.order_reminder125)+list2.buy_count+getString(R.string.serviceproviders4));
			holder.tv_commission.setText(getString(R.string.order_reminder126)+list2.commission);
			return convertView;
		}

		class ViewHodler {
			RelativeLayout rl_item;
			CircleImageView cv_img;
			TextView tv_name,tv_recommended,tv_consumption,tv_commission;

			public ViewHodler(View convertView) {
				rl_item=(RelativeLayout) convertView.findViewById(R.id.rl_item);
				cv_img=(CircleImageView) convertView.findViewById(R.id.cv_img);
				tv_name=(TextView) convertView.findViewById(R.id.tv_name);
				tv_recommended=(TextView) convertView.findViewById(R.id.tv_recommended);
				tv_consumption=(TextView) convertView.findViewById(R.id.tv_consumption);
				tv_commission=(TextView) convertView.findViewById(R.id.tv_commission);
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
			netRun.RichList("20", curpage+"");
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (richInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.RichList("20", curpage+"");
			} else {
				loadMoreSucceed();
				Toast.makeText(RichListActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	
	
}
