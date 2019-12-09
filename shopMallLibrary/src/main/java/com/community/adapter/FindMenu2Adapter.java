package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.activity.WebActivity;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.FindHomeInfo;

import java.util.List;

/**
 * 热门帖子菜单
 * Created by mayn on 2018/9/5.
 */
public class FindMenu2Adapter extends BaseAdapter{
    private Context mcontext;
    private List<FindHomeInfo.cms_class_list>data;

    public FindMenu2Adapter(Context mcontext, List<FindHomeInfo.cms_class_list> data) {
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
            convertView=View.inflate(mcontext,R.layout.item_gettogethermenu,null);
            new FindMenuHolder(convertView);
        }
        FindMenuHolder holder = (FindMenuHolder) convertView.getTag();
        final FindHomeInfo.cms_class_list class_list = data.get(position);
        Glide.with(mcontext).load(class_list.adv_pic).into(holder.iv_menu);
        holder.tv_name.setText(class_list.adv_title);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", class_list.adv_pic_url);
                bundle.putString("title", class_list.adv_title);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }
    class FindMenuHolder{
        ImageView iv_menu;
        TextView tv_name;
        LinearLayout ll_item;
        public FindMenuHolder(View convertView) {
            iv_menu= (ImageView) convertView.findViewById(R.id.iv_menu);
            tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            ll_item= (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(this);
        }
    }
}
