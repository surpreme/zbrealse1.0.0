package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.WebActivity;
import com.aite.a.model.GettogetherListInfo;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 活动
 * Created by mayn on 2018/9/14.
 */
public class GettogetherAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GettogetherListInfo.list> data;

    public GettogetherAdapter(Context mcontext, List<GettogetherListInfo.list> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    /**
     * 刷新
     *
     * @param data
     */
    public void refreshData(List<GettogetherListInfo.list> data) {
        if (data == null) return;
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 添加
     *
     * @param data
     */
    public void addData(List<GettogetherListInfo.list> data) {
        if (data == null) return;
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_gettogether, null);
            new GettogetherHolder(convertView);
        }
        GettogetherHolder holder = (GettogetherHolder) convertView.getTag();
        final GettogetherListInfo.list list = data.get(position);
        Glide.with(mcontext).load(list.thumb).into(holder.iv_img);
        holder.tv_title.setText(list.title);
        holder.tv_time.setText(mcontext.getString(R.string.find_reminder115)+list.start_time);
        holder.tv_address.setText(mcontext.getString(R.string.address_sc)+list.city_name);
        holder.tv_price.setText(mcontext.getString(R.string.find_reminder116)+list.price);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", list.url);
                bundle.putString("title", list.title);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    class GettogetherHolder {
        RelativeLayout rl_item;
        ImageView iv_img;
        TextView tv_title, tv_time, tv_address, tv_price;

        public GettogetherHolder(View convertView) {
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(this);
        }
    }
}
