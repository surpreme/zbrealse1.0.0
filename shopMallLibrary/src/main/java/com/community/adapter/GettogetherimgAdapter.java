package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aite.a.activity.WebActivity;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.GettogetherInfo;

import java.util.List;

/**
 * 活动图片
 * Created by mayn on 2018/9/5.
 */
public class GettogetherimgAdapter extends BaseAdapter{
    private Context mcontext;
    private List<GettogetherInfo.adv_buttom>data;

    public GettogetherimgAdapter(Context mcontext, List<GettogetherInfo.adv_buttom> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(mcontext, R.layout.item_gettogetherimg,null);
            new GettogetherimgHolder(convertView);
        }
        GettogetherimgHolder holder = (GettogetherimgHolder) convertView.getTag();
        Glide.with(mcontext).load(data.get(position).adv_pic).into(holder.iv_img);
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", data.get(position).adv_pic_url);
                bundle.putString("title", data.get(position).adv_title);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }
    class GettogetherimgHolder{
        ImageView iv_img;
        public GettogetherimgHolder(View convertView) {
            convertView.setTag(this);
            iv_img= (ImageView) convertView.findViewById(R.id.iv_img);
        }
    }
}
