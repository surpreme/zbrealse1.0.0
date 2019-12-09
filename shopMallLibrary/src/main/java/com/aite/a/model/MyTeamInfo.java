package com.aite.a.model;

import java.util.List;

/**
 * 我的团队
 * 
 * @author Administrator
 *
 */
public class MyTeamInfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<list> list;
		public List<wx_levels>wx_levels;
		public static class list {
			public String member_id;
			public String member_avatar;
			public String member_name;
			public String nickname;
			public String avator;
			public String commission;
			public String parent_name;
			public String buy_count;
		}
		
		public static class wx_levels {
			public String id;
			public String level_name;
			public String ordinary_comm;
			public String senior_comm;
			public String privilege_comm;
			public String status;
			public String my_member_count;
		}
	}
}
