package com.aite.a.model;

/**
 * 虚拟订单购买第一步
 * Created by mayn on 2018/8/14.
 */

public class VirtualConfirmOrderStep1Info {
    public String error;
    public goods_info goods_info;
    public store_info store_info;
    public member_info member_info;

    public static class goods_info {
        public String goods_id;
        public String goods_name;
        public String goods_price;
        public String goods_total;
        public String goods_image_url;
        public String quantity;
    }

    public static class store_info {
        public String store_id;
        public String store_name;
    }
    public static class member_info {
        public String member_mobile;
        public String available_predeposit;
        public String available_rc_balance;
    }

}
