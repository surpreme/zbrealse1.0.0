package com.aite.a.model;

import java.util.List;

/**
 * 新闻分类列表
 * 
 * @author Administrator
 *
 */
public class NewslistifyInfo {
	public List<news_list> news_list;
	public String class_name;

	public static class news_list {
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
