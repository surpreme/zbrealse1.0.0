package com.aite.a.model;

import java.util.List;

/**
 * 酒店首页
 * 
 * @author Administrator
 *
 */
public class HotelHomeInfo {
	public List<adv> adv;
	public List<style_arr> style_arr;

	public static class adv {
		public String adv_title;
		public String adv_img;
		public String adv_url;
	}

	public static class style_arr{
		public String name;
		public String value;
	}
}
