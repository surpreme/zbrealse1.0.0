package com.aite.a.model;

import java.util.List;


/**
 * 订单详情
 * 
 * @author Administrator
 *
 */
public class OrderInfo {
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
	public String order_friend_mark;
	public String reciver_member_id;
	public String state_desc;
	public String payment_name;
	public extend_order_common extend_order_common;
	public extend_store extend_store;
	public List<extend_order_goods> extend_order_goods;
	public String if_lock;
	public String if_cancel;
	public String if_refund_cancel;
	public String if_complain;
	public String if_receive;
	public String if_deliver;
	public String if_evaluation;
	public String if_share;
	public String order_cancel_day;
	public List<goods_list> goods_list;
	public String goods_count;

	public static class goods_list {
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
		public String image_60_url;
		public String image_240_url;
		public String goods_type_cn;
		public String goods_url;
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
		public String goods_friend_mark;
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
//		public invoice_info invoice_info;
		private String promotion_info;
		private String dlyo_pickup_code;
		private String reciver_area_id;
		private String citysend_info;

		public static class reciver_info {
			public String phone;
			public String mob_phone;
			public String tel_phone;
			public String address;
			public String area;
			public String street;
		}

//		public static class invoice_info {
//			public String 类型;
//			public String 抬头;
//			public String 内容;
//		}
	}

	public static class extend_store {
		public String store_id;
		public String store_name;
		public String grade_id;
		public String member_id;
		public String member_name;
		public String seller_name;
		public String sc_id;
		public String store_company_name;
		public String province_id;
		public String area_info;
		public String store_address;
		public String store_zip;
		public String store_state;
		public String store_close_info;
		public String store_sort;
		public String store_time;
		public String store_end_time;
		public String store_label;
		public String store_banner;
		public String store_avatar;
		public String store_keywords;
		public String store_description;
		public String store_qq;
		public String store_ww;
		public String store_phone;
		public String store_zy;
		public String store_domain;
		public String store_domain_times;
		public String store_recommend;
		public String store_theme;
		public store_credit store_credit;
		public String store_desccredit;
		public String store_servicecredit;
		public String store_deliverycredit;
		public String store_collect;
		public String store_slide;
		public String store_slide_url;
		public String store_stamp;
		public String store_printdesc;
		public String store_sales;
		public List<store_presales> store_presales;
//		public String store_aftersales;
		public String store_workingtime;
		public String store_free_price;
		public String store_decoration_switch;
		public String store_decoration_only;
		public String store_decoration_image_count;
		public String live_store_name;
		public String live_store_address;
		public String live_store_tel;
		public String live_store_bus;
		public String is_own_shop;
		public String bind_all_gc;
		public String store_vrcode_prefix;
		public String store_baozh;
		public String store_baozhopen;
		public String store_baozhrmb;
		public String store_qtian;
		public String store_zhping;
		public String store_erxiaoshi;
		public String store_tuihuo;
		public String store_shiyong;
		public String store_shiti;
		public String store_xiaoxie;
		public String store_huodaofk;
		public String store_points;
		public String city_id;
		public String area_id;
		public String store_type;
		public String rm_id;
		public String agent_comm;
		public String agent_top;
		public String agent_top_top;
		public String agent_top_top_top;
		public String addrole;
		public String store_mb_slide;
		public String store_mb_slide_url;
		public String store_mb_ad1;
		public String store_mb_ad2;
		public String store_mb_banner;
		public String agent_parent_id;
		public String career;
		public String area_state;
		public String goods_count;
		public String store_credit_average;
		public String store_credit_percent;

		public static class store_presales{
			public String name;
			public String type;
			public String num;
		}
		
		public static class store_credit {
			public store_desccredit store_desccredit;
			public store_servicecredit store_servicecredit;
			public store_deliverycredit store_deliverycredit;

			public static class store_desccredit {
				public String text;
				public String credit;
				public String percent;
				public String percent_class;
				public String percent_text;
			}

			public static class store_servicecredit {
				public String text;
				public String credit;
				public String percent;
				public String percent_class;
				public String percent_text;
			}

			public static class store_deliverycredit {
				public String text;
				public String credit;
				public String percent;
				public String percent_class;
				public String percent_text;
			}
		}
	}
}
