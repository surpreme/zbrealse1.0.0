package com.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.GroupDatailsInfo;

import java.util.List;

/**
 * 群组成员
 * Created by mayn on 2018/9/18.
 */
public class GroupDatailsMemberAdapter extends BaseAdapter {
    private Context mcontext;
    private List<GroupDatailsInfo.circle_member_list> data;

    public GroupDatailsMemberAdapter(Context mcontext, List<GroupDatailsInfo.circle_member_list> data) {
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
            convertView = View.inflate(mcontext, R.layout.item_groupdatailsmember, null);
            new GroupDatailsMemberHolder(convertView);
        }
        GroupDatailsMemberHolder holder = (GroupDatailsMemberHolder) convertView.getTag();
        GroupDatailsInfo.circle_member_list circle_member_list = data.get(position);
        Glide.with(mcontext).load(circle_member_list.member_avatar).into(holder.iv_icon);
        return convertView;
    }

    class GroupDatailsMemberHolder {
        CircleImageView iv_icon;

        public GroupDatailsMemberHolder(View convertView) {
            iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(this);
        }
    }
}
