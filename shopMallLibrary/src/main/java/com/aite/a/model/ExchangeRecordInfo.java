package com.aite.a.model;

import java.util.List;

/**
 * 兑换记录
 * 
 * @author Administrator
 *
 */
public class ExchangeRecordInfo {
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
	public List<prodlist> prodlist;

	public static class prodlist {
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

}
