package com.aite.a.model;

import java.util.List;

/**
 * 购物车
 * @author Administrator
 *
 */
public class IntegralCartInfo {

	public String pgoods_pointall;
	public List<cart_array>cart_array;
	
	public static class cart_array{
		public String pcart_id;
		public String pmember_id;
		public String pgoods_id;
		public String pgoods_name;
		public String pgoods_points;
		public String pgoods_choosenum;
		public String pgoods_image;
		public String pgoods_image_old;
		public String pgoods_image_max;
		public String pgoods_image_small;
		public String pgoods_pointone;
	}
	
}
