package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.MyTeamInfo;
import com.aite.a.model.MyTeamInfo.datas.wx_levels;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 我的团队
 * 
 * @author Administrator
 *
 */
public class MyTeamActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_bcreturn;
	private TextView tv_bctitle, tv_nodata;
	private PullToRefreshLayout refresh_view;
	private PullableListView lv_list;
	private int refreshtype = 0, curpage = 1;
	private MyListener myListenr;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private MyTeamInfo myTeamInfo;
	private MyAdapter myAdapter;
	private String level = "1";
	private RecyclerView id_recyclerview_horizontal;
	private MenuAdapter menuAdapter;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case my_team_id:
				if (msg.obj != null) {
					myTeamInfo = (MyTeamInfo) msg.obj;
					menuAdapter = new MenuAdapter(myTeamInfo.datas.wx_levels);
					id_recyclerview_horizontal.setAdapter(menuAdapter);
					if (myTeamInfo.datas.list == null
							|| myTeamInfo.datas.list.size() == 0) {
						tv_nodata.setVisibility(View.VISIBLE);
					} else {
						tv_nodata.setVisibility(View.GONE);
					}
					System.out.println("----------------刷新  "+refreshtype);
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(myTeamInfo.datas.list);
						lv_list.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(myTeamInfo.datas.list);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(myTeamInfo.datas.list);
						myListenr.loadMoreSucceed();
					}
				}
				break;
			case my_team_err:
				tv_nodata.setVisibility(View.VISIBLE);
				Toast.makeText(MyTeamActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myteam);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		lv_list = (PullableListView) findViewById(R.id.lv_list);
		tv_bctitle.setText(getString(R.string.distribution_center18));
		id_recyclerview_horizontal = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
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
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		id_recyclerview_horizontal.setLayoutManager(linearLayoutManager);

		netRun.MyTeam("20", curpage + "", level);

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
	 * 菜单栏
	 * 
	 * @author Administrator
	 *
	 */
	class MenuAdapter extends RecyclerView.Adapter<ViewHolder2> {
		List<wx_levels> wx_levels;

		public MenuAdapter(List<wx_levels> wx_levels) {
			this.wx_levels = wx_levels;
		}

		@Override
		public int getItemCount() {
			return wx_levels.size();
		}

		@Override
		public void onBindViewHolder(ViewHolder2 arg0, final int arg1) {
			MyTeamInfo.datas.wx_levels wx_levels2 = wx_levels.get(arg1);
			arg0.tv_menu.setText(wx_levels2.level_name+"（"+wx_levels2.my_member_count+"）");
			arg0.tv_menu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					level=(arg1+1)+"";
					refreshtype = 1;
					netRun.MyTeam("20", curpage + "", level);
				}
			});
		}

		@Override
		public ViewHolder2 onCreateViewHolder(ViewGroup parent, int arg1) {
			ViewHolder2 holder = new ViewHolder2(LayoutInflater.from(
					MyTeamActivity.this).inflate(R.layout.item_teammenu,
					parent, false));
			return holder;
		}
	}

	class ViewHolder2 extends RecyclerView.ViewHolder {
		TextView tv_menu;

		public ViewHolder2(View itemView) {
			super(itemView);
			tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
		}
	}

	/**
	 * 列表
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<MyTeamInfo.datas.list> list;

		public MyAdapter(List<MyTeamInfo.datas.list> list) {
			this.list = list;
		}

		/**
		 * 刷新
		 * 
		 * @param list
		 */
		public void updata(List<MyTeamInfo.datas.list> list) {
			if (list == null) {
				return;
			}
			this.list = list;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(List<MyTeamInfo.datas.list> list) {
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
				convertView = View.inflate(MyTeamActivity.this,
						R.layout.item_myteam, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			MyTeamInfo.datas.list list2 = list.get(position);
			bitmapUtils.display(holder.iv_icon, list2.avator);
			holder.tv_name.setText(list2.member_name);
			holder.tv_tjr.setText(getString(R.string.distribution_center19)
					+ " " + list2.parent_name);
			holder.tv_consumption
					.setText(getString(R.string.distribution_center20) + " "
							+ list2.buy_count + getString(R.string.serviceproviders4));
			holder.tv_cumulative
					.setText(getString(R.string.distribution_center21) + " "
							+ list2.commission);
			return convertView;
		}

		class ViewHodler {
			TextView tv_name, tv_tjr, tv_consumption, tv_cumulative;
			ImageView iv_icon;

			public ViewHodler(View convertView) {
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_tjr = (TextView) convertView.findViewById(R.id.tv_tjr);
				tv_consumption = (TextView) convertView
						.findViewById(R.id.tv_consumption);
				tv_cumulative = (TextView) convertView
						.findViewById(R.id.tv_cumulative);
				iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
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
			netRun.MyTeam("20", curpage + "", level);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (myTeamInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.MyTeam("20", curpage + "", level);
			} else {
				loadMoreSucceed();
				Toast.makeText(MyTeamActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
