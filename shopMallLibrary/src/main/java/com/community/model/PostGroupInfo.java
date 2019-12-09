package com.community.model;

import java.util.List;

/**
 * 群組
 * Created by mayn on 2018/9/7.
 */
public class PostGroupInfo {
    public String list_total;
    public String is_nextpage;
    public String member_circle;
    public List<list> list;

    public static class list {
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

}
