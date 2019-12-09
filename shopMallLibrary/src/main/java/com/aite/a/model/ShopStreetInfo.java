package com.aite.a.model;

import java.util.List;

/**
 * 店铺街
 * 
 * @author Administrator
 *
 */
public class ShopStreetInfo {
	public List<list> list;

	public static class list {
		public String microshop_store_id;
		public String shop_store_id;
		public String microshop_sort;
		public String microshop_commend;
		public String like_count;
		public String comment_count;
		public String click_count;
		public List<hot_sales_list> hot_sales_list;
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
		public List<store_presales>store_presales;
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
		public String store_mb_banner;
		public String goods_count;
		public String store_credit_average;
		public String store_credit_percent;
	}

	public static class hot_sales_list {
		public String goods_id;
		public String goods_commonid;
		public String goods_name;
		public String goods_short_title;
		public String goods_jingle;
		public String store_id;
		public String store_name;
		public String gc_id;
		public String gc_id_1;
		public String gc_id_2;
		public String gc_id_3;
		public String brand_id;
		public String goods_price;
		public String goods_promotion_price;
		public String goods_promotion_type;
		public String goods_marketprice;
		public String goods_costprice;
		public String goods_serial;
		public String goods_storage_alarm;
		public String goods_click;
		public String goods_salenum;
		public String goods_collect;
		public String goods_spec;
		public String goods_storage;
		public String goods_image;
		public String goods_state;
		public String goods_verify;
		public String goods_addtime;
		public String goods_edittime;
		public String areaid_1;
		public String areaid_2;
		public String color_id;
		public String transport_id;
		public String goods_freight;
		public String goods_vat;
		public String goods_commend;
		public String goods_stcids;
		public String evaluation_good_star;
		public String evaluation_count;
		public String is_virtual;
		public String virtual_indate;
		public String virtual_limit;
		public String virtual_invalid_refund;
		public String is_fcode;
		public String is_appoint;
		public String is_presell;
		public String have_gift;
		public String is_own_shop;
		public String commission;
		public String level_0_price;
		public String level_1_price;
		public String level_2_price;
		public String level_3_price;
		public String level_0_auth_price;
		public String level_1_auth_price;
		public String level_2_auth_price;
		public String level_3_auth_price;
		public String is_more_discount;
		public String goods_type;
		public String parent_commonid;
		public String parent_goodsid;
		public String hotel_id;
		public String wholesale_price;
		public String is_service;
		public String is_installment;
		public String installment_money;
		public String is_vat;
		public String goods_salenum_vr;
		public String goods_start_vr;
		public String house_type;
		public String goods_param;
		
	}

	public static class store_credit {
		public store_desccredit store_desccredit;
		public store_servicecredit store_servicecredit;
		public store_deliverycredit store_deliverycredit;
	}

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
	
	public static class store_presales {
		public String name;
		public String type;
		public String num;
	}
	public static class store_aftersales {
	}
}
