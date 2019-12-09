package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

/**
 * 酒店列表
 * 
 * @author Administrator
 *
 */
public class HotelListInfo implements Serializable {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas implements Serializable {
		public String address;
		public String cityid;
		public List<style_arr> style_arr;
		public List<price_list> price_list;
		public List<stars_list> stars_list;
		public List<brand_list> brand_list;
		public List<sheshi_list> sheshi_list;
		public List<list> list;

		public static class style_arr implements Serializable {
			public String name;
			public String value;
		}

		public static class price_list implements Serializable {
			public String range_id;
			public String range_name;
			public String range_start;
			public String range_end;
			public boolean ischoose = false;
		}

		public static class stars_list implements Serializable {
			public String id;
			public String name;
			public String state;
			public String type;
			public String sort;
			public String imgurl;
			public boolean ischoose = false;
		}

		public static class brand_list implements Serializable {
			public String id;
			public String name;
			public String state;
			public String type;
			public String sort;
			public String imgurl;
		}

		public static class sheshi_list implements Serializable {
			public String id;
			public String name;
			public String state;
			public String type;
			public String sort;
			public String imgurl;
		}

		public static class list implements Serializable {
			public String hotel_id;
			public String price_date;
			public String price;
			public String store_id;
			public String store_name;
			public String hotel_name;
			public String hotel_label;
			public String hotel_area_info;
			public String hotel_points;
			public String hotel_address;
			public String hotel_stars;
			public String hotel_more;
			public String hotel_stars_name;
			public String hotel_imgurl;
			public String avg_total;
			public String eval_total;
			public String distance;
		}

	}

}
