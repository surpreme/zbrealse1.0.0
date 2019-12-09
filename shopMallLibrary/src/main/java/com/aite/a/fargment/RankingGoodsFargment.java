package com.aite.a.fargment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseFargment;
import com.aite.a.model.GoodList;
import com.lidroid.xutils.BitmapUtils;

public class RankingGoodsFargment extends BaseFargment implements OnClickListener {
	private View view;
	private ImageView ranking_oods1;
	private ImageView ranking_oods2;
	private ImageView ranking_oods3;
	private TextView ranking_oods1_sale;
	private TextView ranking_oods2_sale;
	private TextView ranking_oods3_sale;
	private TextView ranking_oods1_price;
	private int currentIndex;
	private List<GoodList> hotGoods = new ArrayList<GoodList>(), newGoods = new ArrayList<GoodList>(), salesGoods = new ArrayList<GoodList>();

	public void setCurrentIndex(int currentIndex, List<GoodList> Goods) {
		this.currentIndex = currentIndex;
		switch (currentIndex) {
		case 0:
			this.hotGoods = Goods;
			break;
		case 1:
			this.newGoods = Goods;
			break;
		case 2:
			this.salesGoods = Goods;
			break;
		}
		initView(Goods);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		bitmapUtils = new BitmapUtils(getActivity());
		view = inflater.inflate(R.layout.ranking_fg, container, false);
		ranking_oods1 = (ImageView) view.findViewById(R.id.ranking_oods1);
		ranking_oods2 = (ImageView) view.findViewById(R.id.ranking_oods2);
		ranking_oods3 = (ImageView) view.findViewById(R.id.ranking_oods3);
		ranking_oods1_sale = (TextView) view.findViewById(R.id.ranking_oods1_sale);
		ranking_oods2_sale = (TextView) view.findViewById(R.id.ranking_oods2_sale);
		ranking_oods3_sale = (TextView) view.findViewById(R.id.ranking_oods3_sale);
		ranking_oods1_price = (TextView) view.findViewById(R.id.ranking_oods1_price);
		return view;
	}

	public void initView(List<GoodList> data) {
		if (data.size() >= 1) {
			ranking_oods1.setOnClickListener(this);
			ranking_oods1_sale.setText(getI18n(R.string.sold) + data.get(0).goods_salenum + getI18n(R.string.act_a));
			bitmapUtils.display(ranking_oods1, data.get(0).getGoods_image_url());
			ranking_oods1_price.setText("￥" + data.get(0).getGoods_price() + getI18n(R.string.yuan));
		}
		if (data.size() >= 2) {
			ranking_oods2.setOnClickListener(this);
			ranking_oods2_sale.setText(getI18n(R.string.sold) + data.get(1).goods_salenum + getI18n(R.string.act_a));
			bitmapUtils.display(ranking_oods2, data.get(1).getGoods_image_url());
		}
		if (data.size() >= 3) {
			ranking_oods3.setOnClickListener(this);
			ranking_oods3_sale.setText(getI18n(R.string.sold) + data.get(2).goods_salenum + getI18n(R.string.act_a));
			bitmapUtils.display(ranking_oods3, data.get(2).getGoods_image_url());
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
		if (v.getId()==R.id.ranking_oods1){
			intent.putExtra("goods_id", MyonClick().get(0).getGoods_id());
		}else if (v.getId()==R.id.ranking_oods2){
			intent.putExtra("goods_id", MyonClick().get(1).getGoods_id());
		}else if(v.getId()==R.id.ranking_oods3){
			intent.putExtra("goods_id", MyonClick().get(2).getGoods_id());
		}
//		switch (v.getId()) {
//		case R.id.ranking_oods1:
//			intent.putExtra("goods_id", MyonClick().get(0).getGoods_id());
//			break;
//		case R.id.ranking_oods2:
//			intent.putExtra("goods_id", MyonClick().get(1).getGoods_id());
//			break;
//		case R.id.ranking_oods3:
//			intent.putExtra("goods_id", MyonClick().get(2).getGoods_id());
//			break;
//		}
		startActivity(intent);
	}

	private List<GoodList> MyonClick() {
		switch (currentIndex) {
		case 0:
			return hotGoods;
		case 1:
			return newGoods;
		case 2:
			return salesGoods;
		}
		return null;
	}
}
