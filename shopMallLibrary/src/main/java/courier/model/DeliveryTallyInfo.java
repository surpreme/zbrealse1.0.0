package courier.model;

import java.util.List;

/**
 * 记账
 * Created by Administrator on 2018/1/22.
 */
public class DeliveryTallyInfo {
    public String hasmore;
    public String page_total;
    public datas datas;

    public static class datas {
        public List<list> list;

        public static class list {
            public String express_id;
            public String express_name;
            public String store_id;
            public String store_name;
            public String addtime;
            public String delivery_num;
            public String ship_num;
            public String delivery_name;
            public String delivery_id;
        }
    }
}
