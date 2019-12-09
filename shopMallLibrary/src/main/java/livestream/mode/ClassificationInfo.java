package livestream.mode;

import java.util.List;

/**
 * 直播分类
 * Created by mayn on 2018/12/26.
 */
public class ClassificationInfo {
    public List<class_list> class_list;

    public static class class_list {
        public String gc_id;
        public String gc_name;
        public boolean ispick=false;
        public List<children1> children1;

        public static class children1 {
            public String gc_id;
            public String gc_name;
            public boolean ispick=false;
            public List<children2> children2;

            public static class children2 {
                public String gc_id;
                public String gc_name;
                public boolean ispick=false;
            }
        }
    }
}
