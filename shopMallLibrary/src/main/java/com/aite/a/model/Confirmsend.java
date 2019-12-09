package com.aite.a.model;

import java.util.List;

public class Confirmsend {

	public String code;
	public datas datas;

	public static class datas {
		public order_info order_info;

	}

	public static class order_info {
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
		public String state_desc;
		public String payment_name;
		public extend_order_common extend_order_common;
		public List<extend_order_goods> extend_order_goods;
	}

	public static class extend_order_common {
		public String order_id;
		public String store_id;
		public String shipping_time;
		public String shipping_express_id;
		public String evaluation_time;
		public String evalseller_state;
		public String evalseller_time;
		public String order_message;
		public String order_pointscount;
		public String voucher_price;
		public String voucher_code;
		public String deliver_explain;
		public String daddress_id;
		public String reciver_name;
		public reciver_info reciver_info;
		public String reciver_province_id;
		public String reciver_city_id;
		public List<invoice_info> invoice_info;
		public String promotion_info;
		public String dlyo_pickup_code;
		public String reciver_area_id;
		public String citysend_info;
	}

	public static class reciver_info {
		public String phone;
		public String mob_phone;
		public String tel_phone;
		public String address;
		public String area;
		public String street;
	}

	public static class invoice_info {

	}

	public static class extend_order_goods {
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
	}
}
