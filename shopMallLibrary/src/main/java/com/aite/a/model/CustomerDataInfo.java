package com.aite.a.model;

import android.graphics.Bitmap;

public class CustomerDataInfo {

	public String id;
	public String member_id;
	public String member_name;
	public String customer_id;
	public String customer_name;
	public String customer_mobile;
	public String provice_id;
	public String city_id;
	public String area_id;
	public String area_info;
	public String add_time;
	public String member_avatar_url;
	public CustomerDataInfo() {
		// TODO Auto-generated constructor stub
	}
	public CustomerDataInfo(String id, String member_id, String member_name,
			String customer_id, String customer_name, String customer_mobile,
			String provice_id, String city_id, String area_id,
			String area_info, String add_time, String member_avatar_url) {
		super();
		this.id = id;
		this.member_id = member_id;
		this.member_name = member_name;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.customer_mobile = customer_mobile;
		this.provice_id = provice_id;
		this.city_id = city_id;
		this.area_id = area_id;
		this.area_info = area_info;
		this.add_time = add_time;
		this.member_avatar_url = member_avatar_url;
	}
	
	
}
