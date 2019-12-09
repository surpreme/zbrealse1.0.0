package com.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.model.CircleoffriendsDatilsInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 朋友圈详情评论
 * Created by mayn on 2018/10/15.
 */
public class CircleoffriendsDatils2Adapter extends BaseAdapter{
    private Context mcontext;
    private List<CircleoffriendsDatilsInfo.threply_list>threply_list;

    public CircleoffriendsDatils2Adapter(Context mcontext, List<CircleoffriendsDatilsInfo.threply_list> threply_list) {
        this.mcontext = mcontext;
        this.threply_list = threply_list;
    }

    @Override
    public int getCount() {
        return threply_list==null?0:threply_list.size();
    }

    @Override
    public Object getItem(int position) {
        return threply_list==null?null:threply_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_comment, null);
            new CommentHolder(convertView);
        }
        CommentHolder holder = (CommentHolder) convertView.getTag();
        CircleoffriendsDatilsInfo.threply_list reply_info = this.threply_list.get(position);
        holder.tv_reply.setText(reply_info.reply_content);
        holder.tv_replyname.setText(reply_info.member_name + ":");
        return convertView;
    }

    class CommentHolder {
        TextView tv_replyname, tv_reply;

        public CommentHolder(View convertView) {
            tv_replyname = (TextView) convertView.findViewById(R.id.tv_replyname);
            tv_reply = (TextView) convertView.findViewById(R.id.tv_reply);
            convertView.setTag(this);
        }
    }
}
