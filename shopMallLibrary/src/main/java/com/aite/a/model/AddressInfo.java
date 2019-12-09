package com.aite.a.model;

import java.io.Serializable;

/**
 * 地址实体类
 * 
 * @author xiaoyu
 *
 */
/**
 * @author Administrator
 *
 */
public class AddressInfo implements Serializable {
	private static final long serialVersionUID = 766671057663749901L;
	public String address_id;
	public String member_id;
	public String true_name;
	public String area_id;
	public String city_id;
	public String area_info;
	public String address;
	public String tel_phone;
	public String mob_phone;
	public String is_default;
	public String dlyp_id;

	private String content;
	private String allow_offpay;
	private String offpay_hash;
	private String offpay_hash_batch;
	

	public AddressInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressInfo(String content, String allow_offpay, String offpay_hash,
			String offpay_hash_batch) {
		super();
		this.content = content;
		this.allow_offpay = allow_offpay;
		this.offpay_hash = offpay_hash;
		this.offpay_hash_batch = offpay_hash_batch;
	}

	public AddressInfo(String address_id, String member_id, String true_name,
			String area_id, String city_id, String area_info, String address,
			String tel_phone, String mob_phone, String is_default,
			String dlyp_id) {
		super();
		this.address_id = address_id;
		this.member_id = member_id;
		this.true_name = true_name;
		this.area_id = area_id;
		this.city_id = city_id;
		this.area_info = area_info;
		this.address = address;
		this.tel_phone = tel_phone;
		this.mob_phone = mob_phone;
		this.is_default = is_default;
		this.dlyp_id = dlyp_id;
	}

	public AddressInfo(String address_id, String member_id, String true_name,
			String area_id, String city_id, String area_info, String address,
			String tel_phone, String mob_phone, String dlyp_id) {
		super();
		this.address_id = address_id;
		this.member_id = member_id;
		this.true_name = true_name;
		this.area_id = area_id;
		this.city_id = city_id;
		this.area_info = area_info;
		this.address = address;
		this.tel_phone = tel_phone;
		this.mob_phone = mob_phone;
		this.dlyp_id = dlyp_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAllow_offpay() {
		return allow_offpay;
	}

	public void setAllow_offpay(String allow_offpay) {
		this.allow_offpay = allow_offpay;
	}

	public String getOffpay_hash() {
		return offpay_hash;
	}

	public void setOffpay_hash(String offpay_hash) {
		this.offpay_hash = offpay_hash;
	}

	public String getOffpay_hash_batch() {
		return offpay_hash_batch;
	}

	public void setOffpay_hash_batch(String offpay_hash_batch) {
		this.offpay_hash_batch = offpay_hash_batch;
	}

	@Override
	public String toString() {
		return "AddressInfo [address_id=" + address_id + ", member_id="
				+ member_id + ", true_name=" + true_name + ", area_id="
				+ area_id + ", city_id=" + city_id + ", area_info=" + area_info
				+ ", address=" + address + ", tel_phone=" + tel_phone
				+ ", mob_phone=" + mob_phone + ", is_default=" + is_default
				+ ", dlyp_id=" + dlyp_id + "]";
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTrue_name() {
		return true_name;
	}

	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getArea_info() {
		return area_info;
	}

	public void setArea_info(String area_info) {
		this.area_info = area_info;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel_phone() {
		return tel_phone;
	}

	public void setTel_phone(String tel_phone) {
		this.tel_phone = tel_phone;
	}

	public String getMob_phone() {
		return mob_phone;
	}

	public void setMob_phone(String mob_phone) {
		this.mob_phone = mob_phone;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getDlyp_id() {
		return dlyp_id;
	}

	public void setDlyp_id(String dlyp_id) {
		this.dlyp_id = dlyp_id;
	}

}
