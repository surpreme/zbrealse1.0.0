package com.aite.a.model;

import java.util.List;

/**
 * 举报列表
 * 
 * @author Administrator
 *
 */
public class ReportListInfo {

	public String hasmore;
	public String page_total;
	public List<datas> datas;

	public static class datas {
		public String inform_id;
		public String inform_member_id;
		public String inform_member_name;
		public String inform_goods_id;
		public String inform_goods_name;
		public String inform_subject_id;
		public String inform_subject_content;
		public String inform_content;
		public String inform_pic1;
		public String inform_pic2;
		public String inform_pic3;
		public String inform_datetime;
		public String inform_store_id;
		public String inform_state;
		public String inform_handle_type;
		public String inform_handle_message;
		public String inform_handle_datetime;
		public String inform_handle_member_id;
		public String inform_goods_image;
		public String inform_store_name;
	}

}
