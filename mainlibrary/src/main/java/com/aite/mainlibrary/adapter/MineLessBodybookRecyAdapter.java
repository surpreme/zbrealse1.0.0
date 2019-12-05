package com.aite.mainlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.BookLessBodyFamilyBean;
import com.aite.mainlibrary.Mainbean.BookMorningNoonEatBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MineLessBodybookRecyAdapter extends RecyclerView.Adapter<MineLessBodybookRecyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<BookLessBodyFamilyBean.OrderListBean> orderListBeans;

    public MineLessBodybookRecyAdapter(Context context, List<BookLessBodyFamilyBean.OrderListBean> orderListBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.orderListBeans = orderListBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_thingbook_layout, parent, false);
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

    //    page_total	整型	总页数
//    datas->order_list[]	数组	订单记录
//    datas->order_list[]->order_id	字符串	订单id
//    datas->order_list[]->order_amount	字符串	订单价格
//    datas->order_list[]->order_shipping_fee	字符串	订单配送费
//    datas->order_list[]->goods_id	字符串	商品id
//    datas->order_list[]->goods_name	字符串	商品名称
//    datas->order_list[]->goods_price	字符串	商品价格
//    datas->order_list[]->goods_image_url	字符串	商品图片
//    datas->order_list[]->add_time	字符串	下单时间
//    datas->order_list[]->order_state_text	字符串	订单状态文字
//    datas->order_list[]->if_cancel	字符串	是否显示取消按钮 1是 0否
//    datas->order_list[]->if_pay	字符串	是否可以显示支付按钮 1是 0否
//    datas->order_list[]->if_detail	字符串	是否可以显示详情按钮 1是 0否
//    datas->order_list[]->is_verify	字符串	是否可以显示取餐码按钮 1可以 0不可
//    datas->order_list[]->if_evaluation	字符串	是否可以显示评价按钮 1是 0否
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(orderListBeans.get(position).getGoods_image_url()).into(holder.iconIv);
        holder.priceTv.setText(String.format("￥%s", orderListBeans.get(position).getGoods_price()));
        holder.titleTv.setText(orderListBeans.get(position).getGoods_name());
        holder.timeTv.setText(String.format("下单时间%s", orderListBeans.get(position).getAdd_time()));
        holder.stateTv.setText(orderListBeans.get(position).getOrder_state_text());


    }

    @Override
    public int getItemCount() {
        return orderListBeans == null ? 0 : orderListBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.icon_iv)
        ImageView iconIv;
        @BindView(R2.id.title_tv)
        TextView titleTv;
        @BindView(R2.id.state_tv)
        TextView stateTv;
        @BindView(R2.id.information_tv)
        TextView informationTv;
        @BindView(R2.id.price_tv)
        TextView priceTv;
        @BindView(R2.id.look_information_tv)
        TextView lookInformationTv;
        @BindView(R2.id.time_tv)
        TextView timeTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
