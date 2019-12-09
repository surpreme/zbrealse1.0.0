package com.community.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.GroupDatailsActivity;

import java.util.List;

/**
 * 群组
 * Created by mayn on 2018/10/16.
 */
public class MyGroupAdapter extends BaseAdapter {
    private Context mcontext;
    private List<MyGroupInfo.is_identity1> data;

    public MyGroupAdapter(Context mcontext, List<MyGroupInfo.is_identity1> data) {
        this.mcontext = mcontext;
        this.data = data;
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
            convertView = View.inflate(mcontext, R.layout.item_mygroup, null);
            new GroupHolder(convertView);
        }
        GroupHolder holder = (GroupHolder) convertView.getTag();
        final MyGroupInfo.is_identity1 is_identity1 = data.get(position);
        Glide.with(mcontext).load(is_identity1.member_avatar).into(holder.iv_icon);
        holder.tv_name.setText(is_identity1.circle_name);
        holder.tv_time.setText("最近活跃:" + is_identity1.cm_jointime);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, GroupDatailsActivity.class);
                intent.putExtra("circle_id",is_identity1.circle_id);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    class GroupHolder {
        RelativeLayout ll_item;
        CircleImageView iv_icon;
        TextView tv_name, tv_time;

        public GroupHolder(View convertView) {
            ll_item = (RelativeLayout) convertView.findViewById(R.id.ll_item);
            iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(this);
        }
    }
}
