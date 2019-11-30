package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.AirMainListBean;
import com.aite.mainlibrary.Mainbean.LessDayBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllLessBodyRecyAdapter extends RecyclerView.Adapter<AllLessBodyRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<LessDayBean.GoodsListBean> listBean;

    public AllLessBodyRecyAdapter(Context context, List<LessDayBean.GoodsListBean> listBean) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listBean = listBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_together_item, parent, false);
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
     * goods_id : 2
     * goods_name : 日托服务商品
     * goods_price : 0.10
     * goods_promotion_price : 0.10
     * goods_image : 2019/10/31/2_06258484919002918.jpg
     * service_hours : 3
     * groupbuy_info : null
     * xianshi_info : null
     * miaosha_info : []
     * spellgroup_info : []
     * bargain_info : []
     * goods_url : STORE_SITE_URL/index.php?act=goods&op=index&goods_id=2
     * group_flag : false
     * xianshi_flag : false
     * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_360.jpg
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listBean.get(position).getGoods_image_url()).into(holder.iconImg);
        holder.titleTv.setText(listBean.get(position).getGoods_name());
        holder.informationTv.setText(String.format("%s小时前发布", listBean.get(position).getService_hours()));
        holder.priceTv.setText("￥" + listBean.get(position).getGoods_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.getPostion(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBean == null ? 0 : listBean.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon_img)
        ImageView iconImg;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.price_tv)
        TextView priceTv;
        @BindView(R2.id.information_tv)
        TextView informationTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
