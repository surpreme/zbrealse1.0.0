package com.community.model;

import java.util.List;

/**
 * 发现首页
 * Created by mayn on 2018/9/6.
 */

public class FindHomeInfo {
    public String find_search;
    public String hot_activity_list_url;
    public String hot_activity_create_url;
    public String theme_list_url;
    public String theme_list_create_url;
    public String circle_list_url;
    public String cms_class_list_url;
    public String cms_article_list_url;
    public String cms_article_create_url;
    public List<adv_list> adv_list;
    public List<nav_list> nav_list;
    public List<hot_activity_list> hot_activity_list;
    public List<theme_list> theme_list;
    public List<circle_list> circle_list;
    public List<cms_class_list> cms_class_list;
    public List<cms_article_list> cms_article_list;
    public List<adv_buttom> adv_buttom;

    public static class adv_list {
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }

    public static class nav_list {
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }

    public static class hot_activity_list {
        public String id;
        public String title;
        public String class_id;
        public String thumb;
        public String start_time;
        public String end_time;
        public String price;
        public String city_id;
        public String city_name;
        public String class_name;
        public String url;
    }

    public static class theme_list {
        public String theme_id;
        public String is_like;
        public String theme_name;
        public String member_id;
        public String member_name;
        public String member_avatar;
        public String theme_addtime;
        public List<String> thumb_list;
        public List<reply_info> reply_info;
        public String url;

        public static class reply_info {
            public String member_name;
            public String reply_content;
        }
    }

    public static class circle_list {
        public String circle_id;
        public String circle_name;
        public String circle_desc;
        public String circle_masterid;
        public String circle_mcount;
        public String circle_notice;
        public String circle_masterid_avatar;
        public String url;
        public String join_url;
        public List<member_list> member_list;

        public static class member_list {
            public String member_id;
            public String member_name;
            public String member_avatar;
        }
    }

    public static class cms_class_list {
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }

    public static class cms_article_list {
        public String article_id;
        public String article_title;
        public String article_image;
        public List<String> article_image_all;
        public String article_publisher_name;
        public String article_publish_time;
        public String article_commend_flag;
        public String article_click;
        public String article_comment_count;
        public String article_attachment_path;
        public String url;
    }

    public static class adv_buttom{
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }
}
