package com.aite.a.model;

import java.util.List;

/**
 * 商品列表
 * Created by Administrator on 2018/1/26.
 */
public class GoodsListModerInfo {
    public String hasmore;
    public String page_total;
    public datas datas;

    public static class datas {
        public List<goods_list> goods_list;
        public List<attr_list> attr_list;
        public List<brand_list> brand_list;
        public String gc_name;

        public static class goods_list {
            public String goods_id;
            public String goods_name;
            public String goods_price;
            public String goods_promotion_price;
            public String goods_marketprice;
            public String goods_image;
            public String goods_salenum;
            public String evaluation_good_star;
            public String evaluation_count;
            public String is_own_shop;
            public String goods_promotion_type;
            public String level_0_price;
            public String level_1_price;
            public String level_2_price;
            public String level_3_price;
            public String level_0_auth_price;
            public String level_1_auth_price;
            public String level_2_auth_price;
            public String level_3_auth_price;
            public String is_virtual;
            public String is_presell;
            public String is_fcode;
            public String have_gift;
            public String group_flag;
            public String xianshi_flag;
            public String goods_image_url;
        }

        public static class brand_list {
            public String brand_id;
            public String brand_name;
            public boolean ischoose=false;
        }

        public static class attr_list {
            public String name;
            public List<value> value;

            public static class value {
                public String attr_value_id;
                public String attr_value_name;
                public boolean ischoose=false;
            }
        }
    }
}
