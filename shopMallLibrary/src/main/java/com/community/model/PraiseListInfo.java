package com.community.model;

import java.util.List;

/**
 * 点赞列表
 * Created by mayn on 2018/10/15.
 */
public class PraiseListInfo {
    public String list_total;
    public String is_nextpage;
    public List<list> list;

    public static class list {
        public String id;
        public String trace_id;
        public String member_id;
        public String member_name;
        public String membere_avatar;
    }
}
