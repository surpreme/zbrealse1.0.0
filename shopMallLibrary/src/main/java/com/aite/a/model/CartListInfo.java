package com.aite.a.model;

public class CartListInfo {
	String bl_id;
	String buyer_id;
	String goods_price;
	String cart_id;
	String goods_num;
	String goods_id;
	String goods_image_url;
	String goods_name;
	String store_id;
	String goods_sum;
	String goods_image;
	String store_name;
	String total_price;
	private boolean isChoosed;

	public CartListInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartListInfo(String goods_price, String goods_num, String total_price) {
		super();
		this.goods_price = goods_price;
		this.goods_num = goods_num;
		this.total_price = total_price;
	}

	public CartListInfo(String bl_id, String buyer_id, String goods_price,
			String cart_id, String goods_num, String goods_id,
			String goods_image_url, String goods_name, String store_id,
			String goods_sum, String goods_image, String store_name) {
		super();
		this.bl_id = bl_id;
		this.buyer_id = buyer_id;
		this.goods_price = goods_price;
		this.cart_id = cart_id;
		this.goods_num = goods_num;
		this.goods_id = goods_id;
		this.goods_image_url = goods_image_url;
		this.goods_name = goods_name;
		this.store_id = store_id;
		this.goods_sum = goods_sum;
		this.goods_image = goods_image;
		this.store_name = store_name;
	}

	public CartListInfo(String bl_id, String buyer_id, String goods_price,
			String cart_id, String goods_num, String goods_id,
			String goods_image_url, String goods_name, String store_id,
			String goods_sum, String goods_image, String store_name,
			String total_price, boolean isChoosed) {
		super();
		this.bl_id = bl_id;
		this.buyer_id = buyer_id;
		this.goods_price = goods_price;
		this.cart_id = cart_id;
		this.goods_num = goods_num;
		this.goods_id = goods_id;
		this.goods_image_url = goods_image_url;
		this.goods_name = goods_name;
		this.store_id = store_id;
		this.goods_sum = goods_sum;
		this.goods_image = goods_image;
		this.store_name = store_name;
		this.total_price = total_price;
		this.isChoosed = isChoosed;
	}

	@Override
	public String toString() {
		return "CartListInfo [bl_id=" + bl_id + ", buyer_id=" + buyer_id
				+ ", goods_price=" + goods_price + ", cart_id=" + cart_id
				+ ", goods_num=" + goods_num + ", goods_id=" + goods_id
				+ ", goods_image_url=" + goods_image_url + ", goods_name="
				+ goods_name + ", store_id=" + store_id + ", goods_sum="
				+ goods_sum + ", goods_image=" + goods_image + ", store_name="
				+ store_name + ", total_price=" + total_price + ", isChoosed="
				+ isChoosed + ", getBl_id()=" + getBl_id() + ", getBuyer_id()="
				+ getBuyer_id() + ", getGoods_price()=" + getGoods_price()
				+ ", getTotal_price()=" + getTotal_price() + ", getCart_id()="
				+ getCart_id() + ", getGoods_num()=" + getGoods_num()
				+ ", getGoods_id()=" + getGoods_id()
				+ ", getGoods_image_url()=" + getGoods_image_url()
				+ ", getGoods_name()=" + getGoods_name() + ", getStore_id()="
				+ getStore_id() + ", getGoods_sum()=" + getGoods_sum()
				+ ", getGoods_image()=" + getGoods_image()
				+ ", getStore_name()=" + getStore_name() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ "]";
	}

	public boolean isChoosed() {
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}

	public String getBl_id() {
		return bl_id;
	}

	public void setBl_id(String bl_id) {
		this.bl_id = bl_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_image_url() {
		return goods_image_url;
	}

	public void setGoods_image_url(String goods_image_url) {
		this.goods_image_url = goods_image_url;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getGoods_sum() {
		return goods_sum;
	}

	public void setGoods_sum(String goods_sum) {
		this.goods_sum = goods_sum;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

}
