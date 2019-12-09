package com.aite.a.fargment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.CommunityGoods;
import com.aite.a.activity.FoundCommenListActivity;
import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.activity.StoreAboutActivity;
import com.aite.a.base.BaseInformation;
import com.aite.a.model.CasuallyLook;
import com.aite.a.model.CasuallyLook.goods_list;
import com.aite.a.model.ShopStreetInfo;
import com.aite.a.model.StaffPicksInfo;
import com.aite.a.model.StaffPicksInfo.list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CustomScrollView;
import com.aite.a.view.CustomScrollView.OnScrollListener;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 艾特微发现
 * 
 * @author Administrator
 *
 */
public class Fragment_found extends BaseInformation implements OnClickListener,
		OnScrollListener {
	private GridView gv_topmenu;
	private MyGridView mgv_goodslist;
	private TextView tv_shouye, tv_shangyiye, tv_xiayiye, tv_weiye,
			tv_pagenumber, tv_more;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private LinearLayout ll_sliding_1, ll_sliding_2, ll_sliding_3,
			ll_sliding_4;
	private ListView lv_shop;
	private List<String> topmenu;// 顶部菜单
	private Topmenu topadapter;
	private CasuallyLook casuallyLook;
	private List<Integer> number = new ArrayList<Integer>();
	private int pagination = 1;
	private Goodslistadapter goodslistadapter;
	private CustomScrollView sc_gd;
	private LinearLayout ll_content;
	private TextView tv_zan = null;
	private StaffPicksInfo staffPicksInfo;
	private StaffPicksadapter staffPicksadapter;
	private ShopStreetInfo shopStreetInfo;
	private Shopadapter shopadapter;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case casually_look_id:
				if (msg.obj != null) {
					casuallyLook = (CasuallyLook) msg.obj;
					// 商品适配
					// if (goodslistadapter == null) {
					goodslistadapter = new Goodslistadapter(
							casuallyLook.goods_list);
					mgv_goodslist.setAdapter(goodslistadapter);
					// addList
					// } else {
					// goodslistadapter.addList(casuallyLook.goods_list);
					// }
				}
				break;
			case casually_look_err:

				break;
			case look_praise_id:
				if (msg.obj != null) {
					String re = (String) msg.obj;
					if (re.equals("1")) {
						if (tv_zan != null) {
							int parseInt = Integer.parseInt(tv_zan.getText()
									.toString());
							parseInt++;
							tv_zan.setText(parseInt + "");
							Toast.makeText(getActivity(),
									getString(R.string.praisesuccess),
									Toast.LENGTH_SHORT).show();
							return;
						}
					}
					Toast.makeText(getActivity(), re, Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case good_things_id:
				if (msg.obj != null) {
					staffPicksInfo = (StaffPicksInfo) msg.obj;
					// 达人推荐适配
					// if (staffPicksadapter == null) {
					staffPicksadapter = new StaffPicksadapter(
							staffPicksInfo.list);
					mgv_goodslist.setAdapter(staffPicksadapter);
					// } else {
					// staffPicksadapter.addList(staffPicksadapter.list);
					// }
				}
				break;
			case shop_street_id:
				//店铺街
				if (msg.obj != null) {
					shopStreetInfo = (ShopStreetInfo) msg.obj;
					shopadapter = new Shopadapter(shopStreetInfo);
					lv_shop.setAdapter(shopadapter);
				}
				break;
			}
		};
	};

	public Fragment_found() {
	}

	private void finview() {
		gv_topmenu = (GridView) layout.findViewById(R.id.gv_topmenu);
		mgv_goodslist = (MyGridView) layout.findViewById(R.id.mgv_goodslist);
		tv_shouye = (TextView) layout.findViewById(R.id.tv_shouye);
		tv_shangyiye = (TextView) layout.findViewById(R.id.tv_shangyiye);
		tv_xiayiye = (TextView) layout.findViewById(R.id.tv_xiayiye);
		tv_weiye = (TextView) layout.findViewById(R.id.tv_weiye);
		ll_sliding_1 = (LinearLayout) layout.findViewById(R.id.ll_sliding_1);
		ll_sliding_2 = (LinearLayout) layout.findViewById(R.id.ll_sliding_2);
		ll_sliding_3 = (LinearLayout) layout.findViewById(R.id.ll_sliding_3);
		ll_sliding_4 = (LinearLayout) layout.findViewById(R.id.ll_sliding_4);
		lv_shop = (ListView) layout.findViewById(R.id.lv_shop);
		tv_pagenumber = (TextView) layout.findViewById(R.id.tv_pagenumber);
		tv_more = (TextView) layout.findViewById(R.id.tv_more);
		sc_gd = (CustomScrollView) layout.findViewById(R.id.sc_gd);
		ll_content = (LinearLayout) layout.findViewById(R.id.ll_content);
		sc_gd.setOnScrollListener(this);

	}

	@Override
	public void onScroll(int scrollY) {
		if (scrollY == (ll_content.getHeight() - sc_gd.getHeight())) {
			// 加载更多
			// pagination++;
			// netRun.casuallylook();
			// else {
			// Toast.makeText(getActivity(), getString(R.string.no_more_data),
			// Toast.LENGTH_SHORT).show();
			// }
		}
	}

	@Override
	protected void initView() {
		finview();
		tv_shouye.setOnClickListener(this);
		tv_shangyiye.setOnClickListener(this);
		tv_xiayiye.setOnClickListener(this);
		tv_weiye.setOnClickListener(this);
		tv_more.setOnClickListener(this);
		topmenu = new ArrayList<String>();
		topmenu.add(getString(R.string.foundmenu1));
		topmenu.add(getString(R.string.foundmenu2));
		topmenu.add(getString(R.string.foundmenu3));
		topadapter = new Topmenu();
		gv_topmenu.setAdapter(topadapter);
	}

	@Override
	protected void initData() {
		netRun = new NetRun(getActivity(), handler);
		bitmapUtils = new BitmapUtils(getActivity());
		netRun.casuallylook();
	}

	@Override
	protected int getlayoutResId() {
		return R.layout.fragment_found;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()== R.id.tv_shouye){
			// 首页
			pagination = 1;
			netRun.casuallylook();
		}else if(v.getId()== R.id.tv_shangyiye){
			// 上一页
			if (pagination > 0) {
				pagination--;
			}
			netRun.casuallylook();
		}else if(v.getId()==R.id.tv_xiayiye){
			netRun.casuallylook();
		}else if(v.getId()==R.id.tv_weiye){
			// 尾页
			// int a = Integer.parseInt(casuallyLook.page_count.page_total);
			// pagination = a;
			netRun.casuallylook();
		}


//		switch (v.getId()) {
//		case R.id.tv_shouye:
//			// 首页
//			pagination = 1;
//			netRun.casuallylook();
//			break;
//		case R.id.tv_shangyiye:
//			// 上一页
//			if (pagination > 0) {
//				pagination--;
//			}
//			netRun.casuallylook();
//			break;
//		case R.id.tv_xiayiye:
//			// 下一页
//			// int za = Integer.parseInt(casuallyLook.page_count.page_total);
//			// if (pagination < za) {
//			// pagination++;
//			// }
//			netRun.casuallylook();
//			break;
//		case R.id.tv_weiye:
//			// 尾页
//			// int a = Integer.parseInt(casuallyLook.page_count.page_total);
//			// pagination = a;
//			netRun.casuallylook();
//			break;
//		case R.id.tv_more:
//			// 加载更多
//			// if (pagination < Integer
//			// .parseInt(casuallyLook.page_count.page_total)) {
//			// pagination++;
//			// netRun.casuallylook(pagination + "");
//			// } else {
//			// Toast.makeText(getActivity(), getString(R.string.no_more_data),
//			// Toast.LENGTH_SHORT).show();
//			// }
//			break;
//		}
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
						// 发现好物
						ll_sliding_1.setVisibility(View.VISIBLE);
						ll_sliding_2.setVisibility(View.INVISIBLE);
						ll_sliding_3.setVisibility(View.INVISIBLE);
						ll_sliding_4.setVisibility(View.INVISIBLE);
						netRun.casuallylook();
						lv_shop.setVisibility(View.GONE);
						mgv_goodslist.setVisibility(View.VISIBLE);
						break;
					case 1:
						// 达人推荐
						ll_sliding_1.setVisibility(View.INVISIBLE);
						ll_sliding_2.setVisibility(View.VISIBLE);
						ll_sliding_3.setVisibility(View.INVISIBLE);
						ll_sliding_4.setVisibility(View.INVISIBLE);
						netRun.StaffPicks();
						lv_shop.setVisibility(View.GONE);
						mgv_goodslist.setVisibility(View.VISIBLE);
						break;
					case 2:
						// 店铺街
						ll_sliding_1.setVisibility(View.INVISIBLE);
						ll_sliding_2.setVisibility(View.INVISIBLE);
						ll_sliding_3.setVisibility(View.VISIBLE);
						ll_sliding_4.setVisibility(View.INVISIBLE);
						// TODO
						netRun.Shopstreet();
						lv_shop.setVisibility(View.VISIBLE);
						mgv_goodslist.setVisibility(View.GONE);
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
	 * 发现好物商品列表适配
	 * 
	 * @author Administrator
	 */
	private class Goodslistadapter extends BaseAdapter {
		List<goods_list> goods_list;

		public Goodslistadapter(List<goods_list> goods_list) {
			this.goods_list = goods_list;
		}

		// 添加数据
		public void addList(List<goods_list> list) {
			if (goods_list == null) {
				goods_list = new ArrayList<goods_list>();
			}
			goods_list.addAll(list);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return goods_list.size();
		}

		@Override
		public Object getItem(int position) {
			return goods_list == null ? null : goods_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.found_item,
						null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final CasuallyLook.goods_list goods_list2 = goods_list
					.get(position);
			bitmapUtils.display(holder.iv_goodsimg,
					goods_list2.commend_goods_image);
			bitmapUtils.display(holder.iv_title, goods_list2.member_avatar);
			holder.tv_goodsname.setText(goods_list2.commend_goods_name);
			holder.tv_goodsprice.setText(goods_list2.commend_goods_price
					+ getString(R.string.yuan));
			holder.tv_name.setText(goods_list2.member_name);
			holder.tv_details.setText(goods_list2.commend_message);
			holder.tv_praise.setText(goods_list2.like_count);
			holder.tv_comments.setText(goods_list2.comment_count);
			holder.iv_praisenum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 点赞
					tv_zan = holder.tv_praise;
					netRun.Praise(goods_list2.commend_id, "1");
				}
			});
			holder.iv_commentsnum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 评论
					Intent intent2 = new Intent(getActivity(),
							FoundCommenListActivity.class);
					intent2.putExtra("commend_id", goods_list2.commend_id);
					intent2.putExtra("comment_type", "1");
					startActivityForResult(intent2, 0);
				}
			});
			holder.ll_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 商品详情
					Intent intent = new Intent(getActivity(),
							CommunityGoods.class);
					intent.putExtra("goodsinfo", (Serializable) goods_list2);
					intent.putExtra("type", 1);
					startActivityForResult(intent, 0);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_goodsimg, iv_title, iv_praisenum, iv_commentsnum;
			TextView tv_goodsname, tv_goodsprice, tv_name, tv_tuijian,
					tv_details, tv_praise, tv_comments;
			LinearLayout ll_item;

			public ViewHolder(View convertView) {
				iv_goodsimg = (ImageView) convertView
						.findViewById(R.id.iv_goodsimg);
				iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
				iv_praisenum = (ImageView) convertView
						.findViewById(R.id.iv_praisenum);
				iv_commentsnum = (ImageView) convertView
						.findViewById(R.id.iv_commentsnum);
				tv_goodsname = (TextView) convertView
						.findViewById(R.id.tv_goodsname);
				tv_goodsprice = (TextView) convertView
						.findViewById(R.id.tv_goodsprice);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_tuijian = (TextView) convertView
						.findViewById(R.id.tv_tuijian);
				tv_details = (TextView) convertView
						.findViewById(R.id.tv_details);
				tv_praise = (TextView) convertView.findViewById(R.id.tv_praise);
				tv_comments = (TextView) convertView
						.findViewById(R.id.tv_comments);
				ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 达人推荐适配
	 * 
	 * @author Administrator
	 *
	 */
	private class StaffPicksadapter extends BaseAdapter {
		List<list> list;

		public StaffPicksadapter(List<list> list) {
			this.list = list;
		}

		// 添加数据
		public void addList(List<list> list) {
			if (list == null) {
				list = new ArrayList<list>();
			}
			list.addAll(list);
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.found_item,
						null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final StaffPicksInfo.list list2 = list
					.get(position);
			bitmapUtils.display(holder.iv_goodsimg, list2.commend_image);
			bitmapUtils.display(holder.iv_title, list2.member_avatar);
			// holder.tv_goodsname.setText(list2.commend_goods_name);
			// holder.tv_goodsprice.setText(list2.commend_goods_price
			// + getString(R.string.yuan));
			holder.tv_goodsname.setVisibility(View.GONE);
			holder.tv_goodsprice.setVisibility(View.GONE);
			holder.tv_name.setText(list2.member_name);
			holder.tv_details.setText(list2.commend_message);
			holder.tv_praise.setText(list2.like_count);
			holder.tv_comments.setText(list2.comment_count);
			holder.iv_praisenum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 点赞
					tv_zan = holder.tv_praise;
					netRun.Praise(list2.personal_id, "2");
				}
			});
			holder.iv_commentsnum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 评论
					Intent intent2 = new Intent(getActivity(),
							FoundCommenListActivity.class);
					intent2.putExtra("commend_id", list2.personal_id);
					intent2.putExtra("comment_type", "2");
					intent2.putExtra("type", 2);
					intent2.putExtra("article_comment_count",
							list2.comment_count);
					intent2.putExtra("article_click", list2.like_count);
					startActivityForResult(intent2, 0);
				}
			});
			holder.ll_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 商品详情
					Intent intent = new Intent(getActivity(),CommunityGoods.class);
					intent.putExtra("list2", (Serializable) list2);
					intent.putExtra("type", 2);
					startActivityForResult(intent, 0);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_goodsimg, iv_title, iv_praisenum, iv_commentsnum;
			TextView tv_goodsname, tv_goodsprice, tv_name, tv_tuijian,
					tv_details, tv_praise, tv_comments;
			LinearLayout ll_item;

			public ViewHolder(View convertView) {
				iv_goodsimg = (ImageView) convertView
						.findViewById(R.id.iv_goodsimg);
				iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
				iv_praisenum = (ImageView) convertView
						.findViewById(R.id.iv_praisenum);
				iv_commentsnum = (ImageView) convertView
						.findViewById(R.id.iv_commentsnum);
				tv_goodsname = (TextView) convertView
						.findViewById(R.id.tv_goodsname);
				tv_goodsprice = (TextView) convertView
						.findViewById(R.id.tv_goodsprice);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_tuijian = (TextView) convertView
						.findViewById(R.id.tv_tuijian);
				tv_details = (TextView) convertView
						.findViewById(R.id.tv_details);
				tv_praise = (TextView) convertView.findViewById(R.id.tv_praise);
				tv_comments = (TextView) convertView
						.findViewById(R.id.tv_comments);
				ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 店铺街
	 * 
	 * @author Administrator
	 */
	private class Shopadapter extends BaseAdapter {
		ShopStreetInfo shopStreetInfo;

		public Shopadapter(ShopStreetInfo shopStreetInfo) {
			this.shopStreetInfo = shopStreetInfo;
		}

		@Override
		public int getCount() {
			return shopStreetInfo.list.size();
		}

		@Override
		public Object getItem(int position) {
			return shopStreetInfo.list == null ? null : shopStreetInfo.list
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
						R.layout.shopstreet_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tv_shopname.setText(getString(R.string.shop2)
					+ shopStreetInfo.list.get(position).store_name);
			if (shopStreetInfo.list.get(position).store_avatar != null
					&& !shopStreetInfo.list.get(position).store_avatar
							.equals("null")) {
				bitmapUtils.display(holder.iv_img1,
						shopStreetInfo.list.get(position).store_avatar);
			} else {
				holder.iv_img1.setBackgroundResource(R.drawable.no_image);
			}
			if (shopStreetInfo.list.get(position).hot_sales_list != null
					&& shopStreetInfo.list.get(position).hot_sales_list.size() != 0) {
				bitmapUtils
						.display(
								holder.iv_img2,
								shopStreetInfo.list.get(position).hot_sales_list
										.get(0).goods_image);
			}

			if (shopStreetInfo.list.get(position).hot_sales_list != null
					&& shopStreetInfo.list.get(position).hot_sales_list.size() > 1) {
				bitmapUtils
						.display(
								holder.iv_img3,
								shopStreetInfo.list.get(position).hot_sales_list
										.get(1).goods_image);
			}

			holder.iv_img1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(getActivity(),
								StoreAboutActivity.class);
						intent.putExtra("store_id", shopStreetInfo.list.get(position).store_id);
						startActivity(intent);
					} catch (Exception e) {
					}
				}
			});
			holder.iv_img2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (shopStreetInfo.list.get(position).hot_sales_list != null
								&& shopStreetInfo.list.get(position).hot_sales_list
										.size() != 0) {
							Intent intent = new Intent();
							intent = new Intent(getActivity(),
									ProductDetailsActivity.class);
							intent.putExtra("goods_id", shopStreetInfo.list
									.get(position).hot_sales_list.get(0).goods_id);
							startActivity(intent);
						}
					} catch (Exception e) {
					}
					
				}
			});
			holder.iv_img3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (shopStreetInfo.list.get(position).hot_sales_list != null
								&& shopStreetInfo.list.get(position).hot_sales_list
										.size() >= 1) {
							Intent intent = new Intent();
							intent = new Intent(getActivity(),
									ProductDetailsActivity.class);
							intent.putExtra("goods_id", shopStreetInfo.list
									.get(position).hot_sales_list.get(1).goods_id);
							startActivity(intent);
						}
					} catch (Exception e) {
					}
				}
			});

			return convertView;
		}

		class ViewHolder {
			ImageView iv_img1, iv_img2, iv_img3;
			TextView tv_shopname;

			public ViewHolder(View convertView) {
				iv_img1 = (ImageView) convertView.findViewById(R.id.iv_img1);
				iv_img2 = (ImageView) convertView.findViewById(R.id.iv_img2);
				iv_img3 = (ImageView) convertView.findViewById(R.id.iv_img3);
				tv_shopname = (TextView) convertView
						.findViewById(R.id.tv_shopname);
				convertView.setTag(this);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 0:
			// goodslistadapter = null;
			// netRun.casuallylook();
			break;
		}
	}

}
