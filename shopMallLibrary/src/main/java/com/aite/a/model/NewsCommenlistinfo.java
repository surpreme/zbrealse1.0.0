package com.aite.a.model;

import java.util.List;


/**
 * 新闻评论列表
 * 
 * @author Administrator
 *
 */
public class NewsCommenlistinfo {
	public List<comment_list> comment_list;
	public String comment_count;

	public static class comment_list {
		public String comment_id;
		public String comment_type;
		public String comment_object_id;
		public String comment_message;
		public String comment_member_id;
		public String comment_time;
		public String comment_quote;
		public String comment_up;
		public String member_avatar;
		public String member_name;
	}
}
