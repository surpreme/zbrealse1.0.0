package courier.model;

import java.util.List;

/**
 * 充值记录
 * Created by Administrator on 2018/1/30.
 */
public class TopUpLogInfo {
    public String hasmore;
    public String page_total;
    public datas datas;
    public static class datas{
        public List<list>list;

        public static class list{
            public String pdr_id;
            public String pdr_sn;
            public String pdr_member_id;
            public String pdr_member_name;
            public String pdr_amount;
            public String pdr_payment_code;
            public String pdr_payment_name;
            public String pdr_trade_sn;
            public String pdr_add_time;
            public String pdr_payment_state;
            public String pdr_payment_time;
            public String pdr_admin;
        }
    }

}
