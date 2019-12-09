package livestream.mode;

import java.util.List;

/**
 * 房间信息
 * Created by mayn on 2018/12/27.
 */
public class LiveRoomDatailsInfo {
    public live_member_info live_member_info;
    public live_room_info live_room_info;
    public member_info member_info;

    public static class live_member_info {
        public String member_avatar;
        public String member_truename;
        public String member_name;
        public String member_level;
        public String region_info;
        public String room_number;
        public String follow_num;
        public String fans_num;
        public String get_gifts;
        public String send_gifts;
        public List<room_label> room_label;

        public static class room_label{
            public String mtag_id;
            public String mtag_name;
        }
    }

    public static class live_room_info {
        public String id;
        public String room_id;
        public String title;
        public String cover;
        public String room_class1;
        public String room_class_info1;
        public String room_class2;
        public String room_class_info2;
        public String room_class3;
        public String room_class_info3;
        public String look_num;
    }

    public static class member_info {
        public String member_avatar;
        public String member_name;
        public String member_truename;
        public String is_follow;
    }
}
