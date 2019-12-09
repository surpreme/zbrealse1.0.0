package com.aite.a.model;

import java.util.List;

/**
 * 代金券列表
 * 
 * @author Administrator
 *
 */
public class HotVouchersListInfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<voucherlist> voucherlist;
		public List<pricelist> pricelist;

		public static class voucherlist {
			public String voucher_t_id;
			public String voucher_t_title;
			public String voucher_t_desc;
			public String voucher_t_start_date;
			public String voucher_t_end_date;
			public String voucher_t_price;
			public String voucher_t_limit;
			public String voucher_t_store_id;
			public String voucher_t_storename;
			public String voucher_t_sc_id;
			public String voucher_t_creator_id;
			public String voucher_t_state;
			public String voucher_t_total;
			public String voucher_t_giveout;
			public String voucher_t_used;
			public String voucher_t_add_date;
			public String voucher_t_quotaid;
			public String voucher_t_points;
			public String voucher_t_eachlimit;
			public String voucher_t_styleimg;
			public String voucher_t_customimg;
			public String voucher_t_recommend;
			public String voucher_t_sc_name;
		}

		public static class pricelist {
			public String voucher_price_id;
			public String voucher_price_describe;
			public String voucher_price;
			public String voucher_defaultpoints;
		}

	}
}
