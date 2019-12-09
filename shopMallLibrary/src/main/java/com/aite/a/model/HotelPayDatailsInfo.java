package com.aite.a.model;

import java.util.List;

/**
 * 酒店购买详情
 * 
 * @author Administrator
 *
 */
public class HotelPayDatailsInfo {
	public priceinfo priceinfo;
	public hotel_info hotel_info;
	public room_info room_info;
	public store_info store_info;
	public String total_price;

	public static class priceinfo {
		public String id;
		public String store_id;
		public String store_name;
		public String hotel_id;
		public String room_id;
		public String bedtype;
		public String breakfast;
		public String is_cancle;
		public String cancle_remark;
		public String start_time;
		public String end_time;
		public String housenum;
	}

	public static class hotel_info {
		public String hotel_id;
		public String store_id;
		public String store_name;
		public String hotel_name;
		public String hotel_label;
		public String hotel_area_info;
		public String hotel_address;
		public String hotel_province_id;
		public String hotel_city_id;
		public String hotel_area_id;
		public String hotel_desc;
		public String hotel_tel;
		public String hotel_sort;
		public String add_time;
		public String hotel_fax;
		public String hotel_type;
		public String hotel_theme;
		public String hotel_brand;
		public String hotel_more;
		public String hotel_sheshi_ids;
		public String hotel_stars;
		public String hotel_is_hot;
		public String hotel_is_promote;
		public String hotel_is_recom;
		public String hotel_points;
		public String hotel_status;
		public String price;
		public String hotel_imgurl;
	}

	public static class room_info {
		public String room_id;
		public String hotel_id;
		public String room_name;
		public String room_imgurl;
		public String persons;
		public String is_broadband;
		public String is_wifi;
		public String room_sort;
		public String room_desc;
		public String status;
		public String store_id;
	}

	public static class store_info {
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
		public List<store_aftersales> store_aftersales;
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
		public String store_mb_ad1_url;
		public String store_mb_ad2_url;
		public String store_mb_banner;
		public String store_anli_count;
		public String visit_number;
		public String introduce;
		public String store_is_weike;
		public String task_price_number;
		public String store_bank_info;
		public String store_industry;
		public String store_open_rush;
		public String goods_count;
		public String store_credit_average;
		public String store_credit_percent;

		public static class store_presales {
			public String name;
			public String type;
			public String num;
		}

		public static class store_aftersales {
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
			}

			public static class store_servicecredit {
				public String text;
				public String credit;
			}

			public static class store_deliverycredit {
				public String text;
				public String credit;
			}
		}

	}

}
