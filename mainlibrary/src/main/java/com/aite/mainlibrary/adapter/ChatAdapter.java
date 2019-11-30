package com.aite.mainlibrary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.lzy.basemodule.util.DateUtil;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> mDatas;

    public ChatAdapter(Context context, List<String> mDatas) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
//        ChatMessage chatMessage = mDatas.get(position);
//        if (chatMessage.getType() == ChatMessage.Type.INCOMING) {
//            return 0;
//        } else {
//            return 1;
//
//        }
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                //判断接受消息的布局
                convertView = inflater.inflate(R.layout.result_chat_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = convertView.findViewById(R.id.msg_time);
                viewHolder.mMsg = convertView.findViewById(R.id.msg_tv);
                viewHolder.mImg = convertView.findViewById(R.id.msg_img);


            } else {
                //判断发送消息的布局
                convertView = inflater.inflate(R.layout.send_chat_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = convertView.findViewById(R.id.msg_time);
                viewHolder.mMsg = convertView.findViewById(R.id.msg_tv);
                viewHolder.mImg = convertView.findViewById(R.id.msg_img);
            }
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
//        viewHolder.mDate.setText((CharSequence) DateUtil.formatTime2String(chatMessage.getDate()),false);
//        viewHolder.mMsg.setText(chatMessage.getMsg());
//        viewHolder.mImg.(chatMessage.getMsg());

        return convertView;
    }

    private final class ViewHolder {
        TextView mDate;
        TextView mMsg;
        ImageView mImg;
    }
}
