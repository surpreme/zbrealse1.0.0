package com.aite.a.model;

import java.util.List;

/**
 * 社区评论列表
 * 
 * @author Administrator
 *
 */
public class Foundcommenlistinfo {
	public List<comment_list> comment_list;
	public micro_info micro_info;

	public static class comment_list {
		public String comment_id;
		public String comment_type;
		public String comment_object_id;
		public String comment_message;
		public String comment_member_id;
		public String comment_time;
		public String member_avatar;
		public String member_name;
	}

	public static class micro_info {
		public String commend_goods_id;
		public String comment_count;
		public String like_count;
	}
}
