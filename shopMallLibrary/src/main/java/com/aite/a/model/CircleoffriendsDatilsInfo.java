package com.aite.a.model;

import java.util.List;

/**
 * 朋友圈详情
 * Created by mayn on 2018/10/15.
 */
public class CircleoffriendsDatilsInfo {
    public theme_info theme_info;
    public List<theme_like> theme_like;
    public List<threply_list> threply_list;
    public String theme_like_url;
    public String theme_news_url;

    public static class theme_info {
        public String theme_id;
        public String theme_name;
        public String member_name;
        public String member_avatar;
        public String is_like;
        public List<String> thumb_list;
        public String theme_addtime;
        public String address;
    }

    public static class theme_like {
        public String id;
        public String trace_id;
        public String member_id;
        public String member_avatar;
        public String member_name;
    }

    public static class threply_list {
        public String theme_id;
        public String reply_id;
        public String member_id;
        public String member_name;
        public String reply_addtime;
        public String reply_content;
        public String member_avatar;

    }

}
