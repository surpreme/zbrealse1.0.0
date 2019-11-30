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

import com.aite.mainlibrary.Mainbean.RecyChoiceUIBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChoiceEatRecyAdapter extends RecyclerView.Adapter<ChoiceEatRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<RecyChoiceUIBean.GoodsListBean> goodsListBeanList;

    public ChoiceEatRecyAdapter(Context context, List<RecyChoiceUIBean.GoodsListBean> goodsListBeanList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.goodsListBeanList = goodsListBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.choice_eat_item, parent, false);
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
     * goods_id : 13
     * goods_name : 助餐商品
     * goods_short_title : 经典萝卜拌饭+萝卜酱+清蒸水蛋11
     * goods_price : 0.01
     * goods_promotion_price : 0.01
     * goods_shipping_fee : 0.00
     * goods_image : 2019/11/22/2_06277498241227640.jpg
     * goods_salenum : 0
     * groupbuy_info : null
     * xianshi_info : null
     * miaosha_info : []
     * spellgroup_info : []
     * bargain_info : []
     * goods_url : STORE_SITE_URL/index.php?act=goods&op=index&goods_id=13
     * group_flag : false
     * xianshi_flag : false
     * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_360.jpg
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleTv.setText(goodsListBeanList.get(position).getGoods_name());
        holder.informationTv.setText(goodsListBeanList.get(position).getGoods_short_title());
        holder.buyInformationTv.setText(String.format("月销售%s   好评度100%%", goodsListBeanList.get(position).getGoods_salenum()));
        holder.priceTv.setText(String.format("￥ %s", goodsListBeanList.get(position).getGoods_price()));
        holder.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstenerInterface.getPostion(position);
            }
        });
        Glide.with(context).
                load(goodsListBeanList.get(position).getGoods_image_url())
                .into(holder.icon);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lstenerInterface.getPostion(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return goodsListBeanList == null ? 0 : goodsListBeanList.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.information_tv)
        TextView informationTv;
        @BindView(R2.id.buy_information_tv)
        TextView buyInformationTv;
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
