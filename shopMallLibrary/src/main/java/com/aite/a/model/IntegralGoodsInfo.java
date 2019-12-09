package com.aite.a.model;

import java.util.List;


/**
 * 兑换商品详情
 * 
 * @author Administrator
 *
 */
public class IntegralGoodsInfo {
	public prodinfo prodinfo;
	public List<orderprod_list> orderprod_list;

	public static class prodinfo {
		public String pgoods_id;
		public String pgoods_name;
		public String pgoods_price;
		public String pgoods_points;
		public String pgoods_image;
		public String pgoods_tag;
		public String pgoods_serial;
		public String pgoods_storage;
		public String pgoods_show;
		public String pgoods_commend;
		public String pgoods_add_time;
		public String pgoods_keywords;
		public String pgoods_description;
		public String pgoods_body;
		public String pgoods_state;
		public String pgoods_close_reason;
		public String pgoods_salenum;
		public String pgoods_view;
		public String pgoods_islimit;
		public String pgoods_limitnum;
		public String pgoods_islimittime;
		public String pgoods_limitmgrade;
		public String pgoods_starttime;
		public String pgoods_endtime;
		public String pgoods_sort;
		public String pgoods_image_old;
		public String pgoods_image_max;
		public String pgoods_image_small;
		public String ex_state;
	}

	public static class orderprod_list {
		public String point_recid;
		public String point_orderid;
		public String point_goodsid;
		public String point_goodsname;
		public String point_goodspoints;
		public String point_goodsnum;
		public String point_goodsimage;
		public String point_buyerid;
		public String point_buyername;
		public String point_buyeremail;
		public String point_addtime;
		public String point_shippingtime;
		public String point_shippingcode;
		public String point_shipping_ecode;
		public String point_finnshedtime;
		public String point_allpoint;
		public String point_ordermessage;
		public String point_orderstate;
		public String member_avatar;
	}
}
