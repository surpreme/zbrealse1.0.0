package com.community.model;

import java.util.List;

/**
 * 帖子列表
 * Created by mayn on 2018/9/19.
 */
public class ForumListInfo {
    public String list_total;
    public String is_nextpage;
    public String keyword_search;
    public List<list> list;

    public static class list {
        public String article_id;
        public String article_title;
        public String article_image;
        public List<String> article_image_all;
        public String article_publisher_name;
        public String article_publish_time;
        public String article_commend_flag;
        public String is_top;
        public String article_click;
        public String article_comment_count;
        public String article_attachment_path;
        public String url;
    }

}
