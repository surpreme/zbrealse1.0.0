package com.aite.a.model;

import android.widget.Adapter;
import android.widget.Spinner;
import android.widget.TextView;




import java.util.List;

import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

/**
 * 编辑图片
 * Created by Administrator on 2017/5/12.
 */
public class GoodssImageInfo {
    public List<image_array> image_array;
    public List<value_array> value_array;

    public static class image_array {
        public String color_id;
        public int bdimg= R.drawable.release_noimg;
        public String imgurl;
        public boolean iszk=false;
        public Spinner sp_album;
        public TextView tv_pagerbtn3, tv_ypsm;
        public MyGridView mgv_album;
        public Adapter imgIfAdapter;

        public List<color_val> color_val;
        public static class color_val {
            public String goods_image_id;
            public String goods_commonid;
            public String store_id;
            public String color_id;
            public String goods_image;
            public String goods_image_sort="0";
            public String is_default;
            public String bdimg;

//            public color_val(String goods_image) {
//                this.goods_image = goods_image;
//            }
//            public color_val() {
//            }
        }
    }

    public static class value_array {
        public String sp_value_id;
        public String sp_value_name;
    }
}
