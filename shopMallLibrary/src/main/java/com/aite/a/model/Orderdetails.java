package com.aite.a.model;

import java.util.List;
/**
 * 订单详情
 * @author Administrator
 *
 */
public class Orderdetails {
	public List<order_list>order_list;
	public String price_all;
	public String fee_all;
	
	public static class order_list{
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
		public String delay_time;
		public String order_from;
		public String shipping_code;
		public String hotel_checked;
		public String picktype;
		public List<goods_list>goods_list;
	}
	
	public static class goods_list{
		public String rec_id;
		public String order_id;
		public String goods_id;
		public String goods_name;
		public String goods_price;
		public String goods_costprice;
		public String goods_num;
		public String goods_image;
		public String goods_pay_price;
		public String store_id;
		public String buyer_id;
		public String goods_type;
		public String promotions_id;
		public String commis_rate;
		public String gc_id;
		public String start_time;
		public String end_time;
		public String dates;
		public String days;
		public String price_items;
		public String hotel_msg;
		public String goods_image_url;
	}
}
