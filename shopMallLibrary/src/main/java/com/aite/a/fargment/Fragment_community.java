package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.FoundCommenListActivity;
import com.aite.a.activity.TopicListActivity;
import com.aite.a.activity.TopicdateilsActivity;
import com.aite.a.base.BaseInformation;
import com.aite.a.model.Communityallinfo;
import com.aite.a.model.Topicslistinfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 社区
 * 
 * @author Administrator
 */
public class Fragment_community extends BaseInformation implements
		OnClickListener {

	private GridView gv_topmenu;
	private LinearLayout ll_sliding_1, ll_sliding_2, ll_sliding_3,
			ll_sliding_4;
	private ImageView iv_communityimg;
	private MyGridView mgv_quanzii, gv_pagenumber;
	private TextView tv_shouye, tv_shangyiye, tv_xiayiye, tv_weiye;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private List<String> topmenu;// 顶部菜单
	private Topmenu topmenuadap;
	private MyListView mgv_tuijainhuati;// 推荐话题
	private int currentpager = 0;
	private Communityallinfo communityallinfo;
	private CommunityAdapter communityAdapter;
	private topicAdapter topicadapter;
	private Topicslistinfo topicslistinfo;
	


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case community_all_id:
				if (msg.obj != null) {
					communityallinfo = (Communityallinfo) msg.obj;
					communityAdapter = new CommunityAdapter(
							communityallinfo.datas.list);
					mgv_quanzii.setAdapter(communityAdapter);
				}
				break;
			case topics_list_id:
				if (msg.obj != null) {
					topicslistinfo = (Topicslistinfo) msg.obj;
					topicadapter = new topicAdapter(topicslistinfo);
					mgv_tuijainhuati.setAdapter(topicadapter);
				}
				break;
			}
		};
	};

	public Fragment_community() {
	}

	private void findview() {
		gv_topmenu = (GridView) layout.findViewById(R.id.gv_topmenu);
		ll_sliding_1 = (LinearLayout) layout.findViewById(R.id.ll_sliding_1);
		ll_sliding_2 = (LinearLayout) layout.findViewById(R.id.ll_sliding_2);
		ll_sliding_3 = (LinearLayout) layout.findViewById(R.id.ll_sliding_3);
		ll_sliding_4 = (LinearLayout) layout.findViewById(R.id.ll_sliding_4);
		iv_communityimg = (ImageView) layout.findViewById(R.id.iv_communityimg);
		mgv_quanzii = (MyGridView) layout.findViewById(R.id.mgv_quanzii);
		gv_pagenumber = (MyGridView) layout.findViewById(R.id.gv_pagenumber);
		tv_shouye = (TextView) layout.findViewById(R.id.tv_shouye);
		tv_shangyiye = (TextView) layout.findViewById(R.id.tv_shangyiye);
		tv_xiayiye = (TextView) layout.findViewById(R.id.tv_xiayiye);
		tv_weiye = (TextView) layout.findViewById(R.id.tv_weiye);
		mgv_tuijainhuati = (MyListView) layout
				.findViewById(R.id.mgv_tuijainhuati);
	}

	@Override
	protected void initView() {
		findview();
		tv_shouye.setOnClickListener(this);
		tv_shangyiye.setOnClickListener(this);
		tv_xiayiye.setOnClickListener(this);
		tv_weiye.setOnClickListener(this);
		iv_communityimg.setOnClickListener(this);
		topmenu = new ArrayList<String>();
		topmenu.add(getString(R.string.communitymenu1));
		topmenu.add(getString(R.string.communitymenu2));
		topmenu.add(getString(R.string.communitymenu3));
		topmenu.add(getString(R.string.communitymenu4));
		topmenuadap = new Topmenu();
		// 顶部菜单
		gv_topmenu.setAdapter(topmenuadap);
	}

	@Override
	protected void initData() {
		netRun = new NetRun(getActivity(), handler);
		bitmapUtils = new BitmapUtils(getActivity());
		netRun.communityall("100","0");
	}

	@Override
	protected int getlayoutResId() {
		return R.layout.fragment_community;
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_shouye:
//			// 首页
//			break;
//		case R.id.tv_shangyiye:
//			// 上一页
//			break;
//		case R.id.tv_xiayiye:
//			// 下一页
//			break;
//		case R.id.tv_weiye:
//			// 尾页
//			break;
//		case R.id.iv_communityimg:
//			// 广告
//			break;
//		}
	}

	/**
	 * 改变分类
	 * 
	 * @param index
	 */
	private void setclassify(int index) {
		switch (index) {
		case 0:
			// 全部
			iv_communityimg.setVisibility(View.VISIBLE);
			mgv_quanzii.setVisibility(View.VISIBLE);
			mgv_tuijainhuati.setVisibility(View.GONE);
			currentpager = 0;
			netRun.communityall("100","0");
			break;
		case 1:
			// 推荐圈子
			iv_communityimg.setVisibility(View.VISIBLE);
			mgv_quanzii.setVisibility(View.VISIBLE);
			mgv_tuijainhuati.setVisibility(View.GONE);
			currentpager = 1;
			netRun.communityall("100","1");
			break;
		case 2:
			// 推荐话题
			iv_communityimg.setVisibility(View.GONE);
			mgv_quanzii.setVisibility(View.GONE);
			mgv_tuijainhuati.setVisibility(View.VISIBLE);
			currentpager = 2;
			netRun.Topicslist("1", "0");
			break;
		case 3:
			// 我的圈子
			iv_communityimg.setVisibility(View.GONE);
			mgv_quanzii.setVisibility(View.VISIBLE);
			mgv_tuijainhuati.setVisibility(View.GONE);
			currentpager = 3;
			netRun.MySocial();
			break;
		}
	}

	/**
	 * 圈子适配
	 * 
	 * @author Administrator
	 *
	 */
	private class CommunityAdapter extends BaseAdapter {
		List<Communityallinfo.datas.list> list;

		public CommunityAdapter(List<Communityallinfo.datas.list> list) {
			this.list = list;
		}

		/**
		 * 刷新
		 * 
		 * @param list
		 */
		public void updata(List<Communityallinfo.datas.list> list) {
			if (list == null) {
				return;
			}
			this.list = list;
			notifyDataSetChanged();
		}

		/**
		 * 加载
		 */
		public void adddata(List<Communityallinfo.datas.list> list) {
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
				convertView = View.inflate(getActivity(),
						R.layout.quanzilist_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final Communityallinfo.datas.list list2 = list.get(position);
			if (currentpager == 3) {
				holder.ll_quanbu.setVisibility(View.GONE);
				holder.ll_wodequanzi.setVisibility(View.VISIBLE);
			} else {
				holder.ll_quanbu.setVisibility(View.VISIBLE);
				holder.ll_wodequanzi.setVisibility(View.GONE);
				if (list2.circle_img != null && !list2.circle_img.equals(null)) {
					bitmapUtils.display(holder.imageView1, list2.circle_img);
				} else {
					holder.imageView1
							.setBackgroundResource(R.drawable.no_image);
				}
				holder.tv_quanziname.setText(list2.circle_name);
				holder.tv_topicnum.setText(list2.circle_thcount);
				holder.tv_crewnum.setText(list2.circle_mcount);
			}
			holder.ll_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 详情
					Intent intent = new Intent(getActivity(),TopicListActivity.class);
					intent.putExtra("is_recommend", list2.is_recommend);
					intent.putExtra("circle_id", list2.circle_id);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView imageView1;
			LinearLayout ll_quanbu, ll_wodequanzi,ll_item;
			TextView tv_quanziname, tv_topicnum, tv_crewnum, tv_quanziname2,
					tv_time, tv_mylevel;

			public ViewHolder(View convertView) {
				imageView1 = (ImageView) convertView
						.findViewById(R.id.imageView1);
				ll_quanbu = (LinearLayout) convertView
						.findViewById(R.id.ll_quanbu);
				ll_item = (LinearLayout) convertView
						.findViewById(R.id.ll_item);
				ll_wodequanzi = (LinearLayout) convertView
						.findViewById(R.id.ll_wodequanzi);
				tv_quanziname = (TextView) convertView
						.findViewById(R.id.tv_quanziname);
				tv_topicnum = (TextView) convertView
						.findViewById(R.id.tv_topicnum);
				tv_crewnum = (TextView) convertView
						.findViewById(R.id.tv_crewnum);
				tv_quanziname2 = (TextView) convertView
						.findViewById(R.id.tv_quanziname2);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_mylevel = (TextView) convertView
						.findViewById(R.id.tv_mylevel);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 话题适配
	 * 
	 * @author Administrator
	 *
	 */
	private class topicAdapter extends BaseAdapter {
		Topicslistinfo topicslistinfo;

		public topicAdapter(Topicslistinfo topicslistinfo) {
			this.topicslistinfo = topicslistinfo;
		}

		@Override
		public int getCount() {
			return topicslistinfo.list.size();
		}

		@Override
		public Object getItem(int position) {
			return topicslistinfo.list == null ? null : topicslistinfo.list
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.topic_item,
						null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			final Topicslistinfo.list list = topicslistinfo.list
					.get(position);
			if (list.member_avatar != null
					&& !list.member_avatar.equals("null")) {
				bitmapUtils.display(holder.iv_title, list.member_avatar);
			}else {
				holder.iv_title.setImageResource(R.drawable.no_image);
			}
			holder.tv_author.setText(getString(R.string.hautizuozhe)
					+ list.circle_name);
			holder.tv_centent.setText(list.theme_name);
			holder.tv_time.setText(TimeStamp2Date(list.theme_addtime,
					"yyyy-MM-dd HH:mm:ss"));
			holder.tv_source.setText(getString(R.string.laiziquanzi)
					+ list.circle_name);
			holder.tv_plnum.setText(list.theme_commentcount);
			holder.rl_ite.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							TopicdateilsActivity.class);
					intent.putExtra("theme_id", list.theme_id);
					startActivityForResult(intent, 0);
				}
			});
			holder.iv_zan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 评论
					Intent intent2 = new Intent(getActivity(),
							FoundCommenListActivity.class);
					intent2.putExtra("commend_id", list.theme_id);
					intent2.putExtra("type", "话题");
					startActivityForResult(intent2, 0);
				}
			});
			return convertView;
		}

		class ViewHolder {
			CircleImageView iv_title;
			ImageView iv_zan;
			TextView tv_author, tv_plnum, tv_centent, tv_time, tv_source;
			RelativeLayout rl_ite;
			public ViewHolder(View convertView) {
				iv_title = (CircleImageView) convertView.findViewById(R.id.iv_title);
				iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
				tv_author = (TextView) convertView.findViewById(R.id.tv_author);
				tv_plnum = (TextView) convertView.findViewById(R.id.tv_plnum);
				tv_centent = (TextView) convertView
						.findViewById(R.id.tv_centent);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_source = (TextView) convertView.findViewById(R.id.tv_source);
				rl_ite = (RelativeLayout) convertView.findViewById(R.id.rl_ite);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 页码适配
	 * 
	 * @author Administrator
	 *
	 */
	private class PagernumAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			return convertView;
		}

		class ViewHolder {

			public ViewHolder(View convertView) {
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 顶部菜单适配
	 * 
	 * @author Administrator
	 */
	private class Topmenu extends BaseAdapter {

		@Override
		public int getCount() {
			return topmenu.size();
		}

		@Override
		public Object getItem(int position) {
			return topmenu == null ? null : topmenu.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.information_topmenu_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_menuname.setText(topmenu.get(position));
			holder.tv_menuname.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (position) {
					case 0:
						// 全部
						setclassify(position);
						ll_sliding_1.setVisibility(View.VISIBLE);
						ll_sliding_2.setVisibility(View.INVISIBLE);
						ll_sliding_3.setVisibility(View.INVISIBLE);
						ll_sliding_4.setVisibility(View.INVISIBLE);
						break;
					case 1:
						// 推荐圈子
						setclassify(position);
						ll_sliding_1.setVisibility(View.INVISIBLE);
						ll_sliding_2.setVisibility(View.VISIBLE);
						ll_sliding_3.setVisibility(View.INVISIBLE);
						ll_sliding_4.setVisibility(View.INVISIBLE);
						break;
					case 2:
						// 推荐话题
						setclassify(position);
						ll_sliding_1.setVisibility(View.INVISIBLE);
						ll_sliding_2.setVisibility(View.INVISIBLE);
						ll_sliding_3.setVisibility(View.VISIBLE);
						ll_sliding_4.setVisibility(View.INVISIBLE);
						break;
					case 3:
						// 我的圈子
						setclassify(position);
						ll_sliding_1.setVisibility(View.INVISIBLE);
						ll_sliding_2.setVisibility(View.INVISIBLE);
						ll_sliding_3.setVisibility(View.INVISIBLE);
						ll_sliding_4.setVisibility(View.VISIBLE);
						break;
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_menuname;

			public ViewHolder(View convertView) {
				tv_menuname = (TextView) convertView
						.findViewById(R.id.tv_menuname);
				convertView.setTag(this);
			}
		}
	}


	
	/**
	 * 时间戳转时间
	 * 
	 * @param timestampString
	 * @param formats
	 * @return
	 */
	public String TimeStamp2Date(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat(formats)
				.format(new java.util.Date(timestamp));
		return date;
	}
}
