package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.LessDayBean;
import com.aite.mainlibrary.Mainbean.NewsGoodBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AroundGoodRecyAdapter extends RecyclerView.Adapter<AroundGoodRecyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<NewsGoodBean.DatasBean> newsGoodBeans;

    public AroundGoodRecyAdapter(Context context, List<NewsGoodBean.DatasBean> newsGoodBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.newsGoodBeans = newsGoodBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.around_goods, parent, false);
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

    /**
     * article_id : 60
     * article_title : 以习近平同志为核心的党中央关心内蒙古发展纪实
     * article_image : https://aitecc.com/data/upload/cms/article/135/05554305132932828.png
     * article_link :
     * article_publish_time : 2017-08-07
     * article_author : aiteshop
     * article_abstract : 美好梦想，奔驰在辽阔草原——以习近平同志为核心的党中央关心内蒙古发展纪实
     * article_click : 274
     * article_image_all : a:1:{s:21:"05554305132932828.png";a:4:{s:4:"name";s:21:"05554305132932828.png";s:5:"width";i:414;s:6:"height";i:384;s:4:"path";s:3:"135";}}
     * article_comment_count : 7
     * article_class_id : 6
     * article_publisher_id : 135
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTv.setText(newsGoodBeans.get(position).getArticle_abstract());
        holder.informationTv.setText(String.format("%s赞  %s", newsGoodBeans.get(position).getArticle_comment_count(), newsGoodBeans.get(position).getArticle_publish_time()));
        Glide.with(context).load(newsGoodBeans.get(position).getArticle_image()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return newsGoodBeans == null ? 0 : newsGoodBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.information_tv)
        TextView informationTv;
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
