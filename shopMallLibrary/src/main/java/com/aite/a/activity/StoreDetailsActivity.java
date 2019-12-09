package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.aite.a.base.BaseFargmentActivity;
import com.aite.a.fargment.RankingGoodsFargment;
import com.aite.a.model.GoodList;
import com.aite.a.model.StoreInfoo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BooleanLogin;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 店铺详情
 * 
 * @author CAOYOU
 * 
 */
public class StoreDetailsActivity extends BaseFargmentActivity {
	private ImageView store_avatar;
	private RelativeLayout rl_bg;
	private TextView tv_store_name;
	private Button collect_store_bt;
	private TextView hot_ranking_tx,tv_fins;
	private TextView new_ranking_tx;
	private TextView sales_ranking_tx;
	private ImageView ranking_cursor_im;
	private ViewPager ranking_viewpager;
	private MyGridView store_goods_gr;
	private static String store_id;
	private TextView _tv_name;
	private ImageView _iv_back;
	private int imageWidth;
	private int offset;
	private int currentIndex;
	private ArrayList<Fragment> fragmentList;
	private RankingGoodsFargment hot_ranking, new_ranking, sales_ranking;
	private TextView store_more;
	private List<GoodList> goodLists = new ArrayList<GoodList>();
	private StoreInfoo storeDetils = null;
	private boolean finish = true;  //请求没有商品数据只退出一次
	private NetRun netRun;
	private int i = 10;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case goods_list_id:
				List<GoodList> goods = (List<GoodList>) msg.obj;
				if (goods != null && goods.size() > 0) {
					switch (i) {
					case 10:
						i++;
						setRecommendGood(goodLists = goods);
						break;
					case 11:
						i++;
						hot_ranking.setCurrentIndex(0, goods);
						break;
					case 12:
						i++;
						new_ranking.setCurrentIndex(1, goods);
						break;
					case 13:
						i = 0;
						sales_ranking.setCurrentIndex(2, goods);
						break;
					}
				}else if (finish){

					CommonTools.showShortToast(StoreDetailsActivity.this,
							getI18n(R.string.stop_empty));
					finish = false ;
//					finish();
				}
				mdialog.dismiss();
				break;
			case goods_list_err:
				CommonTools.showShortToast(StoreDetailsActivity.this,
						getI18n(R.string.act_net_error));
				mdialog.dismiss();
				break;
			case goods_list_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case collectibles_id:
				if (msg.obj.equals("1")) {
					CommonTools.showShortToast(StoreDetailsActivity.this,
							getI18n(R.string.collection_success));
					netRun.getStoreDetails(store_id);
				} else {
					CommonTools.showShortToast(StoreDetailsActivity.this,
							msg.obj.toString());
				}
				mdialog.dismiss();
				break;
			case collectibles_err:
				mdialog.dismiss();
				CommonTools.showShortToast(StoreDetailsActivity.this,
						getI18n(R.string.act_net_error));
				break;
			case collectibles_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case store_detils_id:
				mdialog.dismiss();
				if (msg.obj != null) {
					storeDetils = (StoreInfoo) msg.obj;
					String store_id2 = storeDetils.getStore_id();
					if (store_id2==null||store_id2.equals("null")||store_id2.equals("")) {
						CommonTools.showShortToast(StoreDetailsActivity.this, getI18n(R.string.noinformation));
//						finish();
					}
					setStoreDetils(storeDetils);
				}
				break;
			case store_detils_err:
				mdialog.dismiss();
				CommonTools.showShortToast(StoreDetailsActivity.this,
						getI18n(R.string.act_net_error));
				break;
			case store_detils_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			}
		};
	};

	public ArrayList<Fragment> getFragmentList() {
		return fragmentList;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_details);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			store_id = bundle.getString("store_id");
		} else {
//			finish();
		}
		bitmapUtils = new BitmapUtils(this);
		netRun = new NetRun(this, handler);
		findViewById();
		initView();
		initCursor(3);
		initData();
	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		tv_store_name = (TextView) findViewById(R.id.tv_store_name);
		rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
		store_avatar = (ImageView) findViewById(R.id.store_avatar);
		collect_store_bt = (Button) findViewById(R.id.collect_store);
		hot_ranking_tx = (TextView) findViewById(R.id.hot_ranking);
		new_ranking_tx = (TextView) findViewById(R.id.new_ranking);
		sales_ranking_tx = (TextView) findViewById(R.id.sales_ranking);
		ranking_cursor_im = (ImageView) findViewById(R.id.ranking_cursor);
		ranking_viewpager = (ViewPager) findViewById(R.id.ranking_viewpager);
		store_goods_gr = (MyGridView) findViewById(R.id.store_goods);
		store_more = (TextView) findViewById(R.id.store_more);
		tv_fins = (TextView) findViewById(R.id.tv_fins);
		hot_ranking_tx.setOnClickListener(clickListener);
		new_ranking_tx.setOnClickListener(clickListener);
		sales_ranking_tx.setOnClickListener(clickListener);
		store_more.setOnClickListener(clickListener);
		collect_store_bt.setOnClickListener(clickListener);
		store_goods_gr.setOnItemClickListener(itemClickListener);
		store_goods_gr.setFocusable(false);
	}

	protected void initView() {
		_iv_back.setOnClickListener(clickListener);
		_tv_name.setText(getI18n(R.string.shop_detail));
		fragmentList = new ArrayList<Fragment>();
		hot_ranking = new RankingGoodsFargment();
		new_ranking = new RankingGoodsFargment();
		sales_ranking = new RankingGoodsFargment();
		fragmentList.add(hot_ranking);
		fragmentList.add(new_ranking);
		fragmentList.add(sales_ranking);
	}

	protected void initData() {
		netRun.getStoreDetails(store_id);
		netRun.getGoodsList("2", "2", "8", "1", null, null, store_id);
		netRun.getGoodsList("2", "2", "8", "1", null, null, store_id);
		netRun.getGoodsList("", "1", "8", "1", null, null, store_id);
		netRun.getGoodsList("1", "2", "8", "1", null, null, store_id);
	}

	protected void setStoreDetils(StoreInfoo storeDetils) {
//		ShopMsgBean shopMsgBean= BeanConvertor.convertBean((String)storeDetils.toString(),ShopMsgBean.class);
		Glide.with(this).load(storeDetils.getAvatar()).into(store_avatar);
		if (storeDetils.getStore_name() == null) {
			tv_store_name.setText(getIntent().getStringExtra("store_name"));
		} else {
			tv_store_name.setText(storeDetils.getStore_name());
		}
		if (storeDetils.getAvatar() != null
				&& !storeDetils.getAvatar().equals("null")
				&& storeDetils.getAvatar() != "") {
			bitmapUtils.display(store_avatar, storeDetils.getAvatar());
			bitmapUtils.display(rl_bg, storeDetils.getStore_banner());
		}
		if (storeDetils.store_collect==null) {
			tv_fins.setText("0");
		}else {
			tv_fins.setText(storeDetils.store_collect);
		}
	}

	public void initCursor(int tagNum) {
		imageWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.cursor).getWidth();
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		Display display = getWindowManager().getDefaultDisplay();
		display.getMetrics(displayMetrics);
		offset = (displayMetrics.widthPixels / tagNum - imageWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		ranking_cursor_im.setImageMatrix(matrix);
		ranking_viewpager.setAdapter(new MyPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		ranking_viewpager.setOffscreenPageLimit(3);
		ranking_viewpager.setCurrentItem(0);
		ranking_viewpager.setOnPageChangeListener(new PageChangeListener());
	}

	protected void setRecommendGood(List<GoodList> good) {
		ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1;
		for (GoodList list : good) {
			map1 = new HashMap<String, Object>();
			map1.put("image", list.getGoods_image_url());
			map1.put("price", list.getGoods_price() + getI18n(R.string.yuan));
			map1.put("name", list.getGoods_name());
			imagelist.add(map1);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist,
				R.layout.goods_list_item2, new String[] { "image", "price",
						"name" }, new int[] { R.id.list_iv_image,
						R.id.list_tv_price, R.id.list_tv_content });
		simpleAdapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof String) {
					ImageView iv = (ImageView) view;
					bitmapUtils.display(iv, data.toString());
					return true;
				}
				return false;
			}
		});
		store_goods_gr.setAdapter(simpleAdapter);
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if(v.getId()==R.id.hot_ranking){
				ranking_viewpager.setCurrentItem(0);
			}else if(v.getId()== R.id.new_ranking){
				ranking_viewpager.setCurrentItem(1);
			}else if(v.getId()==R.id.sales_ranking){
				ranking_viewpager.setCurrentItem(2);
			}else if(v.getId()==R.id._iv_back){
				finish();
			}else if(v.getId()==R.id.store_more){
				intent = new Intent(StoreDetailsActivity.this,
						GoodsListActivity.class);
				intent.putExtra("store_id", store_id);
				startActivity(intent);
			}else if(v.getId()==R.id.collect_store){
				if (BooleanLogin.getInstance().hasLogin(
						StoreDetailsActivity.this)) {
					netRun.addFavorites(store_id, "store");
				}
			}else if(v.getId()==R.id.store_img){
				intent = new Intent();
				intent.setClass(StoreDetailsActivity.this,
						StoreAboutActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("storeInfo", storeDetils);// 序列化
				intent.putExtras(bundle);// 发送数据
				startActivity(intent);// 启动intent
			}


//			switch (v.getId()) {
//			case R.id.hot_ranking:
//				ranking_viewpager.setCurrentItem(0);
//				break;
//			case R.id.new_ranking:
//				ranking_viewpager.setCurrentItem(1);
//				break;
//			case R.id.sales_ranking:
//				ranking_viewpager.setCurrentItem(2);
//				break;
//			case R.id._iv_back:
//				finish();
//				break;
//			case R.id.store_more:
//				intent = new Intent(StoreDetailsActivity.this,
//						GoodsListActivity.class);
//				intent.putExtra("store_id", store_id);
//				startActivity(intent);
//				break;
//			case R.id.collect_store:
//				if (BooleanLogin.getInstance().hasLogin(
//						StoreDetailsActivity.this)) {
//					netRun.addFavorites(store_id, "store");
//				}
//				break;
//			case R.id.store_img:
//				intent = new Intent();
//				intent.setClass(StoreDetailsActivity.this,
//						StoreAboutActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("storeInfo", storeDetils);// 序列化
//				intent.putExtras(bundle);// 发送数据
//				startActivity(intent);// 启动intent
//				break;
//			}
		}
	};
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(StoreDetailsActivity.this,
					ProductDetailsActivity.class);
			intent.putExtra("goods_id", goodLists.get(position).getGoods_id());
			startActivity(intent);

		}
	};

	class MyPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm); // 必须调用
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

	class PageChangeListener implements ViewPager.OnPageChangeListener {
		int one = offset * 2 + imageWidth; // 一个页卡占的偏移

		@Override
		public void onPageSelected(int position) {
			Animation animation = new TranslateAnimation(one * currentIndex,
					one * position, 0, 0);
			currentIndex = position;
			animation.setFillAfter(true);
			animation.setDuration(300);
			ranking_cursor_im.startAnimation(animation);
			switch (position) {
			case 0:
				hot_ranking_tx.setTextColor(getResources().getColor(
						R.color.cursor_text));
				new_ranking_tx.setTextColor(getResources().getColor(
						R.color.black));
				sales_ranking_tx.setTextColor(getResources().getColor(
						R.color.black));
				break;
			case 1:
				hot_ranking_tx.setTextColor(getResources().getColor(
						R.color.black));
				new_ranking_tx.setTextColor(getResources().getColor(
						R.color.cursor_text));
				sales_ranking_tx.setTextColor(getResources().getColor(
						R.color.black));
				break;
			case 2:
				hot_ranking_tx.setTextColor(getResources().getColor(
						R.color.black));
				new_ranking_tx.setTextColor(getResources().getColor(
						R.color.black));
				sales_ranking_tx.setTextColor(getResources().getColor(
						R.color.cursor_text));
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// 当前页面被滑动时调用
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// 当前状态改变时调用
		}
	}

}
