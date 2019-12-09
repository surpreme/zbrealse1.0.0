package com.aite.a.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.a.model.ComplaintDialogueInfo;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 申诉对话
 * Created by mayn on 2018/11/21.
 */
public class ComplaintDialogueAdapter extends BaseAdapter{
    private Context mcontext;
    private List<ComplaintDialogueInfo>complaintDialogueInfo;

    public ComplaintDialogueAdapter(Context mcontext, List<ComplaintDialogueInfo> complaintDialogueInfo) {
        this.mcontext = mcontext;
        this.complaintDialogueInfo = complaintDialogueInfo;
    }

    /**
     * 刷新
     * @param complaintDialogueInfo
     */
    public void refreshData( List<ComplaintDialogueInfo> complaintDialogueInfo){
        if (complaintDialogueInfo==null)return;
        this.complaintDialogueInfo=complaintDialogueInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return complaintDialogueInfo==null?0:complaintDialogueInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return complaintDialogueInfo==null?null:complaintDialogueInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view=View.inflate(mcontext, R.layout.item_complaintdialogue,null);
            new ComplaintDialogueHolder(view);
        }
        ComplaintDialogueHolder holder = (ComplaintDialogueHolder) view.getTag();
        ComplaintDialogueInfo complaintDialogueInfo = this.complaintDialogueInfo.get(i);
        holder.tv_cen.setText(complaintDialogueInfo.talk);
        if (complaintDialogueInfo.css.equals("accused")){
            holder.tv_cen.setTextColor(0XFF5AB75A);
        }else{
            holder.tv_cen.setTextColor(0XFFE55050);
        }
        return view;
    }

    class ComplaintDialogueHolder{
        TextView tv_cen;
        public ComplaintDialogueHolder(View view) {
            tv_cen=view.findViewById(R.id.tv_cen);
            view.setTag(this);
        }
    }
}
