package com.community.model;

import java.util.List;

/**
 * 帖子首页
 * Created by mayn on 2018/9/8.
 */

public class ForumInfo {
    public String keyword_search;
    public String cms_article_list_url;
    public List<adv_list> adv_list;
    public List<class_list> class_list;
    public List<cms_article_list> cms_article_list;
    public List<adv_buttom> adv_buttom;

    public static class adv_list {
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }

    public static class class_list {
        public String class_id;
        public String class_name;
        public String class_sort;
        public String class_hot;
        public String class_thumb;
        public String class_desc;
        public String url;
    }

    public static class cms_article_list {
        public String article_id;
        public String article_title;
        public String article_image;
        public String article_publisher_name;
        public String article_publish_time;
        public String article_commend_flag;
        public String is_top;
        public String article_click;
        public String article_comment_count;
        public String article_attachment_path;
        public String url;
        public List<String> article_image_all;
    }

    public static class adv_buttom {
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }


}
