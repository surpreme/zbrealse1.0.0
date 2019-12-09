package com.aite.a.model;

import java.util.List;

/**
 * 店铺商品列表
 * 
 * @author Administrator
 *
 */
public class StoreHomePageList {
	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<list> list;

		public static class list {
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
			public String goods_unit;
			public String fullpath;
		}
	}

}
