package com.aite.a.model;

public class good_arr {
	public String goods_id;
	public String evaluate_score;
	public String evaluate_comment;
	public String goods_name;
	public String rec_id;
	public String goods_price;
	public String goods_image;

	public good_arr(String goods_id, String evaluate_score,
			String evaluate_comment, String goods_name, String rec_id,
			String goods_price, String goods_image) {
		super();
		this.goods_id = goods_id;
		this.evaluate_score = evaluate_score;
		this.evaluate_comment = evaluate_comment;
		this.goods_name = goods_name;
		this.rec_id = rec_id;
		this.goods_price = goods_price;
		this.goods_image = goods_image;
	}

	@Override
	public String toString() {
		return "{goods_id:" + goods_id + ", evaluate_score:" + evaluate_score
				+ ", evaluate_comment:" + evaluate_comment + ", goods_name:"
				+ goods_name + ", rec_id:" + rec_id + ", goods_price:"
				+ goods_price + ", goods_image:" + goods_image + "}";
	}

}
