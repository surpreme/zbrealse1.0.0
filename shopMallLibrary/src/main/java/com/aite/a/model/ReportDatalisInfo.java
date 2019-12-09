package com.aite.a.model;

/**
 * 举报详情
 * 
 * @author Administrator
 *
 */
public class ReportDatalisInfo {

	public inform_info inform_info;
	public goods_info goods_info;
	public subject_info subject_info;

	public static class inform_info {
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

	public static class goods_info {
		public String goods_id;
		public String goods_commonid;
		public String goods_name;
		public String goods_jingle;
		public String store_name;
		public String store_id;
		public String goods_image;
	}

	public static class subject_info {
		public String inform_subject_id;
		public String inform_subject_content;
		public String inform_subject_type_id;
		public String inform_subject_type_name;
		public String inform_subject_state;
	}

}
