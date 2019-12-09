package com.aite.a.model;

public class User {
	public String user_name;
	public String password;
	public String member_rank;
	public String avator;
	public String point;
	public String predepoit;
	public String store_id;
	public String goods_favorites_count;
	public String store_favorites_count;
	public String personal_bg;
	public String member_mobile;
	public String browse_count;
	public String freeze_predeposit;
	public String vouchercount;
	public String nickname;
	public String redpackage_count;
	
	public String ORDER_STATE_NEW;
	public String ORDER_STATE_PAY;
	public String ORDER_STATE_SEND;
	public String ORDER_STATE_SUCCESS;
	

	public User(String user_name, String password) {
		super();
		this.user_name = user_name;
		this.password = password;
	}

	public User(String user_name, String avator, String point, String predepoit, String store_id, String goods_favorites_count, String store_favorites_count) {
		super();
		this.user_name = user_name;
		this.avator = avator;
		this.point = point;
		this.predepoit = predepoit;
		this.store_id = store_id;
		this.goods_favorites_count = goods_favorites_count;
		this.store_favorites_count = store_favorites_count;
	}

	public User(String user_name, String password, String avator, String point,
			String predepoit, String store_id, String goods_favorites_count,
			String store_favorites_count, String personal_bg,
			String oRDER_STATE_NEW, String oRDER_STATE_PAY,
			String oRDER_STATE_SEND, String oRDER_STATE_SUCCESS) {
		super();
		this.user_name = user_name;
		this.password = password;
		this.avator = avator;
		this.point = point;
		this.predepoit = predepoit;
		this.store_id = store_id;
		this.goods_favorites_count = goods_favorites_count;
		this.store_favorites_count = store_favorites_count;
		this.personal_bg = personal_bg;
		ORDER_STATE_NEW = oRDER_STATE_NEW;
		ORDER_STATE_PAY = oRDER_STATE_PAY;
		ORDER_STATE_SEND = oRDER_STATE_SEND;
		ORDER_STATE_SUCCESS = oRDER_STATE_SUCCESS;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getPredepoit() {
		return predepoit;
	}

	public void setPredepoit(String predepoit) {
		this.predepoit = predepoit;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getGoods_favorites_count() {
		return goods_favorites_count;
	}

	public void setGoods_favorites_count(String goods_favorites_count) {
		this.goods_favorites_count = goods_favorites_count;
	}

	public String getStore_favorites_count() {
		return store_favorites_count;
	}

	public void setStore_favorites_count(String store_favorites_count) {
		this.store_favorites_count = store_favorites_count;
	}

	public String getPersonal_bg() {
		return personal_bg;
	}

	public void setPersonal_bg(String personal_bg) {
		this.personal_bg = personal_bg;
	}

	public String getORDER_STATE_NEW() {
		return ORDER_STATE_NEW;
	}

	public void setORDER_STATE_NEW(String oRDER_STATE_NEW) {
		ORDER_STATE_NEW = oRDER_STATE_NEW;
	}

	public String getORDER_STATE_PAY() {
		return ORDER_STATE_PAY;
	}

	public void setORDER_STATE_PAY(String oRDER_STATE_PAY) {
		ORDER_STATE_PAY = oRDER_STATE_PAY;
	}

	public String getORDER_STATE_SEND() {
		return ORDER_STATE_SEND;
	}

	public void setORDER_STATE_SEND(String oRDER_STATE_SEND) {
		ORDER_STATE_SEND = oRDER_STATE_SEND;
	}

	public String getORDER_STATE_SUCCESS() {
		return ORDER_STATE_SUCCESS;
	}

	public void setORDER_STATE_SUCCESS(String oRDER_STATE_SUCCESS) {
		ORDER_STATE_SUCCESS = oRDER_STATE_SUCCESS;
	}
	

}
