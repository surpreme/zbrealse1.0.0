package com.aite.a.model;

import java.util.List;

/**
 * 站内信
 */
public class StationLetterInfo {
	public String hasmore;
	public String page_total;
	public newNum new_num;
	public List<messageArray> message_array;
	
	public static class newNum {
		public String newcommon;
		public String newsystem;
		public String newpersonal;
		public String isallowsend;
	}

	public static class messageArray {
		public String message_id;
		public String message_parent_id;
		public String from_member_id;
		public String to_member_id;
		public String message_title;
		public String message_body;
		public String message_time;
		public String message_update_time;
		public String message_open;
		public String message_state;
		public String message_type;
		public String read_member_id;
		public String del_member_id;
		public String message_ismore;
		public String from_member_name;
		public String to_member_name;
		public boolean ispick=false;
	}
}
