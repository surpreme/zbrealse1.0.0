package com.aite.a.model;

import java.util.List;

/**
 * 话题评论
 * 
 * @author Administrator
 *
 */
public class Topicscommentlistinfo {
	public List<comment_list> comment_list;

	public static class comment_list {
		public String theme_id;
		public String reply_id;
		public String circle_id;
		public String member_id;
		public String member_name;
		public String reply_content;
		public String reply_addtime;
		public String reply_replyid;
		public String reply_replyname;
		public String is_closed;
		public String reply_exp;
		public String member_avatar;
	}

}
