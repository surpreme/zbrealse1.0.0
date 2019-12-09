package com.aite.a.model;

import java.util.List;

/**
 * 积分订单详情
 * 
 * @author Administrator
 *
 */
public class IntegarlOrderInfo {
	public pointorderstate_arr pointorderstate_arr;
	public orderaddress_info orderaddress_info;
	public List<prod_list>prod_list;
	public order_info order_info;
	public ExpressInfo express_info;
	
	public static class pointorderstate_arr{
		public List<String>canceled;
		public List<String>waitship;
		public List<String>waitreceiving;
		public List<String>finished;
	}
	
	public static class orderaddress_info{
		public String point_oaid;
		public String point_orderid;
		public String point_truename;
		public String point_areaid;
		public String point_areainfo;
		public String point_address;
		public String point_zipcode;
		public String point_telphone;
		public String point_mobphone;
	}
	public static class prod_list{
		public String point_recid;
		public String point_orderid;
		public String point_goodsid;
		public String point_goodsname;
		public String point_goodspoints;
		public String point_goodsnum;
		public String point_goodsimage;
		public String point_goodsimage_old;
		public String point_goodsimage_small;
	}
	
	public static class order_info{
		public String point_orderid;
		public String point_ordersn;
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
		public String point_orderstatetext;
		public String point_orderstatesign;
		public String point_orderallowship;
		public String point_orderalloweditship;
		public String point_orderallowcancel;
		public String point_orderallowdelete;
		public String point_orderallowreceiving;
	}

	public static class ExpressInfo {
		public String e_code,e_letter,e_name,e_order,e_url,e_zt_state,id;
	}
}
