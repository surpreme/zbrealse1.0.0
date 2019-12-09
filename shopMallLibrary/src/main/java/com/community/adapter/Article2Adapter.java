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
import com.community.model.ForumInfo;

import java.util.List;

/**
 * 文章
 * Created by mayn on 2018/9/6.
 */

public class Article2Adapter extends BaseAdapter {
    private Context mcontext;
    private List<ForumInfo.cms_article_list> data;

    public Article2Adapter(Context mcontext, List<ForumInfo.cms_article_list> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : (data.size()-1);
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position+1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_article, null);
            new ArticleHolder(convertView);
        }
        ArticleHolder holder = (ArticleHolder) convertView.getTag();
        final ForumInfo.cms_article_list cms_article_list = data.get(position+1);
        Glide.with(mcontext).load(cms_article_list.article_image).into(holder.iv_img);
        holder.tv_titme.setText(cms_article_list.article_title);
        holder.tv_date.setText(cms_article_list.article_publish_time);
        holder.tv_name.setText(cms_article_list.article_publisher_name);
        holder.tv_pl.setText(cms_article_list.article_comment_count);
        holder.tv_ck.setText(cms_article_list.article_click);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", cms_article_list.url);
                bundle.putString("title", cms_article_list.article_title);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ArticleHolder {
        ImageView iv_img;
        TextView tv_titme, tv_date, tv_name, tv_pl, tv_ck;
        RelativeLayout rl_item;
        public ArticleHolder(View convertView) {
            iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            tv_titme = (TextView) convertView.findViewById(R.id.tv_titme);
            tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_pl = (TextView) convertView.findViewById(R.id.tv_pl);
            tv_ck = (TextView) convertView.findViewById(R.id.tv_ck);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}
