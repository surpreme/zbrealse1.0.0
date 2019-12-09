package com.aite.a.model;

import java.util.List;

/**
 * 库存
 * Created by Administrator on 2017/5/8.
 */
public class InventoryInfo {
    public String sid;
    public String name;
    public String marketprice="";
    public String price="";
    public String id="";
    public String stock="";
    public String alarm="";
    public String sku="";
    public String sp_value_color;
    public String level_0_price="0";
    public String level_1_price="0";
    public String level_2_price="0";
    public String level_3_price="0";
    public String level_0_auth_price="0";
    public String level_1_auth_price="0";
    public String level_2_auth_price="0";
    public String level_3_auth_price="0";
    public String goods_points_offset="";
    public String costprice="0.00";

    public List<data>data;
    public static class data{
        public String sp_value_id;
        public String sp_value_name;
        public String sp_value_color;



        public data(String sp_value_id, String sp_value_name, String sp_value_color) {
            this.sp_value_id = sp_value_id;
            this.sp_value_name = sp_value_name;
            this.sp_value_color = sp_value_color;
        }



    }


}
