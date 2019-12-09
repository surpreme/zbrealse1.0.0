package com.community.adapter;

import java.util.List;

/**
 * 朋友圈首页
 * Created by mayn on 2018/9/7.
 */

public class CircleoffriendsInfo {
    public String list_total;
    public String is_nextpage;
    public List<list> list;

    public static class list {
        public String theme_id;
        public String is_like;
        public String theme_name;
        public String member_id;
        public String member_name;
        public String member_avatar;
        public String theme_addtime;
        public String url;
        public List<String> thumb_list;
        public List<reply_info> reply_info;

        public static class reply_info {
            public String member_name;
            public String reply_content;
        }
    }
}
