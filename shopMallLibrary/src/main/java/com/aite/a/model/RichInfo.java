package com.aite.a.model;

import java.util.List;

/**
 * 富豪榜
 * 
 * @author Administrator
 *
 */
public class RichInfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<list> list;

		public static class list {
			public String member_id;
			public String member_avatar;
			public String member_name;
			public String nickname;
			public String commission;
			public String avator;
			public String parent_name;
			public String buy_count;
		}
	}

}
