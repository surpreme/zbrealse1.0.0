package com.community.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.aite.a.base.Mark.PICKIMAGE;

/**
 * 图片
 * Created by mayn on 2018/9/22.
 */
public class Photo2Adapter extends BaseAdapter {
    private Context mcontext;
    private List<PhotoInfo> data;
    private Handler handler;
    private int height;

    public Photo2Adapter(Context mcontext, List<PhotoInfo> data, Handler handler, int height) {
        this.mcontext = mcontext;
        this.data = data;
        this.handler = handler;
        this.height = height;
    }

    /**
     * 添加
     *
     * @param id
     * @param f
     */
    public void add(int id, List<File> f) {
        List<PhotoInfo> l = new ArrayList<>();
        if (data.size() > 0) {
            data.remove(data.size() - 1);
        }
        for (File a : f) {
            l.add(new PhotoInfo(true, a));
        }
        data.addAll(id, l);
        if (data.size() < 5) {
            data.add(new PhotoInfo(false, null));
        }
        notifyDataSetChanged();
    }

    /**
     * 删除
     *
     * @param id
     */
    private void del(int id) {
        data.remove(id);
        if (data.size() == 0){
            data.add(new PhotoInfo(false, null));
        }
        notifyDataSetChanged();
    }

    public List<File> getData() {
        List<File> data2 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isfile) {
                data2.add(data.get(i).img);
            }
        }
        return data2;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_zbphoto, null);
            new PhotoHolder(convertView);
        }
        PhotoHolder holder = (PhotoHolder) convertView.getTag();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.iv_photo.getLayoutParams();
        layoutParams.height = height;
        holder.iv_photo.setLayoutParams(layoutParams);
        if (data.get(position).isfile) {
            holder.iv_del.setVisibility(View.VISIBLE);
            Glide.with(mcontext).load(data.get(position).img).into(holder.iv_photo);
        } else {
            holder.iv_del.setVisibility(View.GONE);
            Glide.with(mcontext).load(R.drawable.add9img).into(holder.iv_photo);
        }
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                del(position);
            }
        });
        holder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.get(position).isfile)
                handler.sendMessage(handler.obtainMessage(PICKIMAGE,position));
            }
        });

        return convertView;
    }

    class PhotoHolder {
        ImageView iv_photo, iv_del;

        public PhotoHolder(View convertView) {
            iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
            convertView.setTag(this);
        }
    }


}
