package com.aite.a.fargment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.base.BaseFargment;
import com.aite.a.model.FavoriteGoodsList;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.custom.MyCircleImage;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFargment extends BaseFargment {
	public static boolean is_compile = false;
	public GridView goods;
	private ListView store;
	private NetRun netRun;
	public List<FavoriteGoodsList> goodsLists = new ArrayList<FavoriteGoodsList>();
	private List<FavoriteGoodsList> storeLists = new ArrayList<FavoriteGoodsList>();
	public FavoriteAdapter goodsAdapter = null;
	public FavoriteAdapter storeAdapter = null;
	private int clickPosition ;
	private int delecteType;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case collectibles_id:
				switch (msg.arg1) {
				case 1:
					goodsLists = (List<FavoriteGoodsList>) msg.obj;
					if (goodsLists.size() > 0) {
						goodsAdapter = new FavoriteAdapter(getActivity(), goodsLists, 1);
						goods.setAdapter(goodsAdapter);
					}
					mdialog.dismiss();
					break;
				case 2:
					storeLists = (List<FavoriteGoodsList>) msg.obj;
					if (storeLists.size() > 0) {
						storeAdapter = new FavoriteAdapter(getActivity(), storeLists, 2);
						store.setAdapter(storeAdapter);
					}
					mdialog.dismiss();
					break;
				}
				break;
			case collectibles_err:
				CommonTools.showShortToast(getActivity(), getI18n(R.string.systembusy));
				mdialog.dismiss();
				break;
			case collectibles_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			case collectibles_del_id:
				mdialog.dismiss();
				if (msg.obj.equals("1")) {
				/*	Bundle bundle = getArguments();
					int i = bundle.getInt("i", 0);
					Intent intent = new Intent(getActivity(), FavoriteListFargmentActivity.class);
					intent.putExtra("i", i);
					startActivity(intent);
					getActivity().finish();*/
					if (delecteType == 2){   //删除店铺成功
						storeAdapter.goodsLists.remove(clickPosition);
						storeAdapter.notifyDataSetChanged();
					}else {
						goodsAdapter.goodsLists.remove(clickPosition);
						goodsAdapter.notifyDataSetChanged();
					}
				}
				break;
			case collectibles_del_err:
				CommonTools.showShortToast(getActivity(), getI18n(R.string.systembusy));
				mdialog.dismiss();
				break;
			case collectibles_del_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
				mdialog.show();
				break;
			}
		};
	};

	public static FavoriteFargment newInstance(int i) {
		FavoriteFargment fragment = new FavoriteFargment();
		Bundle bundle = new Bundle();
		bundle.putInt("i", i);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.favorite_list_fragment, container, false);
		bitmapUtils = new BitmapUtils(getActivity());
		mdialog = new ProgressDialog(getActivity());
		mdialog.setProgressStyle(mdialog.STYLE_SPINNER);
		netRun = new NetRun(getActivity(), handler);

		findViewById();
		requestData();
		myClick();
		return view;
	}

	@Override
	protected void findViewById() {
		goods = (GridView) view.findViewById(R.id.favorite_goods);
		store = (ListView) view.findViewById(R.id.favorite_store);
			
	}

	@Override
	protected void requestData() {
//		mdialog.setMessage(getI18n(R.string.act_waiting));
		Bundle bundle = getArguments();
		int i = bundle.getInt("i", 0);
		switch (i) {
		case 1:
			goods.setVisibility(View.VISIBLE);
			store.setVisibility(View.GONE);
			netRun.getFavoriteList("goods", 1);
			break;
		case 2:
			goods.setVisibility(View.GONE);
			store.setVisibility(View.VISIBLE);
			netRun.getFavoriteList("store", 2);
			break;
		}
	}

	private void myClick() {
		goods.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
				intent.putExtra("goods_id", goodsLists.get(position).goods_id);
				startActivity(intent);
			}
		});
		store.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
				intent.putExtra("store_id", storeLists.get(position).store_id);
				startActivity(intent);
			}
		});
		goods.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				clickPosition = position  ;
				Dialog(getActivity(), getI18n(R.string.sure_to_delete_goods), goodsLists.get(position).fav_id, "goods");

				return true;
			}
		});
		store.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				clickPosition = position  ;
				delecteType = 2 ;
				Dialog(getActivity(), getI18n(R.string.sure_to_delete_shops), storeLists.get(position).fav_id, "store");
				return true;
			}
		});
	}

	private void Dialog(Context context, String text, final String fav_id, final String fav_type) {
		AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
		builder2.setTitle(getString(R.string.tip));// 设置对话框标题
		builder2.setItems(new String[] { text }, null);
		builder2.setNegativeButton(getI18n(R.string.cancel), null);
		builder2.setPositiveButton(getI18n(R.string.confirm), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				netRun.cancelGoodsFavorite(fav_id, fav_type);
			}
		});
		builder2.show();
	}

	public class FavoriteAdapter extends BaseAdapter {
		private List<FavoriteGoodsList> goodsLists;
		private Context context;
		private int type;

		public FavoriteAdapter(Context context, List<FavoriteGoodsList> goodsLists, int type) {
			super();
			this.context = context;
			this.goodsLists = goodsLists;
			this.type = type;
		}

		@Override
		public int getCount() {
			return goodsLists.size();
		}

		@Override
		public Object getItem(int position) {
			return goodsLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public class StoreHolder {
			private final View store_del;
			public MyCircleImage store_image;
			public TextView store_name;
			public TextView store_zy;
			public TextView store_address;

			public StoreHolder(View v) {
				super();
				store_image =  v.findViewById(R.id.store_image);
				store_name = (TextView) v.findViewById(R.id.store_name);
				store_zy = (TextView) v.findViewById(R.id.store_zy);
				store_address = (TextView) v.findViewById(R.id.store_address);
				store_del = v.findViewById(R.id.store_item_del);
			}
		}

		public class GoodsHolder {
			public CheckBox goods_cb;
			public ImageView goods_image;
			public TextView goods_name;
			public TextView goods_price;
			public LinearLayout list_del;
			public LinearLayout list_open_store;

			public GoodsHolder(View v) {
				super();
				list_del = (LinearLayout) v.findViewById(R.id.list_del);
				list_open_store = (LinearLayout) v.findViewById(R.id.list_open_store);
				goods_cb = (CheckBox) v.findViewById(R.id.item_cb);
				goods_image = (ImageView) v.findViewById(R.id.list_iv_image);
				goods_name = (TextView) v.findViewById(R.id.list_tv_content);
				goods_price = (TextView) v.findViewById(R.id.list_tv_price);
			}
		}

		@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		@Override
		public View getView(final int position, View v, ViewGroup parent) {
			GoodsHolder goodsHolder = null;
			StoreHolder storeHolder = null;
			if (v == null) {
				if (type == 1) {
					v = LayoutInflater.from(context).inflate(R.layout.goods_list_item, parent, false);
					goodsHolder = new GoodsHolder(v);
					v.setTag(goodsHolder);
				} else {
					v = LayoutInflater.from(context).inflate(R.layout.store_list_item, parent, false);
					storeHolder = new StoreHolder(v);
					v.setTag(storeHolder);
				}
			} else {
				if (type == 1) {
					goodsHolder = (GoodsHolder) v.getTag();
				} else {
					storeHolder = (StoreHolder) v.getTag();
				}
			}

			FavoriteGoodsList goodsList = goodsLists.get(position);
			if (type == 1) {   //收藏商品
				bitmapUtils.display(goodsHolder.goods_image, goodsList.goods_image_url);
				goodsHolder.goods_name.setText(goodsList.goods_name);
				goodsHolder.goods_price.setText(goodsList.goods_price);
				goodsHolder.list_del.setVisibility(View.VISIBLE);
				goodsHolder.list_open_store.setVisibility(View.VISIBLE);
				if (goodsList.is_show == true) {
					goodsHolder.goods_cb.setVisibility(View.VISIBLE);
				} else {
					goodsHolder.goods_cb.setVisibility(View.GONE);
					// goodsHolder.list_del.setVisibility(View.GONE);
					// goodsHolder.list_open_store.setVisibility(View.GONE);
				}
				goodsHolder.list_del.setOnClickListener(onClick(goodsList, "goods",position));
				goodsHolder.list_open_store.setOnClickListener(onClick(goodsList, "goods",position));
			} else {  //收藏店铺
				if (goodsList.store_avatar != null && !"".equals(goodsList.store_avatar))
					Glide.with(v).load(goodsList.store_avatar).into(storeHolder.store_image);//.onLoadFailed(((Context)v).getDrawable(R.drawable.load_failed));
				storeHolder.store_name.setText(goodsList.store_name);
				storeHolder.store_zy.setText(getI18n(R.string.main_product) + "\t\t" + goodsList.store_zy==null?"":goodsList.store_zy);
				storeHolder.store_address.setText(getI18n(R.string.location) + "：\t" + goodsList.area_info);
				storeHolder.store_del.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						clickPosition = position  ;
						delecteType = 2 ;
						Dialog(getActivity(), getI18n(R.string.sure_to_delete_shops), storeLists.get(position).fav_id, "store");
					}
				});
			}
			return v;
		}

		private OnClickListener onClick(final FavoriteGoodsList goodsList, final String fav_type,final int itemPosition) {
			clickPosition = itemPosition ;
			OnClickListener listener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(v.getId()==R.id.list_del){
						Dialog(context, getI18n(R.string.sure_to_delete_goods), goodsList.fav_id, "store");
					}else if(v.getId()==R.id.list_open_store){
						Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
						intent.putExtra("store_id", goodsList.store_id);
						startActivity(intent);
					}


//					switch (v.getId()) {
//					case R.id.list_del:
//						Dialog(context, getI18n(R.string.sure_to_delete_goods), goodsList.fav_id, "store");
//						break;
//					case R.id.list_open_store:
//						Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
//						intent.putExtra("store_id", goodsList.store_id);
//						startActivity(intent);
//						break;
//					}
				}
			};
			return listener;
		}

		public void isShow() {
			Bundle bundle = getArguments();
			int i = bundle.getInt("i", 0);
			if (i == 1) {
				if (is_compile == false) {
					is_compile = true;
					for (FavoriteGoodsList list : goodsLists) {
						list.setIs_show(true);
					}
				} else {
					is_compile = false;
					for (FavoriteGoodsList list : goodsLists) {
						list.setIs_show(false);
					}
				}
				notifyDataSetChanged();
			} else {
				CommonTools.showShortToast(getActivity(), getI18n(R.string.not_open_shop_edit));
			}
		}

	}
}
