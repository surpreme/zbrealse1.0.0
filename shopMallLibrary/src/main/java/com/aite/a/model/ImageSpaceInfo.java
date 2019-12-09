package com.aite.a.model;

import java.io.File;
import java.util.List;

/**
 * 图片空间
 * Created by Administrator on 2017/5/4.
 */
public class ImageSpaceInfo {

    public String hasmore;
    public String page_total;
    public datas datas;
    public static class datas{
        public List<pic_list>pic_list;
        public List<class_list>class_list;

        public static class pic_list{
            public String apic_id;
            public String apic_name;
            public String apic_tag;
            public String aclass_id;
            public String apic_cover;
            public String apic_size;
            public String apic_spec;
            public String store_id;
            public String upload_time;
            public String full_path;

        }
        public static class class_list{
            public String aclass_id;
            public String aclass_name;
            public String store_id;
            public String aclass_des;
            public String aclass_sort;
            public String aclass_cover;
            public String upload_time;
            public String is_default;
            public String count;
        }
    }
}
