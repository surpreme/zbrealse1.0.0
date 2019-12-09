package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.GoodsListActivity;
import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.model.GoodsListModerInfo;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 商品列表
 * Created by Administrator on 2018/1/26.
 */
public class GoodsListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GoodsListModerInfo.datas.goods_list> goods_list;

    public GoodsListAdapter(Context mcontext, List<GoodsListModerInfo.datas.goods_list> goods_list) {
        this.mcontext = mcontext;
        this.goods_list = goods_list;
    }


    /**
     * 刷新
     *
     * @param goods_list
     */
    public void refreshData(List<GoodsListModerInfo.datas.goods_list> goods_list) {
        if (goods_list == null) return;
        this.goods_list = goods_list;
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param goods_list
     */
    public void moreData(List<GoodsListModerInfo.datas.goods_list> goods_list) {
        if (goods_list == null) return;
//        this.goods_list.addAll(goods_list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return goods_list == null ? 0 : goods_list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_goodslist1, null);
            new GoodsHodler(convertView);
        }
        GoodsHodler holder = (GoodsHodler) convertView.getTag();
        final GoodsListModerInfo.datas.goods_list goods = this.goods_list.get(position);
        Glide.with(mcontext).load(goods.goods_image_url).into(holder.iv_goodsimg);
//        holder.tv_name.setText(goods.goods_name);
        String keyWord = ((GoodsListActivity)mcontext).keyword;
        String goods_name = goods.goods_name;
        if (keyWord != null && goods_name.contains(keyWord) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String styleName = goods_name.substring(0,goods_name.indexOf(keyWord))+
                    "<font color='#F23131'>"+keyWord+"</font>"+
                    goods_name.substring(goods_name.indexOf(keyWord)+keyWord.length());
            Spanned spanned = Html.fromHtml(styleName, Html.FROM_HTML_MODE_COMPACT);
            holder.tv_name.setText("".equals(styleName) ? goods_name: spanned);
        }else {
            holder.tv_name.setText(goods_name);
        }
        holder.tv_price.setText( goods.goods_price);
        holder.tv_volume.setText(goods.goods_salenum+" " + mcontext.getString(R.string.sales_volume));
        holder.tv_type.setText(goods.is_own_shop.equals("1") ? mcontext.getString(R.string.order_reminder161) : "");
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //商品详情
                Intent intent = new Intent(mcontext,
                        ProductDetailsActivity.class);
                intent.putExtra("goods_id", goods.goods_id);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    class GoodsHodler {
        ImageView iv_goodsimg;
        TextView tv_name, tv_price, tv_volume, tv_type;
        RelativeLayout rl_item;

        public GoodsHodler(View convertView) {
            iv_goodsimg = (ImageView) convertView.findViewById(R.id.iv_goodsimg);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            tv_volume = (TextView) convertView.findViewById(R.id.tv_volume);
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}
