package livestream.mode;

import java.util.List;

/**
 * 直播列表
 * Created by mayn on 2018/12/20.
 */
public class LiveBroadcastListInfo {
    public String hasmore;
    public String page_total;
    public datas datas;

    public static class datas {
        public List<list> list;

        public static class list {
            public String id;
            public String member_id;
            public String member_name;
            public String room_class;
            public String member_avatar;
            public String room_id;
            public String room_title;
            public String room_cover;
            public String status;
            public String status_str;
            public String look_num;
            public String play_url;
        }
    }
}
