package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

public class GoodList2 implements Serializable {
	private static final long serialVersionUID = -462071791561436120L;
	public List<goods_list>goods_list;
	public String gc_name;
	public static class goods_list{
		public String goods_id;
		public String goods_name;
		public String goods_price;
		public String goods_marketprice;
		public String goods_image;
		public String goods_promotion_price;
		public String goods_salenum;
		public String evaluation_good_star;
		public String evaluation_count;
		public String is_virtual;
		public String is_presell;
		public String is_fcode;
		public String have_gift;
		public String group_flag;
		public String xianshi_flag;
		public String goods_image_url;
		public String rec_id;

		@Override
		public String toString() {
			return "GoodList [goods_id=" + goods_id + ", goods_name=" + goods_name
					+ ", goods_price=" + goods_price + ", goods_marketprice="
					+ goods_marketprice + ", goods_image=" + goods_image
					+ ", goods_promotion_price=" + goods_promotion_price
					+ ", goods_salenum=" + goods_salenum
					+ ", evaluation_good_star=" + evaluation_good_star
					+ ", evaluation_count=" + evaluation_count + ", is_virtual="
					+ is_virtual + ", is_presell=" + is_presell + ", is_fcode="
					+ is_fcode + ", have_gift=" + have_gift + ", group_flag="
					+ group_flag + ", xianshi_flag=" + xianshi_flag
					+ ", goods_image_url=" + goods_image_url + "]";
		}

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

		public String getGoods_marketprice() {
			return goods_marketprice;
		}

		public void setGoods_marketprice(String goods_marketprice) {
			this.goods_marketprice = goods_marketprice;
		}

		public String getGoods_image() {
			return goods_image;
		}

		public void setGoods_image(String goods_image) {
			this.goods_image = goods_image;
		}

		public String getGoods_promotion_price() {
			return goods_promotion_price;
		}

		public void setGoods_promotion_price(String goods_promotion_price) {
			this.goods_promotion_price = goods_promotion_price;
		}

		public String getGoods_salenum() {
			return goods_salenum;
		}

		public void setGoods_salenum(String goods_salenum) {
			this.goods_salenum = goods_salenum;
		}

		public String getEvaluation_good_star() {
			return evaluation_good_star;
		}

		public void setEvaluation_good_star(String evaluation_good_star) {
			this.evaluation_good_star = evaluation_good_star;
		}

		public String getEvaluation_count() {
			return evaluation_count;
		}

		public void setEvaluation_count(String evaluation_count) {
			this.evaluation_count = evaluation_count;
		}

		public String getIs_virtual() {
			return is_virtual;
		}

		public void setIs_virtual(String is_virtual) {
			this.is_virtual = is_virtual;
		}

		public String getIs_presell() {
			return is_presell;
		}

		public void setIs_presell(String is_presell) {
			this.is_presell = is_presell;
		}

		public String getIs_fcode() {
			return is_fcode;
		}

		public void setIs_fcode(String is_fcode) {
			this.is_fcode = is_fcode;
		}

		public String getHave_gift() {
			return have_gift;
		}

		public void setHave_gift(String have_gift) {
			this.have_gift = have_gift;
		}

		public String getGroup_flag() {
			return group_flag;
		}

		public void setGroup_flag(String group_flag) {
			this.group_flag = group_flag;
		}

		public String getXianshi_flag() {
			return xianshi_flag;
		}

		public void setXianshi_flag(String xianshi_flag) {
			this.xianshi_flag = xianshi_flag;
		}

		public String getGoods_image_url() {
			return goods_image_url;
		}

		public void setGoods_image_url(String goods_image_url) {
			this.goods_image_url = goods_image_url;
		}
	}

}
