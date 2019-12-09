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
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.MyActivityInfo;

import java.util.List;

/**
 * 我的活动
 * Created by mayn on 2018/10/16.
 */
public class MyActivityAdapter extends BaseAdapter {
    private Context mcontext;
    private List<MyActivityInfo.list> list;

    public MyActivityAdapter(Context mcontext, List<MyActivityInfo.list> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    /**
     * 刷新
     *
     * @param list
     */
    public void refreshDta(List<MyActivityInfo.list> list) {
        if (list == null) return;
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 添加
     *
     * @param list
     */
    public void addDta(List<MyActivityInfo.list> list) {
        if (list == null) return;
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_myactivity, null);
            new MyActivityHolder(convertView);
        }
        MyActivityHolder holder = (MyActivityHolder) convertView.getTag();
        final MyActivityInfo.list list = this.list.get(position);
        Glide.with(mcontext).load(list.thumb).into(holder.iv_img);
        holder.tv_title.setText(list.title);
        holder.tv_time.setText(mcontext.getString(R.string.find_reminder115)+list.start_time);
        holder.tv_address.setText(mcontext.getString(R.string.find_reminder118)+list.city_name == null ? "" : list.city_name);
        holder.tv_price.setText(mcontext.getString(R.string.find_reminder116)+list.price);
        if (list.is_end.equals("1")) {
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_type.setText(mcontext.getString(R.string.order_reminder157));
        } else {
            holder.tv_type.setVisibility(View.GONE);
        }

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

    class MyActivityHolder {
        ImageView iv_img;
        TextView tv_title, tv_time, tv_address, tv_price, tv_type;
        RelativeLayout rl_item;

        public MyActivityHolder(View convertView) {
            iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}
