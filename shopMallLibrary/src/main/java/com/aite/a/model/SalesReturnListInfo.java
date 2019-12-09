package com.aite.a.model;

import java.util.List;

/**
 * 退货列表
 * 
 * @author Administrator
 *
 */
public class SalesReturnListInfo {
	public List<return_list> return_list;
	public show_page show_page;

	public static class return_list {
		public String refund_id;
		public String order_id;
		public String order_sn;
		public String refund_sn;
		public String store_id;
		public String store_name;
		public String buyer_id;
		public String buyer_name;
		public String goods_id;
		public String order_goods_id;
		public String goods_name;
		public String goods_num;
		public String refund_amount;
		public String goods_image;
		public String order_goods_type;
		public String refund_type;
		public String seller_state;
		public String refund_state;
		public String return_type;
		public String order_lock;
		public String goods_state;
		public String add_time;
		public String seller_time;
		public String admin_time;
		public String reason_id;
		public String reason_info;
		public String pic_info;
		public String buyer_message;
		public String seller_message;
		public String admin_message;
		public String express_id;
		public String invoice_no;
		public String ship_time;
		public String delay_time;
		public String receive_time;
		public String receive_message;
		public String commis_rate;
	}

	public static class show_page {
		public String hasmore;
		public String page_total;
	}
}
