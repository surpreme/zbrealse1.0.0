package com.aite.a.utils;

public class OrderGoodsList {
//	private static OrderGoodsList og = null;

	public OrderGoodsList() {
	}

//	public static OrderGoodsList getInstance() {
//		if (og == null) {
//			og = new OrderGoodsList();
//		}
//		return og;
//	}

	private String gc_id;
	private String is_more_discount;
	private String state;
	private String goods_name;
	private String is_vat;
	private String goods_storage;
	private String goods_total;
	private String transport_id;
	private String cart_id;
	private String goods_price;
	private String goods_commonid;
	private String goods_costtotal;
	private String goods_num;
	private String goods_image_url;
	private String store_id;
	private String is_fcode;
	private String goods_image;
	private String goods_vat;
	private String bl_id;
	private String goods_freight;
	private String wholesale_price;
	private String have_gift;
	private String goods_storage_alarm;
	private String goods_id;
	private String storage_state;
	private String goods_costprice;

	public String getGc_id() {
		return gc_id;
	}

	public void setGc_id(String gc_id) {
		this.gc_id = gc_id;
	}

	public String getIs_more_discount() {
		return is_more_discount;
	}

	public void setIs_more_discount(String is_more_discount) {
		this.is_more_discount = is_more_discount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getIs_vat() {
		return is_vat;
	}

	public void setIs_vat(String is_vat) {
		this.is_vat = is_vat;
	}

	public String getGoods_storage() {
		return goods_storage;
	}

	public void setGoods_storage(String goods_storage) {
		this.goods_storage = goods_storage;
	}

	public String getGoods_total() {
		return goods_total;
	}

	public void setGoods_total(String goods_total) {
		this.goods_total = goods_total;
	}

	public String getTransport_id() {
		return transport_id;
	}

	public void setTransport_id(String transport_id) {
		this.transport_id = transport_id;
	}

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_commonid() {
		return goods_commonid;
	}

	public void setGoods_commonid(String goods_commonid) {
		this.goods_commonid = goods_commonid;
	}

	public String getGoods_costtotal() {
		return goods_costtotal;
	}

	public void setGoods_costtotal(String goods_costtotal) {
		this.goods_costtotal = goods_costtotal;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getGoods_image_url() {
		return goods_image_url;
	}

	public void setGoods_image_url(String goods_image_url) {
		this.goods_image_url = goods_image_url;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getIs_fcode() {
		return is_fcode;
	}

	public void setIs_fcode(String is_fcode) {
		this.is_fcode = is_fcode;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public String getGoods_vat() {
		return goods_vat;
	}

	public void setGoods_vat(String goods_vat) {
		this.goods_vat = goods_vat;
	}

	public String getBl_id() {
		return bl_id;
	}

	public void setBl_id(String bl_id) {
		this.bl_id = bl_id;
	}

	public String getGoods_freight() {
		return goods_freight;
	}

	public void setGoods_freight(String goods_freight) {
		this.goods_freight = goods_freight;
	}

	public String getWholesale_price() {
		return wholesale_price;
	}

	public void setWholesale_price(String wholesale_price) {
		this.wholesale_price = wholesale_price;
	}

	public String getHave_gift() {
		return have_gift;
	}

	public void setHave_gift(String have_gift) {
		this.have_gift = have_gift;
	}

	public String getGoods_storage_alarm() {
		return goods_storage_alarm;
	}

	public void setGoods_storage_alarm(String goods_storage_alarm) {
		this.goods_storage_alarm = goods_storage_alarm;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getStorage_state() {
		return storage_state;
	}

	public void setStorage_state(String storage_state) {
		this.storage_state = storage_state;
	}

	public String getGoods_costprice() {
		return goods_costprice;
	}

	public void setGoods_costprice(String goods_costprice) {
		this.goods_costprice = goods_costprice;
	}

	public OrderGoodsList(String gc_id, String is_more_discount, String state,
			String goods_name, String is_vat, String goods_storage,
			String goods_total, String transport_id, String cart_id,
			String goods_price, String goods_commonid, String goods_costtotal,
			String goods_num, String goods_image_url, String store_id,
			String is_fcode, String goods_image, String goods_vat,
			String bl_id, String goods_freight, String wholesale_price,
			String have_gift, String goods_storage_alarm, String goods_id,
			String storage_state, String goods_costprice) {
		super();
		this.gc_id = gc_id;
		this.is_more_discount = is_more_discount;
		this.state = state;
		this.goods_name = goods_name;
		this.is_vat = is_vat;
		this.goods_storage = goods_storage;
		this.goods_total = goods_total;
		this.transport_id = transport_id;
		this.cart_id = cart_id;
		this.goods_price = goods_price;
		this.goods_commonid = goods_commonid;
		this.goods_costtotal = goods_costtotal;
		this.goods_num = goods_num;
		this.goods_image_url = goods_image_url;
		this.store_id = store_id;
		this.is_fcode = is_fcode;
		this.goods_image = goods_image;
		this.goods_vat = goods_vat;
		this.bl_id = bl_id;
		this.goods_freight = goods_freight;
		this.wholesale_price = wholesale_price;
		this.have_gift = have_gift;
		this.goods_storage_alarm = goods_storage_alarm;
		this.goods_id = goods_id;
		this.storage_state = storage_state;
		this.goods_costprice = goods_costprice;
	}

}
