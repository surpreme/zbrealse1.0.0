package com.aite.a.model;

import java.util.List;

/**
 * 资讯首页
 * 
 * @author Administrator
 *
 */
public class InformationInfo {
	public List<new_list> new_list;
	public List<new_list_top> new_list_top;
	public List<new_list_tuijian> new_list_tuijian;
	public List<new_class_list> new_class_list;
	public List<get_ad> get_ad;

	public static class new_list {
		public String article_id;
		public String article_title;
		public String article_image;
		public String article_link;
		public String article_publish_time;
		public String article_author;
		public String article_abstract;
		public String article_click;
		public String article_image_all;
		public String article_comment_count;
		public String article_class_id;
		public String article_publisher_id;
	}

	public static class new_list_top {
		public String article_id;
		public String article_title;
		public String article_image;
		public String article_link;
		public String article_publish_time;
		public String article_author;
		public String article_abstract;
		public String article_click;
		public String article_image_all;
		public String article_comment_count;
		public String article_class_id;
		public String article_publisher_id;
	}

	public static class new_list_tuijian {
		public String article_id;
		public String article_title;
		public String article_image;
		public String article_link;
		public String article_publish_time;
		public String article_author;
		public String article_abstract;
		public String article_click;
		public String article_image_all;
		public String article_comment_count;
		public String article_class_id;
		public String article_publisher_id;
	}

	public static class new_class_list {
		public String class_id;
		public String class_name;
		public String class_sort;
	}
	public static class get_ad {
		public String adv_title;
		public String adv_img;
		public String adv_url;
	}
	
}
