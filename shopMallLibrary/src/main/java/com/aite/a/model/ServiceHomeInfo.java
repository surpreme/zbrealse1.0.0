package com.aite.a.model;

import java.util.List;

/**
 * 服务首页
 * 
 * @author Administrator
 *
 */
public class ServiceHomeInfo {
	public List<goods_class> goods_class;
	public List<tuijian_list> tuijian_list;
	public List<recommend_store_list> recommend_store_list;
	public List<advuplist> advuplist;
	public top_class top_class;

	public static class goods_class {
		public String gc_id;
		public String gc_name;
		public String type_id;
		public String type_name;
		public String gc_parent_id;
		public String commis_rate;
		public String gc_sort;
		public String gc_virtual;
		public String gc_title;
		public String gc_keywords;
		public String gc_description;
		public String gc_show_in_menu;
		public String gc_bind_param_type_id;
		public String IsSystem;
		public String IsEnable;
		public String pic;
	}

	public static class tuijian_list {
		public String goods_id;
		public String goods_name;
		public String goods_image_url;
		public String goods_price;
		public String gc;
		public String goods_salenum;
	}

	public static class recommend_store_list {
		public String store_id;
		public String store_name;
		public String store_image_url;
		public String store_sales;
		public String st;
	}

	public static class advuplist {
		public String adv_title;
		public String adv_img;
		public String adv_url;
	}

	public static class top_class{
		public String adv_title;
		public String adv_img;
		public String adv_url;
	}
}
