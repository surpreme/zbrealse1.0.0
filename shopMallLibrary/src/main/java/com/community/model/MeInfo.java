package com.community.model;

/**
 * 会员中心
 * Created by mayn on 2018/10/15.
 */
public class MeInfo {
    public member_info member_info;
    public String member_activity_url;
    public String member_cms_article_url;
    public String member_circle_url;
    public String member_circle_state_url;
    public String member_activity_order_url;
    public String member_index_url;

    public static class member_info {
        public String nmember_id;
        public String member_name;
        public String member_avatar;
        public String member_grade;
        public String activity_total;
        public String circle_total;
        public String cms_article_total;
    }
}
