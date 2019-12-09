package com.aite.a.model;

import java.util.List;

/**
 * 商品分类
 * Created by Administrator on 2017/5/4.
 */
public class GoodsClassInfo {
    public List<goods_class> goods_class;

    public static class goods_class {
        public String gc_id;
        public String gc_name;
        public String type_id;
        public String type_name;
        public String gc_parent_id;
        public String commis_rate;
        public String gc_sort;
        public String gc_virtual;
        public String gc_title;
        public String gc_keywords;
        public String gc_description;
        public List<class2> class2;

        public static class class2 {
            public String gc_id;
            public String gc_name;
            public String type_id;
            public String type_name;
            public String gc_parent_id;
            public String commis_rate;
            public String gc_sort;
            public String gc_virtual;
            public String gc_title;
            public String gc_keywords;
            public String gc_description;
            public List<class3> class3;

            public static class class3 {
                public String gc_id;
                public String gc_name;
                public String type_id;
                public String type_name;
                public String gc_parent_id;
                public String commis_rate;
                public String gc_sort;
                public String gc_virtual;
                public String gc_title;
                public String gc_keywords;
                public String gc_description;
            }
        }
    }

}
