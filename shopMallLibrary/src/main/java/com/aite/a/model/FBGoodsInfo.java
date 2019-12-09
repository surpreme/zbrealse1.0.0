package com.aite.a.model;

import java.util.List;

/**
 * 发布商品
 * Created by Administrator on 2017/5/6.
 */
public class FBGoodsInfo {
    public List<plate_list> plate_list;
    public List<store_goods_class> store_goods_class;
    public List<brand_list> brand_list;
    public List<attr_list> attr_list;
    public List<spec_list> spec_list;

    public static class plate_list {
        public String plate_id;
        public String plate_name;
        public String plate_position;
    }

    public static class store_goods_class {
        public String stc_id;
        public String stc_name;
        public List<child> child;

        public static class child {
            public String stc_id;
            public String stc_name;
            public String stc_parent_id;
            public String stc_state;
            public String store_id;
            public String stc_sort;
        }
    }

    public static class brand_list {
        public String brand_id;
        public String brand_name;
        public String brand_initial;
        public String brand_class;
        public String brand_pic;
        public String brand_sort;
        public String brand_recommend;
        public String store_id;
        public String brand_apply;
        public String class_id;
        public String show_type;
        public String channle_id;
    }

    public static class attr_list {
        public String attr_id;
        public String attr_name;
        public String choosename;
        public List<value> value;

        public static class value {
            public String attr_value_id;
            public String attr_value_name;
        }
    }

    public static class spec_list {
        public String sp_id;
        public String sp_name;
        public String sp_format;
        public List<value> value;

        public static class value {
            public String sp_value_id;
            public String sp_value_name;
            public boolean ischoose=false;
            public String sp_value_color;

            public value(String sp_value_id, String sp_value_name) {
                this.sp_value_id = sp_value_id;
                this.sp_value_name = sp_value_name;
            }
            public value() {
            }
        }
    }

}
