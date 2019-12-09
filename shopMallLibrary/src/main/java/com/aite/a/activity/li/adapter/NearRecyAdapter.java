package com.aite.a.activity.li.adapter;

import android.content.Context;
import android.content.Intent;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.activity.li.activity.Amapactivity;
import com.aite.a.activity.li.bean.NearBean;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;



public class NearRecyAdapter extends RecyclerView.Adapter<NearRecyAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<NearBean.DatasBean.ListBean> mDatas;

    public NearRecyAdapter(Context context, List<NearBean.DatasBean.ListBean> datas) {
        //这里适配器是写给主活动互相调用的方法
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);

    }

    public void cleardata() {
        mDatas.clear();
    }

    public GuessLikeAdapter.OnclickInterface getOnclickInterface() {
        return onclickInterface;
    }

    public void setOnclickInterface(GuessLikeAdapter.OnclickInterface onclickInterface) {
        this.onclickInterface = onclickInterface;
    }

    private GuessLikeAdapter.OnclickInterface onclickInterface;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public NearRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.near_item_layout, parent, false);
        NearRecyAdapter.ViewHolder viewHolder = new NearRecyAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NearRecyAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(mDatas.get(position).getStore_label()).into(holder.shop_icon);
        holder.shop_name.setText(mDatas.get(position).getStore_name());
        holder.shop_msg.setText(mDatas.get(position).getStore_zy());
        holder.shop_address.setText(mDatas.get(position).getStore_address());
        long away = (Long.valueOf(mDatas.get(position).getDistance()));
        String io = null;
        if (away < 500)
            io = "<1km";
        if (away < 2000 && away > 1000)
            io = "<2km";
        if (away > 2000 && away < 5000)
            io = "<5km";
        if (away > 5000)
            io = ">5km";
        //"<" + away > 1000 ? Long.valueOf(mDatas.get(position).getDistance()) / 1000 + "km" : mDatas.get(position).getDistance() + "m"

        holder.shop_away.setText(io == null ? "" : io);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoreDetailsActivity.class);
                intent.putExtra("store_id", mDatas.get(position).getStore_id());
                context.startActivity(intent);
            }
        });
        holder.location_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Amapactivity.class);
                intent.putExtra("store_points", mDatas.get(position).getStore_points());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name, shop_msg;
        TextView shop_address, shop_away;

        ImageView shop_icon;
        LinearLayout layout, location_ll;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_icon = itemView.findViewById(R.id.shop_icon);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_msg = itemView.findViewById(R.id.shop_msg);
            shop_address = itemView.findViewById(R.id.shop_address);
            shop_away = itemView.findViewById(R.id.shop_away);
            layout = itemView.findViewById(R.id.layout);
            location_ll = itemView.findViewById(R.id.location_ll);


        }
    }

    public interface OnclickInterface {
        void getPostion(int postion);
    }

}
