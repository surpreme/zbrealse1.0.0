package com.aite.a.model;

import java.util.List;

/**
 * 积分购买第一步
 * 
 * @author Administrator
 *
 */
public class IntegralOneInfo {
	public pointprod_arr pointprod_arr;
	public List<address_list> address_list;

	public static class pointprod_arr {
		public List<pointprod_list> pointprod_list;
		public String pgoods_pointall;
		
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
			public String quantity;
			public String onepoints;
		}
	}

	public static class address_list {
		public String address_id;
		public String member_id;
		public String true_name;
		public String area_id;
		public String city_id;
		public String area_info;
		public String address;
		public String tel_phone;
		public String mob_phone;
		public String is_default;
		public String dlyp_id;
	}

}
