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
import com.community.model.GettogetherInfo;

import java.util.List;

/**
 * 官方活动
 * Created by mayn on 2018/9/5.
 */

public class OfficialAdapter extends RecyclerView.Adapter<OfficialHolder>{
    private Context mcontext;
    private List<GettogetherInfo.off_list>off_list;

    public OfficialAdapter(Context mcontext, List<GettogetherInfo.off_list> off_list) {
        this.mcontext = mcontext;
        this.off_list = off_list;
    }

    @Override
    public OfficialHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfficialHolder holder = new OfficialHolder(LayoutInflater.from(mcontext)
                .inflate(R.layout.item_official, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(OfficialHolder holder, int position) {
        final GettogetherInfo.off_list hot_list = off_list.get(position);
        Glide.with(mcontext).load(hot_list.thumb).into(holder.iv_img);
        holder.tv_title.setText(hot_list.title);
        holder.tv_time.setText(mcontext.getString(R.string.find_reminder119)+hot_list.start_time);
        holder.tv_address.setText(mcontext.getString(R.string.find_reminder118)+hot_list.city_name);
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
        return off_list==null?0:off_list.size();
    }
}
class OfficialHolder extends RecyclerView.ViewHolder{
    LinearLayout ll_item;
    TextView tv_title, tv_time, tv_address, tv_price,tv_type;
    ImageView iv_img;
    public OfficialHolder(View itemView) {
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
