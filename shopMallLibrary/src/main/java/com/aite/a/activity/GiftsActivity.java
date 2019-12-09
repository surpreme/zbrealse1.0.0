package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.GiftsInfo;
import com.aite.a.model.GoodsListInfo;
import com.aite.a.model.GoodsListInfo.goods_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyChooseGiftPopu;
import com.aite.a.view.MyChooseGiftPopu.date;
import com.aite.a.view.MyGridView;
import com.aite.a.view.MyKeyboard;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 赠送赠品
 * <p/>
 * Created by Administrator on 2017/6/6.
 */
public class GiftsActivity extends BaseActivity implements OnClickListener {
	private ImageView _iv_back;
	private TextView _tv_name, tv_netxt;
	private MyListView rv_gifts;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private Madapter madapter;
	private RelativeLayout cv_sm;
	private String commonid;
	private List<GiftsInfo> giftsInfo;
	private String name = "";
	private GoodsListInfo goodsListInfo;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case gift_info_id:
				// 赠品详情
				if (msg.obj != null) {
					giftsInfo = (List<GiftsInfo>) msg.obj;
					madapter = new Madapter(giftsInfo);
					rv_gifts.setAdapter(madapter);
				}
				break;
			case gift_info_err:
				Toast.makeText(GiftsActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case 10010:
				isupdatapager = true;
				break;
			case save_gifts_id:
				// 提交
				if (msg.obj != null) {
					String str = (String) msg.obj;
					if (str.equals("1")) {
						Toast.makeText(GiftsActivity.this,
								getString(R.string.operationissuccessful),
								Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(GiftsActivity.this, str,
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case save_gifts_err:
				Toast.makeText(GiftsActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gifts);
		findViewById();
	}

	@Override
	protected void findViewById() {
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_netxt = (TextView) findViewById(R.id.tv_netxt);
		rv_gifts = (MyListView) findViewById(R.id.rv_gifts);
		cv_sm = (RelativeLayout) findViewById(R.id.cv_sm);
		rv_gifts.setFocusable(false);
		cv_sm.requestFocus();
		_tv_name.setText(getString(R.string.gifts9));
		_iv_back.setOnClickListener(this);
		tv_netxt.setOnClickListener(this);
		initView();
	}

	@Override
	protected void initView() {
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		initData();
	}

	@Override
	protected void initData() {
		commonid = getIntent().getStringExtra("commonid");
		netRun.GiftInfo(commonid);
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_netxt:
//			// 提交
//			if (madapter != null) {
//				netRun.SaveGifts(commonid, madapter.getjson());
//			}
//			break;
//		}
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.tv_netxt){
			// 提交
			if (madapter != null) {
				netRun.SaveGifts(commonid, madapter.getjson());
			}
		}
	}

	/**
	 * 显示礼物
	 * 
	 */
	private void shoupopu(int id) {
		MyChooseGiftPopu myChooseGiftPopu = new MyChooseGiftPopu(
				GiftsActivity.this, id);
		myChooseGiftPopu.setdate(new date() {

			@Override
			public void onItemClick(goods_list goods, int idd) {
				Log.i("------------------", goods.toString());
				GiftsInfo.gift_list combo_array = new GiftsInfo.gift_list();
				combo_array.goods_id = goods.goods_id;
				combo_array.gift_goodsname = goods.goods_name;
				combo_array.gift_goodsimage = goods.goods_image;
				combo_array.gift_amount = "1";
				madapter.addItem(combo_array, idd);
			}
		});
		myChooseGiftPopu.showAtLocation(tv_netxt, Gravity.BOTTOM, 0, 0);
	}

	private int pagerid = 0, pagernumber = 1;
	private boolean isupdatapager = true;

	/**
	 * 商品列表
	 */
	class Madapter extends BaseAdapter {
		List<GiftsInfo> giftsInfo;

		public Madapter(List<GiftsInfo> giftsInfo) {
			this.giftsInfo = giftsInfo;
		}

		/**
		 * 获取已选数组
		 */
		public String getjson() {
			try {
				JSONObject jsonObject = new JSONObject();
				for (int i = 0; i < giftsInfo.size(); i++) {
					// JSONArray jsonArray2 = new JSONArray();
					JSONObject jsonObject2 = new JSONObject();
					for (int j = 0; j < giftsInfo.get(i).gift_list.size(); j++) {
						jsonObject2.put(
								giftsInfo.get(i).gift_list.get(j).goods_id,
								giftsInfo.get(i).gift_list.get(j).gift_amount);
						// jsonArray2.put(giftsInfo.get(i).gift_list.get(j).goods_id);
					}
					jsonObject.put(giftsInfo.get(i).goods_id, jsonObject2);
				}
				Log.i("-----------------", "获取已选数组 " + jsonObject.toString());
				return jsonObject.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "";
		}

		/**
		 * 添加
		 */
		public void addItem(GiftsInfo.gift_list gift, int id) {
			giftsInfo.get(id).gift_list.add(gift);
			notifyDataSetChanged();
		}

		public List<GiftsInfo> getData() {
			return this.giftsInfo;
		}

		@Override
		public int getCount() {
			return giftsInfo == null ? 0 : giftsInfo.size();
		}

		@Override
		public Object getItem(int position) {
			return giftsInfo == null ? null : giftsInfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(GiftsActivity.this,
						R.layout.item_gifts, null);
				new ViewHodler(convertView);
			}
			final ViewHodler holder = (ViewHodler) convertView.getTag();
			GiftsInfo giftsInfo = this.giftsInfo.get(position);
			bitmapUtils.display(holder.iv_img, giftsInfo.goods_image);
			holder.tv_name.setText(giftsInfo.goods_name);
			holder.tv_pagerbtn3.setText(giftsInfo.pagernumber + "");
			// 赠品捆绑
			if (giftsInfo.gift_list == null) {
				giftsInfo.gift_list = new ArrayList<>();
			}
			Madapter3 adapter3 = new Madapter3(giftsInfo.gift_list);
			holder.rv_gifts.setAdapter(adapter3);
			giftsInfo.adapter3 = adapter3;
			holder.tv_xz.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 选择商品推荐组合
					shoupopu(position);
				}
			});
		
			holder.tv_search.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 搜索
					String s = holder.ed_input1.getText().toString();
					if (TextUtils.isEmpty(s)) {
						s = "";
					}
					name = s;
					shoupopu(position);
				}
			});
			return convertView;
		}

		class ViewHodler {
			ImageView iv_img;
			TextView tv_name, tv_price, tv_inventory, tv_gift, tv_xz,
					tv_search, tv_pagerbtn1, tv_pagerbtn2, tv_pagerbtn3,
					tv_pagerbtn4, tv_pagerbtn5;
			EditText ed_input1;
			RelativeLayout rl_search;
			View v_fg;
			MyListView rv_gifts;
			MyGridView rv_giftslist;

			public ViewHodler(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				tv_inventory = (TextView) convertView
						.findViewById(R.id.tv_inventory);
				tv_gift = (TextView) convertView.findViewById(R.id.tv_gift);
				tv_xz = (TextView) convertView.findViewById(R.id.tv_xz);
				tv_search = (TextView) convertView.findViewById(R.id.tv_search);
				tv_pagerbtn1 = (TextView) convertView
						.findViewById(R.id.tv_pagerbtn1);
				tv_pagerbtn2 = (TextView) convertView
						.findViewById(R.id.tv_pagerbtn2);
				tv_pagerbtn3 = (TextView) convertView
						.findViewById(R.id.tv_pagerbtn3);
				tv_pagerbtn4 = (TextView) convertView
						.findViewById(R.id.tv_pagerbtn4);
				tv_pagerbtn5 = (TextView) convertView
						.findViewById(R.id.tv_pagerbtn5);
				rv_gifts = (MyListView) convertView.findViewById(R.id.rv_gifts);
				rv_giftslist = (MyGridView) convertView
						.findViewById(R.id.rv_giftslist);
				ed_input1 = (EditText) convertView.findViewById(R.id.ed_input1);
				rl_search = (RelativeLayout) convertView
						.findViewById(R.id.rl_search);
				v_fg = convertView.findViewById(R.id.v_fg);
				convertView.setTag(this);
			}
		}

	}

	/**
	 * 赠品捆绑
	 */
	public class Madapter3 extends BaseAdapter {
		List<GiftsInfo.gift_list> gift_list;

		public Madapter3(List<GiftsInfo.gift_list> gift_list) {
			this.gift_list = gift_list;
		}

		/**
		 * 添加
		 */
		public void addItem(GiftsInfo.gift_list gift) {
			gift_list.add(gift);
			notifyDataSetChanged();
		}

		/**
		 * 删除
		 */
		public void deleteItem(int id) {
			if (id < gift_list.size()) {
				gift_list.remove(id);
				notifyDataSetChanged();
			}
		}

		@Override
		public int getCount() {
			return gift_list == null ? 0 : gift_list.size();
		}

		@Override
		public Object getItem(int position) {
			return gift_list == null ? null : gift_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(GiftsActivity.this,
						R.layout.item_giftbundled, null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final GiftsInfo.gift_list gift_list = this.gift_list.get(position);
			bitmapUtils.display(holder.iv_img, gift_list.gift_goodsimage);
			holder.tv_name.setText(gift_list.gift_goodsname);
			holder.tv_number.setText(getString(R.string.gifts14)
					+ gift_list.gift_amount);
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteItem(position);
				}
			});
			holder.cv_sm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showpopu(gift_list, position, holder.tv_number);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_img, iv_delete;
			TextView tv_name, tv_number;
			RelativeLayout cv_sm;

			public ViewHolder(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
				iv_delete = (ImageView) convertView
						.findViewById(R.id.iv_delete);
				tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				tv_number = (TextView) convertView.findViewById(R.id.tv_number);
				cv_sm = (RelativeLayout) convertView.findViewById(R.id.cv_sm);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 弹出数字键盘
	 */
	private void showpopu(final GiftsInfo.gift_list gift_list, final int id,
			final TextView tv) {
		MyKeyboard mypopu = new MyKeyboard(GiftsActivity.this);
		mypopu.sethuidiao(new MyKeyboard.huidiao() {
			@Override
			public void onItemClick(String number) {
				if (!number.equals("0")) {
					gift_list.gift_amount = number;
					tv.setText(getString(R.string.gifts14)
							+ gift_list.gift_amount);
				}
			}
		});
		mypopu.showAtLocation(cv_sm, Gravity.BOTTOM, 0, 0);
	}

	@Override
	public void ReGetData() {

	}
}
