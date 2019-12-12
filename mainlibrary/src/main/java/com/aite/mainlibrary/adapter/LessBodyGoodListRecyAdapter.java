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

import com.aite.mainlibrary.Mainbean.LessBodyInformationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LessBodyGoodListRecyAdapter extends RecyclerView.Adapter<LessBodyGoodListRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<LessBodyInformationBean.GoodsCommendListBean> lessBodyInformationBeans;

    public LessBodyGoodListRecyAdapter(Context context, List<LessBodyInformationBean.GoodsCommendListBean> lessBodyInformationBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.lessBodyInformationBeans = lessBodyInformationBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.moringnoon_eat_item, parent, false);
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
     * * goods_id : 8
     * * goods_name : 测试早餐1
     * * goods_price : 0.01
     * * goods_marketprice : 1.00
     * * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg
     * *
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleTv.setText(lessBodyInformationBeans.get(position).getGoods_name());
        holder.buyBtn.setVisibility(View.GONE);
        holder.informationTv.setVisibility(View.GONE);
        holder.priceTv.setText(String.format("￥ %s", lessBodyInformationBeans.get(position).getGoods_price()));
        Glide.with(context).
                load(lessBodyInformationBeans.get(position).getGoods_image_url())
                .into(holder.icon);
        holder.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstenerInterface.getPostion(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessBodyInformationBeans == null ? 0 : lessBodyInformationBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.information_tv)
        TextView informationTv;
        @BindView(R2.id.price_tv)
        TextView priceTv;
        @BindView(R2.id.buy_btn)
        TextView buyBtn;
        @BindView(R2.id.father_layout)
        LinearLayout fatherLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
