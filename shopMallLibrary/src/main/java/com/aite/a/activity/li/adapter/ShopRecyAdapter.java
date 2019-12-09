package com.aite.a.activity.li.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.activity.li.bean.ShopBean;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ShopRecyAdapter extends RecyclerView.Adapter<ShopRecyAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<ShopBean.DatasBean.StoreListBean> mDatas;

    public ShopRecyAdapter(Context context, List<ShopBean.DatasBean.StoreListBean> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }

    private OnClickInterface onClickInterface;

    public OnClickInterface getOnClickInterface() {
        return onClickInterface;
    }

    public void setOnClickInterface(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    public void removeAllData() {
        mDatas.clear();
    }

    @NonNull
    @Override
    public ShopRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.shop_item_layout, parent, false);
        ShopRecyAdapter.ViewHolder viewHolder = new ShopRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShopRecyAdapter.ViewHolder holder, final int position) {
        holder.shop_name.setText(mDatas.get(position).getStore_name());
        Glide.with(context).load(mDatas.get(position).getStore_avatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.shop_icon);
//        0人  好评率0   距离0s mDatas.get(position).getStore_sales()
        long away = Long.valueOf(mDatas.get(position).getDistance());
        long bug = Long.valueOf(mDatas.get(position).getStore_sales());
        String buyS = String.valueOf(bug);
        String awayS = String.valueOf(away);
        if (away > 1000)
            awayS = String.valueOf(away / 1000) + "km";
        if (bug > 1000)
            buyS = String.valueOf(bug / 1000) + "k";
        holder.tv_shop_msg.setText(
                "收藏" + mDatas.get(position).getStore_collect() + " " +
                        "好评率" + mDatas.get(position).getStore_evaluate() + " " +
                        "距离" + awayS +
                        " 销量" + buyS);
//        holder.tv_shop_msg.setText(
//                "收藏人数"+mDatas.get(position).getStore_collect()+"人 "+
//                        "好评率"+mDatas.get(position).getStore_evaluate()+" "+
//                        "距离"+
//                        ((Long.valueOf(mDatas.get(position).getDistance()))>1000?
//                        ((Long.valueOf(mDatas.get(position).getDistance())))/1000+"km":(Long.valueOf(mDatas.get(position).getDistance())+"m"+
//                        " 店铺销量"+(Long.valueOf(mDatas.get(position).getStore_sales())>1000?
//                        (Long.valueOf(mDatas.get(position).getStore_sales()))/1000+"千":(Long.valueOf(mDatas.get(position).getStore_sales())))))
//        );
        holder.shop_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.getPostion(position);
            }
        });
        if (mDatas.get(position).getGoods_lists().size() > 0) {
            holder.layout1.setVisibility(View.VISIBLE);
            Glide.with(context).load(mDatas.get(position).getGoods_lists().get(0).getGoods_image()).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(holder.photo1);
            holder.photo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context,
                            ProductDetailsActivity.class);
                    intent.putExtra("goods_id", mDatas.get(position).getGoods_lists().get(0).getGoods_id());
                    context.startActivity(intent);
                }
            });
            holder.text1.setText("$" + mDatas.get(position).getGoods_lists().get(0).getGoods_price());
            if (mDatas.get(position).getGoods_lists().size() > 1) {
                holder.layout2.setVisibility(View.VISIBLE);
                Glide.with(context).load(mDatas.get(position).getGoods_lists().get(1).getGoods_image()).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(holder.photo2);
                holder.photo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context,
                                ProductDetailsActivity.class);
                        intent.putExtra("goods_id", mDatas.get(position).getGoods_lists().get(1).getGoods_id());
                        context.startActivity(intent);
                    }
                });
                holder.text1.setText("$" + mDatas.get(position).getGoods_lists().get(1).getGoods_price());
                if (mDatas.get(position).getGoods_lists().size() > 2) {
                    holder.layout3.setVisibility(View.VISIBLE);
                    Glide.with(context).load(mDatas.get(position).getGoods_lists().get(2).getGoods_image()).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(holder.photo3);
                    holder.photo3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context,
                                    ProductDetailsActivity.class);
                            intent.putExtra("goods_id", mDatas.get(position).getGoods_lists().get(2).getGoods_id());
                            context.startActivity(intent);
                        }
                    });
                    holder.text1.setText("$" + mDatas.get(position).getGoods_lists().get(2).getGoods_price());
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name, tv_shop_msg;
        ImageView shop_icon, photo1, photo2, photo3;
        Button goshop_btn;
        RelativeLayout layout1, layout2, layout3;
        TextView text1, text2, text3;
        LinearLayout shop_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_icon = itemView.findViewById(R.id.shop_icon);
            photo1 = itemView.findViewById(R.id.iv_photo_1);
            photo2 = itemView.findViewById(R.id.iv_photo_2);
            photo3 = itemView.findViewById(R.id.iv_photo_3);
            tv_shop_msg = itemView.findViewById(R.id.tv_shop_msg);
            goshop_btn = itemView.findViewById(R.id.goshop_btn);
            layout1 = itemView.findViewById(R.id.layout_img_1);
            layout2 = itemView.findViewById(R.id.layout_img_2);
            layout3 = itemView.findViewById(R.id.layout_img_3);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
            shop_layout = itemView.findViewById(R
                    .id.shop_layout);

        }
    }

    public interface OnClickInterface {
        void getPostion(int position);
    }
}

