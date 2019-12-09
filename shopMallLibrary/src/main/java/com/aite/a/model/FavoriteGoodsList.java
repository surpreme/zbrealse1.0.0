package com.aite.a.model;

/**
 * @author Administrator
 *
 */
public class FavoriteGoodsList {
	public String goods_id;
	public String goods_name;
	public String goods_price;
	public String goods_image;
	public String store_id;
	public String fav_id;
	public String goods_image_url;

	public String store_name;
	public String seller_name;
	public String area_info;
	public String store_label;
	public String store_zy;
	public String store_grade;
	public String store_avatar;

	public boolean is_check;
	public boolean is_show;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getFav_id() {
		return fav_id;
	}

	public void setFav_id(String fav_id) {
		this.fav_id = fav_id;
	}

	public String getGoods_image_url() {
		return goods_image_url;
	}

	public void setGoods_image_url(String goods_image_url) {
		this.goods_image_url = goods_image_url;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getSeller_name() {
		return seller_name;
	}

	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}

	public String getArea_info() {
		return area_info;
	}

	public void setArea_info(String area_info) {
		this.area_info = area_info;
	}

	public String getStore_label() {
		return store_label;
	}

	public void setStore_label(String store_label) {
		this.store_label = store_label;
	}

	public String getStore_zy() {
		return store_zy;
	}

	public void setStore_zy(String store_zy) {
		this.store_zy = store_zy;
	}

	public String getStore_grade() {
		return store_grade;
	}

	public void setStore_grade(String store_grade) {
		this.store_grade = store_grade;
	}

	public String getStore_avatar() {
		return store_avatar;
	}

	public void setStore_avatar(String store_avatar) {
		this.store_avatar = store_avatar;
	}

	public boolean isIs_check() {
		return is_check;
	}

	public void setIs_check(boolean is_check) {
		this.is_check = is_check;
	}

	public boolean isIs_show() {
		return is_show;
	}

	public void setIs_show(boolean is_show) {
		this.is_show = is_show;
	}

	public FavoriteGoodsList() {
		// TODO Auto-generated constructor stub
	}

	public FavoriteGoodsList(String goods_id, String goods_name,
			String goods_price, String goods_image, String store_id,
			String fav_id, String goods_image_url, String store_name,
			String seller_name, String area_info, String store_label,
			String store_zy, String store_grade, String store_avatar,
			boolean is_check, boolean is_show) {
		super();
		this.goods_id = goods_id;
		this.goods_name = goods_name;
		this.goods_price = goods_price;
		this.goods_image = goods_image;
		this.store_id = store_id;
		this.fav_id = fav_id;
		this.goods_image_url = goods_image_url;
		this.store_name = store_name;
		this.seller_name = seller_name;
		this.area_info = area_info;
		this.store_label = store_label;
		this.store_zy = store_zy;
		this.store_grade = store_grade;
		this.store_avatar = store_avatar;
		this.is_check = is_check;
		this.is_show = is_show;
	}

	@Override
	public String toString() {
		return "FavoriteGoodsList [goods_id=" + goods_id + ", goods_name="
				+ goods_name + ", goods_price=" + goods_price
				+ ", goods_image=" + goods_image + ", store_id=" + store_id
				+ ", fav_id=" + fav_id + ", goods_image_url=" + goods_image_url
				+ ", store_name=" + store_name + ", seller_name=" + seller_name
				+ ", area_info=" + area_info + ", store_label=" + store_label
				+ ", store_zy=" + store_zy + ", store_grade=" + store_grade
				+ ", store_avatar=" + store_avatar + ", is_check=" + is_check
				+ ", is_show=" + is_show + "]";
	}

}
