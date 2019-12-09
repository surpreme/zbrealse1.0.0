package com.aite.a.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.TopicListInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 话题列表
 * 
 * @author Administrator
 *
 */
public class TopicListActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_bcreturn;
	private TextView tv_bctitle, tv_nodata;
	private PullToRefreshLayout refresh_view;
	private PullableListView lv_list;
	private int refreshtype = 0, curpage = 1;
	private MyListener myListenr;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private MyAdapter myAdapter;
	private TopicListInfo topicListInfo;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case topic_list_id:
				if (msg.obj != null) {
					topicListInfo = (TopicListInfo) msg.obj;
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(topicListInfo.datas.list);
						lv_list.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(topicListInfo.datas.list);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(topicListInfo.datas.list);
						myListenr.loadMoreSucceed();
					}

				}
				break;
			case topic_list_err:
				Toast.makeText(TopicListActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};
	private String is_recommend;
	private String circle_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topiclist);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		lv_list = (PullableListView) findViewById(R.id.lv_list);
		tv_bctitle.setText(getString(R.string.topic_list));
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
		is_recommend = getIntent().getStringExtra("is_recommend");
		circle_id = getIntent().getStringExtra("circle_id");
		netRun.TopicList("20", "", circle_id, curpage + "");
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
		if(v.getId()== R.id._iv_back){
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
		List<TopicListInfo.datas.list> list;

		public MyAdapter(List<TopicListInfo.datas.list> list) {
			this.list = list;
		}

		/**
		 * 刷新
		 * 
		 * @param list
		 */
		public void updata(List<TopicListInfo.datas.list> list) {
			if (list == null) {
				return;
			}
			this.list = list;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(List<TopicListInfo.datas.list> list) {
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
				convertView = View.inflate(TopicListActivity.this,
						R.layout.item_topic, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			TopicListInfo.datas.list list2 = list
					.get(position);
			bitmapUtils.display(holder.cv_icon, list2.member_avatar);
			holder.tv_name.setText(getString(R.string.order_reminder132) + list2.member_name);
			holder.tv_cn.setText(list2.theme_name);
			holder.tv_time.setText(TimeStamp2Date(list2.theme_addtime,
					"yyyy-MM-dd") + getString(R.string.order_reminder133) + list2.circle_name);
			holder.tv_zan.setText(list2.theme_commentcount);
			holder.iv_commentsnum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 点赞
				}
			});
			return convertView;
		}

		class ViewHodler {
			CircleImageView cv_icon;
			TextView tv_name, tv_cn, tv_time, tv_zan;
			ImageView iv_commentsnum;

			public ViewHodler(View convertView) {
				cv_icon = (CircleImageView) convertView
						.findViewById(R.id.cv_icon);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_cn = (TextView) convertView.findViewById(R.id.tv_cn);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
				iv_commentsnum = (ImageView) convertView
						.findViewById(R.id.iv_commentsnum);
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
			netRun.TopicList("20", "", circle_id, curpage + "");
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (topicListInfo!=null&&topicListInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.TopicList("20", "", circle_id, curpage + "");
			} else {
				loadMoreSucceed();
				Toast.makeText(TopicListActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 时间戳转时间
	 * 
	 * @param timestampString
	 *            时间戳
	 * @param formats
	 *            格式(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public String TimeStamp2Date(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat(formats)
				.format(new java.util.Date(timestamp));
		return date;
	}

}
