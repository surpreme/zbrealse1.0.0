package com.aite.a.model;

import java.util.List;

/**购物车
 * Created by mayn on 2018/11/12.
 */
public class ShoppingCartInfo {
    public List<cart_list> cart_list;
    public String sum;

    public static class cart_list {
        public String store_id;
        public String store_name;
        public boolean ispick=false;
        public List<goods_list> goods_list;

        public static class goods_list {
            public boolean ispick=false;
            public String cart_id;
            public String buyer_id;
            public String store_id;
            public String store_name;
            public String goods_id;
            public String goods_name;
            public String goods_price;
            public String goods_num;
            public String goods_image;
            public String bl_id;
            public String goods_image_url;
            public String goods_sum;
            public String goods_spec;

        }

    }

}
