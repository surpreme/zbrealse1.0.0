package com.aite.a.model;

import java.util.List;

/**
 * 品牌
 * Created by Administrator on 2017/5/4.
 */
public class BrandDatailsInfo {
    public List<brand_list> brand_list;
    public static class brand_list{
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
}
