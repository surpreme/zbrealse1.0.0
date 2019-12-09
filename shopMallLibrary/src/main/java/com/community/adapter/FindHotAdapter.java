package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.WebActivity;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.FindHomeInfo;

import java.util.List;

/**
 * 热门活动
 * Created by mayn on 2018/9/6.
 */
public class FindHotAdapter extends RecyclerView.Adapter<FindHotHotHolder>{
    private Context mcontext;
    private List<FindHomeInfo.hot_activity_list> data;

    public FindHotAdapter(Context mcontext, List<FindHomeInfo.hot_activity_list> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    @Override
    public FindHotHotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FindHotHotHolder holder = new FindHotHotHolder(LayoutInflater.from(mcontext)
                .inflate(R.layout.item_gettogetherhot, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FindHotHotHolder holder, int position) {
        final FindHomeInfo.hot_activity_list hot_list = data.get(position);
        Glide.with(mcontext).load(hot_list.thumb).into(holder.iv_img);
        holder.tv_title.setText(hot_list.title);
        holder.tv_time.setText(mcontext.getString(R.string.find_reminder115)+hot_list.start_time);
        holder.tv_address.setText(mcontext.getString(R.string.address_sc)+hot_list.city_name);
        holder.tv_price.setText(mcontext.getString(R.string.find_reminder116)+hot_list.price);
        holder.tv_type.setText(hot_list.class_name);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", hot_list.url);
                bundle.putString("title", hot_list.class_name);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
class FindHotHotHolder extends RecyclerView.ViewHolder {
    LinearLayout ll_item;
    TextView tv_title, tv_time, tv_address, tv_price,tv_type;
    ImageView iv_img;
    public FindHotHotHolder(View itemView) {
        super(itemView);
        ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        tv_address = (TextView) itemView.findViewById(R.id.tv_address);
        tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        tv_type = (TextView) itemView.findViewById(R.id.tv_type);
    }
}