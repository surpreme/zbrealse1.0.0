package chat.model;

/**
 * 定制消息
 * Created by mayn on 2018/12/7.
 */
public class CustomMsgInfo {
    public String CustomType;
    public goods goods;
    public order_info order_info;

    public static class goods {
        public String goods_image;
        public String goods_name;
        public String goods_price;
        public String goods_sales;
        public String goods_id;
    }

    public static class order_info {
        public String goods_image;
        public String goods_name;
        public String goods_id;
        public String goods_total;
        public String order_amount;
        public String reciver_name;
        public String reciver_mobile;
        public String reciver_address;
        public String edit_url;
        public String confirm_url;
        public String confirm_address;
        public String order_id;
    }
}
