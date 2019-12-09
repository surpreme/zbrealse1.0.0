package com.aite.a.adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.activity.ReleaseGoodsActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.GoodsManageList;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

public class GoodsManageAdapter extends BaseAdapter {
	private List<GoodsManageList> goodsManageLists = new ArrayList<GoodsManageList>();
	private List<GoodsManageList> goodsManageLists2 = new ArrayList<GoodsManageList>();
	private List<GoodsManageList> goodsManageLists3 = new ArrayList<GoodsManageList>();
	private List<GoodsManageList> goodsManageLists4 = new ArrayList<GoodsManageList>();
	private Context context;
	private NetRun netRun;
	private int type;

	public GoodsManageAdapter(Context context, Handler handler) {
		super();
		this.context = context;
		netRun = new NetRun(context, handler);
	}

	@Override
	public int getCount() {
		switch (type) {
		case 0:
			return goodsManageLists.size();
		case 1:
			return goodsManageLists2.size();
		case 2:
			return goodsManageLists3.size();
		case 3:
			return goodsManageLists4.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		switch (type) {
		case 0:
			return goodsManageLists.get(position);
		case 1:
			return goodsManageLists2.get(position);
		case 2:
			return goodsManageLists3.get(position);
		case 3:
			return goodsManageLists4.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addGoodsManageLists(List<GoodsManageList> goodsManageLists, int type) {
		this.type = type;
		switch (type) {
		case 0:
			this.goodsManageLists.addAll(goodsManageLists);
			break;
		case 1:
			this.goodsManageLists2.addAll(goodsManageLists);
			break;
		case 2:
			this.goodsManageLists3.addAll(goodsManageLists);
			break;
		case 3:
			this.goodsManageLists4.addAll(goodsManageLists);
			break;
		}
	}

	private class ViewHolder {
		private ImageView goods_img;
		private TextView goods_name, goods_price, repertory;
		private Button putaway, unShelve, outShelve, detlGoods;

		public ViewHolder(View view) {
			goods_img = (ImageView) view.findViewById(R.id.goods_img);
			goods_name = (TextView) view.findViewById(R.id.goods_name);
			goods_price = (TextView) view.findViewById(R.id.goods_price);
			repertory = (TextView) view.findViewById(R.id.repertory);
			putaway = (Button) view.findViewById(R.id.putaway);
			unShelve = (Button) view.findViewById(R.id.unShelve);
			detlGoods = (Button) view.findViewById(R.id.detlGoods);
			outShelve = (Button) view.findViewById(R.id.outShelve);
		}
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.goods_manage_item, parent, false);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		List<GoodsManageList> goodsLists = new ArrayList<GoodsManageList>();
		if (type == 0) {
			goodsLists = goodsManageLists;
			holder.putaway.setVisibility(View.VISIBLE);
			holder.outShelve.setVisibility(View.VISIBLE);
			holder.detlGoods.setVisibility(View.VISIBLE);
			holder.unShelve.setVisibility(View.GONE);
		}
		if (type == 1) {
			holder.putaway.setVisibility(View.VISIBLE);
			holder.unShelve.setVisibility(View.GONE);
			holder.outShelve.setVisibility(View.GONE);
			holder.detlGoods.setVisibility(View.GONE);
			goodsLists = goodsManageLists2;
		}
		if (type == 2) {
			holder.putaway.setVisibility(View.VISIBLE);
			holder.unShelve.setVisibility(View.GONE);
			holder.outShelve.setVisibility(View.GONE);
			holder.detlGoods.setVisibility(View.GONE);
			goodsLists = goodsManageLists3;
		}
		if (type == 3) {
			holder.putaway.setVisibility(View.VISIBLE);
			holder.outShelve.setVisibility(View.GONE);
			holder.detlGoods.setVisibility(View.GONE);
			holder.unShelve.setVisibility(View.VISIBLE);
			goodsLists = goodsManageLists4;
		}
		if (goodsLists.size() > 0) {
			GoodsManageList list = goodsLists.get(position);
			new BitmapUtils(context).display(holder.goods_img, list.goods_image);
			holder.goods_name.setText(list.goods_name);
			holder.goods_price.setText(context.getString(R.string.storehome33) + list.goods_price + context.getString(R.string.yuan));
			holder.repertory.setText(context.getString(R.string.storehome34) + list.goods_storage + context.getString(R.string.act_a));
			holder.putaway.setOnClickListener(OnClick(list));
			holder.unShelve.setOnClickListener(OnClick(list));
			holder.outShelve.setOnClickListener(OnClick(list));
			holder.detlGoods.setOnClickListener(OnClick(list));
		}
		return view;
	}

	private OnClickListener OnClick(final GoodsManageList list) {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(v.getId()==R.id.putaway){
					Intent intent = new Intent(context, ReleaseGoodsActivity.class);
					Bundle bundle = new Bundle();
					if (type == 0) {
						bundle.putString("common_id", list.commonid);
					} else {
						bundle.putString("common_id", list.commonid);
					}
					bundle.putString("goods", "redact");
					intent.putExtras(bundle);
					((Activity) context).startActivity(intent);

				}else if (v.getId()==R.id.unShelve){
					netRun.goodsOperation(Mark.goods_show, list.commonid);
				}else if(v.getId()==R.id.outShelve){
					netRun.goodsOperation(Mark.goods_unshow, list.commonid);
				}else if(v.getId()==R.id.detlGoods){
					Builder builder = new Builder(context);
					builder.setTitle(context.getString(R.string.storehome35));
					builder.setMessage(context.getString(R.string.storehome36));
					builder.setPositiveButton(context.getString(R.string.confirmdelivery), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							netRun.goodsOperation(Mark.drop_goods, list.commonid);
							dialog.dismiss();
						}
					});
					builder.setNegativeButton(context.getString(R.string.cancel), null);
					builder.create().show();
				}



//				switch (v.getId()) {
//				case R.id.putaway:
////					Intent intent = new Intent(context, PostTradeActivity.class);
//					Intent intent = new Intent(context, ReleaseGoodsActivity.class);
//					Bundle bundle = new Bundle();
//					if (type == 0) {
//						bundle.putString("common_id", list.commonid);
//					} else {
//						bundle.putString("common_id", list.commonid);
//					}
//					bundle.putString("goods", "redact");
//					intent.putExtras(bundle);
//					((Activity) context).startActivity(intent);
//					break;
//				case R.id.unShelve:
//					netRun.goodsOperation(Mark.goods_show, list.commonid);
//					break;
//				case R.id.outShelve:
//					netRun.goodsOperation(Mark.goods_unshow, list.commonid);
//					break;
//				case R.id.detlGoods:
//					Builder builder = new Builder(context);
//					builder.setTitle(context.getString(R.string.storehome35));
//					builder.setMessage(context.getString(R.string.storehome36));
//					builder.setPositiveButton(context.getString(R.string.confirmdelivery), new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							netRun.goodsOperation(Mark.drop_goods, list.commonid);
//							dialog.dismiss();
//						}
//					});
//					builder.setNegativeButton(context.getString(R.string.cancel), null);
//					builder.create().show();
//					break;
//				}
			}
		};
		return listener;
	}

	public void clear() {
		switch (type) {
		case 0:
			goodsManageLists.clear();
			break;
		case 1:
			goodsManageLists2.clear();
			break;
		case 2:
			goodsManageLists3.clear();
			break;
		case 3:
			goodsManageLists4.clear();
			break;
		}
		notifyDataSetChanged();
	}
}