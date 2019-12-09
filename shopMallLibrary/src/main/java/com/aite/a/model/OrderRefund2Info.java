package com.aite.a.model;

import java.util.List;

/**
 * 部分退款
 * Created by mayn on 2018/11/20.
 */
public class OrderRefund2Info {
    public List<reason_list> reason_list;
    public goods goods;
    public static class reason_list {
        public String reason_id;
        public String reason_info;
        public String sort;
        public String update_time;
    }

    public static class goods {
        public String rec_id;
        public String order_id;
        public String goods_pay_price;
        public String goods_num;
    }

}
