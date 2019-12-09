package courier.model;

import java.util.List;

/**
 * 投递费用
 * Created by Administrator on 2018/1/30.
 */
public class EditCostInfo {
    public String hasmore;
    public String page_total;
    public datas datas;
    public static  class datas{
        public List<list>list;
        public static  class list{
            public String id;
            public String store_id;
            public String express_id;
            public String express_name;
            public String dlyp_id;
            public String dlyp_address_name;
            public String img_path;
            public String money;
        }
    }
}
