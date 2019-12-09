package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

/**
 * 投诉列表
 * 
 * @author Administrator
 *
 */
public class ComplaintslistInfo implements Serializable{
	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas implements Serializable{
		public List<list> list;
		public List<goods_list> goods_list;

		public static class list implements Serializable{
			public String complain_id;
			public String order_id;
			public String order_goods_id;
			public String accuser_id;
			public String accuser_name;
			public String accused_id;
			public String accused_name;
			public String complain_subject_content;
			public String complain_subject_id;
			public String complain_content;
			public String complain_pic1;
			public String complain_pic2;
			public String complain_pic3;
			public String complain_datetime;
			public String complain_handle_datetime;
			public String complain_handle_member_id;
			public String appeal_message;
			public String appeal_datetime;
			public String appeal_pic1;
			public String appeal_pic2;
			public String appeal_pic3;
			public String final_handle_message;
			public String final_handle_datetime;
			public String final_handle_member_id;
			public String complain_state;
			public String complain_active;
			public String complain_state_desc;
		}

		public static class goods_list implements Serializable{
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
			public String goods_friend_mark;
			public String goods_image_url;
		}
	}
}
