package com.aite.a.model;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

/**
 * 推荐商品组合
 * Created by Administrator on 2017/6/6.
 */
public class GetRecommendedInfo {
    public List<goods_array> goods_array;

    public static class goods_array {
        public String goods_id;
        public String goods_commonid;
        public String goods_name;
        public String goods_short_title;
        public String goods_jingle;
        public String store_id;
        public String store_name;
        public String goods_price;
        public String goods_image;
        public List<combo_list>combo_list;

        public int pagernumber=1;
//        public RecyclerView.Adapter adapter2;
//        public RecyclerView.Adapter adapter3;

        public static class combo_list {
            public String goods_id;
            public String goods_name;
            public String goods_image;
            public String goods_price;
        }
    }



}
