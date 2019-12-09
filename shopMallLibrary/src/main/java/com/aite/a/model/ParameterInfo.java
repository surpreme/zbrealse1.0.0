package com.aite.a.model;

import java.util.List;

/**
 * 编辑参数
 * Created by Administrator on 2017/6/13.
 */
public class ParameterInfo {
    public List<param_class_list> param_class_list;

    public static class param_class_list {
        public String class_id;
        public String class_name;
        public String class_bind_type_id;
        public String class_sort;
        public List<param> param;

        public static class param {
            public String param_id;
            public String param_name;
            public String param_bind_class_id;
            public String param_sort;
            public String inputcontent="";
            public boolean ischoose=false;
        }

    }

}
