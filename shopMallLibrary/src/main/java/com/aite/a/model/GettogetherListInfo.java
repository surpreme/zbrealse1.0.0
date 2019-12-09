package com.aite.a.model;

import java.util.List;

/**
 * 活动列表
 * Created by mayn on 2018/9/14.
 */
public class GettogetherListInfo {
    public String activity_create_url;
    public String list_total;
    public String is_nextpage;
    public List<class_list> class_list;
    public List<list> list;

    public static class class_list {
        public String class_id;
        public String class_name;
        public String class_parent_id;
        public String class_sort;
        public String thumb;
        public String deep;
        public String url;
    }

    public static class list {
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

}
