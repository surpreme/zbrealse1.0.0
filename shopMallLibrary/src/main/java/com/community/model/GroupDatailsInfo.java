package com.community.model;

import java.util.List;

/**
 * 群组详情
 * Created by mayn on 2018/9/18.
 */
public class GroupDatailsInfo {
    public circle_info circle_info;
    public List<circle_member_list> circle_member_list;
    public String join_url;

    public static class circle_info {
        public String circle_id;
        public String circle_name;
        public String circle_desc;
        public String circle_masterid;
        public String circle_mastername;
        public String circle_img;
        public String class_id;
        public String circle_mcount;
        public String circle_thcount;
        public String circle_gcount;
        public String circle_pursuereason;
        public String circle_notice;
        public String circle_status;
        public String circle_joinaudit;
        public String circle_addtime;
        public String circle_noticetime;
        public String is_recommend;
        public String is_hot;
        public String circle_tag;
        public String new_verifycount;
        public String new_informcount;
        public String mapply_open;
        public String mapply_ml;
        public String new_mapplycount;
        public String circle_type;
        public String hx_groupid;
        public String circle_back_img;
        public String circle_master_avatar;
        public String member_count;
        public List<String> circle_tag_arr;
        public String circle_member;
    }

    public static class circle_member_list {
        public String member_id;
        public String member_name;
        public String member_avatar;
    }
}
