package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.GroupDatailsActivity;
import com.community.activity.JoinGroupActivity;
import com.community.model.FindHomeInfo;

import java.util.List;

/**
 * 群组
 * Created by mayn on 2018/9/6.
 */

public class GroupAdapter extends BaseAdapter {
    private Context mcontext;
    private List<FindHomeInfo.circle_list> data;

    public GroupAdapter(Context mcontext, List<FindHomeInfo.circle_list> data) {
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
            convertView = View.inflate(mcontext, R.layout.item_group, null);
            new GroupHolder(convertView);
        }
        GroupHolder holder = (GroupHolder) convertView.getTag();
        final FindHomeInfo.circle_list circle_list = data.get(position);
        Glide.with(mcontext).load(circle_list.circle_masterid_avatar).into(holder.iv_icon);
        holder.iv_title.setText(circle_list.circle_name);
        holder.tv_numberpeople.setText(mcontext.getString(R.string.find_reminder120) + circle_list.circle_mcount + mcontext.getString(R.string.find_reminder121));
        holder.tv_introduction.setText(circle_list.circle_desc);
        holder.tv_description.setText(circle_list.circle_notice);
        if (circle_list.member_list != null && circle_list.member_list.size() != 0) {
            MemberAdapter memberAdapter=new MemberAdapter(circle_list.member_list);
            holder.gv_member.setAdapter(memberAdapter);
        }
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, GroupDatailsActivity.class);
                intent.putExtra("circle_id",circle_list.circle_id);
                mcontext.startActivity(intent);
            }
        });
        holder.tv_join.setOnClickListener(new View.OnClickListener() {//加入群组
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, JoinGroupActivity.class);
                intent.putExtra("circle_id",circle_list.circle_id);
                mcontext.startActivity(intent);
//                Intent intent3 = new Intent(mcontext,WebActivity.class);
//                Bundle bundle3 = new Bundle();
//                bundle3.putString("url", circle_list.join_url);
//                bundle3.putString("title", "申请加群");
//                intent3.putExtras(bundle3);
//                mcontext.startActivity(intent3);
            }
        });
        return convertView;
    }

    class GroupHolder {
        RelativeLayout rl_item;
        ImageView iv_icon;
        TextView iv_title, tv_join, tv_numberpeople, tv_introduction, tv_description;
        MyGridView gv_member;

        public GroupHolder(View convertView) {
            iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            iv_title = (TextView) convertView.findViewById(R.id.iv_title);
            tv_join = (TextView) convertView.findViewById(R.id.tv_join);
            tv_numberpeople = (TextView) convertView.findViewById(R.id.tv_numberpeople);
            tv_introduction = (TextView) convertView.findViewById(R.id.tv_introduction);
            tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            gv_member = (MyGridView) convertView.findViewById(R.id.gv_member);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }

    class MemberAdapter extends BaseAdapter {
        private List<FindHomeInfo.circle_list.member_list> member_list;

        public MemberAdapter(List<FindHomeInfo.circle_list.member_list> member_list) {
            this.member_list = member_list;
        }

        @Override
        public int getCount() {
            return member_list == null ? 0 : member_list.size();
        }

        @Override
        public Object getItem(int position) {
            return member_list == null ? null : member_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mcontext, R.layout.item_groupimg, null);
                new MemberHolder(convertView);
            }
            MemberHolder holder= (MemberHolder) convertView.getTag();
            Glide.with(mcontext).load(member_list.get(position)).into(holder.iv_icon);
            return convertView;
        }

        class MemberHolder {
            ImageView iv_icon;

            public MemberHolder(View convertView) {
                iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                convertView.setTag(this);
            }
        }
    }
}
