package com.aite.a.model;

import java.util.List;

/**
 * 咨询列表
 * 
 * @author Administrator
 *
 */
public class CustomerServiceinfo {
	public String code;
	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<consult_list> consult_list;

		public static class consult_list {
			public String mc_id;
			public String mct_id;
			public String member_id;
			public String member_name;
			public String mc_content;
			public String mc_addtime;
			public String is_reply;
			public String mc_reply;
			public String mc_reply_time;
			public String admin_id;
			public String admin_name;
		}
	}
}
