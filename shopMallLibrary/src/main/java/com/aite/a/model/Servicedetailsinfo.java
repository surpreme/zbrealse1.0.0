package com.aite.a.model;

import java.util.List;

/**
 * 服务详情
 * 
 * @author Administrator
 *
 */
public class Servicedetailsinfo {

	public goodsdel goodsdel;
	public storedel storedel;

	
	public static class goodsdel {
		public String goods_id;
		public String goods_name;
		public String goods_image_url;
		public String goods_price;
		public String gc;
		public String goods_salenum;
		public String goods_click;
		public String goods_collect;
		public String goods_url;
		public String is_favorites;
	}

	public static class storedel {
		public String store_id;
		public String store_name;
		public String store_image_url;
		public String store_sales;
		public String store_deliverycredit;
		public String store_desccredit;
		public String store_servicecredit;
		public String store_qq;
	}
}
