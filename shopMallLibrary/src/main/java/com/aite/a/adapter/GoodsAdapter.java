package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.model.CustomHomeInfo;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 商品
 * Created by mayn on 2018/4/26.
 */

public class GoodsAdapter extends BaseAdapter{
    private List<CustomHomeInfo.goods.item> list = null;
    private Context mcontext;
    public GoodsAdapter(List<CustomHomeInfo.goods.item> list,Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
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
            convertView = View.inflate(mcontext, R.layout.good_recommend,null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
//			bitmapUtils.display(holder.goods_image, list.get(position)
//					.getGoods_image_url());
        final CustomHomeInfo.goods.item item = list.get(position);
        Glide.with(mcontext).load(item.goods_image).into(holder.goods_image);
        // 商品适配
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.goods_image
                .getLayoutParams();
        layoutParams.height = getw() / 2;
        holder.goods_image.setLayoutParams(layoutParams);
        holder.goods_name.setText(item.goods_name);
        holder.goods_price.setText("￥"+item.goods_price);
        holder.goods_price2.setText(item.goods_marketprice);
        holder.goods_price2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        holder.goods_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,
                        ProductDetailsActivity.class);
                intent.putExtra("goods_id", item.goods_id);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView goods_image;
        TextView goods_name, goods_price,goods_price2;

        public ViewHolder(View convertView) {
            goods_image = (ImageView) convertView
                    .findViewById(R.id.goods_image);
            goods_name = (TextView) convertView
                    .findViewById(R.id.goods_name);
            goods_price = (TextView) convertView
                    .findViewById(R.id.goods_price);
            goods_price2 = (TextView) convertView
                    .findViewById(R.id.goods_price2);
            convertView.setTag(this);
        }
    }


    private int getw() {
        WindowManager wm = (WindowManager) mcontext
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
}
