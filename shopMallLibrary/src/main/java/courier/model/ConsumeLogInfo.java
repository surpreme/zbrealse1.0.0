package courier.model;

import java.util.List;

/**
 * 消费记录
 * Created by Administrator on 2018/1/29.
 */
public class ConsumeLogInfo {
    public String hasmore;
    public String page_total;
    public datas datas;

    public static class datas {
        public List<list> list;

        public static class list {
            public String id;
            public String name;
            public String addtime;
            public String money;
            public String zong_money;
            public String store_id;
            public String store_name;
            public String dlyp_id;
            public String dlyp_address_name;
        }

    }

}
