package com.aite.a.model;

import java.util.List;

/**
 * 酒店详情
 * 
 * @author Administrator
 *
 */
public class HotelDatailsInfo {

	public String distance;
	public String stime;
	public String etime;
	public List<room_list> room_list;
	public List<pic_list> pic_list;
	public info_hotel info_hotel;

	public static class room_list {
		public String room_id;
		public String hotel_id;
		public String room_name;
		public String room_imgurl;
		public String room_desc;
		public String persons;
		public String is_broadband;
		public String is_wifi;
		public String room_sort;
		public String status;
		public String store_id;
		public String store_name;
		public String room_imgurls;
		public boolean ischoose = false;
		public List<pricelist> pricelist;

		public static class pricelist {
			public String id;
			public String store_id;
			public String store_name;
			public String hotel_id;
			public String room_id;
			public String bedtype;
			public String breakfast;
			public String is_cancle;
			public String cancle_remark;
			public String start_time;
			public String end_time;
			public String prices;
			public String housenum;
		}
	}

	public static class pic_list {
		public String id;
		public String hotel_id;
		public String room_id;
		public String class_id;
		public String img_name;
		public String imgurl;
		public String upload_time;
		public String img_url;
	}

	public static class info_hotel {
		public String hotel_id;
		public String store_id;
		public String store_name;
		public String hotel_name;
		public String hotel_label;
		public String hotel_area_info;
		public String hotel_address;
		public String hotel_province_id;
		public String hotel_city_id;
		public String hotel_area_id;
		public String hotel_desc;
		public String hotel_tel;
		public String hotel_sort;
		public String add_time;
		public String hotel_fax;
		public String hotel_type;
		public String hotel_theme;
		public String hotel_brand;
		public String hotel_more;
		public String hotel_sheshi_ids;
		public String hotel_stars;
		public String hotel_is_hot;
		public String hotel_is_promote;
		public String hotel_is_recom;
		public String hotel_points;
		public String hotel_status;
		public String hotel_contents;
		public String price;
		public String hotel_stars_name;
		public String eval_total;
		public String avg_total;
		public String hotel_imgurl;
		public String hotel_bodyurl;
		public List<String> hotel_more_array;

	}
}
