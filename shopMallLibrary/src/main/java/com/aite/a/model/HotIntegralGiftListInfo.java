package com.aite.a.model;

import java.util.List;

/**
 * 积分商品兑换列表
 * 
 * @author Administrator
 *
 */
public class HotIntegralGiftListInfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<membergrade_arr>membergrade_arr;
		public List<pointprod_list>pointprod_list;
		
		public static class membergrade_arr {
			public String level;
			public String level_name;
			public String exppoints;
		}

		public static class pointprod_list {
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
			public String pgoods_image_small;
			public String ex_state;
		}
	}
}
