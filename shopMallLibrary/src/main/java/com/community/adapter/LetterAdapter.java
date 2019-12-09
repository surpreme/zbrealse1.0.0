package com.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.model.StationLetterInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 站内信
 * Created by mayn on 2018/9/20.
 */
public class LetterAdapter extends BaseAdapter {
    private Context mcontext;
    private List<StationLetterInfo.messageArray> data;

    public LetterAdapter(Context mcontext, List<StationLetterInfo.messageArray> data) {
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
            convertView = View.inflate(mcontext, R.layout.station_letter_item, null);
            new LetterHolder(convertView);
        }
        LetterHolder holder = (LetterHolder) convertView.getTag();
        final StationLetterInfo.messageArray messageArray = data.get(position);
        holder.tv_title.setText(messageArray.from_member_name);
        holder.tv_date.setText(messageArray.message_time);
        holder.tv_content.setText(messageArray.message_body);
        if (messageArray.ispick) {
            holder.iv_arrow.setImageResource(R.drawable.icon_up_arrow);
            holder.ll_hide_content.setVisibility(View.VISIBLE);
        } else {
            holder.iv_arrow.setImageResource(R.drawable.icon_down_arrow);
            holder.ll_hide_content.setVisibility(View.GONE);
        }
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageArray.ispick) {
                    messageArray.ispick = false;
                } else {
                    messageArray.ispick = true;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class LetterHolder {
        TextView tv_title, tv_date, tv_content;
        ImageView iv_arrow;
        LinearLayout ll_hide_content;
        RelativeLayout rl_item;

        public LetterHolder(View convertView) {
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            iv_arrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            ll_hide_content = (LinearLayout) convertView.findViewById(R.id.ll_hide_content);
            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(this);
        }
    }
}
