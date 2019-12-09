package courier.model;

import java.util.List;

/**
 * 通知列表
 * Created by Administrator on 2018/1/22.
 */
public class NoticeInfo {
    public String hasmore;
    public String page_total;
    public datas datas;
    public static class datas{
        public List<list> list;
        public static class list{
            public String id;
            public String title;
            public String content;
            public String addtime;
            public String is_show;
            public String type;
        }
    }


}
