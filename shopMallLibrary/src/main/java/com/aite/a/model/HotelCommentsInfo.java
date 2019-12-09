package com.aite.a.model;

import java.util.List;

/**
 * 评价详情
 * 
 * @author Administrator
 *
 */
public class HotelCommentsInfo {
	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public String eval_total;
		public String goods_total;
		public String general_total;
		public String bad_total;
		public List<list> list;
		public avg_info avg_info;

		public static class list {
			public String eval_id;
			public String order_id;
			public String order_sn;
			public String hotel_id;
			public String hotel_name;
			public String room_name;
			public String hotel_image;
			public String member_id;
			public String member_name;
			public String eval_con;
			public String add_time;
			public String health_score;
			public String sheshi_score;
			public String service_score;
			public String traffic_score;
			public String avg_score;
			public String eval_images;
			public String status;
			public String eval_remark;
		}

		public static class avg_info {
			public String avg_total;
			public String avg_health_score;
			public String avg_sheshi_score;
			public String avg_service_score;
			public String avg_traffic_score;
		}
	}

}
