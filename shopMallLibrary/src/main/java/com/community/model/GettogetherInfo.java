package com.community.model;

import java.util.List;

/**
 * 活动首页
 * Created by mayn on 2018/9/5.
 */

public class GettogetherInfo {
    public List<adv_list> adv_list;
    public List<class_list> class_list;
    public List<hot_list> hot_list;
    public List<off_list> off_list;
    public List<adv_buttom> adv_buttom;
    public String hot_list_url;
    public String off_list_url;

    public static class adv_list {
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }

    public static class class_list {
        public String class_id;
        public String class_name;
        public String class_parent_id;
        public String class_sort;
        public String thumb;
        public String deep;
        public String url;
    }

    public static class hot_list {
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

    public static class off_list {
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

    public static class adv_buttom {
        public String adv_id;
        public String adv_title;
        public String adv_pic;
        public String adv_pic_url;
    }

}
