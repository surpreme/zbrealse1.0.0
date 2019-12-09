package com.aite.a.model;

import java.util.List;

/**
 * 资讯分类
 * 
 * @author Administrator
 *
 */
public class InformationclassifyInfo {
	public List<article_class_list> article_class_list;
	public List<new_list> new_list;

	public static class article_class_list {
		public String class_id;
		public String class_name;
		public String class_sort;
	}

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

}
