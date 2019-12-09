package com.aite.a.model;

import java.util.List;

public class CartListInfo2 {
	public List<cart_list> cart_list;
	public String sum;

	public static class cart_list {
		public String cart_id;
		public String buyer_id;
		public String store_id;
		public String store_name;
		public String goods_id;
		public String goods_name;
		public String goods_price;
		public String goods_num;
		public String goods_image;
		public String bl_id;
		public String goods_image_url;
		public String goods_sum;
		public boolean isChoosed;
	}

}
