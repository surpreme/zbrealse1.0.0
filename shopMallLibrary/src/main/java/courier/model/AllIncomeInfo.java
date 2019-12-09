package courier.model;

import java.util.List;

/**
 * 总收入
 * Created by Administrator on 2018/1/25.
 */
public class AllIncomeInfo {
    public String order_money;
    public String express_money;
    public String money;
    public String express_deposit;
    public String jiesuan;
    public String time_name;
    public list list;

    public static class list {
        public List<express_list> express_list;
        public List<order_list> order_list;

        public static class express_list {
            public String id;
            public String express_id;
            public String express_name;
            public String express_code;
            public String express_no;
            public String consignee;
            public String consignee_mobile;
            public String is_receivables;
            public String is_send;
            public String is_sendsms;
            public String sms_code;
            public String addtime;
            public String status;
            public String outtime;
            public String store_id;
            public String store_name;
            public String dlyp_id;
            public String dlyp_name;
            public String tuitime;
            public String is_sms;
            public String money;
        }

        public static class order_list {
            public String order_id;
            public String order_sn;
            public String pay_sn;
            public String store_id;
            public String store_name;
            public String buyer_id;
            public String buyer_name;
            public String buyer_email;
            public String add_time;
            public String payment_code;
            public String payment_time;
            public String finnshed_time;
            public String goods_amount;
            public String order_amount;
            public String order_costamount;
            public String rcb_amount;
            public String pd_amount;
            public String shipping_fee;
            public String evaluation_state;
            public String order_state;
            public String refund_state;
            public String lock_state;
            public String delete_state;
            public String refund_amount;
            public String refund_points;
            public String delay_time;
            public String order_from;
            public String shipping_code;
            public String hotel_checked;
            public String picktype;
            public String pay_voucher_img;
            public String order_type;
            public String phy_store_id;
            public String store_points_offset;
            public String store_points_offset_amount;
            public String platform_points_offset;
            public String platform_points_offset_amount;
            public String first_comm;
            public String second_comm;
            public String three_comm;
            public String true_store_id;
        }
    }

}
