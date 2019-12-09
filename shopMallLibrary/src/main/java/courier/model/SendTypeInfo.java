package courier.model;

import java.util.List;

/**
 * 短信发送列表
 * Created by Administrator on 2018/1/30.
 */
public class SendTypeInfo {
    public String hasmore;
    public String page_total;
    public datas datas;

    public static class datas {
        public List<list> list;

        public static class list {
            public String id;
            public String order_id;
            public String phone;
            public String content;
            public String addtime;
            public String state;
            public String store_id;
            public String store_name;
        }
    }

}
