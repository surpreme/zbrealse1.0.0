package com.aite.a.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.activity.ProductDetailsActivity;
import com.aite.a.activity.StoreDetailsActivity;
import com.aite.a.model.ShoppingCartInfo;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aite.a.base.Mark.SETCART_NUMBER;
import static com.aite.a.base.Mark.TOTAL_PRICE;

/**
 * 购物车
 * Created by mayn on 2018/11/12.
 */
public class ShoppingCartAdapter extends BaseAdapter {
    private Context mcontext;
    private List<ShoppingCartInfo.cart_list> cart_list;
    private Handler handler;
    private String goods_id;//修改商品数量ID

    public ShoppingCartAdapter(Context mcontext, List<ShoppingCartInfo.cart_list> cart_list, Handler handler) {
        this.mcontext = mcontext;
        this.cart_list = cart_list;
        this.handler = handler;
    }

    /**
     * 刷新
     * @param cart_list
     */
    public void refreshData(List<ShoppingCartInfo.cart_list> cart_list){
        if (cart_list==null)return;
        this.cart_list=cart_list;
        notifyDataSetChanged();
    }

    /**
     * 全选
     */
    public void pickAll(boolean pick) {
        if (cart_list == null || cart_list.size() == 0) return;
        for (int i = 0; i < cart_list.size(); i++) {
            cart_list.get(i).ispick = pick;
            for (int j = 0; j < cart_list.get(i).goods_list.size(); j++) {
                cart_list.get(i).goods_list.get(j).ispick = pick;
            }
        }
        notifyDataSetChanged();
        getTotalPrice();
    }

    /**
     * 选择店铺
     *
     * @param id
     */
    public void pickStore(int id) {
        cart_list.get(id).ispick = !cart_list.get(id).ispick;
        for (int i = 0; i < cart_list.get(id).goods_list.size(); i++) {
            cart_list.get(id).goods_list.get(i).ispick = cart_list.get(id).ispick;
        }
        notifyDataSetChanged();
        getTotalPrice();
    }

    /**
     * 选择店铺
     *
     * @param id
     * @param pick
     */
    public void pickStore(int id, boolean pick) {
        cart_list.get(id).ispick = pick;
        notifyDataSetChanged();
        getTotalPrice();
    }

    /**
     * 计算总价
     *
     * @return
     */
    public void getTotalPrice() {
        float price = 0;
        int num = 0;
        boolean isall = true;
        for (int i = 0; i < cart_list.size(); i++) {
            if (cart_list.get(i).ispick == false) isall = false;
            for (int j = 0; j < cart_list.get(i).goods_list.size(); j++) {
                if (cart_list.get(i).goods_list.get(j).ispick == false) isall = false;
                if (cart_list.get(i).goods_list.get(j).ispick) {
                    price += Float.parseFloat(cart_list.get(i).goods_list.get(j).goods_sum);
                    num++;
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("price", price);
        map.put("num", num);
        map.put("isall", isall);
        handler.sendMessage(handler.obtainMessage(TOTAL_PRICE, map));
    }

    /**
     * 修改数量
     *
     * @param map
     */
    public void setNumber(Map<String, String> map) {
        for (int i = 0; i < cart_list.size(); i++) {
            for (int j = 0; j < cart_list.get(i).goods_list.size(); j++) {
                if (cart_list.get(i).goods_list.get(j).goods_id.equals(goods_id)) {
                    cart_list.get(i).goods_list.get(j).goods_num = map.get("quantity");
                    cart_list.get(i).goods_list.get(j).goods_price = map.get("goods_price");
                    cart_list.get(i).goods_list.get(j).goods_sum = map.get("total_price");
                    break;
                }
            }
        }
        notifyDataSetChanged();
        getTotalPrice();
    }

    /**
     * 获取删除选中ID
     * @return
     */
    public String getPickGoodsId() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cart_list.size(); i++) {
            for (int j = 0; j < cart_list.get(i).goods_list.size(); j++) {
                if (cart_list.get(i).goods_list.get(j).ispick) {
                    stringBuffer.append(cart_list.get(i).goods_list.get(j).cart_id + "|");
                }
            }
        }
        if (stringBuffer.toString().length() == 0) return null;
        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
    }
    /**
     * 获取结算选中ID
     * @return
     */
    public String getPickGoodsId2() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cart_list.size(); i++) {
            for (int j = 0; j < cart_list.get(i).goods_list.size(); j++) {
                if (cart_list.get(i).goods_list.get(j).ispick) {
                    stringBuffer.append(cart_list.get(i).goods_list.get(j).cart_id + "|"+cart_list.get(i).goods_list.get(j).goods_num+",");
                }
            }
        }
        if (stringBuffer.toString().length() == 0) return null;
        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
    }

    @Override
    public int getCount() {
        return cart_list == null ? 0 : cart_list.size();
    }

    @Override
    public Object getItem(int i) {
        return cart_list == null ? null : cart_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_shoppingcart, null);
            new ShoppingCartHolder(view);
        }
        ShoppingCartHolder holder = (ShoppingCartHolder) view.getTag();
        final ShoppingCartInfo.cart_list cart_list = this.cart_list.get(i);
        holder.iv_pick.setImageResource(cart_list.ispick ? R.drawable.check_red_2 : R.drawable.check_red_1);
        holder.tv_storename.setText(cart_list.store_name);
        GoodsAdapter adapter = new GoodsAdapter(cart_list.goods_list, i);
        holder.lv_goods.setAdapter(adapter);
        holder.ll_tostore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,
                        StoreDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("store_id", cart_list.store_id);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        holder.iv_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickStore(i);
            }
        });
        return view;
    }

    class ShoppingCartHolder {
        ImageView iv_pick;
        TextView tv_storename;
        LinearLayout ll_tostore;
        MyListView lv_goods;

        public ShoppingCartHolder(View view) {
            iv_pick = view.findViewById(R.id.iv_pick);
            tv_storename = view.findViewById(R.id.tv_storename);
            ll_tostore = view.findViewById(R.id.ll_tostore);
            lv_goods = view.findViewById(R.id.lv_goods);
            view.setTag(this);
        }
    }

    private class GoodsAdapter extends BaseAdapter {
        List<ShoppingCartInfo.cart_list.goods_list> goods_list;
        int id;

        public GoodsAdapter(List<ShoppingCartInfo.cart_list.goods_list> goods_list, int id) {
            this.goods_list = goods_list;
            this.id = id;
        }

        /**
         * 选择商品
         *
         * @param p
         */
        public void pickGoods(int p) {
            goods_list.get(p).ispick = !goods_list.get(p).ispick;
            boolean storeallpick = true;
            for (int i = 0; i < goods_list.size(); i++) {
                if (goods_list.get(i).ispick == false) {
                    storeallpick = false;
                    break;
                }
            }
            pickStore(id, storeallpick);//修改店铺选中
        }

        @Override
        public int getCount() {
            return goods_list == null ? 0 : goods_list.size();
        }

        @Override
        public Object getItem(int i) {
            return goods_list == null ? null : goods_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(mcontext, R.layout.item_shoppingcartgoods, null);
                new GoodsHolder(view);
            }
            GoodsHolder holder = (GoodsHolder) view.getTag();
            final ShoppingCartInfo.cart_list.goods_list goods_list = this.goods_list.get(i);
            holder.iv_pick.setImageResource(goods_list.ispick ? R.drawable.check_red_2 : R.drawable.check_red_1);
            Glide.with(mcontext).load(goods_list.goods_image_url).into(holder.iv_img);
            holder.tv_goodsname.setText(goods_list.goods_name);
            holder.tv_price.setText("￥" + goods_list.goods_price);
            holder.tv_number.setText(goods_list.goods_num);
            if (goods_list.goods_spec==null||goods_list.goods_spec.length()==0){
                holder.tv_srec.setVisibility(View.INVISIBLE);
            }else{
                holder.tv_srec.setVisibility(View.VISIBLE);
                holder.tv_srec.setText(goods_list.goods_spec);
            }
            holder.tv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//加
                    int i1 = Integer.parseInt(goods_list.goods_num);
                    i1++;
                    Map<String, String> map = new HashMap<>();
                    map.put("num", i1 + "");
                    map.put("cart_id", goods_list.cart_id);
                    goods_id = goods_list.goods_id;
                    handler.sendMessage(handler.obtainMessage(SETCART_NUMBER, map));
                }
            });
            holder.tv_less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//减
                    Log.i("--------------", "减 ");
                    int i1 = Integer.parseInt(goods_list.goods_num);
                    if (i1 > 1) {
                        i1--;
                        Map<String, String> map = new HashMap<>();
                        map.put("num", i1 + "");
                        map.put("cart_id", goods_list.cart_id);
                        goods_id = goods_list.goods_id;
                        handler.sendMessage(handler.obtainMessage(SETCART_NUMBER, map));
                    }
                }
            });
            holder.iv_pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickGoods(i);
                }
            });
            holder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, ProductDetailsActivity.class);
                    intent.putExtra("goods_id", goods_list.goods_id);
                    mcontext.startActivity(intent);
                }
            });
            return view;
        }

        class GoodsHolder {
            ImageView iv_pick, iv_img;
            TextView tv_goodsname, tv_srec, tv_price, tv_plus, tv_number, tv_less;
            RelativeLayout rl_item;
            public GoodsHolder(View view) {
                iv_pick = view.findViewById(R.id.iv_pick);
                iv_img = view.findViewById(R.id.iv_img);
                tv_goodsname = view.findViewById(R.id.tv_goodsname);
                tv_srec = view.findViewById(R.id.tv_srec);
                tv_price = view.findViewById(R.id.tv_price);
                tv_plus = view.findViewById(R.id.tv_plus);
                tv_number = view.findViewById(R.id.tv_number);
                tv_less = view.findViewById(R.id.tv_less);
                rl_item = view.findViewById(R.id.rl_item);
                view.setTag(this);
            }
        }
    }
}
