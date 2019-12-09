package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.ImagePreview;
import com.community.utils.ClutterUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 朋友圈详情
 * Created by mayn on 2018/10/15.
 */
public class CircleoffriendsDatilsAdapter extends BaseAdapter{
    private Context mcontext;
    private List<String>thumb_list;
    private int h = 0;//图片的高度

    public CircleoffriendsDatilsAdapter(Context mcontext, List<String> thumb_list) {
        this.mcontext = mcontext;
        this.thumb_list = thumb_list;
        h = ClutterUtils.getScreenWidth(mcontext) - ClutterUtils.dp2px(mcontext, 85);
    }

    @Override
    public int getCount() {
        return thumb_list==null?0:thumb_list.size();
    }

    @Override
    public Object getItem(int position) {
        return thumb_list==null?null:thumb_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_dynamicimg, null);
            new ImgHolder(convertView);
        }
        ImgHolder holder = (ImgHolder) convertView.getTag();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_img.getLayoutParams();
        if (getCount() < 2) {
            layoutParams.height = h;
        } else if (getCount() < 3) {
            layoutParams.height = h / 2;
        } else {
            layoutParams.height = h / 3;
        }
        holder.iv_img.setLayoutParams(layoutParams);
        Glide.with(mcontext).load(thumb_list.get(position)).into(holder.iv_img);
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    ArrayList<String> list = new ArrayList<String>();
//                    for (int i = 0; i < findImagesModels.size(); i++) {
//                        list.add(findImagesModels.get(i).getApic_cover());
//                    }
                Intent intent = new Intent(mcontext, ImagePreview.class);
                intent.putStringArrayListExtra("photourl", (ArrayList<String>) thumb_list);
                intent.putExtra("id", position);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ImgHolder {
        ImageView iv_img;

        public ImgHolder(View convertView) {
            iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(this);
        }
    }
}
