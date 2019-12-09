package courier.model;

import java.util.List;

/**
 * 入库记录
 * Created by Administrator on 2018/1/29.
 */
public class StorageLogInfo {
    public String hasmore;
    public String page_total;
    public datas datas;

    public static class datas {
        public List<list> list;

        public static class list {
            public String id;
            public String express_id;
            public String express_name;
            public String express_code;
            public String express_no;
            public String consignee;
            public String consignee_mobile;
            public String is_receivables;
            public String is_send;
            public String is_sendsms;
            public String sms_code;
            public String addtime;
            public String status;
            public String outtime;
            public String store_id;
            public String store_name;
            public String dlyp_id;
            public String dlyp_name;
            public String tuitime;
            public String is_sms;
            public String money;
            public String code;
        }
    }

}
