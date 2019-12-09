package com.aite.a.model;

/**
 * 实名认证
 * 
 * @author Administrator
 *
 */
public class IdentityInfo {

	public String member_is_auth;
	public member_auth_info member_auth_info;

	public static class member_auth_info {
		public String id;
		public String member_id;
		public String auth_name;
		public String auth_card;
		public String auth_sex;
		public String auth_open_time;
		public String auth_address;
		public String auth_over_time;
		public String occupation_id;
		public String id_card_img;
		public String business_license_img;
		public String auth_fm_imgurl;
		public String auth_sc_imgurl;
		public String apply_time;
		public String auth_time;
		public String mobile;
		public String edit_time;
		public String treatment_state;
		public String treatment_time;
		public String treatment_remark;
	}
}
