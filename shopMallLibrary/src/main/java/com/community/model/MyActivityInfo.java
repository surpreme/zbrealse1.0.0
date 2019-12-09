package com.community.model;

import java.util.List;

/**
 * 我的活动
 * Created by mayn on 2018/10/16.
 */
public class MyActivityInfo {
    public String activity_create_url;
    public String list_total;
    public String is_nextpage;
    public List<list> list;

    public static class list {
        public String id;
        public String title;
        public String class_id;
        public String thumb;
        public String start_time;
        public String end_time;
        public String price;
        public String now_sign_num;
        public String city_id;
        public String address;
        public String city_name;
        public String class_name;
        public String url;
        public String is_end;
        public String sign_url;
    }
}
