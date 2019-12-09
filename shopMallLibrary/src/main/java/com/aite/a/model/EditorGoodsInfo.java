package com.aite.a.model;

import java.util.List;

/**
 * 编辑商品
 * Created by Administrator on 2017/5/6.
 */
public class EditorGoodsInfo {
    public goods goods;
    public goods_class goods_class;
    public List<spec_list> spec_list;
    public List<attr_list> attr_list;
    public List<Object> attr_checked;
    public List<spec_checked> spec_checked;
    public List<sp_value> sp_value;
    public List<store_goods_class> store_goods_class;
    public List<Object> store_class_goods;
    public List<plate_list> plate_list;
    public List<fcode_array> fcode_array;
    public List<md_rule> md_rule;

    public static class goods {
        public String goods_commonid;
        public String goods_name;
        public String goods_short_title;
        public String goods_jingle;
        public String goods_tags;
        public String gc_id;
        public String gc_id_1;
        public String gc_id_2;
        public String gc_id_3;
        public String gc_name;
        public String store_id;
        public String store_name;
        public String brand_id;
        public String brand_name;
        public String type_id;
        public String goods_image;
        public String goods_attr;
        public List<mobile_body> mobile_body;
        public String goods_state;
        public String goods_stateremark;
        public String goods_verify;
        public String goods_verifyremark;
        public String goods_lock;
        public String goods_addtime;
        public String goods_selltime;
        public String goods_specname;
        public String goods_price;
        public String goods_marketprice;
        public String goods_costprice;
        public String goods_discount;
        public String goods_serial;
        public String goods_storage_alarm;
        public String transport_id;
        public String transport_title;
        public String goods_commend;
        public String goods_freight;
        public String goods_vat;
        public String areaid_1;
        public String areaid_2;
        public String goods_stcids;
        public String plateid_top;
        public String plateid_bottom;
        public String is_virtual;
        public String virtual_indate;
        public String virtual_limit;
        public String virtual_invalid_refund;
        public String is_fcode;
        public String is_appoint;
        public String appoint_satedate;
        public String is_presell;
        public String presell_deliverdate;
        public String is_own_shop;
        public String commission;
        public String channle_id;
        public String level_0_price;
        public String level_1_price;
        public String level_2_price;
        public String level_3_price;
        public String level_0_auth_price;
        public String level_1_auth_price;
        public String level_2_auth_price;
        public String level_3_auth_price;
        public String is_more_discount;
        public String goods_type;
        public String parent_commonid;
        public String hotel_id;
        public String is_service;
        public String is_installment;
        public String installment_money;
        public String is_vat;
        public String house_type;
        public String is_kuajing;
        public String origin;
        public String kj_service;
        public String native_info;
        public String is_native;
        public String seaport;
        public String clearance;
        public String clearance_sn;
        public String goods_points_offset;
        public String g_storage;
        public String goods_fullimage;

        public static class mobile_body{
            public String type;
            public String value;
        }
    }

    public static class goods_class {
        public String gc_id;
        public String type_id;
        public String gc_virtual;
        public String gc_id_1;
        public String gc_tag_name;
        public String gc_tag_value;
        public String gc_id_2;
        public String gc_id_3;
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

    public static class spec_checked {
        public String id;
        public String name;
    }

    public static class sp_value {
        public String sid;
        public String marketprice;
        public String price;
        public String id;
        public String stock;
        public String alarm;
        public String sku;
        public String level_0_price;
        public String level_1_price;
        public String level_2_price;
        public String level_3_price;
        public String level_0_auth_price;
        public String level_1_auth_price;
        public String level_2_auth_price;
        public String level_3_auth_price;
        public String goods_points_offset;
        public String costprice;
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

    public static class plate_list {
        public String plate_id;
        public String plate_name;
        public String plate_position;
    }

    public static class fcode_array {
        public String fc_id;
        public String goods_commonid;
        public String fc_code;
        public String fc_state;
    }

    public static class md_rule {
        public String id;
        public String goods_common_id;
        public String count;
        public String discount;
        public String state;
    }

}
