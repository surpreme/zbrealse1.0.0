package com.aite.a.model;

import java.util.List;

/**
 * 代金券列表
 * 
 * @author Administrator
 *
 */
public class VouchersListInfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<voucher_list> voucher_list;

		public static class voucher_list {
			public String voucher_id;
			public String voucher_code;
			public String voucher_title;
			public String voucher_desc;
			public String voucher_start_date;
			public String voucher_end_date;
			public String voucher_price;
			public String voucher_limit;
			public String voucher_state;
			public String voucher_order_id;
			public String voucher_store_id;
			public String store_name;
			public String store_id;
			public String store_domain;
			public String voucher_t_customimg;
			public String voucher_state_text;
		}
	}
}
