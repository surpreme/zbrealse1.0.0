package com.aite.a.model;

import java.util.List;

/**
 * 自定义首页
 * 
 * @author Administrator
 *
 */
public class CustomHomeInfo {
	public String type;
	public adv_list adv_list;// 滚动广告
	public navigation navigation;// 导航
	public home1 home1;// 单个广告
	public home2 home2;// 左一大右二小广告
	public home3 home3;// 一排两个列表广告
	public home4 home4;// 左二小右一大广告
	public home5 home5;// 一排三个广告
	public home5 home6;// 一排四个广告
	public goods goods;// 一排两个商品列表

	public static class adv_list {
		public String style;
		public List<item> item;

		public static class item {
			public String image;
			public String type;
			public String data;
		}
	}

	public static class navigation {
		public String style;
		public List<item> item;

		public static class item {
			public String navigation_image_name;
			public String navigation_name;
			public String navigation_type;
			public String navigation_data;
			public String image;
		}
	}

	public static class home1 {
		public String title;
		public String style;
		public String image;
		public String type;
		public String data;
	}

	public static class home3 {
		public String title;
		public String style;
		public List<item> item;

		public static class item {
			public String image;
			public String type;
			public String data;
		}
	}

	public static class goods {
		public String title;
		public String style;
		public List<item> item;

		public static class item {
			public String goods_id;
			public String goods_name;
			public String goods_promotion_type;
			public String goods_promotion_price;
			public String goods_image;
			public String goods_marketprice;
			public String level_0_price;
			public String level_1_price;
			public String level_2_price;
			public String level_3_price;
			public String level_0_auth_price;
			public String level_1_auth_price;
			public String level_2_auth_price;
			public String level_3_auth_price;
			public String goods_price;
			public String is_own_shop;
		}
	}

	public static class home2 {
		public String title;
		public String style;
		public String square_image;
		public String square_type;
		public String square_data;
		public String rectangle1_image;
		public String rectangle1_type;
		public String rectangle1_data;
		public String rectangle2_image;
		public String rectangle2_type;
		public String rectangle2_data;
	}

	public static class home4 {
		public String title;
		public String style;
		public String rectangle1_image;
		public String rectangle1_type;
		public String rectangle1_data;
		public String rectangle2_image;
		public String rectangle2_type;
		public String rectangle2_data;
		public String square_image;
		public String square_type;
		public String square_data;
	}

	public static class home5 {
		public String title;
		public List<item>item;
		public static class item{
			public String image;
			public String type;
			public String data;
		}
	}
}
