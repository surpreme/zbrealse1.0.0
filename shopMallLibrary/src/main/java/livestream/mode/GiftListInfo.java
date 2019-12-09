package livestream.mode;

import java.util.List;

/**
 * 礼物列表
 * Created by mayn on 2019/1/3.
 */
public class GiftListInfo {
    public datas datas;

    public static class datas {
        public List<gift_list> gift_list;

        public static class gift_list {
            public String id;
            public String gift_name;
            public String gift_image;
            public String introduce;
            public String need_money;
            public String need_points;
            public String is_show;
            public String add_time;
            public String gift_image_gif;
            public String sort;
            public boolean ischoose=false;
        }
    }
}
