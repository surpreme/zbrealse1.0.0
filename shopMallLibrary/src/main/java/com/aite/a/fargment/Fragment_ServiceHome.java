package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.ServiceDetails;
import com.aite.a.activity.ServiceHallActivity;
import com.aite.a.activity.ServiceSearchActivity;
import com.aite.a.activity.StoreHomePageActivity;
import com.aite.a.activity.WebActivity;
import com.aite.a.base.BaseInformation;
import com.aite.a.base.Mark;
import com.aite.a.model.AdColumnInfo;
import com.aite.a.model.ServiceHomeInfo;
import com.aite.a.model.ServiceHomeInfo.advuplist;
import com.aite.a.model.ServiceHomeInfo.goods_class;
import com.aite.a.model.ServiceHomeInfo.recommend_store_list;
import com.aite.a.model.ServiceHomeInfo.top_class;
import com.aite.a.model.ServiceHomeInfo.tuijian_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CustomScrollView;
import com.aite.a.view.MyAdGallery;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyListView;
import com.aite.a.view.MyAdGallery.MyOnItemClickListener;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

//服务首页
public class Fragment_ServiceHome extends BaseInformation implements
		OnClickListener, Mark {

	private EditText ed_search;
	private ImageView iv_search, main_ib_sort, iv_headlines, iv_advertising1,
			iv_advertising2, iv_advertising3, iv_advertising4, iv_advertising5,
			iv_advertising6, iv_housekeeper, iv_traffic, iv_forwarding, iv_tt;
	private MyAdGallery mag_shuffling;
	private LinearLayout ll_ovalLayout, ll_advertising1, ll_advertising3,
			ll_advertising4, ll_advertisingtwo_1, ll_advertisingtwo_2,
			ll_advertisingtwo_3;
	private MyGridView mgv_navigation;
	private RelativeLayout rl_recommended_serviceproviders,
			rl_recommended_service, rl_advertising2, rl_advertising5,
			rl_advertising6;
	private MyListView mlv_recommended_serviceproviders,
			mlv_recommended_service;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private List<Integer> navigationimg = new ArrayList<Integer>();
	private List<String> navigationtext = new ArrayList<String>();
	private NavigationAdapter navigationAdapter;
	private ViewPager vp_headlines;
	private List<String> headlines = new ArrayList<String>();
	private TextView[] mTextView;
	private MyPageradapter mpageradapter;
	private int current = 0;
	private ServiceHomeInfo serviceHomeInfo;
	private RecommendedAdapter recommendedAdapter;
	private Recommended2Adapter recommended2Adapter;
	private List<AdColumnInfo> ad_Top = new ArrayList<AdColumnInfo>();
	private CustomScrollView sv_gd;

	private Handler activityHandler;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case service_home_id:
				if (msg.obj != null) {
					serviceHomeInfo = (ServiceHomeInfo) msg.obj;
					// 导航适配
					navigationAdapter = new NavigationAdapter(
							serviceHomeInfo.goods_class);
					mgv_navigation.setAdapter(navigationAdapter);
					// 推荐服务商
					recommendedAdapter = new RecommendedAdapter(
							serviceHomeInfo.recommend_store_list);
					mlv_recommended_serviceproviders
							.setAdapter(recommendedAdapter);
					// 推荐服务
					recommended2Adapter = new Recommended2Adapter(
							serviceHomeInfo.tuijian_list);
					mlv_recommended_service.setAdapter(recommended2Adapter);
					// 广告
					initguanggao(serviceHomeInfo.advuplist,
							serviceHomeInfo.top_class);
				}
				break;
			case service_home_err:
				Toast.makeText(getActivity(),
						getString(R.string.act_net_error), Toast.LENGTH_SHORT)
						.show();
				break;
			case home_ad_id:
				if (msg.obj != null) {
					Map<String, Object> map = (Map<String, Object>) msg.obj;
					ad_Top = (List<AdColumnInfo>) map.get("1");
					setAD(ad_Top);
					netRun.servicehome();
				}
				break;
			}
		};
	};

	@Override
	protected void initView() {
		fandview();
	}

	private void fandview() {
		ed_search = (EditText) layout.findViewById(R.id.ed_search);
		iv_search = (ImageView) layout.findViewById(R.id.iv_search);
		main_ib_sort = (ImageView) layout.findViewById(R.id.main_ib_sort);
		mag_shuffling = (MyAdGallery) layout.findViewById(R.id.mag_shuffling);
		ll_ovalLayout = (LinearLayout) layout.findViewById(R.id.ll_ovalLayout);
		mgv_navigation = (MyGridView) layout.findViewById(R.id.mgv_navigation);
		rl_recommended_serviceproviders = (RelativeLayout) layout
				.findViewById(R.id.rl_recommended_serviceproviders);
		rl_recommended_service = (RelativeLayout) layout
				.findViewById(R.id.rl_recommended_service);
		mlv_recommended_serviceproviders = (MyListView) layout
				.findViewById(R.id.mlv_recommended_serviceproviders);
		mlv_recommended_service = (MyListView) layout
				.findViewById(R.id.mlv_recommended_service);
		ll_advertising1 = (LinearLayout) layout
				.findViewById(R.id.ll_advertising1);
		rl_advertising2 = (RelativeLayout) layout
				.findViewById(R.id.rl_advertising2);
		ll_advertising3 = (LinearLayout) layout
				.findViewById(R.id.ll_advertising3);
		ll_advertising4 = (LinearLayout) layout
				.findViewById(R.id.ll_advertising4);
		rl_advertising5 = (RelativeLayout) layout
				.findViewById(R.id.rl_advertising5);
		rl_advertising6 = (RelativeLayout) layout
				.findViewById(R.id.rl_advertising6);
		ll_advertisingtwo_1 = (LinearLayout) layout
				.findViewById(R.id.ll_advertisingtwo_1);
		ll_advertisingtwo_2 = (LinearLayout) layout
				.findViewById(R.id.ll_advertisingtwo_2);
		ll_advertisingtwo_3 = (LinearLayout) layout
				.findViewById(R.id.ll_advertisingtwo_3);
		vp_headlines = (ViewPager) layout.findViewById(R.id.vp_headlines);
		iv_headlines = (ImageView) layout.findViewById(R.id.iv_headlines);
		iv_advertising1 = (ImageView) layout.findViewById(R.id.iv_advertising1);
		iv_advertising2 = (ImageView) layout.findViewById(R.id.iv_advertising2);
		iv_advertising3 = (ImageView) layout.findViewById(R.id.iv_advertising3);
		iv_advertising4 = (ImageView) layout.findViewById(R.id.iv_advertising4);
		iv_advertising5 = (ImageView) layout.findViewById(R.id.iv_advertising5);
		iv_advertising6 = (ImageView) layout.findViewById(R.id.iv_advertising6);
		iv_housekeeper = (ImageView) layout.findViewById(R.id.iv_housekeeper);
		iv_traffic = (ImageView) layout.findViewById(R.id.iv_traffic);
		iv_forwarding = (ImageView) layout.findViewById(R.id.iv_forwarding);
		iv_tt = (ImageView) layout.findViewById(R.id.iv_tt);
		sv_gd = (CustomScrollView) layout.findViewById(R.id.sv_gd);
		initview();
	}

	private void initview() {
		ll_advertising1.setOnClickListener(this);
		rl_advertising2.setOnClickListener(this);
		ll_advertising3.setOnClickListener(this);
		ll_advertising4.setOnClickListener(this);
		rl_advertising5.setOnClickListener(this);
		rl_advertising6.setOnClickListener(this);
		ll_advertisingtwo_1.setOnClickListener(this);
		ll_advertisingtwo_2.setOnClickListener(this);
		ll_advertisingtwo_3.setOnClickListener(this);
		iv_headlines.setOnClickListener(this);
		main_ib_sort.setOnClickListener(this);
		iv_search.setOnClickListener(this);
		iv_tt.setOnClickListener(this);
		rl_recommended_serviceproviders.setOnClickListener(this);
		rl_recommended_service.setOnClickListener(this);
		netRun = new NetRun(getActivity(), handler);
		bitmapUtils = new BitmapUtils(getActivity());
		// 添加导航图片
		// navigationimg.add(R.drawable.service_navigation1);
		// navigationimg.add(R.drawable.service_navigation2);
		// navigationimg.add(R.drawable.service_navigation3);
		// navigationimg.add(R.drawable.service_navigation4);
		// navigationimg.add(R.drawable.service_navigation5);
		// navigationimg.add(R.drawable.service_navigation6);
		// navigationimg.add(R.drawable.service_navigation7);
		// navigationimg.add(R.drawable.service_navigation8);
		// 添加导航文字
		// navigationtext.add("设计");
		// navigationtext.add("营销");
		// navigationtext.add("商务");
		// navigationtext.add("网址建设");
		// navigationtext.add("装修");
		// navigationtext.add("文案");
		// navigationtext.add("生活");
		// navigationtext.add("全部分类");

		// initheadlines();

		// sv_gd.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScroll(int scrollY) {
		//
		// }
		// });
	}

	/**
	 * 初始化广告
	 */
	private void initguanggao(List<advuplist> advuplist, top_class top_class) {
		bitmapUtils.display(iv_tt, top_class.adv_img);
		bitmapUtils.display(iv_advertising1, advuplist.get(0).adv_img);
		bitmapUtils.display(iv_advertising5, advuplist.get(1).adv_img);
		bitmapUtils.display(iv_advertising3, advuplist.get(2).adv_img);
		bitmapUtils.display(iv_advertising4, advuplist.get(3).adv_img);
		bitmapUtils.display(iv_advertising2, advuplist.get(4).adv_img);
		bitmapUtils.display(iv_advertising6, advuplist.get(5).adv_img);
		bitmapUtils.display(iv_housekeeper, advuplist.get(6).adv_img);
		bitmapUtils.display(iv_traffic, advuplist.get(7).adv_img);
		bitmapUtils.display(iv_forwarding, advuplist.get(8).adv_img);
	}

	/**
	 * 初始化头条
	 */
	private void initheadlines() {
		// 头条
		headlines.add("再次超越比尔·盖茨，“一天世界首富”最棒的不是头衔！");
		headlines.add("河北快递员刀捅3同事致1死2伤 辅警制止身中数刀");
		headlines.add("鲁豫采访格力遇尴尬 董明珠差点又当场摔手机了");

		mTextView = new TextView[headlines.size()];
		for (int i = 0; i < headlines.size(); i++) {
			TextView textView = new TextView(getActivity());
			textView.setRotation(-90);
			textView.setLines(2);
			textView.setEllipsize(TruncateAt.END);
			mTextView[i] = textView;
			textView.setText(headlines.get(i));
		}
		mpageradapter = new MyPageradapter();
		vp_headlines.setAdapter(mpageradapter);
		vp_headlines.setOnPageChangeListener(listener);
		setcurrentitem();
	}

	@Override
	protected void initData() {
		netRun.Intex();
	}

	
	@Override
	protected int getlayoutResId() {
		return R.layout.activity_service;
	}

	@SuppressLint({"NewApi", "ValidFragment"})
	public Fragment_ServiceHome() {

	}
	@SuppressLint({"NewApi", "ValidFragment"})
	public Fragment_ServiceHome(Handler handler) {
		this.activityHandler = handler;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.ll_advertising1){
			// 商标查询
			Intent intent = new Intent(getActivity(), WebActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("url", serviceHomeInfo.advuplist.get(0).adv_url);
			intent.putExtras(bundle);
			startActivity(intent);
		}else if(v.getId()==R.id.rl_advertising2){
			// 商标担保
			Intent intent2 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle2 = new Bundle();
			bundle2.putString("url", serviceHomeInfo.advuplist.get(1).adv_url);
			intent2.putExtras(bundle2);
			startActivity(intent2);
		}else if(v.getId()==R.id.ll_advertising3){
			// 开办公司
			Intent intent3 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle3 = new Bundle();
			bundle3.putString("url", serviceHomeInfo.advuplist.get(2).adv_url);
			intent3.putExtras(bundle3);
			startActivity(intent3);
		}else if(v.getId()==R.id.ll_advertising4){
			// 企业问诊
			Intent intent4 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle4 = new Bundle();
			bundle4.putString("url", serviceHomeInfo.advuplist.get(3).adv_url);
			intent4.putExtras(bundle4);
			startActivity(intent4);
		}else if(v.getId()==R.id.rl_advertising5){
			// 法律文书
			Intent intent5 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle5 = new Bundle();
			bundle5.putString("url", serviceHomeInfo.advuplist.get(4).adv_url);
			intent5.putExtras(bundle5);
			startActivity(intent5);
		}else if(v.getId()==R.id.rl_advertising6){
			// 地面推广
			Intent intent6 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle6 = new Bundle();
			bundle6.putString("url", serviceHomeInfo.advuplist.get(5).adv_url);
			intent6.putExtras(bundle6);
			startActivity(intent6);
		}else if(v.getId()==R.id.ll_advertisingtwo_1){
			// 企业管家
			Intent intent7 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle7 = new Bundle();
			bundle7.putString("url", serviceHomeInfo.advuplist.get(6).adv_url);
			intent7.putExtras(bundle7);
			startActivity(intent7);
		}else if(v.getId()==R.id.ll_advertisingtwo_2){
			// 淘宝流量
			Intent intent8 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle8 = new Bundle();
			bundle8.putString("url", serviceHomeInfo.advuplist.get(7).adv_url);
			intent8.putExtras(bundle8);
			startActivity(intent8);
		}else if(v.getId()==R.id.ll_advertisingtwo_3){
			// 朋友转发
			Intent intent9 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle9 = new Bundle();
			bundle9.putString("url", serviceHomeInfo.advuplist.get(8).adv_url);
			intent9.putExtras(bundle9);
			startActivity(intent9);
		}else if(v.getId()==R.id.iv_headlines){
			// 头条
			vp_headlines.setRotation(90);
		}else if(v.getId()== R.id.main_ib_sort){
			// 分类
			sv_gd.smoothScrollTo(0, mgv_navigation.getTop());
		}else if(v.getId()==R.id.iv_search){
			// 搜索
			search();
		}else if(v.getId()== R.id.iv_tt){
			// 广告
			Intent intent10 = new Intent(getActivity(), WebActivity.class);
			Bundle bundle10 = new Bundle();
			bundle10.putString("url", serviceHomeInfo.top_class.adv_url);
			intent10.putExtras(bundle10);
			startActivity(intent10);
		}else if(v.getId()==R.id.rl_recommended_serviceproviders){
			// 服务商
			Message msg1 = activityHandler.obtainMessage();
			msg1.what = change_fragment2;
			activityHandler.sendMessage(msg1);
		}else if(v.getId()==R.id.rl_recommended_service){
			// 服务
			Message msg = activityHandler.obtainMessage();
			msg.what = change_fragment;
			activityHandler.sendMessage(msg);
		}
//		switch (v.getId()) {
//		case R.id.ll_advertising1:
//			// 商标查询
//			Intent intent = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putString("url", serviceHomeInfo.advuplist.get(0).adv_url);
//			intent.putExtras(bundle);
//			startActivity(intent);
//			break;
//		case R.id.rl_advertising2:
//			// 商标担保
//			Intent intent2 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle2 = new Bundle();
//			bundle2.putString("url", serviceHomeInfo.advuplist.get(1).adv_url);
//			intent2.putExtras(bundle2);
//			startActivity(intent2);
//			break;
//		case R.id.ll_advertising3:
//			// 开办公司
//			Intent intent3 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle3 = new Bundle();
//			bundle3.putString("url", serviceHomeInfo.advuplist.get(2).adv_url);
//			intent3.putExtras(bundle3);
//			startActivity(intent3);
//			break;
//		case R.id.ll_advertising4:
//			// 企业问诊
//			Intent intent4 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle4 = new Bundle();
//			bundle4.putString("url", serviceHomeInfo.advuplist.get(3).adv_url);
//			intent4.putExtras(bundle4);
//			startActivity(intent4);
//			break;
//		case R.id.rl_advertising5:
//			// 法律文书
//			Intent intent5 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle5 = new Bundle();
//			bundle5.putString("url", serviceHomeInfo.advuplist.get(4).adv_url);
//			intent5.putExtras(bundle5);
//			startActivity(intent5);
//			break;
//		case R.id.rl_advertising6:
//			// 地面推广
//			Intent intent6 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle6 = new Bundle();
//			bundle6.putString("url", serviceHomeInfo.advuplist.get(5).adv_url);
//			intent6.putExtras(bundle6);
//			startActivity(intent6);
//			break;
//		case R.id.ll_advertisingtwo_1:
//			// 企业管家
//			Intent intent7 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle7 = new Bundle();
//			bundle7.putString("url", serviceHomeInfo.advuplist.get(6).adv_url);
//			intent7.putExtras(bundle7);
//			startActivity(intent7);
//			break;
//		case R.id.ll_advertisingtwo_2:
//			// 淘宝流量
//			Intent intent8 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle8 = new Bundle();
//			bundle8.putString("url", serviceHomeInfo.advuplist.get(7).adv_url);
//			intent8.putExtras(bundle8);
//			startActivity(intent8);
//			break;
//		case R.id.ll_advertisingtwo_3:
//			// 朋友转发
//			Intent intent9 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle9 = new Bundle();
//			bundle9.putString("url", serviceHomeInfo.advuplist.get(8).adv_url);
//			intent9.putExtras(bundle9);
//			startActivity(intent9);
//			break;
//		case R.id.iv_headlines:
//			// 头条
//			vp_headlines.setRotation(90);
//			break;
//		case R.id.main_ib_sort:
//			// 分类
//			sv_gd.smoothScrollTo(0, mgv_navigation.getTop());
//			break;
//		case R.id.iv_search:
//			// 搜索
//			search();
//			break;
//		case R.id.iv_tt:
//			// 广告
//			Intent intent10 = new Intent(getActivity(), WebActivity.class);
//			Bundle bundle10 = new Bundle();
//			bundle10.putString("url", serviceHomeInfo.top_class.adv_url);
//			intent10.putExtras(bundle10);
//			startActivity(intent10);
//			break;
//		case R.id.rl_recommended_serviceproviders:
//			// 服务商
//			Message msg1 = activityHandler.obtainMessage();
//			msg1.what = change_fragment2;
//			activityHandler.sendMessage(msg1);
//			break;
//		case R.id.rl_recommended_service:
//			// 服务
//			Message msg = activityHandler.obtainMessage();
//			msg.what = change_fragment;
//			activityHandler.sendMessage(msg);
//			break;
//		}
	}

	/**
	 * 搜索
	 */
	private void search() {
		if (TextUtils.isEmpty(ed_search.getText().toString())) {
			Toast.makeText(getActivity(),
					getString(R.string.put_in_goods_name), Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		Intent intent = new Intent(getActivity(), ServiceSearchActivity.class);
		intent.putExtra("name", ed_search.getText().toString());
		startActivity(intent);
	}

	/**
	 * 设置广告轮播
	 * 
	 * @param obj
	 */
	protected void setAD(final List<AdColumnInfo> obj) {
		List<String> listAd = new ArrayList<String>();
		for (AdColumnInfo string : obj) {
			listAd.add(string.image);
		}
		String[] ADurl = listAd.toArray(new String[listAd.size()]);
		if (mag_shuffling.mUris == null)
			mag_shuffling.start(getActivity(), ADurl, null, 3000,
					ll_ovalLayout, R.drawable.dot_focused,
					R.drawable.dot_normal);
		mag_shuffling.setMyOnItemClickListener(new MyOnItemClickListener() {

			@Override
			public void onItemClick(int curIndex) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("url", obj.get(curIndex).data);
				bundle.putString("title", obj.get(curIndex).type);
				intent.putExtras(bundle);
				startActivity(intent);
				// openActivity(WebActivity.class, bundle);
			}
		});
	}

	/**
	 * 导航适配
	 * 
	 * @author Administrator
	 */
	private class NavigationAdapter extends BaseAdapter {
		List<goods_class> goods_class;

		public NavigationAdapter(List<goods_class> goods_class) {
			this.goods_class = goods_class;
		}

		@Override
		public int getCount() {
			return 8;
		}

		@Override
		public Object getItem(int position) {
			return goods_class == null ? null : goods_class.get(position);
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
						R.layout.miyamenuitem, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_miyamenuiag,
					goods_class.get(position).pic);
			// holder.iv_miyamenuiag.setImageResource(goods_class.get(position).pic);
			holder.tv_miyamenutext.setText(goods_class.get(position).gc_name);
			holder.ll_svhmitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							ServiceHallActivity.class);
					intent.putExtra("name", goods_class.get(position).gc_name);
					intent.putExtra("gc_id", goods_class.get(position).gc_id);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_miyamenuiag;
			TextView tv_miyamenutext;
			LinearLayout ll_svhmitem;

			public ViewHolder(View convertView) {
				iv_miyamenuiag = (ImageView) convertView
						.findViewById(R.id.iv_miyamenuiag);
				tv_miyamenutext = (TextView) convertView
						.findViewById(R.id.tv_miyamenutext);
				ll_svhmitem = (LinearLayout) convertView
						.findViewById(R.id.ll_svhmitem);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 推荐服务商适配
	 * 
	 * @author Administrator
	 *
	 */
	private class RecommendedAdapter extends BaseAdapter {
		List<recommend_store_list> recommend_store_list;

		public RecommendedAdapter(
				List<recommend_store_list> recommend_store_list) {
			this.recommend_store_list = recommend_store_list;
		}

		@Override
		public int getCount() {
			return recommend_store_list.size();
		}

		@Override
		public Object getItem(int position) {
			return recommend_store_list == null ? null : recommend_store_list
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
						R.layout.recommended_serviceproviders_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_serviceprovidersimg,
					recommend_store_list.get(position).store_image_url);
			holder.tv_serviceprovidersname.setText(recommend_store_list
					.get(position).store_name);
			holder.tv_serviceproviderstext.setText(recommend_store_list
					.get(position).store_sales);
			holder.tv_chance.setText(recommend_store_list.get(position).st);

			holder.ll_stitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							StoreHomePageActivity.class);
					intent.putExtra("store_id",
							recommend_store_list.get(position).store_id);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_serviceprovidersimg;
			TextView tv_serviceprovidersname, tv_serviceproviderstext,
					tv_chance;
			LinearLayout ll_stitem;

			public ViewHolder(View convertView) {
				iv_serviceprovidersimg = (ImageView) convertView
						.findViewById(R.id.iv_serviceprovidersimg);
				tv_serviceprovidersname = (TextView) convertView
						.findViewById(R.id.tv_serviceprovidersname);
				tv_serviceproviderstext = (TextView) convertView
						.findViewById(R.id.tv_serviceproviderstext);
				tv_chance = (TextView) convertView.findViewById(R.id.tv_chance);
				ll_stitem = (LinearLayout) convertView
						.findViewById(R.id.ll_stitem);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 推荐服务适配
	 * 
	 * @author Administrator
	 *
	 */
	private class Recommended2Adapter extends BaseAdapter {
		List<tuijian_list> tuijian_list;

		public Recommended2Adapter(List<tuijian_list> tuijian_list) {
			this.tuijian_list = tuijian_list;
		}

		@Override
		public int getCount() {
			return tuijian_list.size();
		}

		@Override
		public Object getItem(int position) {
			return tuijian_list == null ? null : tuijian_list.get(position);
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
						R.layout.recommended_servic_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			bitmapUtils.display(holder.iv_serviceprovidersimg2,
					tuijian_list.get(position).goods_image_url);
			holder.tv_serviceprovidersname2
					.setText(tuijian_list.get(position).goods_name);
			holder.tv_type.setText(tuijian_list.get(position).gc);
			holder.tv_price.setText(tuijian_list.get(position).goods_price);
			holder.tv_sellnumber
					.setText(tuijian_list.get(position).goods_salenum);
			holder.ll_svitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 详情
					Intent intent = new Intent(getActivity(),
							ServiceDetails.class);
					intent.putExtra("goods_id",
							tuijian_list.get(position).goods_id);
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_serviceprovidersimg2;
			TextView tv_serviceprovidersname2, tv_type, tv_price,
					tv_sellnumber;
			LinearLayout ll_svitem;

			public ViewHolder(View convertView) {
				iv_serviceprovidersimg2 = (ImageView) convertView
						.findViewById(R.id.iv_serviceprovidersimg2);
				tv_serviceprovidersname2 = (TextView) convertView
						.findViewById(R.id.tv_serviceprovidersname2);
				tv_type = (TextView) convertView.findViewById(R.id.tv_type);
				tv_price = (TextView) convertView.findViewById(R.id.tv_fcfpricee);
				tv_sellnumber = (TextView) convertView
						.findViewById(R.id.tv_sellnumber);
				ll_svitem = (LinearLayout) convertView
						.findViewById(R.id.ll_svitem);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 头条
	 */
	private class MyPageradapter extends PagerAdapter {
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		// 断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		// 是从ViewGroup中移出当前View
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		// 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(mTextView[position
					% mTextView.length], 0);
			return mTextView[position % mTextView.length];
		}
	}

	/**
	 * 广告监听
	 */
	private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			// Log.i("------------",position+"");
			current = position;
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	/**
	 * 头条自动滚动
	 */
	private void setcurrentitem() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							vp_headlines.setCurrentItem(current + 1);
						}
					});
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
