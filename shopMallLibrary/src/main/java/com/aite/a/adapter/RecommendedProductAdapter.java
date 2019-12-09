package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.model.GoodsDetailsInfo;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 推荐商品
 * Created by mayn on 2018/11/14.
 */
public class RecommendedProductAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GoodsDetailsInfo.GoodsCommendList> goodsCommendList;

    public RecommendedProductAdapter(Context mcontext, List<GoodsDetailsInfo.GoodsCommendList> goodsCommendList) {
        this.mcontext = mcontext;
        this.goodsCommendList = goodsCommendList;
    }

    @Override
    public int getCount() {
        return goodsCommendList == null ? 0 : goodsCommendList.size();
    }

    @Override
    public Object getItem(int i) {
        return goodsCommendList == null ? null : goodsCommendList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_recommendedproduct, null);
            new RecommendedProductHolder(view);
        }
        RecommendedProductHolder holder = (RecommendedProductHolder) view.getTag();
        final GoodsDetailsInfo.GoodsCommendList goodsCommendList = this.goodsCommendList.get(i);
        Glide.with(mcontext).load(goodsCommendList.goods_image_url).into(holder.iv_img);
        holder.tv_name.setText(goodsCommendList.goods_name);
        holder.tv_price.setText("￥" + goodsCommendList.goods_price);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, ProductDetailsActivity.class);
                intent.putExtra("goods_id",goodsCommendList.goods_id);
                mcontext.startActivity(intent);
            }
        });
        //
        return view;
    }

    class RecommendedProductHolder {
        ImageView iv_img;
        TextView tv_name, tv_price;
        LinearLayout ll_item;

        public RecommendedProductHolder(View view) {
            iv_img = view.findViewById(R.id.iv_img);
            tv_name = view.findViewById(R.id.tv_name);
            tv_price = view.findViewById(R.id.tv_price);
            ll_item = view.findViewById(R.id.ll_item);
            view.setTag(this);
        }
    }
}
