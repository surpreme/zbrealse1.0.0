package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.NewsGoodBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineHouseRecyAdapter extends RecyclerView.Adapter<MineHouseRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<NewsGoodBean.DatasBean> newsGoodBeans;

    public MineHouseRecyAdapter(Context context, List<NewsGoodBean.DatasBean> newsGoodBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.newsGoodBeans = newsGoodBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recy_itemcollectage, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private OnClickLstenerInterface.OnRecyClickInterface clickInterface;

    public OnClickLstenerInterface.OnRecyClickInterface getClickInterface() {
        return clickInterface;
    }

    public void setClickInterface(OnClickLstenerInterface.OnRecyClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTv.setText(newsGoodBeans.get(position).getArticle_abstract());
        holder.informationTv.setText(String.format("%s赞  %s", newsGoodBeans.get(position).getArticle_comment_count(), newsGoodBeans.get(position).getArticle_publish_time()));
//        Glide.with(context).load(newsGoodBeans.get(position).getArticle_image()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return newsGoodBeans == null ? 0 : newsGoodBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon_iv)
        ImageView iconIv;
        @BindView(R2.id.name_tv)
        TextView nameTv;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.information_tv)
        TextView informationTv;
        @BindView(R2.id.eyes_tv)
        TextView eyesTv;
        @BindView(R2.id.anther_tv)
        TextView antherTv;
        @BindView(R2.id.write_tv)
        TextView writeTv;
        @BindView(R2.id.good_tv)
        TextView goodTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
