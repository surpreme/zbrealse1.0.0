package com.community.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiteshangcheng.a.R;
import com.community.msgholder.MessageTxt1Holder;
import com.community.msgholder.MessageTxt2Holder;

import java.util.List;

import chat.model.SessionMessageInfo;

/**
 * 对话适配
 * Created by mayn on 2018/9/5.
 */
public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<SessionMessageInfo> data;

    public ChatListAdapter(Context mcontext, List<SessionMessageInfo> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    public void addData(SessionMessageInfo data){
        this.data.add(data);
        notifyItemInserted(this.data.size()-1);
    }

    @Override
    public int getItemViewType(int position) {
        switch (data.get(position).msgtype) {
            case "TXT":
                if (data.get(position).isSend.equals("1")){
                    return 1;
                }else if (data.get(position).isSend.equals("2")){
                    return 2;
                }
            case "IMAGE":
                return 2;
            case "VOICE":
                return 3;
            case "FILE":
                return 4;
            case "VIDEO":
                return 5;
            case "LOCATION":
                return 6;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_self, parent, false);
                return new MessageTxt1Holder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_other, parent, false);
                return new MessageTxt2Holder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SessionMessageInfo messageInfo = data.get(position);
        if (messageInfo.isSend.equals("1")){
            if (holder instanceof MessageTxt1Holder){
                MessageTxt1Holder holder1= (MessageTxt1Holder) holder;
                holder1.tv_txt.setText(messageInfo.textmsg.txtcontent);
            }
        }else if (messageInfo.isSend.equals("2")){
            if (holder instanceof MessageTxt2Holder){
                MessageTxt2Holder holder1= (MessageTxt2Holder) holder;
                holder1.tv_txt.setText(messageInfo.textmsg.txtcontent);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }
}

