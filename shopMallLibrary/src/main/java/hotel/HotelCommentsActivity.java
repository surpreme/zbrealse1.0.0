package hotel;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.HotelCommentsInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aite.a.view.MyProgressBar;
import com.aite.a.view.ProgressRing;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullToRefreshLayout.OnRefreshListener;
import com.aite.a.view.PullableScrollView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 酒店评价
 * 
 * @author Administrator
 *
 */
public class HotelCommentsActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_back;
	private TextView tv_title, tv_score, tv_score2, tv_score3, tv_score4,
			tv_score5, tv_menu1, tv_menu2, tv_menu3, tv_menu4;
	private ProgressRing pr_score1;
	private MyProgressBar pr_score2, pr_score3, pr_score4, pr_score5;
	private View v_1, v_2, v_3, v_4;
	private MyListView mv_list;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private String hotel_id = "", type = "all", page = "20";
	private int curpage = 1, refreshtype = 0;
	private PullToRefreshLayout refresh_view;
	private PullableScrollView pv_sv;
	private MyListener myListenr;
	private MyAdapter myAdapter;
	private HotelCommentsInfo hotelCommentsInfo;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case comments_datails_id:
				if (msg.obj != null) {
					hotelCommentsInfo = (HotelCommentsInfo) msg.obj;
					// 总评
					float fractions = getFractions(hotelCommentsInfo.datas.avg_info.avg_total);
					pr_score1.setProgress(fractions);
					tv_score.setText(fractions + getString(R.string.minus));
					// 卫生
					float fractions2 = getFractions(hotelCommentsInfo.datas.avg_info.avg_health_score);
					pr_score4.setProgress(fractions2);
					tv_score4.setText(fractions2 +  getString(R.string.minus));
					// 设施
					float fractions3 = getFractions(hotelCommentsInfo.datas.avg_info.avg_sheshi_score);
					pr_score5.setProgress(fractions3);
					tv_score5.setText(fractions3 + getString(R.string.minus));
					// 服务
					float fractions4 = getFractions(hotelCommentsInfo.datas.avg_info.avg_service_score);
					pr_score3.setProgress(fractions4);
					tv_score3.setText(fractions4 +  getString(R.string.minus));
					// 位置
					float fractions5 = getFractions(hotelCommentsInfo.datas.avg_info.avg_traffic_score);
					pr_score2.setProgress(fractions5);
					tv_score2.setText(fractions5 +  getString(R.string.minus));
					// 全部
					tv_menu1.setText(getString(R.string.m_all)+"\n("
							+ hotelCommentsInfo.datas.eval_total + ")");
					// 好评
					tv_menu2.setText(getString(R.string.beidianzan)+"\n("
							+ hotelCommentsInfo.datas.goods_total + ")");
					// 中评
					tv_menu3.setText(getString(R.string.order_reminder177)+"\n("
							+ hotelCommentsInfo.datas.general_total + ")");
					// 差评
					tv_menu4.setText(getString(R.string.order_reminder178)+"\n("
							+ hotelCommentsInfo.datas.bad_total + ")");
					if (refreshtype == 0) {
						myAdapter = new MyAdapter(hotelCommentsInfo.datas.list);
						mv_list.setAdapter(myAdapter);
					} else if (refreshtype == 1) {
						// 刷新
						myAdapter.updata(hotelCommentsInfo.datas.list);
						myListenr.refreshSucceed();
					} else if (refreshtype == 2) {
						// 加载
						myAdapter.adddata(hotelCommentsInfo.datas.list);
						myListenr.loadMoreSucceed();
					}
				}
				break;
			case comments_datails_err:
				Toast.makeText(HotelCommentsActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_score = (TextView) findViewById(R.id.tv_score);
		tv_score2 = (TextView) findViewById(R.id.tv_score2);
		tv_score3 = (TextView) findViewById(R.id.tv_score3);
		tv_score4 = (TextView) findViewById(R.id.tv_score4);
		tv_score5 = (TextView) findViewById(R.id.tv_score5);
		tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
		tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
		tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
		tv_menu4 = (TextView) findViewById(R.id.tv_menu4);
		pr_score1 = (ProgressRing) findViewById(R.id.pr_score1);
		pr_score2 = (MyProgressBar) findViewById(R.id.pr_score2);
		pr_score3 = (MyProgressBar) findViewById(R.id.pr_score3);
		pr_score4 = (MyProgressBar) findViewById(R.id.pr_score4);
		pr_score5 = (MyProgressBar) findViewById(R.id.pr_score5);
		v_1 = findViewById(R.id.v_1);
		v_2 = findViewById(R.id.v_2);
		v_3 = findViewById(R.id.v_3);
		v_4 = findViewById(R.id.v_4);
		mv_list = (MyListView) findViewById(R.id.mv_list);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		pv_sv = (PullableScrollView) findViewById(R.id.pv_sv);
		initView();
	}

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		iv_back.setOnClickListener(this);
		tv_menu1.setOnClickListener(this);
		tv_menu2.setOnClickListener(this);
		tv_menu3.setOnClickListener(this);
		tv_menu4.setOnClickListener(this);
		myListenr = new MyListener();
		refresh_view.setOnRefreshListener(myListenr);
		hotel_id = getIntent().getStringExtra("hotel_id");
		initData();
	}

	@Override
	protected void initData() {
		netRun.CommentsDatails(hotel_id, type, page, curpage + "");
	}

	@Override
	public void ReGetData() {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_back) {
			finish();
		} else if (id == R.id.tv_menu1) {// 全部
			type = "all";
			curpage = 1;
			netRun.CommentsDatails(hotel_id, type, page, curpage + "");
			setMenu(tv_menu1, v_1);
		} else if (id == R.id.tv_menu2) {// 好评
			type = "good";
			curpage = 1;
			netRun.CommentsDatails(hotel_id, type, page, curpage + "");
			setMenu(tv_menu2, v_2);
		} else if (id == R.id.tv_menu3) {// 中评
			type = "zhong";
			curpage = 1;
			netRun.CommentsDatails(hotel_id, type, page, curpage + "");
			setMenu(tv_menu3, v_3);
		} else if (id == R.id.tv_menu4) {// 差评
			type = "cha";
			curpage = 1;
			netRun.CommentsDatails(hotel_id, type, page, curpage + "");
			setMenu(tv_menu4, v_4);
		}
	}

	/**
	 * 评论
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<HotelCommentsInfo.datas.list> list;

		public MyAdapter(
				List<HotelCommentsInfo.datas.list> list) {
			this.list = list;
		}

		/**
		 * 刷新
		 * 
		 * @param list
		 */
		public void updata(
				List<HotelCommentsInfo.datas.list> list) {
			if (list == null) {
				return;
			}
			this.list = list;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(
				List<HotelCommentsInfo.datas.list> list) {
			if (list == null) {
				return;
			}
			this.list.addAll(list);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return list.size();
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
				convertView = View.inflate(HotelCommentsActivity.this,
						R.layout.item_hotelcommentslist, null);
				new ViewHodler(convertView);
			}
			ViewHodler holder = (ViewHodler) convertView.getTag();
			HotelCommentsInfo.datas.list list2 = list
					.get(position);
			holder.tv_score.setText(list2.avg_score +  getString(R.string.minus));
			holder.tv_name.setText(list2.member_name);
			holder.tv_content.setText(list2.eval_con);
			holder.tv_time.setText(TimeStamp2Date(list2.add_time, "yyyy-MM-dd")
					+ getString(R.string.find_reminder137));
			return convertView;
		}

		class ViewHodler {
			TextView tv_score, tv_name, tv_time, tv_content;

			public ViewHodler(View convertView) {
				tv_score = (TextView) convertView.findViewById(R.id.tv_score);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_content = (TextView) convertView
						.findViewById(R.id.tv_content);
				convertView.setTag(this);
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

	/**
	 * 修改选中菜单
	 * 
	 * @param txt
	 * @param v
	 */
	private void setMenu(TextView txt, View v) {
		tv_menu1.setTextColor(0xff6D6D6D);
		tv_menu2.setTextColor(0xff6D6D6D);
		tv_menu3.setTextColor(0xff6D6D6D);
		tv_menu4.setTextColor(0xff6D6D6D);
		v_1.setVisibility(View.INVISIBLE);
		v_2.setVisibility(View.INVISIBLE);
		v_3.setVisibility(View.INVISIBLE);
		v_4.setVisibility(View.INVISIBLE);
		v.setVisibility(View.VISIBLE);
		txt.setTextColor(0xff0092DD);
	}

	/**
	 * 获取分数
	 * 
	 * @param str
	 * @return
	 */
	public float getFractions(String str) {
		try {
			if (str == null || str.equals("null") || str.equals("")) {
				return 0;
			}
			return Float.parseFloat(str);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 刷新监听
	 * 
	 * @author Administrator
	 *
	 */
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
			netRun.CommentsDatails(hotel_id, type, page, curpage + "");
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			this.pullToRefreshLayout = pullToRefreshLayout;
			if (hotelCommentsInfo.hasmore.equals("true")) {
				refreshtype = 2;
				curpage++;
				netRun.CommentsDatails(hotel_id, type, page, curpage + "");
			} else {
				loadMoreSucceed();
				Toast.makeText(HotelCommentsActivity.this,
						getString(R.string.act_no_data_load),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
