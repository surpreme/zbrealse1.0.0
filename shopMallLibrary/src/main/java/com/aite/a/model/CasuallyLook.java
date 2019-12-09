package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

/**
 * 随心看
 * 
 * @author Administrator
 *
 */
public class CasuallyLook {
	public List<goods_list> goods_list;

	public static class goods_list implements Serializable{
		public String commend_id;
		public String commend_goods_id;
		public String commend_goods_name;
		public String commend_goods_price;
		public String commend_goods_image;
		public String commend_goods_store_id;
		public String commend_message;
		public String comment_count;
		public String like_count;
		public String commend_member_id;
		public String member_name;
		public String member_avatar;
	}

	public static class page_count {
		public String hasmore;
		public String page_total;
	}
}
