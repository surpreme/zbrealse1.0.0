package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

/**
 * 达人推荐
 * 
 * @author Administrator
 *
 */
public class StaffPicksInfo {
	public List<list> list;

	public static class list implements Serializable{
		public String personal_id;
		public String commend_member_id;
		public String commend_image;
		public String commend_buy;
		public String commend_message;
		public String commend_time;
		public String class_id;
		public String like_count;
		public String comment_count;
		public String click_count;
		public String microshop_commend;
		public String microshop_sort;
		public String member_name;
		public String member_avatar;
	}
}
