package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.NewsBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsRecyAdapter extends RecyclerView.Adapter<NewsRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<NewsBean.DatasBean.ListBean> listBeans;

    public NewsRecyAdapter(Context context, List<NewsBean.DatasBean.ListBean> listBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBeans = listBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public OnClickLstenerInterface.OnRecyClickInterface getLstenerInterface() {
        return lstenerInterface;
    }

    public void setLstenerInterface(OnClickLstenerInterface.OnRecyClickInterface lstenerInterface) {
        this.lstenerInterface = lstenerInterface;
    }

    private OnClickLstenerInterface.OnRecyClickInterface lstenerInterface;

    /**
     * circle_id : 69
     * circle_name : 1111231
     * circle_desc : 1
     * circle_masterid : 1986
     * circle_mastername : boslm
     * circle_img : https://aitecc.com/data/upload/circle/default_group_logo.gif
     * class_id : 1
     * circle_mcount : 1
     * circle_thcount : 0
     * circle_gcount : 0
     * circle_pursuereason : 1
     * circle_notice :
     * circle_status : 1
     * circle_statusinfo :
     * circle_joinaudit : 0
     * circle_addtime : 1573090460
     * circle_noticetime : null
     * is_recommend : 0
     * is_hot : 0
     * circle_tag : 1
     * new_verifycount : 0
     * new_informcount : 0
     * mapply_open : 0
     * mapply_ml : 0
     * new_mapplycount : 0
     * circle_type : web
     * circle_back_img : null
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(listBeans.get(position).getCircle_img()).into(holder.iconIv);
        holder.titleTv.setText(String.format("圈子  %s", listBeans.get(position).getCircle_name()));
        holder.msgTv.setText(String.format("话题 %s组员 %s", listBeans.get(position).getCircle_mcount(),
                listBeans.get(position).getNew_informcount()));
        holder.informationTv.setText(listBeans.get(position).getCircle_desc());
    }

    @Override
    public int getItemCount() {
        return listBeans == null ? 0 : listBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon_iv)
        ImageView iconIv;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.msg_tv)
        TextView msgTv;
        @BindView(R2.id.information_tv)
        TextView informationTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
