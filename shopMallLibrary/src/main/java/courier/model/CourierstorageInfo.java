package courier.model;

import java.util.List;

/**
 * 未入库列表
 * Created by Administrator on 2018/1/19.
 */
public class CourierstorageInfo {
    public String hasmore;
    public String page_total;
    public datas datas;

    public static class datas {
        public List<list> list;

        public static class list {
            public String order_id;
            public String addtime;
            public String order_sn;
            public String dlyp_id;
            public String shipping_code;
            public String express_code;
            public String express_name;
            public String reciver_name;
            public String reciver_telphone;
            public String reciver_mobphone;
            public String dlyo_state;
            public String dlyo_pickup_code;
        }
    }

}
