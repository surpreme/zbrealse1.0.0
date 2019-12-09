package courier.model;

import java.util.List;

/**
 * 客户列表
 * Created by Administrator on 2018/1/29.
 */
public class CustomerListInfo {
    public String type_shouzimu;
    public List<info>info;
    public static class info{
        public String id;
        public String dlyp_id;
        public String dlyp_name;
        public String customer_name;
        public String customer_mobile;
        public String customer_province_id;
        public String customer_city_id;
        public String customer_area_id;
        public String customer_town_id;
        public String is_vip;
        public String is_member;
        public String customer_member_id;
        public String type_shouzimu;
        public String img_path;
    }

}
