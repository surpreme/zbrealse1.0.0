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
import com.aite.mainlibrary.Mainbean.TopNewsBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TopNewsRecyAdapter extends RecyclerView.Adapter<TopNewsRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<TopNewsBean.DatasBean> listBeans;

    public TopNewsRecyAdapter(Context context, List<TopNewsBean.DatasBean> listBeans) {
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
     * article_id : 55
     * article_title : 中秋夜深圳地铁3号线被孔明灯逼停 地铁发生短路
     * article_image : https://aitecc.com/data/upload/cms/article/2/05274232573365326.jpg
     * article_link :
     * article_publish_time : 2016-09-17
     * article_author : aitebobo
     * article_abstract : 15日晚23点43分左右，龙岗线地铁发生短路，产生火花。车站工作人员马上赶往现场进行处理，所幸没有乘客受伤，列车和车站器材也没有造成损坏。
     * article_click : 149
     * article_image_all : a:1:{s:21:"05274232573365326.jpg";a:4:{s:4:"name";s:21:"05274232573365326.jpg";s:5:"width";i:238;s:6:"height";i:147;s:4:"path";s:1:"2";}}
     * article_comment_count : 45
     * article_class_id : 1
     * article_publisher_id : 2
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(listBeans.get(position).getArticle_image()).into(holder.iconIv);
        holder.titleTv.setText(listBeans.get(position).getArticle_abstract());
        holder.msgTv.setText(listBeans.get(position).getArticle_comment_count() + "赞" +
                listBeans.get(position).getArticle_publish_time());
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
