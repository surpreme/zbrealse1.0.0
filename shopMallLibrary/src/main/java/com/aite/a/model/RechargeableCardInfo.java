package com.aite.a.model;

import java.util.List;


/**
 * 充值卡充值
 * 
 * @author Administrator
 *
 */
public class RechargeableCardInfo {
	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<list> list;
		public member_info member_info;

		public static class list {
			public String id;
			public String member_id;
			public String member_name;
			public String type;
			public String add_time;
			public String available_amount;
			public String freeze_amount;
			public String description;
		}

		public static class member_info {
			public String available_rc_balance;
			public String freeze_rc_balance;
		}
	}

}
