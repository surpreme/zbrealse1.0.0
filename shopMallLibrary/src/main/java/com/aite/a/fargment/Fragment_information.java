package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aite.a.activity.NewsinfoActivity;
import com.aite.a.adapter.TopMenuAdapter;
import com.aite.a.base.BaseInformation;
import com.aite.a.model.AdvertisinglbInfo;
import com.aite.a.model.InformationInfo;
import com.aite.a.model.InformationclassifyInfo;
import com.aite.a.model.NewsclassifyInfo;
import com.aite.a.model.NewslistifyInfo;
import com.aite.a.model.NewslistifyInfo.news_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyAdGallery.MyOnItemClickListener;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 资讯中心
 *
 * @author Administrator
 *
 */
public class Fragment_information extends BaseInformation implements
		OnClickListener {
	private MyListView mlv_recommended, mlv_recommendedlist;
	private ImageView iv_select;
	private MyAdGallery mag_adgallery;
	private LinearLayout ll_ovalLayout;
	private Recommended recommended;
	private RecommendedList recommendedList;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private RecyclerView id_recyclerview_horizontal;

	private InformationInfo informationInfo;
	private Topmenu topmenu;
	private TopMenuAdapter topMenuAdapter;
	private ListView lv_classify;
	private ScrollView sv_home;
	private InformationclassifyInfo informationclassifyInfo;
	private classifyList classifylist;
	private TextView tv_adgalleryname;
	private List<NewsclassifyInfo> newsclassifyInfo;
	private List<AdvertisinglbInfo> advertisinglbInfo;
	private List<AdvertisinglbInfo> advertisinglbInfo2;
	private List<AdvertisinglbInfo> advertisinglbInfo3;
	private NewslistifyInfo newslistifyInfo;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					if (msg.obj != null) {
						int cur = (int) msg.obj;
						// 改变广告的标题
						tv_adgalleryname
								.setText(setyh(advertisinglbInfo.get(cur).article_title));
					}
					break;
				case article_class_id:
					if (msg.obj != null) {
						// 顶部分类
						newsclassifyInfo = (List<NewsclassifyInfo>) msg.obj;
//					topmenu = new Topmenu(newsclassifyInfo);
//					gv_topmenu.setAdapter(topmenu);
						topMenuAdapter=new TopMenuAdapter(getActivity(),newsclassifyInfo,handler);
						id_recyclerview_horizontal.setAdapter(topMenuAdapter);
						netRun.advertisinglb();
					}
					break;
				case top_advertising_id:
					if (msg.obj != null) {
						// 轮播广告
						advertisinglbInfo = (List<AdvertisinglbInfo>) msg.obj;
						initAdvertising(advertisinglbInfo);
						netRun.topnews();
					}
					break;
				case top_news_id:
					if (msg.obj != null) {
						// 置顶文章
						advertisinglbInfo2 = (List<AdvertisinglbInfo>) msg.obj;
						recommended = new Recommended(advertisinglbInfo2);
						mlv_recommended.setAdapter(recommended);
						netRun.recommendednews();
					}
					break;
				case recommended_news_id:
					if (msg.obj != null) {
						// 推荐文章
						advertisinglbInfo3 = (List<AdvertisinglbInfo>) msg.obj;
						recommendedList = new RecommendedList(advertisinglbInfo3);
						mlv_recommendedlist.setAdapter(recommendedList);
					}
					break;
				case new_slistify_id:
					if (msg.obj != null) {
						// 分类文章列表
						newslistifyInfo = (NewslistifyInfo) msg.obj;
						classifylist = new classifyList(newslistifyInfo.news_list);
						lv_classify.setAdapter(classifylist);
					}
					break;
				case 211:
					if (msg.obj!=null){
						String str= (String) msg.obj;
						setvi2(str);
					}
					break;
			}
		};
	};

	public Fragment_information() {
	}

	private void findView() {
		mlv_recommended = (MyListView) layout
				.findViewById(R.id.mlv_recommended);
		mlv_recommendedlist = (MyListView) layout
				.findViewById(R.id.mlv_recommendedlist);
		iv_select = (ImageView) layout.findViewById(R.id.iv_select);
		mag_adgallery = (MyAdGallery) layout.findViewById(R.id.mag_adgallery);
		ll_ovalLayout = (LinearLayout) layout.findViewById(R.id.ll_ovalLayout);
		id_recyclerview_horizontal = (RecyclerView) layout.findViewById(R.id.id_recyclerview_horizontal);
		lv_classify = (ListView) layout.findViewById(R.id.lv_classify);
		sv_home = (ScrollView) layout.findViewById(R.id.sv_home);
		tv_adgalleryname = (TextView) layout
				.findViewById(R.id.tv_adgalleryname);
	}

	@Override
	protected void initView() {
		findView();
		netRun = new NetRun(getActivity(), handler);
		bitmapUtils = new BitmapUtils(getActivity());
		iv_select.setOnClickListener(this);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		id_recyclerview_horizontal.setLayoutManager(linearLayoutManager);
	}

	@Override
	protected void initData() {
		netRun.newsclassify();
	}

	@Override
	protected int getlayoutResId() {
		return R.layout.fragment_information;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.iv_select){

		}
//		switch (v.getId()) {
//			case R.id.iv_select:
//				if (informationInfo != null) {
////				Intent intent = new Intent(getActivity(), WebActivity.class);
////				Bundle bundle = new Bundle();
////				bundle.putString("url", informationInfo.get_ad.get(0).adv_url);
////				bundle.putString("tv_title_name",
////						informationInfo.get_ad.get(0).adv_title);
////				startActivity(intent);
//				}
//				break;
//		}
	}

	/**
	 * 初始化广告
	 */
	private void initAdvertising(final List<AdvertisinglbInfo> advertisinglbInfo) {
		List<String> listAd = new ArrayList<String>();
		for (AdvertisinglbInfo string : advertisinglbInfo) {
			listAd.add(string.article_image);
		}
		String[] ADurl = listAd.toArray(new String[listAd.size()]);
		if (ADurl.length==0){
			return;
		}
		if (mag_adgallery.mUris == null)
			mag_adgallery.start(getActivity(), ADurl, null, 3000,
					ll_ovalLayout, R.drawable.dot_focused,
					R.drawable.dot_normal);
		mag_adgallery.starthandler(handler);
		mag_adgallery.setMyOnItemClickListener(new MyOnItemClickListener() {

			@Override
			public void onItemClick(int curIndex) {
				Intent intent = new Intent(getActivity(),
						NewsinfoActivity.class);
				intent.putExtra(
						"url",advertisinglbInfo.get(curIndex).article_id);
				startActivity(intent);
//				Bundle bundle = new Bundle();
//				bundle.putString(
//						"url",
//						"http://aitecc.com/wap/index.php?act=cms&op=cms_article_arc_android&article_id="
//								+ advertisinglbInfo.get(curIndex).article_id
//								+ "&class_id="
//								+ advertisinglbInfo.get(curIndex).article_class_id
//								+ "&user_id="
//								+ advertisinglbInfo.get(curIndex).article_publisher_id);
//				bundle.putString("title",
//						advertisinglbInfo.get(curIndex).article_title);
//				Intent intent = new Intent(getActivity(), WebActivity.class);
//				intent.putExtras(bundle);
//				startActivity(intent);
			}
		});
	}

	/**
	 * 选择主页
	 */
	public void setvi() {
		sv_home.setVisibility(View.VISIBLE);
		lv_classify.setVisibility(View.GONE);
	}

	/**
	 * 选择分类
	 */
	private void setvi2(String class_id) {
		sv_home.setVisibility(View.GONE);
		lv_classify.setVisibility(View.VISIBLE);
		netRun.newslistify(class_id, "");
	}

	/**
	 * 顶部菜单适配
	 *
	 * @author Administrator
	 */
	private class Topmenu extends BaseAdapter {
		List<NewsclassifyInfo> newsclassifyInfo;

		public Topmenu(List<NewsclassifyInfo> newsclassifyInfo) {
			this.newsclassifyInfo = newsclassifyInfo;
		}

		@Override
		public int getCount() {
			return newsclassifyInfo.size();
		}

		@Override
		public Object getItem(int position) {
			return newsclassifyInfo == null ? null : newsclassifyInfo
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
				convertView = View.inflate(getActivity(),
						R.layout.information_topmenu_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_menuname
					.setText(newsclassifyInfo.get(position).class_name);
			holder.tv_menuname.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (position) {
						case 0:
							// 行业动态
							setvi2(newsclassifyInfo.get(position).class_id);
							break;
						case 1:
							// 社会百态
							setvi2(newsclassifyInfo.get(position).class_id);
							break;
						case 2:
							// 夺金时刻
							setvi2(newsclassifyInfo.get(position).class_id);
							break;
						case 3:
							// 新闻资讯
							setvi2(newsclassifyInfo.get(position).class_id);
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
	 * 推荐文章适配
	 *
	 * @author Administrator
	 *
	 */
	private class Recommended extends BaseAdapter {
		List<AdvertisinglbInfo> advertisinglbInfo;

		public Recommended(List<AdvertisinglbInfo> advertisinglbInfo) {
			this.advertisinglbInfo = advertisinglbInfo;
		}

		@Override
		public int getCount() {
			return advertisinglbInfo.size();
		}

		@Override
		public Object getItem(int position) {
			return advertisinglbInfo == null ? null : advertisinglbInfo
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
				convertView = View.inflate(getActivity(),
						R.layout.recommendedarticles_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_articlesimg,
					advertisinglbInfo.get(position).article_image);
			holder.tv_articlesname
					.setText(advertisinglbInfo.get(position).article_title);
			holder.tv_time
					.setText(advertisinglbInfo.get(position).article_comment_count
							+ "评\t"
							+ advertisinglbInfo.get(position).article_publish_time);
			holder.ll_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							NewsinfoActivity.class);
					intent.putExtra(
							"url",advertisinglbInfo.get(position).article_id);
					startActivity(intent);
					// netRun.Newsinfo(advertisinglbInfo.get(position).article_id);
					// Bundle bundle = new Bundle();
					//
					// bundle.putString(
					// "url",
					// "http://aitecc.com/wap/index.php?act=cms&op=cms_article_arc_android&article_id="
					// + informationInfo.new_list.get(position).article_id
					// + "&class_id="
					// + informationInfo.new_list.get(position).article_class_id
					// + "&user_id="
					// +
					// informationInfo.new_list.get(position).article_publisher_id);
					// bundle.putString(
					// "title",
					// informationInfo.new_list.get(position).article_title);
					// Intent intent = new Intent(getActivity(),
					// WebActivity.class);
					// intent.putExtras(bundle);
					// startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_articlesimg;
			TextView tv_articlesname, tv_time, tv_zhiding;
			LinearLayout ll_item;

			public ViewHolder(View convertView) {
				iv_articlesimg = (ImageView) convertView
						.findViewById(R.id.iv_articlesimg);
				tv_articlesname = (TextView) convertView
						.findViewById(R.id.tv_articlesname);
				tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				tv_zhiding = (TextView) convertView
						.findViewById(R.id.tv_zhiding);
				ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 文章列表适配
	 *
	 * @author Administrator
	 *
	 */
	private class RecommendedList extends BaseAdapter {
		List<AdvertisinglbInfo> advertisinglbInfo3;

		public RecommendedList(List<AdvertisinglbInfo> advertisinglbInfo3) {
			this.advertisinglbInfo3 = advertisinglbInfo3;
		}

		@Override
		public int getCount() {
			return advertisinglbInfo3.size();
		}

		@Override
		public Object getItem(int position) {
			return advertisinglbInfo3 == null ? null : advertisinglbInfo3
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
				convertView = View.inflate(getActivity(),
						R.layout.recommendedlist_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_img,
					advertisinglbInfo3.get(position).article_image);
			holder.tv_listname
					.setText(advertisinglbInfo3.get(position).article_title);
			holder.tv_listtime
					.setText(advertisinglbInfo3.get(position).article_comment_count
							+ "评       "
							+ advertisinglbInfo3.get(position).article_publish_time);
			holder.ll_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							NewsinfoActivity.class);
					intent.putExtra(
							"url",advertisinglbInfo3.get(position).article_id);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_listname, tv_listtime;
			ImageView iv_img;
			LinearLayout ll_item;

			public ViewHolder(View convertView) {
				tv_listname = (TextView) convertView
						.findViewById(R.id.tv_listname);
				tv_listtime = (TextView) convertView
						.findViewById(R.id.tv_listtime);
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 分类列表
	 *
	 * @author Administrator
	 *
	 */
	private class classifyList extends BaseAdapter {
		List<news_list> news_list;

		public classifyList(List<news_list> news_list) {
			this.news_list = news_list;
		}

		@Override
		public int getCount() {
			return news_list.size();
		}

		@Override
		public Object getItem(int position) {
			return news_list == null ? null : news_list.get(position);
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
						R.layout.recommendedlist_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_img,
					news_list.get(position).article_image);
			holder.tv_listname.setText(news_list.get(position).article_title);
			holder.tv_listtime
					.setText(news_list.get(position).article_comment_count
							+ "评      "
							+ news_list.get(position).article_publish_time);
			holder.ll_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							NewsinfoActivity.class);
					intent.putExtra(
							"url",news_list.get(position).article_id);
					startActivity(intent);
//					netRun.Newsinfo(news_list.get(position).article_id);
					// Bundle bundle = new Bundle();
					// bundle.putString(
					// "url",
					// "http://aitecc.com/wap/index.php?act=cms&op=cms_article_arc_android&article_id="
					// + news_list.get(position).article_id
					// + "&class_id="
					// + news_list.get(position).article_class_id
					// + "&user_id="
					// + news_list.get(position).article_publisher_id);
					// bundle.putString("title",
					// news_list.get(position).article_title);
					// Intent intent = new Intent(getActivity(),
					// WebActivity.class);
					// intent.putExtras(bundle);
					// startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_listname, tv_listtime;
			ImageView iv_img;
			LinearLayout ll_item;

			public ViewHolder(View convertView) {
				tv_listname = (TextView) convertView
						.findViewById(R.id.tv_listname);
				tv_listtime = (TextView) convertView
						.findViewById(R.id.tv_listtime);
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
				convertView.setTag(this);
			}
		}
	}
	private String setyh(String s){
		String replaceFirst = s.replaceFirst("&quot;", "“");
		String replaceAll = replaceFirst.replaceAll("&quot;", "”");
		return replaceAll;
	}
}
