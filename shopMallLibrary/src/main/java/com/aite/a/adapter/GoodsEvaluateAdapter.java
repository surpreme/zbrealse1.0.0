package com.aite.a.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aite.a.model.GoodsDetailsInfo;
import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 商品评价
 * Created by mayn on 2018/11/14.
 */
public class GoodsEvaluateAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GoodsDetailsInfo.GoodsEvaluateList> goodsEvaluateList;

    public GoodsEvaluateAdapter(Context mcontext, List<GoodsDetailsInfo.GoodsEvaluateList> goodsEvaluateList) {
        this.mcontext = mcontext;
        this.goodsEvaluateList = goodsEvaluateList;
    }

    @Override
    public int getCount() {
        return goodsEvaluateList == null ? 0 : goodsEvaluateList.size();
    }

    @Override
    public Object getItem(int i) {
        return goodsEvaluateList == null ? null : goodsEvaluateList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_goodsevaluate, null);
            new GoodsEvaluateHolder(view);
        }
        GoodsEvaluateHolder holder = (GoodsEvaluateHolder) view.getTag();
        GoodsDetailsInfo.GoodsEvaluateList goodsEvaluateList = this.goodsEvaluateList.get(i);
        Glide.with(mcontext).load(goodsEvaluateList.geval_member_avatar).into(holder.iv_img);
        holder.tv_name.setText(goodsEvaluateList.geval_frommembername);
        holder.tv_centent.setText(goodsEvaluateList.geval_content);
        holder.ratingBar.setRating(Float.parseFloat(goodsEvaluateList.geval_scores));
        return view;
    }

    class GoodsEvaluateHolder {
        RatingBar ratingBar;
        CircleImageView iv_img;
        TextView tv_name, tv_centent;

        public GoodsEvaluateHolder(View view) {
            iv_img = view.findViewById(R.id.iv_img);
            tv_name = view.findViewById(R.id.tv_name);
            tv_centent = view.findViewById(R.id.tv_centent);
            ratingBar = view.findViewById(R.id.rb_score);
            view.setTag(this);
        }
    }
}
