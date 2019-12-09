package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.WebActivity;
import com.aiteshangcheng.a.R;
import com.community.model.ForumSectionInfo;

import java.util.List;

/**
 * 帖子板块热门
 * Created by mayn on 2018/9/8.
 */

public class ForumSectionAdapter extends BaseAdapter{
    private Context mcontext;
    private List<ForumSectionInfo.cms_article_hot_list>data;

    public ForumSectionAdapter(Context mcontext, List<ForumSectionInfo.cms_article_hot_list> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data==null?null:data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(mcontext, R.layout.item_forumsection,null);
            new ForumSectionHolder(convertView);
        }
        ForumSectionHolder holder = (ForumSectionHolder) convertView.getTag();
        final ForumSectionInfo.cms_article_hot_list cms_article_hot_list = data.get(position);
        holder.tv_name.setText(cms_article_hot_list.article_title);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent9 = new Intent(mcontext,WebActivity.class);
                Bundle bundle9 = new Bundle();
                bundle9.putString("url", cms_article_hot_list.url);
                bundle9.putString("title", cms_article_hot_list.article_title);
                intent9.putExtras(bundle9);
                mcontext.startActivity(intent9);
            }
        });
        return convertView;
    }
    class ForumSectionHolder{
        TextView tv_name;
        LinearLayout ll_item;
        public ForumSectionHolder(View convertView) {
            tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            ll_item= (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(this);
        }
    }
}
