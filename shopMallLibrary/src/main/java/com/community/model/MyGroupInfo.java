package com.community.model;

import java.util.List;

/**
 * 我的群组
 * Created by mayn on 2018/10/16.
 */
public class MyGroupInfo {
    public List<is_identity1> is_identity1;
    public List<is_identity1> is_identity2;
    public List<is_identity1> is_identity3;
    public String is_identity1_num;
    public String is_identity2_num;
    public String is_identity3__num;

    public static class is_identity1 {
        public String circle_id;
        public String cm_jointime;
        public String is_identity;
        public String circle_name;
        public String member_name;
        public String member_avatar;
    }

}
