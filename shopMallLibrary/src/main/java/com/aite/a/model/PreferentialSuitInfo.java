package com.aite.a.model;

import java.util.List;

/**
 * 优惠套装
 * 
 * @author Administrator
 *
 */
public class PreferentialSuitInfo {

	public String id;
	public String name;
	public String cost_price;
	public String price;
	public String freight;

	public List<goods_list> goods_list;

	public static class goods_list {
		public String id;
		public String image;
		public String name;
		public String shop_price;
		public String price;
	}

}
