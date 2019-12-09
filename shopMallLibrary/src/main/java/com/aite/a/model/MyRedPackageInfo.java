package com.aite.a.model;

import java.util.List;

/**
 * 我的红包
 * 
 * @author Administrator
 *
 */
public class MyRedPackageInfo {

	public String hasmore;
	public String page_total;
	public List<datas> datas;

	public static class datas {
		public String id;
		public String activity_id;
		public String activity_title;
		public String member_id;
		public String member_name;
		public String state;
		public String amount;
		public String add_time;
		public String cash_sn;
		public String withdrawal_state;
		public String withdrawal_time;
		public String issue_time;
		public String send_listid;
		public String state_desc;
		public String withdrawal_state_desc;
		public String cash_sn_desc;
	}
}
