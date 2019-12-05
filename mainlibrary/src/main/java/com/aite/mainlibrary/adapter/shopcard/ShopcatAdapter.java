package com.aite.mainlibrary.adapter.shopcard;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.RecyChoiceUIBean;
import com.aite.mainlibrary.Mainbean.ShopCardlistBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.logcat.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class ShopcatAdapter extends RecyclerView.Adapter<ShopcatAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private boolean isShow = true;//是否显示编辑/完成
    private CheckInterface checkInterface;
    private ChangeshopCardNumberInterface changeshopCardNumberInterface;
    private List<ShopCardlistBean.CartListBean> cartListBeans;

    public ShopcatAdapter(Context context, List<ShopCardlistBean.CartListBean> cartListBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.cartListBeans = cartListBeans;
    }

    public void setChangeshopCardNumberInterface(ChangeshopCardNumberInterface changeshopCardNumberInterface) {
        this.changeshopCardNumberInterface = changeshopCardNumberInterface;
    }


    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }
    /**
     * 是否显示可编辑
     *
     * @param show
     */

    public void isShow(boolean show) {
        isShow = show;
        notifyDataSetChanged();
    }

    public void clearData() {
        cartListBeans.clear();
        notifyDataSetChanged();
    }

    /**
     * 局部更新个别item 更新数量 防止闪屏
     *
     * @param holder
     * @param position
     * @param payloads
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            //payloads 为 空，说明是更新整个 ViewHolder
            onBindViewHolder(holder, position);
        } else {
            String num = (String) payloads.get(0);
            LogUtils.e(num + "  onBindViewHolder");
            holder.tvNumber.setText(String.format("x%s", num));
            holder.tvNumberhide.setText(String.format("x%s", num));
            cartListBeans.get(position).setGoods_num(num);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_shopcard_recy, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * cart_id : 3
     * buyer_id : 7
     * store_id : 2
     * store_name : 艾特技术
     * goods_id : 12
     * goods_name : 测试助餐套餐
     * goods_price : 0.01
     * goods_num : 31
     * goods_image : 2019/10/31/2_06258484919002918.jpg
     * bl_id : 0
     * cart_type : 1
     * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_240.jpg
     * goods_sum : 0.31
     * goods_shipping_fee : 0.10
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(cartListBeans.get(position).getGoods_image_url()).into(holder.ivImg);
        holder.tvGoodsname.setText(cartListBeans.get(position).getGoods_name());
        holder.tvItemShopcartShopname.setText(cartListBeans.get(position).getStore_name());
        holder.tvPrice.setText(cartListBeans.get(position).getGoods_price());
        holder.tvNumber.setText(String.format("x%s", cartListBeans.get(position).getGoods_num()));
        holder.tvNumberhide.setText(String.format("x%s", cartListBeans.get(position).getGoods_num()));
        holder.ivPick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartListBeans.get(position).setChoosed(isChecked);
//                notifyDataSetChanged();
                checkInterface.checkGroup(position, isChecked);
            }
        });
        if (isShow) {
            holder.img_delete_hide.setVisibility(View.GONE);
            holder.tv_number_ll_hide.setVisibility(View.VISIBLE);
            holder.tvNumber.setVisibility(View.GONE);
        } else {
            holder.img_delete_hide.setVisibility(View.VISIBLE);
            holder.tv_number_ll_hide.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.VISIBLE);
        }
        holder.ivPick.setChecked(cartListBeans.get(position).isChoosed());
        //增加按钮
        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeshopCardNumberInterface.addlessThings(position, 1);


            }
        });
        //删减按钮
        holder.tvLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeshopCardNumberInterface.addlessThings(position, 2);
//                if ((Integer.valueOf(cartListBeans.get(position).getGoods_num()) > 1)) {
//                    cartListBeans.get(position).setGoods_num(String.valueOf(Integer.valueOf(cartListBeans.get(position).getGoods_num()) - 1));
//                    holder.tvNumber.setText(cartListBeans.get(position).getGoods_num());
//                    holder.tvNumberhide.setText(cartListBeans.get(position).getGoods_num());
//                    notifyDataSetChanged();
//                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return cartListBeans == null ? 0 : cartListBeans.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.iv_item_shopcart_shopselect)
        CheckBox ivItemShopcartShopselect;
        @BindView(R2.id.tv_item_shopcart_shopname)
        TextView tvItemShopcartShopname;
        @BindView(R2.id.ll_shopcart_header)
        LinearLayout llShopcartHeader;
        @BindView(R2.id.iv_pick)
        CheckBox ivPick;
        @BindView(R2.id.iv_img)
        ImageView ivImg;
        @BindView(R2.id.tv_goodsname)
        TextView tvGoodsname;
        @BindView(R2.id.tv_srec)
        TextView tvSrec;
        @BindView(R2.id.tv_price)
        TextView tvPrice;
        @BindView(R2.id.tv_less)
        TextView tvLess;
        @BindView(R2.id.tv_number)
        TextView tvNumber;
        @BindView(R2.id.tv_number_hide)
        TextView tvNumberhide;
        @BindView(R2.id.tv_plus)
        TextView tvPlus;
        @BindView(R2.id.img_delete_hide)
        ImageView img_delete_hide;
        @BindView(R2.id.tv_number_ll_hide)
        LinearLayout tv_number_ll_hide;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }

    public interface ChangeshopCardNumberInterface {
        boolean addlessThings(int position, int islessAdd);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);
    }
}
