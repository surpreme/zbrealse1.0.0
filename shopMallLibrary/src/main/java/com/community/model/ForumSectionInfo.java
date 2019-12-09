package com.community.model;

import java.util.List;

/**
 * 帖子板块
 * Created by mayn on 2018/9/8.
 */
public class ForumSectionInfo {
    public String keyword_search;
    public class_info class_info;
    public List<cms_article_hot_list> cms_article_hot_list;
    public List<cms_article_list> cms_article_list;

    public static class class_info {
        public String class_id;
        public String class_name;
        public String class_sort;
        public String class_hot;
        public String class_thumb;
        public String class_desc;
        public String is_like;
        public String article_total;
        public String article_essence_total;
        public String article_curday_total;
    }

    public static class cms_article_hot_list {
        public String article_id;
        public String article_title;
        public String url;
    }

    public static class cms_article_list {
        public String article_id;
        public String article_title;
        public String article_image;
        public List<String> article_image_all;
        public String article_publisher_name;
        public String article_publish_time;
        public String article_commend_flag;
        public String is_top;
        public String article_click;
        public String article_comment_count;
        public String article_attachment_path;
        public String url;
    }
}
