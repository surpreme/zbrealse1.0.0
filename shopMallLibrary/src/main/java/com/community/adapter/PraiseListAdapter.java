package com.community.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.PraiseListInfo;

import java.util.List;

/**
 * 点赞列表
 * Created by mayn on 2018/10/15.
 */
public class PraiseListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<PraiseListInfo.list> list;
    private Handler handler;

    public PraiseListAdapter(Context mcontext, List<PraiseListInfo.list> list,Handler handler) {
        this.mcontext = mcontext;
        this.list = list;
        this.handler = handler;
    }

    /**
     * 刷新
     * @param list
     */
    public void refreshData(List<PraiseListInfo.list> list){
        if (list==null)return;
        this.list=list;
        notifyDataSetChanged();
    }

    /**
     * 添加
     * @param list
     */
    public void addData(List<PraiseListInfo.list> list){
        if (list==null)return;
        this.list=list;
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
            convertView = View.inflate(mcontext, R.layout.item_praiselist, null);
            new PraiseListHolder(convertView);
        }
        PraiseListHolder holder = (PraiseListHolder) convertView.getTag();
        final PraiseListInfo.list list = this.list.get(position);
        Glide.with(mcontext).load(list.membere_avatar).into(holder.iv_iocn);
        holder.tv_name.setText(list.member_name);
        holder.tv_addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//加好友
                handler.sendMessage(handler.obtainMessage(101,list.member_id));
            }
        });
//        holder.rl_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return convertView;
    }

    class PraiseListHolder {
        RelativeLayout rl_item;
        CircleImageView iv_iocn;
        TextView tv_name, tv_addfriend;

        public PraiseListHolder(View convertView) {
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            iv_iocn = (CircleImageView) convertView.findViewById(R.id.iv_iocn);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_addfriend = (TextView) convertView.findViewById(R.id.tv_addfriend);
            convertView.setTag(this);
        }
    }
}
