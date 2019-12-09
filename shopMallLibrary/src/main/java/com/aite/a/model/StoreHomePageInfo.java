package com.aite.a.model;

import java.util.List;

/**
 * 店铺首页
 * 
 * @author Administrator
 *
 */
public class StoreHomePageInfo {
	public storedel storedel;
	public eval_info eval_info;
	public static class storedel{
		public String store_id;
		public String goods_count;
		public String order_amount;
		public String uu;
		public String introduce_url;
		public String store_url;
		public String store_image_url;
		public String store_name;
		public String store_credit;
		public String store_qq;
	}	
	
	public static class eval_info{
		public String composite;
		public String member_all;
		public String employers_d;
		public String employers_a;
		public String employers_p;
	}
	
}
