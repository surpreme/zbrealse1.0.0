package com.aite.a.model;

import java.util.List;


/**
 * 充值详情
 * 
 * @author Administrator
 *
 */
public class TopUpInfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public member_info member_info;
		public List<list> list;

		public static class list {
			public String pdr_id;
			public String pdr_sn;
			public String pdr_member_id;
			public String pdr_member_name;
			public String pdr_amount;
			public String pdr_payment_code;
			public String pdr_payment_name;
			public String pdr_trade_sn;
			public String pdr_add_time;
			public String pdr_payment_state;
			public String pdr_payment_time;
			public String pdr_admin;
		}

		public static class member_info {
			public String available_predeposit;
			public String freeze_predeposit;
		}
	}
}
